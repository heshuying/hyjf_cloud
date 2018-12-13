package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.cs.message.handle.MsgPushHandle;
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

/**
 * @author xiasq
 * @version AppMessageConsumer, v0.1 2018/4/12 14:58
 */

@Service
@RocketMQMessageListener(topic = MQConstant.APP_MESSAGE_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.APP_MESSAGE_GROUP)
public class AppMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
	private static final Logger logger = LoggerFactory.getLogger(AppMessageConsumer.class);
	@Autowired
	private MsgPushHandle msgPushHandle;
	@Override
	public void onMessage(MessageExt  message) {
		AppMsMessage appMsMessage = JSONObject.parseObject(message.getBody(), AppMsMessage.class);
		logger.debug("AppMessageConsumer 收到请求，开始推送app消息....appMsMessage is：{}", appMsMessage);
		if (null != appMsMessage) {
			switch (appMsMessage.getServiceType()) {
				case MessageConstant.APP_MS_SEND_FOR_MOBILE:
					msgPushHandle.sendMessages(appMsMessage.getTplCode(), appMsMessage.getReplaceStrs(),
							appMsMessage.getMobile());
					break;
				case MessageConstant.APP_MS_SEND_FOR_USER:
					msgPushHandle.sendMessages(appMsMessage.getTplCode(), appMsMessage.getReplaceStrs(),
							appMsMessage.getUserId());
					break;

				case MessageConstant.APP_MS_SEND_FOR_MSG:
					msgPushHandle.sendMessages(appMsMessage.getMsgId());
					break;

				default:
					logger.error("error app push type...");
					return ;
			}
		}
	}
	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		logger.info("====FddCertificateConsumer consumer=====");
	}


}
