/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.statistics.mq.consumer;

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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.statistics.bean.StatisticsTzj;
import com.hyjf.am.statistics.bean.StatisticsTzjHour;
import com.hyjf.am.statistics.mongo.StatisticsTzjDao;
import com.hyjf.am.statistics.mongo.StatisticsTzjHourDao;
import com.hyjf.am.statistics.mq.base.Consumer;
import com.hyjf.am.vo.datacollect.TzjDayReportVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetDate;

/**
 * @author fuqiang
 * @version StatisticsTzjConsumer, v0.1 2018/7/2 11:12
 */
@Component
public class StatisticsTzjConsumer extends Consumer {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private StatisticsTzjDao statisticsTzjDao;
	@Autowired
	private StatisticsTzjHourDao statisticsTzjHourDao;

	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup(MQConstant.STATISTICS_TZJ_GROUP);
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe(MQConstant.STATISTICS_TZJ_TOPIC, "*");
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.registerMessageListener(new StatisticsTzjConsumer.MessageListener());
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		defaultMQPushConsumer.start();
		logger.info("====StatisticsTzjConsumer consumer=====");
	}

	public class MessageListener implements MessageListenerConcurrently {
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			logger.info("StatisticsTzjConsumer 收到消息，开始处理....msgs is :{}", msgs);

			for (MessageExt msg : msgs) {
				TzjDayReportVO tzjDayReportVO = JSONObject.parseObject(msg.getBody(), TzjDayReportVO.class);
				if (tzjDayReportVO != null) {
					StatisticsTzj entity = this.calculateRate(tzjDayReportVO);
					this.doSave(entity);
				}
			}
			// 如果没有return success ，consumer会重新消费该消息，直到return success
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}

		/**
		 * 保存数据
		 */
		private void doSave(StatisticsTzj entity) {
			Query query = new Query();
			Criteria criteria = Criteria.where("day").is(entity.getDay());
			query.addCriteria(criteria);
			StatisticsTzj statisticsTzj = statisticsTzjDao.findOne(query);
			if (statisticsTzj != null) {
				entity.setId(statisticsTzj.getId());
			}
			// 没有插入，有则更新
			statisticsTzjDao.save(entity);

			// 处理小时维度的数据
			Query hourQuery = new Query();
			Criteria hourCriteria = Criteria.where("day").is(entity.getDay()).and("hour")
					.is(String.valueOf(GetDate.getHour(new Date())));
			hourQuery.addCriteria(hourCriteria);
			StatisticsTzjHour statisticsTzjHour = statisticsTzjHourDao.findOne(hourQuery);
			if (statisticsTzjHour != null) {
				statisticsTzjHour.setRegistCount(entity.getRegistCount() - statisticsTzj.getRegistCount());
				statisticsTzjHour
						.setTenderFirstCount(entity.getTenderFirstCount() - statisticsTzj.getTenderFirstCount());
				statisticsTzjHour.setCreateTime(new Date());
				statisticsTzjHour.setUpdateTime(new Date());
			} else {
				statisticsTzjHour = new StatisticsTzjHour();
				statisticsTzjHour.setDay(entity.getDay());
				statisticsTzjHour.setHour(String.valueOf(GetDate.getHour(new Date())));
				statisticsTzjHour.setRegistCount(entity.getRegistCount());
				statisticsTzjHour.setTenderFirstCount(entity.getTenderFirstCount());
				statisticsTzjHour.setCreateTime(new Date());
				statisticsTzjHour.setUpdateTime(new Date());
			}
			statisticsTzjHourDao.save(statisticsTzjHour);
		}

		/**
		 * 计算各种比率
		 * 
		 * @param tzjDayReportVO
		 * @return
		 */
		private StatisticsTzj calculateRate(TzjDayReportVO tzjDayReportVO) {
			StatisticsTzj entity = new StatisticsTzj();
			BeanUtils.copyProperties(tzjDayReportVO, entity);

			// 开户率
			BigDecimal openRate = BigDecimal.ZERO;
			// 绑卡率
			BigDecimal cardBindRate = BigDecimal.ZERO;
			// 新投比
			BigDecimal tenderNewRate = BigDecimal.ZERO;
			// 复投比
			BigDecimal tenderAgainRate = BigDecimal.ZERO;

			BigDecimal value100 = new BigDecimal(100);

			int registCount = tzjDayReportVO.getRegistCount();
			int tenderCount = tzjDayReportVO.getTenderCount();
			if (registCount != 0) {
				openRate = new BigDecimal(tzjDayReportVO.getOpenCount())
						.divide(new BigDecimal(registCount), 2, BigDecimal.ROUND_HALF_UP).multiply(value100);
				cardBindRate = new BigDecimal(tzjDayReportVO.getCardBindCount())
						.divide(new BigDecimal(registCount), 2, BigDecimal.ROUND_HALF_UP).multiply(value100);
				tenderNewRate = new BigDecimal(tzjDayReportVO.getTenderNewCount())
						.divide(new BigDecimal(registCount), 2, BigDecimal.ROUND_HALF_UP).multiply(value100);
			}

			if (tenderCount != 0) {
				tenderAgainRate = new BigDecimal(tzjDayReportVO.getTenderAgainCount())
						.divide(new BigDecimal(tenderCount), 2, BigDecimal.ROUND_HALF_UP).multiply(value100);
			}

			entity.setOpenRate(openRate);
			entity.setCardBindRate(cardBindRate);
			entity.setTenderNewRate(tenderNewRate);
			entity.setTenderAgainRate(tenderAgainRate);

			entity.setCreateTime(new Date());
			entity.setUpdateTime(new Date());
			return entity;
		}
	}
}
