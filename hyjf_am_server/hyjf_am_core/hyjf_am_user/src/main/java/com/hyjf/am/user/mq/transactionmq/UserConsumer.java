package com.hyjf.am.user.mq.transactionmq;

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
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.service.UserService;

/**
 * @author xiasq
 * @version UserConsumer, v0.1 2018/4/12 14:58
 */

@Component
public class UserConsumer extends Consumer {
	private static final Logger logger = LoggerFactory.getLogger(UserConsumer.class);
	@Autowired
	private UserService userService;

	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup("user_transaction_group_test");
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe("userTransationTest1", "*");
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.registerMessageListener(new MessageListener());
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		defaultMQPushConsumer.start();
		logger.info("====user consumer=====");
	}

	public class MessageListener implements MessageListenerConcurrently {
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			logger.info("UserConsumer 收到消息，开始处理....");
			MessageExt msg = msgs.get(0);
			Integer userId = JSONObject.parseObject(msg.getBody(), Integer.class);

			try {
				User user = userService.findUserByUserId(userId);
				if (user == null)
					throw new RuntimeException("找不到用户，userId is : " + userId);
				userService.updateUserById(user);
			} catch (Exception e) {
				logger.error("更新user投资标志失败....", e);
			}
			// 如果没有return success ，consumer会重新消费该消息，直到return success
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}
}
