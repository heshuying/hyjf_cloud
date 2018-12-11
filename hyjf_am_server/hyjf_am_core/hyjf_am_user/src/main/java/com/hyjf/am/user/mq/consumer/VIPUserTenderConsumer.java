package com.hyjf.am.user.mq.consumer;

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

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.service.front.user.UserService;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.validator.Validator;

/**
 * 保存vip用户信息mq消费
 * @author jun
 * @date 20180708
 */
@Service
@RocketMQMessageListener(topic = MQConstant.VIP_USER_TENDER_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.VIP_USER_TENDER_GROUP)
public class VIPUserTenderConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

	private static final Logger logger = LoggerFactory.getLogger(VIPUserTenderConsumer.class);
	@Autowired
	private UserService userService;
	
	
	@Override
	public void onMessage(MessageExt  message) {
		logger.info("VIPUserTenderConsumer 收到消息，开始处理....");
		MessageExt msg = message;
		JSONObject para = JSONObject.parseObject(msg.getBody(), JSONObject.class);
		if(Validator.isNotNull(para)) {
			try {
				userService.insertVipUserTender(para);
			} catch (Exception e) {
				logger.error("消费失败，保存VIP用户信息发生异常...",e);
				return;
			}
		}else {
			logger.error("消费失败，未获取到VIP用户信息...");
			return;
		}
		// 如果没有return success ，consumer会重新消费该消息，直到return success
		return;
	
	}

	@Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		// MQ默认集群消费
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		logger.info("====VIPUserTenderConsumer start=====");
	}

}
