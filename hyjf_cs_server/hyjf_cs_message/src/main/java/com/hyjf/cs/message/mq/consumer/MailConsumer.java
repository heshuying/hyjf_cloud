package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.cs.message.handle.MailHandle;
import com.hyjf.cs.message.mq.base.Consumer;
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

import java.io.File;
import java.util.List;

/**
 * @author xiasq
 * @version MailConsumer, v0.1 2018/4/12 14:58
 */

@Component
public class MailConsumer extends Consumer {
	private static final Logger logger = LoggerFactory.getLogger(MailConsumer.class);

	@Autowired
	private MailHandle mailHandle;

	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup(MQConstant.MAIL_GROUP);
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe(MQConstant.MAIL_TOPIC, "*");
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.registerMessageListener(new MessageListener());
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		defaultMQPushConsumer.start();
		logger.info("====mail consumer=====");
	}

	public class MessageListener implements MessageListenerConcurrently {
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			MessageExt msg = msgs.get(0);
			MailMessage mailMessage = JSONObject.parseObject(msg.getBody(), MailMessage.class);
			logger.info("MailConsumer 收到消息，开始处理....mailMessage is :{}", mailMessage);
			if (null != mailMessage) {
				switch (mailMessage.getServiceType()) {
				case MessageConstant.MAIL_SEND_FOR_USER:
					mailHandle.sendMail(mailMessage.getUserId(), mailMessage.getSubject(), mailMessage.getBody(),
							mailMessage.getFileNames());
					break;
				case MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS:
					mailHandle.sendMail(mailMessage.getToMailArray(), mailMessage.getSubject(),
							mailMessage.getMailKbn(), mailMessage.getReplaceStrs(), mailMessage.getFileNames());
					break;
				case MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS_MSG:
					mailHandle.sendMail(mailMessage.getToMailArray(), mailMessage.getSubject(), mailMessage.getBody(),
							mailMessage.getFileNames());
					break;

				default:
					logger.error("error mail type...");
					return ConsumeConcurrentlyStatus.RECONSUME_LATER;
				}
				if (null != mailMessage.getFileNames()) {
					if (mailMessage.getFileNames().length > 1) {
						File f = new File(mailMessage.getFileNames()[0] + mailMessage.getFileNames()[1]);
						if (f.exists()) {
							f.delete();
						}
						File dir = new File(mailMessage.getFileNames()[0]);
						if (dir.exists()) {
							dir.delete();
						}
					} else if (mailMessage.getFileNames().length == 1) {
						File dir = new File(mailMessage.getFileNames()[0]);
						if (dir.exists()) {
							dir.delete();
						}
					}
				}
			}

			// 如果没有return success ，consumer会重新消费该消息，直到return success
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}
}
