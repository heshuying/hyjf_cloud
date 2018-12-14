/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.sensorsdata.openaccount;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.service.consumer.SensorsDataOpenAccountService;
import org.apache.commons.lang.StringUtils;
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
 * 神策数据统计:用户开户相关
 *
 * @author liuyang
 * @version SensorsDataOpenAccountMessageConsumer, v0.1 2018/10/23 18:28
 */
@Service
@RocketMQMessageListener(topic = MQConstant.SENSORSDATA_OPEN_ACCOUNT_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.SENSORSDATA_OPEN_ACCOUNT_GROUP)
public class SensorsDataOpenAccountMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(SensorsDataOpenAccountMessageConsumer.class);

    @Autowired
    private SensorsDataOpenAccountService sensorsDataOpenAccountService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer){
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====SensorsDataOpenAccountMessageConsumer start=====");
    }

    @Override
    public void onMessage(MessageExt paramBean) {
        logger.info("神策数据统计:用户开户相关,收到消息，开始处理....");
        SensorsDataBean sensorsDataBean = null;
        String msgBody = new String(paramBean.getBody());
        try {
            sensorsDataBean = JSONObject.parseObject(msgBody, SensorsDataBean.class);
            if (sensorsDataBean == null) {
                logger.error("神策数据采集:开户相关接收到的消息Bean为空");
                return;
            }
            // 用户ID
            Integer userId = sensorsDataBean.getUserId();
            if (userId == null || userId == 0) {
                logger.error("神策数据采集:开户用户ID为空");
                return;
            }

            // 事件类型
            String evenCode = sensorsDataBean.getEventCode();
            if (StringUtils.isBlank(evenCode)) {
                logger.error("神策数据采集:事件类型为空");
                return;
            }
            // 从redis里取看redis里是否存在
            if (!RedisUtils.exists(RedisConstants.SENSORS_DATA_OPEN_ACCOUNT + userId)) {
                // 如果不存在
                logger.info("神策数据采集:用户ID在redis里不存在");
                return;
            }
            // 发送神策数据
            sensorsDataOpenAccountService.sendSensorsData(sensorsDataBean);

        }catch (Exception e){
            logger.error("神策数据统计:用户开户相关统计异常:" + e.getMessage());
            return;
        }
        return;
    }
}
