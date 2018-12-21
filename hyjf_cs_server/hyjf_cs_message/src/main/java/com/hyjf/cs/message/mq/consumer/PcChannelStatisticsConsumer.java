/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.message.bean.ic.PcChannelStatistics;
import com.hyjf.cs.message.mongo.ic.PcChannelStatisticsDao;
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
 * @version PcChannelStatisticsConsumer, v0.1 2018/7/2 10:11
 */
@Service
@RocketMQMessageListener(topic = MQConstant.PC_CHANNEL_STATISTICS_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.PC_CHANNEL_STATISTICS_GROUP)
public class PcChannelStatisticsConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    Logger logger = LoggerFactory.getLogger(getClass());

    private static int MAX_RECONSUME_TIME = 3;

    @Autowired
    private PcChannelStatisticsDao pcChannelStatisticsDao;

    @Override
    public void onMessage(MessageExt message) {
        logger.info("PcChannelStatisticsConsumer 收到消息，开始处理....msgs is :{}", new String(message.getBody()));
        MessageExt msg = message;
            PcChannelStatistics pcChannelStatistics = JSONObject.parseObject(msg.getBody(), PcChannelStatistics.class);
            if (pcChannelStatistics != null) {
                pcChannelStatisticsDao.save(pcChannelStatistics);
            }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        //设置最大重试次数
        defaultMQPushConsumer.setMaxReconsumeTimes(MAX_RECONSUME_TIME);
        logger.info("====PcChannelStatisticsConsumer consumer=====");
    }
}
