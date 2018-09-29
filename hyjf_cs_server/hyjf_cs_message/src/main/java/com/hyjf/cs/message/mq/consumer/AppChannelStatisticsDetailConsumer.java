package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.message.bean.ic.AppChannelStatisticsDetail;
import com.hyjf.cs.message.mongo.ic.AppChannelStatisticsDetailDao;
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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xiasq
 * @version AppChannelStatisticsDetailConsumer, v0.1 2018/4/12 14:58
 */

@Component
public class AppChannelStatisticsDetailConsumer extends Consumer {
	private static final Logger logger = LoggerFactory.getLogger(AppChannelStatisticsDetailConsumer.class);

	@Autowired
	private AppChannelStatisticsDetailDao appChannelStatisticsDetailDao;

	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_GROUP);
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_TOPIC, "*");
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.registerMessageListener(new MessageListener());
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		defaultMQPushConsumer.start();
		logger.info("====AppChannelStatisticsDetail consumer=====");
	}

	public class MessageListener implements MessageListenerConcurrently {
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			logger.info("AppChannelStatisticsDetailConsumer 收到消息，开始处理....msgs is :{}", msgs);

			for (MessageExt msg : msgs) {
				// 开户更新
				if (MQConstant.APP_CHANNEL_STATISTICS_DETAIL_UPDATE_TAG.equals(msg.getTags())) {
					Integer userId = JSONObject.parseObject(msg.getBody(), Integer.class);
					if (userId == null) {
						logger.error("参数错误，userId is null...");
						return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
					}
					// 更新AppChannelStatisticsDetailDao开户时间
					AppChannelStatisticsDetail entity = appChannelStatisticsDetailDao.findByUserId(userId);
					if (entity != null) {
						entity.setOpenAccountTime(new Date());
						appChannelStatisticsDetailDao.save(entity);
					}
				} else if (MQConstant.APP_CHANNEL_STATISTICS_DETAIL_SAVE_TAG.equals(msg.getTags())) {
					logger.info("app渠道统计保存消息....");
					AppChannelStatisticsDetail entity = JSONObject.parseObject(msg.getBody(),
							AppChannelStatisticsDetail.class);
					if (entity != null) {
						appChannelStatisticsDetailDao.save(entity);
					}
				} else if (MQConstant.APP_CHANNEL_STATISTICS_DETAIL_CREDIT_TAG.equals(msg.getTags())
						|| MQConstant.APP_CHANNEL_STATISTICS_DETAIL_INVEST_TAG.equals(msg.getTags())) {
					JSONObject entity = JSONObject.parseObject(msg.getBody(), JSONObject.class);
					if (Validator.isNotNull(entity)) {
						Integer investFlag = entity.getInteger("investFlag");
						if (investFlag == 1) {
							Query query = new Query();
							Integer userId = entity.getInteger("userId");
							Criteria criteria = Criteria.where("userId").is(userId);
							query.addCriteria(criteria);
							Update update = new Update();
							BigDecimal accountDecimal = entity.getBigDecimal("accountDecimal");
							update.inc("cumulativeInvest", accountDecimal);
							appChannelStatisticsDetailDao.update(query, update);
						} else if (investFlag == 0) {
							Query query = new Query();
							Integer userId = entity.getInteger("userId");
							Criteria criteria = Criteria.where("userId").is(userId);
							query.addCriteria(criteria);
							Update update = new Update();
							BigDecimal accountDecimal = entity.getBigDecimal("accountDecimal");
							String projectType = entity.getString("projectType");
							Integer investTime = entity.getInteger("investTime");
							String investProjectPeriod = entity.getString("investProjectPeriod");

							update.inc("cumulativeInvest", accountDecimal).set("investAmount", accountDecimal)
									.set("investProjectType", projectType).set("firstInvestTime", investTime)
									.set("investProjectPeriod", investProjectPeriod);
							appChannelStatisticsDetailDao.update(query, update);
						}
					}
				}
			}

			// 如果没有return success ，consumer会重新消费该消息，直到return success
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}
}
