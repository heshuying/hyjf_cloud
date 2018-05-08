package com.hyjf.cs.message.mq;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.cs.message.handle.MsgPushHandle;
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
import org.springframework.stereotype.Component;

/**
 * @author xiasq
 * @version AppMessageConsumer, v0.1 2018/4/12 14:58
 */

@Component
public class AppMessageConsumer extends Consumer {
	private static final Logger logger = LoggerFactory.getLogger(AppMessageConsumer.class);

	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup(MQConstant.APP_MESSAGE_GROUP);
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe(MQConstant.APP_MESSAGE_TOPIC, "*");
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.registerMessageListener(new MessageListener());
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		defaultMQPushConsumer.start();
		logger.info("====appMessage consumer=====");
	}

	public class MessageListener implements MessageListenerConcurrently {
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			MessageExt msg = msgs.get(0);
			AppMsMessage appMsMessage = JSONObject.parseObject(msg.getBody(), AppMsMessage.class);
			logger.info("AppMessageConsumer 收到请求，开始推送app消息....appMsMessage is：{}", appMsMessage);
			if (null != appMsMessage) {
				switch (appMsMessage.getServiceType()) {
				case MessageConstant.APPMSSENDFORMOBILE:// 根据电话号码和模版给指定手机号推送app消息
					MsgPushHandle.sendMessages(appMsMessage.getTplCode(), appMsMessage.getReplaceStrs(),
							appMsMessage.getMobile());
					break;
				case MessageConstant.APPMSSENDFORUSER: // 根据用户编号和模版号给某电话推送app消息
					MsgPushHandle.sendMessages(appMsMessage.getTplCode(), appMsMessage.getReplaceStrs(),
							appMsMessage.getUserId());
					break;

				case MessageConstant.APPMSSENDFORMSG:
					MsgPushHandle.sendMessages(appMsMessage.getMsgId());
					break;

				default:
					logger.error("error app push type...");
					return ConsumeConcurrentlyStatus.RECONSUME_LATER;
				}
			}

			// 如果没有return success ，consumer会重新消费该消息，直到return success
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}
}
