package com.hyjf.cs.message.mq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.handler.MsgPushHandler;
import com.hyjf.cs.message.service.msgpush.MessagePushErrorService;

/**
 * @author xiasq
 * @version AppMessageConsumer, v0.1 2018/4/12 14:58
 */

@Service
@RocketMQMessageListener(topic = MQConstant.APP_MESSAGE_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.APP_MESSAGE_GROUP)
public class AppMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
	private static final Logger logger = LoggerFactory.getLogger(AppMessageConsumer.class);
	private static int MAX_RECONSUME_TIME = 3;
	@Autowired
	private MsgPushHandler msgPushHandler;
	@Autowired
	private MessagePushErrorService messagePushErrorService;

	@Override
	public void onMessage(MessageExt message) {
		AppMsMessage appMsMessage = JSONObject.parseObject(message.getBody(), AppMsMessage.class);
		logger.debug("AppMessageConsumer 收到请求，开始推送app消息....appMsMessage is：{}", appMsMessage);

		try {
			if (null != appMsMessage) {
				switch (appMsMessage.getServiceType()) {
					case MessageConstant.APP_MS_SEND_FOR_MOBILE:
						msgPushHandler.sendMessages(appMsMessage.getTplCode(), appMsMessage.getReplaceStrs(),
								appMsMessage.getMobile());
						break;
					case MessageConstant.APP_MS_SEND_FOR_USER:
						msgPushHandler.sendMessages(appMsMessage.getTplCode(), appMsMessage.getReplaceStrs(),
								appMsMessage.getUserId());
						break;
					case MessageConstant.APP_MS_SEND_FOR_MSG:
						msgPushHandler.sendMessages(appMsMessage.getMsgId());
						break;

					case MessageConstant.APP_MS_SEND_REPEAT:
						MessagePushMsgHistory msg = messagePushErrorService.getRecord(appMsMessage.getMsgId());
						if (msg != null) {
							msgPushHandler.send(msg);
						}
						break;
					default:
						logger.error("error app push type...");
						return;
				}
			}
		} catch (Exception e) {
			logger.error("推送失败， 消息体: {}", appMsMessage, e);
		}
	}

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		// 设置最大重试次数
		defaultMQPushConsumer.setMaxReconsumeTimes(MAX_RECONSUME_TIME);
		logger.info("====FddCertificateConsumer consumer=====");
	}

}
