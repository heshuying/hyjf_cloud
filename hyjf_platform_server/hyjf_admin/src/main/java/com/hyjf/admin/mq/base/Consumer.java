package com.hyjf.admin.mq.base;

import javax.annotation.PostConstruct;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author xiasq
 * @version Consumer, v0.1 2018/4/12 14:55
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
