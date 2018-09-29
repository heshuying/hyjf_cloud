package com.hyjf.cs.trade.mq.base;

import com.hyjf.common.exception.MQException;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * @author xiasq
 * @version Producer, v0.1 2018/4/12 11:04
 */
public abstract class Producer {
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);

	private DefaultMQProducer defaultMQProducer;

	@Value("${rocketMQ.namesrvAddr}")
	private String namesrvAddr;

	@PostConstruct
	protected void init() throws MQClientException {
		ProducerFieldsWrapper wrapper = this.getFieldsWrapper();
		Assert.notNull(wrapper, "ProducerFieldsWrapper must not be null");

		defaultMQProducer = new DefaultMQProducer(wrapper.getGroup());
		defaultMQProducer.setNamesrvAddr(namesrvAddr);
		defaultMQProducer.setVipChannelEnabled(false);
		defaultMQProducer.setInstanceName(wrapper.getInstanceName());
		defaultMQProducer.start();
	}

	protected abstract ProducerFieldsWrapper getFieldsWrapper();

	protected boolean send(Message message)
			throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
//		logger.info("mq address--->{}", namesrvAddr);
		SendResult sendResult = defaultMQProducer.send(message);
		if (sendResult != null && sendResult.getSendStatus() == SendStatus.SEND_OK) {
			return true;
		} else {
			return false;
		}
	}

	protected boolean messageSend(MessageContent messageContent) throws MQException {
		try {
			Message message = new Message(messageContent.topic, messageContent.tag, messageContent.keys,
					messageContent.body);
			return send(message);
		} catch (MQClientException | RemotingException | MQBrokerException e) {
			throw new MQException("mq send error", e);
		} catch (InterruptedException e){
			Thread.currentThread().interrupt();
			throw new MQException("mq InterruptedException send error", e);
		}
	}
}
