/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq;

import java.util.List;

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
import com.hyjf.am.trade.service.RealTimeBorrowLoanService;
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

/**
 * @author dxj
 * @version BorrowLoanRealTimeConsumer.java, v0.1 2018年6月20日 下午6:09:19
 */
public class BorrowLoanRealTimeConsumer extends Consumer {

	private static final Logger logger = LoggerFactory.getLogger(BorrowLoanRealTimeConsumer.class);

	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup(MQConstant.BORROW_GROUP);
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe(MQConstant.BORROW_REALTIMELOAN_ZT_REQUEST_TOPIC, "*");
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.registerMessageListener(new MessageListener());
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		defaultMQPushConsumer.start();
		logger.info("====放款业务消费结束=====");
	}

	public class MessageListener implements MessageListenerConcurrently {

	    @Autowired
		RealTimeBorrowLoanService batchLoanService;

	    @Autowired
	    SystemConfig systemConfig;
	    
		@Autowired
		private MailProducer mailProducer;
	    
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			logger.info("直投放款请求 收到消息，开始处理....");
			BorrowApicron borrowApicron;
			
			try {
				MessageExt msgD = msgs.get(0);
				borrowApicron = JSONObject.parseObject(msgD.getBody(), BorrowApicron.class);
	            if(Validator.isNull(borrowApicron)){
	            	return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	            }
	        } catch (Exception e1) {
	            e1.printStackTrace();
	            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	        }

			String borrowNid = borrowApicron.getBorrowNid();// 借款编号
			int borrowUserId = borrowApicron.getUserId();// 借款人userId
			int loanStatus = borrowApicron.getStatus();// 放款状态
			Integer failTimes = borrowApicron.getFailCounts();
			// 生成任务key 校验并发请求
			String redisKey = RedisConstants.ZHITOU_LOAN_TASK + ":" + borrowApicron.getBorrowNid() + "_"
					+ borrowApicron.getPeriodNow();
			
			try {
				
				logger.info("标的编号：" + borrowNid + "，开始实时放款！");
				boolean result = RedisUtils.tranactionSet(redisKey, 300);
				if (!result) {
					logger.error("直投类放款请求中....");
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
				
				// 如果放款状态为请求中
				if (loanStatus == CustomConstants.BANK_BATCH_STATUS_SENDING) {
					// 发送放款
					BankCallBean requestLoanBean = this.batchLoanService.requestLoans(borrowApicron);
					if (requestLoanBean == null) {
						borrowApicron.setFailTimes(borrowApicron.getFailTimes() + 1);
						// 放款失败处理
						boolean batchDetailFlag = this.batchLoanService.updateBatchDetailsQuery(borrowApicron,requestLoanBean);
						if (!batchDetailFlag) {
							throw new Exception("放款成功后，变更放款数据失败。" + "[借款编号：" + borrowNid + "]");
						}
						boolean apicronResultFlag = this.batchLoanService.updateBorrowApicron(borrowApicron,
								CustomConstants.BANK_BATCH_STATUS_FAIL);
						if (apicronResultFlag) {
							throw new Exception(
									"更新状态为（放款请求失败）失败。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
						} else {
							throw new Exception("放款失败,[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
						}
					} else {// 放款成功
							// 进行后续操作
						boolean batchDetailFlag = this.batchLoanService.updateBatchDetailsQuery(borrowApicron,
								requestLoanBean);
						if (!batchDetailFlag) {
							throw new Exception("放款成功后，变更放款数据失败。" + "[借款编号：" + borrowNid + "]");
						}
						// 放款成功,更新mongo运营数据
						logger.info("放款成功更新运营数据...");
						JSONObject params = new JSONObject();
						params.put("type", 1);// 散标
						params.put("money", borrowApicron.getBorrowAccount());
						
						//TODO: 运营数据的统计队列，暂时不加
//						rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_COUPON,
//								RabbitMQConstants.ROUTINGKEY_OPERATION_DATA, JSONObject.toJSONString(params));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringBuffer sbError = new StringBuffer();// 错误信息
				sbError.append(e.getMessage()).append("<br/>");
				String online = "生产环境";// 取得是否线上
				if (systemConfig.isEnvTest()) {
					online = "测试环境";
				}
				// 发送错误邮件
				StringBuffer msg = new StringBuffer();
				msg.append("借款标号：").append(borrowNid).append("<br/>");
				msg.append("放款时间：").append(GetDate.formatTime()).append("<br/>");
				msg.append("执行次数：").append("第" + failTimes + "次").append("<br/>");
				msg.append("错误信息：").append(e.getMessage()).append("<br/>");
				msg.append("详细错误信息：<br/>").append(sbError.toString());
				String[] toMail = new String[] {};
				if ("测试环境".equals(online)) {
					toMail = new String[] { "jiangying@hyjf.com", "liudandan@hyjf.com" };
				} else {
					toMail = new String[] { "sunjijin@hyjf.com", "gaohonggang@hyjf.com", };
				}
				MailMessage mailmessage = new MailMessage(null, null,
						"[" + online + "] " + borrowNid + " 第" + failTimes + "次放款失败", msg.toString(), null, toMail,
						null, MessageConstant.MAILSENDFORMAILINGADDRESSMSG);

				try {
					mailProducer.messageSend(new Producer.MassageContent(MQConstant.MAIL_TOPIC, JSON.toJSONBytes(mailmessage)));
				} catch (MQException e2) {
					logger.error("发送邮件失败..", e2);
				}
				
			}
			logger.info("--------------------------------------放款任务结束，项目编号：" + borrowNid + "=============");
			RedisUtils.del(redisKey);
			logger.info("----------------------------直投放款结束--------------------------------");

			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}

}
