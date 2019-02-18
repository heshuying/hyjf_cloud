/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.front.consumer.BatchBorrowRepayZTService;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 直投类标的还款消息处理
 * @author dxj
 * @version BorrowRepayZTConsumer.java, v0.1 2018年6月20日 下午6:09:19
 */
@Service
@RocketMQMessageListener(topic = MQConstant.BORROW_REPAY_ZT_RESULT_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.BORROW_REPAY_ZT_RESULT_GROUP)
public class BorrowRepayZTConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
	
	private static final Logger logger = LoggerFactory.getLogger(BorrowRepayZTConsumer.class);
    
    @Autowired
    private BatchBorrowRepayZTService batchBorrowRepayZTService;

    @Autowired
    private SystemConfig systemConfig;
    
	@Autowired
	private CommonProducer commonProducer;

	@Override
	public void onMessage(MessageExt messageExt) {
		logger.info("【直投还款】收到消息，开始处理...");
		BorrowApicron borrowApicron;
		try{
			try {
				MessageExt msg = messageExt;
				borrowApicron = JSONObject.parseObject(msg.getBody(), BorrowApicron.class);
				if(borrowApicron == null || borrowApicron.getId() == null || StringUtils.isBlank(borrowApicron.getBorrowNid())
						|| borrowApicron.getTxDate() == null || StringUtils.isBlank(borrowApicron.getBatchNo()) ){
					logger.error("【直投还款】收到消息，信息不全！");
					return;
				}
			} catch (Exception e1) {
				logger.error("【直投还款】收到消息，解析异常！", e1);
				return;
			}
			String borrowNid = borrowApicron.getBorrowNid();// 借款编号
			int borrowUserId = borrowApicron.getUserId();// 借款人userId
			logger.info("【直投还款】借款编号：{}，开始还款。", borrowNid);
			// 生成任务key 校验并发请求
			String redisKey = RedisConstants.ZHITOU_REPAY_TASK + ":" + borrowApicron.getBorrowNid() + "_" + borrowApicron.getPeriodNow();
			boolean result = RedisUtils.tranactionSet(redisKey, 300);
			if(!result){
				logger.error("【直投还款】借款编号：{}，还款请求中...", borrowNid);
				return;
			}
			try{
				// 查询防止重复
				borrowApicron = batchBorrowRepayZTService.selApiCronByPrimaryKey(borrowApicron.getId());
				// 如果已经发生过相应的还款请求，则查询相应的状态
				if (borrowApicron.getStatus() == 6) {
					logger.error("【直投还款】借款编号：{}，还款已经成功，状态有误！", borrowNid);
					return;
				}
				String bankSeqNo = borrowApicron.getBankSeqNo();// 还款序列号
				// 查询批次还款状态
				BankCallBean batchResult = batchBorrowRepayZTService.batchQuery(borrowApicron);
				if (batchResult == null) {
					throw new Exception("调用接口查询还款状态失败！[银行唯一订单号：" + bankSeqNo + "]，[借款编号：" + borrowNid + "]");
				}
				logger.info("【直投还款】借款编号：{}，批次查询成功！", borrowNid);
				// 批次还款返回码
				String retCode = StringUtils.isNotBlank(batchResult.getRetCode()) ? batchResult.getRetCode() : "";
				if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
					String retMsg = batchResult.getRetMsg();
					throw new Exception("还款状态查询失败！银行返回信息：" + retMsg + "，[银行唯一订单号：" + bankSeqNo + "]，[借款编号：" + borrowNid + "]");
				}
				// 批次还款状态
				String batchState = batchResult.getBatchState();
				if (StringUtils.isBlank(batchState)) {
					throw new Exception("还款状态查询失败！[银行唯一订单号：" + bankSeqNo + "]，[借款编号：" + borrowNid + "]");
				}
				logger.info("【直投还款】借款编号：{}，批次查询状态：{}", borrowNid, batchState);
				// 如果是批次处理失败
				if (batchState.equals(BankCallConstant.BATCHSTATE_TYPE_FAIL)) {
					// 失败原因
					String failMsg = batchResult.getFailMsg();
					if (StringUtils.isNotBlank(failMsg)) {
						logger.error("【直投还款】借款编号：{}，批次处理失败：{}", borrowNid, failMsg);
						borrowApicron.setData(failMsg);
						borrowApicron.setFailTimes(borrowApicron.getFailTimes() + 1);
						// 更新任务API状态
						boolean apicronResultFlag = batchBorrowRepayZTService.updateBorrowApicron(borrowApicron, CustomConstants.BANK_BATCH_STATUS_FAIL);
						if (!apicronResultFlag) {
							throw new Exception("批次还款任务表(ht_borrow_apicron)更新状态(还款失败)失败！[用户ID：" + borrowUserId + "]，[借款编号：" + borrowNid + "]");
						}
					} else {
						logger.info("【直投还款】借款编号：{}，未返回批次处理失败原因，尝试更新还款明细数据。", borrowNid);
						// 查询批次交易明细，进行后续操作
						int batchDetailStatus = batchBorrowRepayZTService.reapyBatchDetailsUpdate(borrowApicron);
						// 进行后续失败的还款的还款请求
						if (CustomConstants.BANK_BATCH_STATUS_SUCCESS != batchDetailStatus) {
							String statusStr = CustomConstants.BANK_BATCH_STATUS_PART_FAIL == batchDetailStatus ? "部分成功" : "失败";
							throw new Exception("批次查询未返回失败原因，还款明细更新" + statusStr + "。[银行唯一订单号：" + bankSeqNo + "]，[借款编号：" + borrowNid + "]");
						}
					}
				}
				// 如果是批次处理成功
				else if (batchState.equals(BankCallConstant.BATCHSTATE_TYPE_SUCCESS)) {
					logger.info("【直投还款】借款编号：{}，批次处理状态查询成功，开始还款明细数据更新。", borrowNid);
					// 查询批次交易明细，进行后续操作
					int batchDetailStatus = batchBorrowRepayZTService.reapyBatchDetailsUpdate(borrowApicron);
					logger.info("【直投还款】借款编号：{}，还款明细数据更新操作结果：{}", borrowNid, batchDetailStatus);
					if (CustomConstants.BANK_BATCH_STATUS_SUCCESS != batchDetailStatus) {
						String statusStr = CustomConstants.BANK_BATCH_STATUS_PART_FAIL == batchDetailStatus ? "部分成功" : "失败";
						throw new Exception("批次查询成功后，还款明细更新" + statusStr + "。[银行唯一订单号：" + bankSeqNo + "]，[借款编号：" + borrowNid + "]");
					}
					try {
						JSONObject param = new JSONObject();
						param.put("borrowNid", borrowApicron.getBorrowNid());
						param.put("repayPeriod", borrowApicron.getPeriodNow());
						commonProducer.messageSendDelay(new MessageContent(MQConstant.NIFA_REPAY_INFO_TOPIC,UUID.randomUUID().toString(),param),2);
					} catch (Exception e) {
						logger.error("【直投还款】发送mq到生成互金还款相关信息失败，放款标的:{}", borrowNid);
					}
					try {
						// add 合规数据上报 埋点 liubin 20181122 start
						JSONObject params = new JSONObject();
						params.put("borrowNid", borrowApicron.getBorrowNid());
						params.put("repayPeriod", borrowApicron.getPeriodNow());
						// 推送数据到MQ 还款（每期）
						commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.REPAY_SINGLE_SUCCESS_TAG, UUID.randomUUID().toString(), params),
								MQConstant.HG_REPORT_DELAY_LEVEL);

						// 最后一期
						if(borrowApicron.getBorrowPeriod().equals(borrowApicron.getPeriodNow())){
							params = new JSONObject();
							params.put("borrowNid", borrowApicron.getBorrowNid());
							params.put("flag", "1"); //1（散）2（智投）
							// status=5标的已还款
							params.put("status", "5"); //5(标的已还款)
							commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.REPAY_ALL_SUCCESS_TAG, UUID.randomUUID().toString(), params),
									MQConstant.HG_REPORT_DELAY_LEVEL);
						}
					} catch (Exception e) {
						logger.error("【直投还款】借款编号：{}，合规数据上报发生系统异常！", borrowNid, e);
					}
					// add 合规数据上报 埋点 liubin 20181122 end
                    logger.info("【直投还款】借款编号：{}，还款成功！", borrowNid);
				}
			} catch (Exception e) {
				logger.error("【直投还款】还款发生系统异常！", e);
				StringBuffer sbError = new StringBuffer();// 错误信息
				sbError.append(e.getMessage()).append("<br/>");
				String online = "生产环境";// 取得是否线上
				String toMail[] = systemConfig.getLoadRepayMailAddrs();
				if (systemConfig.isEnvTest()) {
					online = "测试环境";
				}
				// 发送错误邮件
				StringBuffer msg = new StringBuffer();
				msg.append("借款标号：").append(borrowApicron.getBorrowNid()).append("<br/>");
				msg.append("还款时间：").append(GetDate.formatTime()).append("<br/>");
				msg.append("错误信息：").append(e.getMessage()).append("<br/>");
				msg.append("详细错误信息：<br/>").append(sbError.toString());

				try {
					if(toMail == null) {
						throw new Exception("未配置收件人！[借款编号：" + borrowNid + "]");
					}
					MailMessage mailMessage = new MailMessage(null, null, "[" + online + "] " + borrowApicron.getBorrowNid(), msg.toString(), null, toMail, null,
							MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS_MSG);
					commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, borrowApicron.getBorrowNid(), mailMessage));
				} catch (Exception e2) {
					logger.error("【直投还款】发送邮件失败..", e2);
				}
				// 消息队列指令不消费
				return;
			}
			RedisUtils.del(redisKey);
			// 如果没有return success ，consumer会重新消费该消息，直到return success
		} catch (Exception e) {
			logger.error("【直投还款】消费异常!", e);
			return;
		}
		return;
	}

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		// 设置并发数
		defaultMQPushConsumer.setConsumeThreadMin(1);
		defaultMQPushConsumer.setConsumeThreadMax(1);
		defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);
		defaultMQPushConsumer.setConsumeTimeout(30);
		logger.info("====直投还款业务消费启动=====");
	}
}
