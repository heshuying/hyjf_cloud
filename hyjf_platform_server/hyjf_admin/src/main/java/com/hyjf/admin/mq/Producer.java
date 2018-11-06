package com.hyjf.admin.mq;

import com.hyjf.admin.mq.base.MessageContent;
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
		logger.info("mq address--->{}", namesrvAddr);
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

	/**
	 *  包含延时参数的消息发送(delayLevel超过最大值,则默认最大值)
	 * @date 2018/8/10 9:49
	 */
	protected boolean messageSendDelay(MessageContent messageContent,int delayLevel) throws MQException {
		try {
			Message message = new Message(messageContent.topic, messageContent.tag, messageContent.keys,
					messageContent.body);
			if (delayLevel > 0){
				message.setDelayTimeLevel(delayLevel);
			}
			return send(message);
		} catch (MQClientException | RemotingException | MQBrokerException e) {
			throw new MQException("mq send error", e);
		} catch (InterruptedException e){
			Thread.currentThread().interrupt();
			throw new MQException("mq InterruptedException send error", e);
		}
	}
}
