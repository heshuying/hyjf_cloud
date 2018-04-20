package com.hyjf.cs.user.mq;

import java.io.Serializable;

import javax.annotation.PostConstruct;

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

import com.hyjf.common.exception.MQException;

/**
 * @author xiasq
 * @version Producer, v0.1 2018/4/12 11:04
 */
public abstract class Producer {
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);

	private DefaultMQProducer defaultMQProducer;

	@Value("${rocketMQ.namesrvAddr}")
	private String namesrvAddr;

	@Value("${rocketMQ.defaultProducerGroup}")
	private String producerGroup;

	@PostConstruct
	protected void init() throws MQClientException {
		defaultMQProducer = new DefaultMQProducer(producerGroup);
		defaultMQProducer.setNamesrvAddr(namesrvAddr);
		defaultMQProducer.setVipChannelEnabled(false);
		defaultMQProducer.setInstanceName(this.getFieldsWrapper().getInstanceName());
		defaultMQProducer.start();
	}

	protected abstract ProducerFieldsWrapper getFieldsWrapper();

	protected boolean send(Message message)
			throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
		logger.info("mq address--->{}", namesrvAddr);
		SendResult sendResult = defaultMQProducer.send(message);
		if (sendResult != null && sendResult.getSendStatus() == SendStatus.SEND_OK) {
			return true;
		} else {
			return false;
		}
	}

	protected boolean messageSend(MassageContent messageContent) throws MQException {
		try {
			Message message = new Message(messageContent.topic, messageContent.tag, messageContent.keys,
					messageContent.body);
			return send(message);
		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
			throw new MQException("mq send error", e);
		}
	}

	protected DefaultMQProducer getDefaultMQProducer() throws MQClientException {
		return defaultMQProducer;
	}

	public static class MassageContent implements Serializable {
		private static final long serialVersionUID = -6846413929342308237L;
		public final String topic;
		public final String tag;
		public final String keys;
		public final byte[] body;

		public MassageContent(String topic, String tag, String keys, byte[] body) {
			this.topic = topic;
			this.tag = tag;
			this.keys = keys;
			this.body = body;
		}
	}

	protected static class ProducerFieldsWrapper {
		private String group;
		private String instanceName;

		public String getGroup() {
			return group;
		}

		public void setGroup(String group) {
			this.group = group;
		}

		public String getInstanceName() {
			return instanceName;
		}

		public void setInstanceName(String instanceName) {
			this.instanceName = instanceName;
		}
	}
}
