/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.mq.base.Consumer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.MailProducer;
import com.hyjf.am.trade.mq.producer.nifa.NifaRepayInfoMessageProducer;
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
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * 直投类标的还款消息处理
 * @author dxj
 * @version BorrowRepayZTConsumer.java, v0.1 2018年6月20日 下午6:09:19
 */
@Component
//@Profile("test")
public class BorrowRepayZTConsumer extends Consumer{
	
	private static final Logger logger = LoggerFactory.getLogger(BorrowRepayZTConsumer.class);
    
    @Autowired
    BatchBorrowRepayZTService batchBorrowRepayZTService;

    @Autowired
    SystemConfig systemConfig;
    
	@Autowired
	private MailProducer mailProducer;

	@Autowired
	NifaRepayInfoMessageProducer nifaRepayInfoMessageProducer;

	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
//		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup(MQConstant.BORROW_REPAY_ZT_RESULT_GROUP);
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe(MQConstant.BORROW_REPAY_ZT_RESULT_TOPIC, "*");
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
		
		defaultMQPushConsumer.registerMessageListener(new MessageListener());
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		defaultMQPushConsumer.start();
		logger.info("====直投还款业务消费启动=====");
	}

	public class MessageListener implements MessageListenerConcurrently {
		
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			logger.info("直投类还款请求 收到消息，开始处理...."+msgs.size());
	        BorrowApicron borrowApicron;
	        
	        try {
				MessageExt msg = msgs.get(0);
	            borrowApicron = JSONObject.parseObject(msg.getBody(), BorrowApicron.class);
	            if(borrowApicron == null || borrowApicron.getId() == null || StringUtils.isBlank(borrowApicron.getBorrowNid())
	            		|| borrowApicron.getTxDate() == null || StringUtils.isBlank(borrowApicron.getBatchNo()) ){
	            	logger.info("直投还款请求 收到消息，解析为空");
	            	return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	            }
	        } catch (Exception e1) {
				logger.error(e1.getMessage());
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	        }
	        
	        
	        String borrowNid = borrowApicron.getBorrowNid();// 借款编号
			int borrowUserId = borrowApicron.getUserId();// 借款人userId
			logger.info("标的编号："+borrowNid+"，直投类开始还款！");
	        // 生成任务key 校验并发请求
	        String redisKey = RedisConstants.ZHITOU_REPAY_TASK + ":" + borrowApicron.getBorrowNid() + "_" + borrowApicron.getPeriodNow();
	        boolean result = RedisUtils.tranactionSet(redisKey, 300);
	        if(!result){
	            logger.error("直投类还款请求中....");
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	        }
	        
	        try{

				// 查询防止重复
				borrowApicron = batchBorrowRepayZTService.selApiCronByPrimaryKey(borrowApicron.getId());
				// 如果已经发生过相应的还款请求，则查询相应的状态
				if (borrowApicron.getStatus() == 6) {
					throw new Exception(borrowNid+" 还款已经成功，状态有误");
				}
				
				String bankSeqNo = borrowApicron.getBankSeqNo();// 还款序列号
				// 查询批次还款状态
				BankCallBean batchResult = batchBorrowRepayZTService.batchQuery(borrowApicron);
				if (batchResult == null) {
					throw new Exception("调用接口查询还款状态失败！[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
				}
				logger.info("标的编号："+borrowNid+"，批次查询成功！");
				// 批次还款返回码
				String retCode = StringUtils.isNotBlank(batchResult.getRetCode()) ? batchResult.getRetCode() : "";
				if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
					String retMsg = batchResult.getRetMsg();
					throw new Exception("还款状态查询失败！银行返回信息：" + retMsg + ",[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
				}
				// 批次还款状态
				String batchState = batchResult.getBatchState();
				if (StringUtils.isBlank(batchState)) {
					throw new Exception("还款状态查询失败！[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
				}
				
				logger.info("标的编号："+borrowNid+"，批次查询状态："+batchState);
				// 如果是批次处理失败
				if (batchState.equals(BankCallConstant.BATCHSTATE_TYPE_FAIL)) {
					// 失败原因
					String failMsg = batchResult.getFailMsg();
					logger.info("标的编号："+borrowNid+"，批次处理失败: "+failMsg);
					
					if (StringUtils.isNotBlank(failMsg)) {
						borrowApicron.setData(failMsg);
						borrowApicron.setFailTimes(borrowApicron.getFailTimes() + 1);
						// 更新任务API状态
						boolean apicronResultFlag = batchBorrowRepayZTService.updateBorrowApicron(borrowApicron, CustomConstants.BANK_BATCH_STATUS_FAIL);
						if (!apicronResultFlag) {
							throw new Exception("更新状态为（还款请求失败）失败。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
						}
					} else {
						// 查询批次交易明细，进行后续操作
						boolean batchDetailFlag = batchBorrowRepayZTService.reapyBatchDetailsUpdate(borrowApicron);
						// 进行后续失败的还款的还款请求
						if (!batchDetailFlag) {
							throw new Exception("还款失败后，查询还款明细失败。[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
						}
					}
				}
				// 如果是批次处理成功
				else if (batchState.equals(BankCallConstant.BATCHSTATE_TYPE_SUCCESS)) {
					logger.info("标的编号："+borrowNid+"，批次处理状态查询成功，开始明细数据更新");
					// 查询批次交易明细，进行后续操作
					boolean batchDetailFlag = batchBorrowRepayZTService.reapyBatchDetailsUpdate(borrowApicron);
					logger.info("标的编号："+borrowNid+"，查询批次交易明细，进行后续操作，操作结果："+batchDetailFlag);
					if (!batchDetailFlag) {
						throw new Exception("还款成功后，查询还款明细失败。[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
					}
					try {
						JSONObject param = new JSONObject();
						param.put("borrowNid", borrowApicron.getBorrowNid());
						param.put("borrowNid", borrowApicron.getPeriodNow());
						nifaRepayInfoMessageProducer.messageSend(new MessageContent(MQConstant.NIFA_REPAY_INFO_TOPIC,UUID.randomUUID().toString(),JSON.toJSONBytes(param)));
					} catch (Exception e) {
						logger.error("发送mq到生成互金还款相关信息失败,放款标的:" + borrowApicron.getBorrowNid());
					}
					logger.info("标的编号："+borrowNid+"，还款成功！");
				}
				
	        }catch(Exception e){
	        	
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
						throw new Exception("错误收件人没有配置。" + "[借款编号：" + borrowNid + "]");
					}
		        	MailMessage mailMessage = new MailMessage(null, null, "[" + online + "] " + borrowApicron.getBorrowNid(), msg.toString(), null, toMail, null,
		        			MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
					mailProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, borrowApicron.getBorrowNid(), JSON.toJSONBytes(mailMessage)));
				} catch (Exception e2) {
					logger.error("发送邮件失败..", e2);
				}
	        	// 消息队列指令不消费
	            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	        }
			RedisUtils.del(redisKey);
			logger.info("----------------------------直投还款结束--------------------------------");
			// 如果没有return success ，consumer会重新消费该消息，直到return success
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}
}
