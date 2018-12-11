package com.hyjf.am.config.mq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hyjf.am.config.mq.base.Consumer;

/**
 * 
 * @author dxj
 * @date 2018/07/06
 */
//@Component
@Service
@RocketMQMessageListener(topic = "TEST_TOPIC2", selectorExpression = "*", consumerGroup = "TESTC1")
public class TestConsumer2 extends Consumer {
	private static final Logger logger = LoggerFactory.getLogger(TestConsumer2.class);


	@Override
	public void onMessage(MessageExt  message) {
		logger.info("TestConsumer 收到消息，开始处理....");
		
		MessageExt msg = message;
		logger.info(msg.getTags()+" TestConsumer "+ new String(msg.getBody()));
		
		if(1==1) {
			throw new RuntimeException("dsdf");
		}
		
//		return ConsumeOrderlyStatus.SUCCESS;
	}

	@Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        // set consumer consume message from now
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		consumer.setMessageModel(MessageModel.CLUSTERING);
//		consumer.registerMessageListener(new MessageListener());
		
		//设置并发数为1
		consumer.setConsumeThreadMin(1);
		consumer.setConsumeThreadMax(1);
    }
}
