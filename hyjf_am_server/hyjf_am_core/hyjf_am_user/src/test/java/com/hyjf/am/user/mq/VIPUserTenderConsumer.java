package com.hyjf.am.user.mq;

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
import com.hyjf.am.user.mq.base.Consumer;
import com.hyjf.am.user.service.UserService;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.validator.Validator;
/**
 * 保存vip用户信息mq消费
 * @author jun
 * @date 20180708
 */
@Component
public class VIPUserTenderConsumer extends Consumer {

	private static final Logger logger = LoggerFactory.getLogger(VIPUserTenderConsumer.class);
	@Autowired
	private UserService userService;
	
	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup(MQConstant.VIP_USER_TENDER_GROUP);
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe(MQConstant.VIP_USER_TENDER_TOPIC, "*");
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.registerMessageListener(new MessageListener());
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可
		defaultMQPushConsumer.start();
		logger.info("====VIPUserTenderConsumer start=====");
	}
	
	
	
	
	public class MessageListener implements MessageListenerConcurrently {
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			logger.info("VIPUserTenderConsumer 收到消息，开始处理....");
			MessageExt msg = msgs.get(0);
			JSONObject para = JSONObject.parseObject(msg.getBody(), JSONObject.class);
			if(Validator.isNotNull(para)) {
				try {
					userService.insertVipUserTender(para);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("消费失败，保存VIP用户信息发生异常...");	
				}	
			}else {
				logger.error("消费失败，未获取到VIP用户信息...");
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}
			// 如果没有return success ，consumer会重新消费该消息，直到return success
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}

}
