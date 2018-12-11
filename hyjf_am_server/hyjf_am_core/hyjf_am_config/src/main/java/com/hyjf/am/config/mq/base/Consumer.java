package com.hyjf.am.config.mq.base;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;

/**
 * @author xiasq
 * @version Consumer, v0.1 2018/4/12 14:55
 */
public abstract class Consumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
	
}
