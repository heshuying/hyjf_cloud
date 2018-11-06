/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.sensorsdata.openaccount;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.mq.base.Consumer;
import com.hyjf.cs.trade.service.consumer.SensorsDataOpenAccountService;
import org.apache.commons.lang.StringUtils;
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

import java.util.List;

/**
 * 神策数据统计:用户开户相关
 *
 * @author liuyang
 * @version SensorsDataOpenAccountMessageConsumer, v0.1 2018/10/23 18:28
 */
@Component
public class SensorsDataOpenAccountMessageConsumer extends Consumer {

    private static final Logger logger = LoggerFactory.getLogger(SensorsDataOpenAccountMessageConsumer.class);

    @Autowired
    private SensorsDataOpenAccountService sensorsDataOpenAccountService;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.SENSORSDATA_OPEN_ACCOUNT_GROUP);
        //订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.SENSORSDATA_OPEN_ACCOUNT_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new SensorsDataOpenAccountMessageConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可
        defaultMQPushConsumer.start();
        logger.info("====SensorsDataOpenAccountMessageConsumer start=====");
    }


    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
            logger.info("神策数据统计:用户开户相关,收到消息，开始处理....");
            MessageExt paramBean = list.get(0);
            SensorsDataBean sensorsDataBean = null;
            String msgBody = new String(paramBean.getBody());
            try {
                sensorsDataBean = JSONObject.parseObject(msgBody, SensorsDataBean.class);
                if (sensorsDataBean == null) {
                    logger.error("神策数据采集:开户相关接收到的消息Bean为空");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                // 用户ID
                Integer userId = sensorsDataBean.getUserId();
                if (userId == null || userId == 0) {
                    logger.error("神策数据采集:开户用户ID为空");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                // 事件类型
                String evenCode = sensorsDataBean.getEventCode();
                if (StringUtils.isBlank(evenCode)) {
                    logger.error("神策数据采集:事件类型为空");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                // 从redis里取看redis里是否存在
                if (!RedisUtils.exists("SENSORS_DATA_OPEN_ACCOUNT:" + userId)) {
                    // 如果不存在
                    logger.info("神策数据采集:用户ID在redis里不存在");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                // 发送神策数据
                sensorsDataOpenAccountService.sendSensorsData(sensorsDataBean);

            }catch (Exception e){
                logger.info("神策数据统计:用户开户相关统计异常:" + e.getMessage());
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
