/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.consumer;

import java.util.List;
import java.util.UUID;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.mq.base.Consumer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.MailProducer;
import com.hyjf.am.trade.service.BatchBorrowRepayZTService;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;

/**
 * @author dxj
 * @version BorrowRepayZTConsumer.java, v0.1 2018年6月20日 下午6:09:19
 */
public class BorrowRepayZTConsumer extends Consumer{
	
	private static final Logger logger = LoggerFactory.getLogger(BorrowRepayZTConsumer.class);

	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup(MQConstant.BORROW_GROUP);
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe(MQConstant.BORROW_REPAY_ZT_RESULT_TOPIC, "*");
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.registerMessageListener(new MessageListener());
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		defaultMQPushConsumer.start();
		logger.info("====还款业务消费结束=====");
	}

	public class MessageListener implements MessageListenerConcurrently {
	    
	    @Autowired
	    BatchBorrowRepayZTService batchBorrowRepayZTService;

	    @Autowired
	    SystemConfig systemConfig;
	    
		@Autowired
		private MailProducer mailProducer;
		
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			logger.info("还款请求 收到消息，开始处理....");
	        BorrowApicron borrowApicron;
	        
	        try {
				MessageExt msg = msgs.get(0);
	            borrowApicron = JSONObject.parseObject(msg.getBody(), BorrowApicron.class);
	            if(Validator.isNull(borrowApicron) || borrowApicron.getBorrowNid() == null){
	            	return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	            }
	        } catch (Exception e1) {
				logger.error(e1.getMessage());
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	        }
	        
	        
	        String borrowNid = borrowApicron.getBorrowNid();// 借款编号
			int borrowUserId = borrowApicron.getUserId();// 借款人userId
			String batchNo = borrowApicron.getBatchNo();
			String txDate = Validator.isNotNull(borrowApicron.getTxDate()) ? String.valueOf(borrowApicron.getTxDate()) : null;
			logger.info("标的编号："+borrowNid+"，直投类开始还款！");
	        // 生成任务key 校验并发请求
	        String redisKey = RedisConstants.ZHITOU_REPAY_TASK + ":" + borrowApicron.getBorrowNid() + "_" + borrowApicron.getPeriodNow();
	        boolean result = RedisUtils.tranactionSet(redisKey, 300);
	        if(!result){
	            logger.error("直投类还款请求中....");
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	        }
	        
	        try{
	        	logger.info("标的编号："+borrowNid+"，请求成功或校验成功。。。");
				// 如果已经发生过相应的还款请求，则查询相应的状态
				if (StringUtils.isBlank(batchNo) || StringUtils.isBlank(txDate)) {
					throw new Exception("参数信息不全");
				}
				String bankSeqNo = borrowApicron.getBankSeqNo();// 还款序列号
				// 查询批次还款状态
				BankCallBean batchResult = this.batchBorrowRepayZTService.batchQuery(borrowApicron);
				if (Validator.isNull(batchResult)) {
					throw new Exception("还款状态查询失败！[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
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
					logger.info("标的编号："+borrowNid+"，批次处理失败");
					String failMsg = batchResult.getFailMsg();// 失败原因
					if (StringUtils.isNotBlank(failMsg)) {
						borrowApicron.setData(failMsg);
						borrowApicron.setFailTimes(borrowApicron.getFailTimes() + 1);
						// 更新任务API状态
						boolean apicronResultFlag = this.batchBorrowRepayZTService.updateBorrowApicron(borrowApicron, CustomConstants.BANK_BATCH_STATUS_FAIL);
						if (!apicronResultFlag) {
							throw new Exception("更新状态为（还款请求失败）失败。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
						}
					} else {
						// 查询批次交易明细，进行后续操作
						boolean batchDetailFlag = this.batchBorrowRepayZTService.updateBatchDetailsQuery(borrowApicron);
						// 进行后续失败的还款的还款请求
						if (!batchDetailFlag) {
							throw new Exception("还款失败后，查询还款明细失败。[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
						}
					}
				}
				// 如果是批次处理成功
				else if (batchState.equals(BankCallConstant.BATCHSTATE_TYPE_SUCCESS)) {
					logger.info("标的编号："+borrowNid+"，批次处理成功");
					// 查询批次交易明细，进行后续操作
					boolean batchDetailFlag = this.batchBorrowRepayZTService.updateBatchDetailsQuery(borrowApicron);
					logger.info("标的编号："+borrowNid+"，查询批次交易明细，进行后续操作，操作结果："+batchDetailFlag);
					if (!batchDetailFlag) {
						throw new Exception("还款成功后，查询还款明细失败。[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
					}
					logger.info("标的编号："+borrowNid+"，还款成功！");
				}
				
	        }catch(Exception e){
	        	StringBuffer sbError = new StringBuffer();// 错误信息
	        	sbError.append(e.getMessage()).append("<br/>");
	        	String online = "生产环境";// 取得是否线上
	        	if (systemConfig.isEnvTest()) {
	        		online = "测试环境";
	        	}
	        	// 发送错误邮件
	        	StringBuffer msg = new StringBuffer();
	        	msg.append("借款标号：").append(borrowApicron.getBorrowNid()).append("<br/>");
	        	msg.append("还款时间：").append(GetDate.formatTime()).append("<br/>");
	        	msg.append("错误信息：").append(e.getMessage()).append("<br/>");
	        	msg.append("详细错误信息：<br/>").append(sbError.toString());
	        	String[] toMail = new String[] {};
	        	if ("测试环境".equals(online)) {
	        		toMail = new String[] { "jiangying@hyjf.com", "liudandan@hyjf.com" };
	        	} else {
	        		toMail = new String[] { "sunjijin@hyjf.com", "gaohonggang@hyjf.com","zhangjinpeng@hyjf.com" };
	        	}
	        	MailMessage mailMessage = new MailMessage(null, null, "[" + online + "] " + borrowApicron.getBorrowNid(), msg.toString(), null, toMail, null,
	        			MessageConstant.MAILSENDFORMAILINGADDRESSMSG);

				try {
					mailProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(mailMessage)));
				} catch (MQException e2) {
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
