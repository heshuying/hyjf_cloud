/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.message.bean.ic.AppChannelStatistics;
import com.hyjf.cs.message.mongo.ic.AppChannelStatisticsDao;
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
 * @author fuqiang
 * @version AppChannelStatisticsConsumer, v0.1 2018/7/2 10:53
 */
@Service
@RocketMQMessageListener(topic = MQConstant.APP_CHANNEL_STATISTICS_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.APP_CHANNEL_STATISTICS_GROUP)
public class AppChannelStatisticsConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(AppChannelStatisticsConsumer.class);
    @Autowired
    private AppChannelStatisticsDao appChannelStatisticsDao;

    @Override
    public void onMessage(MessageExt message) {
        logger.info("AppChannelStatisticsConsumer 收到消息，开始处理....msgId is :{}", message.getMsgId());
        AppChannelStatistics appChannelStatistics = JSONObject.parseObject(message.getBody(), AppChannelStatistics.class);
        if (appChannelStatistics != null) {
            appChannelStatisticsDao.save(appChannelStatistics);
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
