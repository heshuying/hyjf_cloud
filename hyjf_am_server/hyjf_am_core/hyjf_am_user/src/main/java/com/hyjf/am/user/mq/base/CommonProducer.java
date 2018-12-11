package com.hyjf.am.user.mq.base;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyjf.common.exception.MQException;

/**
 * 
 * 通用发消息
 * @author dxj
 *
 */
@Component
public class CommonProducer {
	private static final Logger logger = LoggerFactory.getLogger(CommonProducer.class);

	@Autowired
	private RocketMQTemplate rocketMQTemplate;

	public boolean messageSend(MessageContent messageContent) throws MQException {
		try {
			Message message = new Message(messageContent.topic, messageContent.tag, messageContent.keys,
					messageContent.body);
			SendResult sendResult = rocketMQTemplate.getProducer().send(message);
			if (sendResult != null && sendResult.getSendStatus() == SendStatus.SEND_OK) {
				return true;
			} else {
				return false;
			}
		} catch (MQClientException | RemotingException | MQBrokerException e ) {
			throw new MQException("mq send error", e);
		} catch (InterruptedException e){
//			Thread.currentThread().interrupt();
			throw new MQException("mq InterruptedException send error", e);
		}
	}
}
