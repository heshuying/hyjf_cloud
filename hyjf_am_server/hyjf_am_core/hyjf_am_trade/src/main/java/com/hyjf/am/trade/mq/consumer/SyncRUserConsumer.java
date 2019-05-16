/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.service.front.consumer.SyncRUserService;
import com.hyjf.common.constants.MQConstant;
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
 * r_user用户信息同步处理器
 * 
 * @author dxj
 * @version SyncRUserConsumer.java, v0.1 2018年6月20日 下午6:09:19
 */
@Service
@RocketMQMessageListener(topic = MQConstant.SYNC_RUSER_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.AM_TRADE_GENERAL_GROUP)
public class SyncRUserConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(SyncRUserConsumer.class);
    
    @Autowired
    private SyncRUserService syncRUserService;

    @Override
    public void onMessage(MessageExt messageExt) {
        logger.info("Ruser 用户信息同步....");
        // RUser根据userId更新时，同步失败抛出异常，重新消费 update by wgx 2019/05/05
        MessageExt msg = messageExt;
        String tagName = null;
        String jsonMsg = null;
        JSONObject jsonObj = null;
        try {
            tagName = msg.getTags();
            jsonMsg = new String(msg.getBody());
            jsonObj = JSON.parseObject(jsonMsg);
        } catch (Exception e) {
            logger.error("【RUser同步】解析失败!", e);
            return;
        }
        logger.info("【{}】Ruser:{}", tagName, jsonMsg);
        if ("ht_user_info".equals(tagName)) {
            syncRUserService.updateUserInfo(jsonObj);
        } else if ("up_ht_user".equals(tagName)) {
            syncRUserService.updateUser(jsonObj);
        } else if ("ht_spreads_user".equals(tagName)) {
            syncRUserService.updateSpreadUser(jsonObj);
        } else {
            try {
                if ("ht_user".equals(tagName)) {
                    syncRUserService.insertUser(jsonObj);
                } else if ("ht_user_info_referrer".equals(tagName)) {
                    syncRUserService.updateUserInfoByReferrer(jsonObj);
                }
            } catch (Exception e) {
                logger.error("【RUser同步】消费失败!", e);
                return;
            }
        }
        logger.info("【{}】Ruser同步OK",tagName);
        return;
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
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
        logger.info("====Ruser 用户信息同步=====");
    }
}
