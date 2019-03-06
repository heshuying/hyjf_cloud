package com.hyjf.am.user.mq.consumer;

import java.util.HashMap;

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
 * 更新首投信息mq消费
 * @author jun
 * @date 20180706
 */
@Service
@RocketMQMessageListener(topic = MQConstant.STATISTICS_UTM_REG_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.STATISTICS_UTM_REG_GROUP)
public class UtmRegConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

	private static final Logger logger = LoggerFactory.getLogger(UtmRegConsumer.class);
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public void onMessage(MessageExt  message) {
		logger.info("UtmRegConsumer 收到消息，开始处理....");
		MessageExt msg = message;
		HashMap<String,Object> params = JSONObject.parseObject(msg.getBody(), HashMap.class);
		if(Validator.isNotNull(params)) {
			try {
				userService.updateFirstUtmReg(params);
			} catch (Exception e) {
				logger.error(e.getMessage());
				logger.error("消费失败，更新首投信息发生异常...");	
			}	
		}else {
			logger.error("消费失败，未获取到用户首投信息...");
			return;
		}
		// 如果没有return success ，consumer会重新消费该消息，直到return success
		;
	
	}

	@Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		// MQ默认集群消费
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		logger.info("====UtmRegConsumer start=====");
	}

}
