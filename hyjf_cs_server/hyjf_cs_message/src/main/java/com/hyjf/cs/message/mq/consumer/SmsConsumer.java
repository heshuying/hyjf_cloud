package com.hyjf.cs.message.mq.consumer;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.cs.message.handle.SmsHandle;
import com.hyjf.cs.message.mq.base.Consumer;

/**
 * @author xiasq
 * @version SmsConsumer, v0.1 2018/4/12 14:58
 */

@Component
public class SmsConsumer extends Consumer {
	private static final Logger logger = LoggerFactory.getLogger(SmsConsumer.class);

	@Autowired
	private SmsHandle smsHandle;

	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup(MQConstant.SMS_CODE_GROUP);
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe(MQConstant.SMS_CODE_TOPIC, "*");
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.registerMessageListener(new MessageListener());
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		defaultMQPushConsumer.start();
		logger.info("====sms consumer=====");
	}

	public class MessageListener implements MessageListenerConcurrently {
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			MessageExt msg = msgs.get(0);
			SmsMessage smsMessage = JSONObject.parseObject(msg.getBody(), SmsMessage.class);
			logger.info("SmsConsumer 收到消息，开始处理....smsMessage is :{}", smsMessage);
			if (null != smsMessage) {
				switch (smsMessage.getServiceType()) {
				case MessageConstant.SMS_SEND_FOR_MANAGER:
					smsHandle.sendMessages(smsMessage.getTplCode(), smsMessage.getReplaceStrs(), smsMessage.getSender(),
							smsMessage.getChannelType());
					break;
				case MessageConstant.SMS_SEND_FOR_MOBILE:
					smsHandle.sendMessages(smsMessage.getMobile(), smsMessage.getTplCode(), smsMessage.getReplaceStrs(),
							smsMessage.getChannelType());
					break;
				case MessageConstant.SMS_SEND_FOR_USER:
					smsHandle.sendMessages(smsMessage.getUserId(), smsMessage.getTplCode(), smsMessage.getReplaceStrs(),
							smsMessage.getChannelType());
					break;
				case MessageConstant.SMS_SEND_FOR_USERS_NO_TPL:
					try {
						smsHandle.sendMessage(smsMessage.getMobile(), smsMessage.getMessage(),
								smsMessage.getServiceType(), null, smsMessage.getChannelType());
					} catch (Exception e) {
						logger.error("send sms error....");
						return ConsumeConcurrentlyStatus.RECONSUME_LATER;
					}
					break;
				default:
					logger.error("error sms type...");
					return ConsumeConcurrentlyStatus.RECONSUME_LATER;
				}
			}

			// 如果没有return success ，consumer会重新消费该消息，直到return success
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}
}
