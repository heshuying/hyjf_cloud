/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer;

import com.hyjf.common.constants.MQConstant;
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

/**
 * 纳觅返现活动计划放款更新放款时间
 * @author tanyy
 * @version ReturnCashOneActivityMessageConsumer, v0.1 2018/11/8 14:04
 */
@Service
@RocketMQMessageListener(topic = MQConstant.RETURN_CASH1_ACTIVITY_SAVE_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.RETURN_CASH1_ACTIVITY_SAVE_GROUP)
public class ReturnCashOneActivityMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private Logger _log = LoggerFactory.getLogger(ReturnCashOneActivityMessageConsumer.class);

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        _log.info("====纳觅返现消费端开始运行=====");
    }

    @Override
    public void onMessage(MessageExt msg) {

    }
}
