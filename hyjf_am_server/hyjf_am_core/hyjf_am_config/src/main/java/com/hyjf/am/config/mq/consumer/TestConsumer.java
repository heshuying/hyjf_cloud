package com.hyjf.am.config.mq.consumer;

import java.util.List;
import java.util.Map;

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
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.mq.base.Consumer;
import com.hyjf.common.constants.MQConstant;

/**
 * 
 * @author dxj
 * @date 2018/07/06
 */
@Component
public class TestConsumer extends Consumer {
	private static final Logger logger = LoggerFactory.getLogger(TestConsumer.class);


	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup(MQConstant.TEST_GROUP);
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe(MQConstant.TEST_TOPIC, "*");
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.registerMessageListener(new MessageListener());
		
		//设置并发数为1
//		defaultMQPushConsumer.setConsumeThreadMin(1);
//        defaultMQPushConsumer.setConsumeThreadMax(1);
        
        
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可
		defaultMQPushConsumer.start();
		logger.info("====TestConsumer start=====");
	}

	public class MessageListener implements MessageListenerConcurrently {
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			logger.info("TestConsumer 收到消息，开始处理....");
			MessageExt msg = msgs.get(0);
			
			Map<String, String> maps = JSONObject.parseObject(msg.getBody(), Map.class);
			
			logger.info("TestConsumer "+ maps.get("hehe"));

			// 如果没有return success ，consumer会重新消费该消息，直到return success
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}
}
