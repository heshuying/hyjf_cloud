package com.hyjf.am.user.mq.consumer;

import java.math.BigDecimal;
import java.util.Date;
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
import com.hyjf.am.user.dao.model.auto.AppUtmReg;
import com.hyjf.am.user.mq.base.Consumer;
import com.hyjf.am.user.service.front.user.AppUtmRegService;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.validator.Validator;

/**
 * @author xiasq
 * @version AppUtmRegConsumer, v0.1 2018/4/12 14:58
 */

@Component
public class AppUtmRegConsumer extends Consumer {
	private static final Logger logger = LoggerFactory.getLogger(AppUtmRegConsumer.class);

	@Autowired
	private AppUtmRegService appUtmRegService;

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
					AppUtmReg entity = appUtmRegService.findByUserId(userId);
					if (entity != null) {
						entity.setOpenAccountTime(new Date());
						appUtmRegService.update(entity);
					}
				} else if (MQConstant.APP_CHANNEL_STATISTICS_DETAIL_SAVE_TAG.equals(msg.getTags())) {
					logger.info("app渠道统计保存消息....");
					AppUtmReg entity = JSONObject.parseObject(msg.getBody(),
							AppUtmReg.class);
					if (entity != null) {
						appUtmRegService.insert(entity);
					}
				} else if (MQConstant.APP_CHANNEL_STATISTICS_DETAIL_CREDIT_TAG.equals(msg.getTags())
						|| MQConstant.APP_CHANNEL_STATISTICS_DETAIL_INVEST_TAG.equals(msg.getTags())) {
					JSONObject entity = JSONObject.parseObject(msg.getBody(), JSONObject.class);
					if (Validator.isNotNull(entity)) {
						boolean investFlag = entity.getBooleanValue("investFlag");
						// 不是首投
						if (!investFlag) {
							Integer userId = entity.getInteger("userId");
							AppUtmReg appUtmReg = appUtmRegService.findByUserId(userId);
							BigDecimal accountDecimal = entity.getBigDecimal("accountDecimal")==null?BigDecimal.ZERO:entity.getBigDecimal("accountDecimal");
                            BigDecimal invest = appUtmReg.getCumulativeInvest() == null? BigDecimal.ZERO: appUtmReg.getCumulativeInvest();
                            appUtmReg.setCumulativeInvest(invest.add(accountDecimal));
                            appUtmRegService.update(appUtmReg);
						} else {
						    // 首投
                            Integer userId = entity.getInteger("userId");
                            AppUtmReg appUtmReg = appUtmRegService.findByUserId(userId);
							BigDecimal accountDecimal = entity.getBigDecimal("accountDecimal")==null?BigDecimal.ZERO:entity.getBigDecimal("accountDecimal");
							String projectType = entity.getString("projectType");
							Integer investTime = entity.getInteger("investTime");
							String investProjectPeriod = entity.getString("investProjectPeriod");
                            appUtmReg.setCumulativeInvest(accountDecimal);
                            appUtmReg.setInvestAmount(accountDecimal);
                            appUtmReg.setInvestProjectType(projectType);
                            appUtmReg.setFirstInvestTime(investTime);
                            appUtmReg.setInvestProjectPeriod(investProjectPeriod);
                            appUtmRegService.update(appUtmReg);
						}
					}
				}
			}

			// 如果没有return success ，consumer会重新消费该消息，直到return success
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}
}
