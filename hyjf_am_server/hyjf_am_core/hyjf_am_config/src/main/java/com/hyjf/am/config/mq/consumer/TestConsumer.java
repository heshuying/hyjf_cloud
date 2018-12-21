package com.hyjf.am.config.mq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hyjf.common.constants.MQConstant;

/**
 * 
 * @author dxj
 * @date 2018/07/06
 */
//@Component
@Service
@RocketMQMessageListener(topic = MQConstant.TEST_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.AM_USER_GENERAL_GROUP)
public class TestConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
	private static final Logger logger = LoggerFactory.getLogger(TestConsumer.class);


	@Override
	public void onMessage(MessageExt  message) {
		logger.info("TestConsumer 收到消息，开始处理....");
		
		MessageExt msg = message;
		logger.info(msg.getTags()+" TestConsumer "+ new String(msg.getBody()));
		
		
//		return ConsumeOrderlyStatus.SUCCESS;
	}

	@Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        // set consumer consume message from now
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		consumer.setMessageModel(MessageModel.CLUSTERING);
//		consumer.registerMessageListener(new MessageListener());
//		consumer.getcon
		//设置并发数为1
		consumer.setConsumeThreadMin(3);
		consumer.setConsumeThreadMax(30);
    }
}
