package com.hyjf.am.statistics.mq;

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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.statistics.bean.AccountWebList;
import com.hyjf.am.statistics.mongo.AccountWebListDao;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.common.constants.MQConstant;

/**
 * @author xiasq
 * @version AccountWebListConsumer, v0.1 2018/6/19 16:46
 */
@Component
public class AccountWebListConsumer extends Consumer {
	private static final Logger logger = LoggerFactory.getLogger(AccountWebListConsumer.class);

	@Autowired
	private AccountWebListDao accountWebListDao;

	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup(MQConstant.ACCOUNT_WEB_LIST_GROUP);
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe(MQConstant.ACCOUNT_WEB_LIST_TOPIC, "*");
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.registerMessageListener(new AccountWebListConsumer.MessageListener());
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		defaultMQPushConsumer.start();
		logger.info("====AccountWebListConsumer consumer=====");
	}

	public class MessageListener implements MessageListenerConcurrently {
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			logger.info("AccountWebListConsumer 收到消息，开始处理....msgs is :{}", msgs);

			for (MessageExt msg : msgs) {
				AccountWebListVO accountWebListVO = JSONObject.parseObject(msg.getBody(), AccountWebListVO.class);
				AccountWebList accountWebList = null;
				if (accountWebListVO != null) {
					accountWebList = new AccountWebList();
					BeanUtils.copyProperties(accountWebListVO, accountWebList);
				}
				accountWebListDao.save(accountWebList);
			}

			// 如果没有return success ，consumer会重新消费该消息，直到return success
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}
}