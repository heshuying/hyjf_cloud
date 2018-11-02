package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.message.bean.mc.UserOperationLogEntity;
import com.hyjf.cs.message.mongo.mc.UserOperationLogMongDao;
import com.hyjf.cs.message.mq.base.Consumer;
import org.apache.commons.lang3.StringUtils;
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

import java.util.Date;
import java.util.List;

/**
 * @author tyy
 * @version UserOperationLogConsumer, v0.1 2018/11/2 14:58
 */

@Component
public class UserOperationLogConsumer extends Consumer {
	private static final Logger logger = LoggerFactory.getLogger(UserOperationLogConsumer.class);

	@Autowired
	private UserOperationLogMongDao userOperationLogMongDao;

	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup(MQConstant.USER_OPERATION_LOG_GROUP);
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe(MQConstant.USER_OPERATION_LOG_TOPIC, "*");
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.registerMessageListener(new MessageListener());
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		defaultMQPushConsumer.start();
		logger.info("====UserOperationLog consumer=====");
	}

	public class MessageListener implements MessageListenerConcurrently {
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			MessageExt msg = msgs.get(0);
			UserOperationLogEntity userOperationLogEntity = JSONObject.parseObject(msg.getBody(), AppMsMessage.class);
			logger.debug("UserOperationLogConsumer 收到请求，userOperationLogEntity is：{}", userOperationLogEntity);
			if (null != userOperationLogEntity&& StringUtils.isNotBlank(userOperationLogEntity.getUserName())) {
				userOperationLogEntity.setOperationTime(new Date());
				userOperationLogMongDao.save(userOperationLogEntity);
			}
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}
}
