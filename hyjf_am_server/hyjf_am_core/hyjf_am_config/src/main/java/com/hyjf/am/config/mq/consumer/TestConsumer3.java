/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.mq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author wangjun
 * @version TestConsumer3, v0.1 2018/7/20 9:28
 */
@Service
@RocketMQMessageListener(topic = "BROADCAST_TEST_TOPIC", selectorExpression = "*", consumerGroup = "BROADCAST_TEST_GROUP")
public class TestConsumer3 implements RocketMQListener<MessageExt> ,RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(TestConsumer3.class);

    @Override
    public void onMessage(MessageExt message) {
        logger.info("------- 接收到消息 msgId:{}, body:{} ", message.getMsgId(), new String(message.getBody()));
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        // set consumer consume message from now
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
        consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
    }
}
