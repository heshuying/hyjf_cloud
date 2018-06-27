package com.hyjf.cs.trade.mq;

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
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.fdd.fddgeneratecontract.FddGenerateContractBean;
import com.hyjf.cs.trade.handle.FddHandle;

/**
 * 法大大consumer
 * 
 * @author jijun
 * @version 20180627
 */

@Component
public class FddConsumer extends Consumer {

	private static final Logger logger = LoggerFactory.getLogger(FddConsumer.class);

	@Autowired
	private FddHandle fddHandle;

	@Autowired
	private FddProducer fddProducer;

	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup(MQConstant.FDD_GROUP);
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe(MQConstant.FDD_TOPIC, "*");
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.registerMessageListener(new MessageListener());
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		defaultMQPushConsumer.start();
		logger.info("====法大大 consumer=====");
	}

	public class MessageListener implements MessageListenerConcurrently {
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			logger.info("法大大 收到消息，开始处理....msgs is :{}", msgs);

			for (MessageExt msg : msgs) {
				if (MQConstant.FDD_GENERATE_CONTRACT_TAG.equals(msg.getTags())) {
					// 生成合同
					logger.info("----------------------开始生成法大大合同------------------------");
					// 订单号
					String orderId = null;
					// 交易类型 1:散标 2：计划加入 3：直投债转 4：计划债转
					Integer transType = null;
					try {
						FddGenerateContractBean bean = JSONObject.parseObject(msg.getBody(),
								FddGenerateContractBean.class);
						if (Validator.isNull(bean)) {
							logger.info("法大大为空！");
							throw new RuntimeException("传入参数不得为空！");
						}
						orderId = bean.getOrdid();
						transType = bean.getTransType();
						if (Validator.isNull(orderId) && Validator.isNull(transType)) {
							throw new RuntimeException("传入参数不得为空！");
						}
						if (FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == transType) {// 散标投资
							fddHandle.tenderGenerateContract(bean);
						} else if (FddGenerateContractConstant.PROTOCOL_TYPE_PLAN == transType) {// 计划加入
							fddHandle.planJoinGenerateContract(bean);
						} else if (FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT == transType) {// 债转投资
							fddHandle.creditGenerateContract(bean);
						} else if (FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET == transType) {// 计划债转
							fddHandle.planCreditGenerateContract(bean);
						}

					} catch (Exception e) {
						logger.info("=============生成法大大合同任务异常，订单号：" + orderId + ",错误信息：" + e.getMessage()
								+ "=============");
						return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
					}finally {
						logger.info("--------------------------------------生成法大大合同任务结束，订单号：" + orderId + "=============");
					}

				} else if (MQConstant.FDD_AUTO_SIGN_TAG.equals(msg.getTags())) {
					// 自动签署

				} else if (MQConstant.FDD_DOWNPDF_AND_DESSENSITIZATION_TAG.equals(msg.getTags())) {
					// 下载脱敏

				}
			}
			// 如果没有return success ，consumer会重新消费该消息，直到return success
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}
}
