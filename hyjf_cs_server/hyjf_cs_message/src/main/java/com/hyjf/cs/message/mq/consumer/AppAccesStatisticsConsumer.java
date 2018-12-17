package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.datacollect.AppAccesStatisticsVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.message.bean.ic.AppAccesStatistics;
import com.hyjf.cs.message.mongo.ic.AppAccesStatisticsDao;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiasq
 * @version AppAccesStatistics, v0.1 2018/7/19 14:15 app登录渠道统计
 */
@Service
@RocketMQMessageListener(topic = MQConstant.APP_ACCESS_STATISTICS_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.APP_ACCESS_STATISTICS_GROUP)
public class AppAccesStatisticsConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
	private static final Logger logger = LoggerFactory.getLogger(AppAccesStatisticsConsumer.class);

	private static int MAX_RECONSUME_TIME = 3;

	@Autowired
	private AppAccesStatisticsDao appAccesStatisticsDao;

	@Override
	public void onMessage(MessageExt message) {
		logger.info("AppAccesStatisticsDao 收到消息，开始处理....msgId is :{}", message.getMsgId());
		AppAccesStatisticsVO appAccesStatisticsVO = JSONObject.parseObject(message.getBody(),
				AppAccesStatisticsVO.class);
		AppAccesStatistics appAccesStatistics = null;
		if (appAccesStatisticsVO != null) {
			appAccesStatistics = new AppAccesStatistics();
			BeanUtils.copyProperties(appAccesStatisticsVO, appAccesStatistics);
		}
		appAccesStatisticsDao.save(appAccesStatistics);
	}
	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		//设置最大重试次数
		defaultMQPushConsumer.setMaxReconsumeTimes(MAX_RECONSUME_TIME);
		logger.info("====AppAccesStatisticsConsumer consumer=====");
	}
}