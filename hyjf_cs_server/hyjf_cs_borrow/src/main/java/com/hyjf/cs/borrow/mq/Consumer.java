/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.borrow.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * @author fuqiang
 * @version Consumer, v0.1 2018/6/12 15:25
 */
public abstract class Consumer {

    protected DefaultMQPushConsumer defaultMQPushConsumer;
    @Value("${rocketMQ.namesrvAddr}")
    private String namesrvAddr;

    @PostConstruct
    public void init() throws MQClientException {
        defaultMQPushConsumer = new DefaultMQPushConsumer();
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
        defaultMQPushConsumer.setVipChannelEnabled(false);
        init(defaultMQPushConsumer);
    }

    public abstract void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException;
}
