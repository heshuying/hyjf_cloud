/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.consumer;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.mq.base.Consumer;
import com.hyjf.am.trade.service.front.consumer.SyncRUserService;
import com.hyjf.common.constants.MQConstant;

/**
 * r_user用户信息同步处理器
 * 
 * @author dxj
 * @version SyncRUserConsumer.java, v0.1 2018年6月20日 下午6:09:19
 */
//@Component
public class SyncRUserConsumer extends Consumer {

    private static final Logger logger = LoggerFactory.getLogger(SyncRUserConsumer.class);
    
    @Autowired
    SyncRUserService syncRUserService;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        // defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.AM_TRADE_GENERAL_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.SYNC_RUSER_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);

        // 设置并发数
        defaultMQPushConsumer.setConsumeThreadMin(1);
        defaultMQPushConsumer.setConsumeThreadMax(1);
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);
        defaultMQPushConsumer.setConsumeTimeout(30);

        defaultMQPushConsumer.registerMessageListener(new MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====Ruser 用户信息同步=====");
    }

    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            logger.info("Ruser 用户信息同步...." + msgs.size());

            try {

                MessageExt msg = msgs.get(0);
                String tagName = msg.getTags();
                String jsonMsg = new String(msg.getBody());
                JSONObject jsonObj = JSON.parseObject(jsonMsg);

                logger.info(tagName+" Ruser: " + jsonMsg);

                if ("ht_user_info".equals(tagName)) {

                    syncRUserService.updateUserInfo(jsonObj);
                    
                } else if ("ht_user".equals(tagName)) {
                    
                    syncRUserService.insertUser(jsonObj);

                } else if ("ht_spreads_user".equals(tagName)) {
                    
                    syncRUserService.updateSpreadUser(jsonObj);

                }

                logger.info(tagName+" Ruser同步OK ");
                
            } catch (Exception e1) {
                logger.error(e1.getMessage());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            // 如果没有return success ，consumer会重新消费该消息，直到return success
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

        }
    }
}
