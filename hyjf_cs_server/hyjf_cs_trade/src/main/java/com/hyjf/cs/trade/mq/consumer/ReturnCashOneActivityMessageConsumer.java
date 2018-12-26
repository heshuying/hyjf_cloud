/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.client.AmMarketClient;
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
 * 纳觅返现活动计划放款更新放款时间
 * @author tanyy
 * @version ReturnCashOneActivityMessageConsumer, v0.1 2018/11/8 14:04
 */
@Service
@RocketMQMessageListener(topic = MQConstant.RETURN_CASH1_ACTIVITY_SAVE_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.RETURN_CASH1_ACTIVITY_SAVE_GROUP)
public class ReturnCashOneActivityMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private Logger _log = LoggerFactory.getLogger(ReturnCashOneActivityMessageConsumer.class);
    @Autowired
    AmMarketClient amMarketClient;
    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        _log.info("====纳觅返现消费端开始运行=====");
    }

    @Override
    public void onMessage(MessageExt msg) {
        if(msg == null || msg.getBody() == null){
            _log.error("【散标放款时间处理】接收到的消息为null");
            return;
        }
        String msgBody = new String(msg.getBody());
        _log.info("【散标放款时间处理】接收到的消息：" + msgBody);
        JSONObject requestJson;
        String borrowNid = null;
        int nowTime = 0;
        try {
            requestJson = JSONObject.parseObject(msgBody);

            borrowNid = (String) requestJson.get("borrowNid");
            nowTime = (int)requestJson.get("nowTime");
        } catch (Exception e1) {
            _log.error("散标放款时间处理参数解释失败：" + msgBody, e1);
            return;
        }

        try {
            amMarketClient.updateJoinTime(borrowNid,nowTime);
        }catch (Exception e){
            _log.error("【散标放款时间】处理失败", e);
        }
        _log.info("----------------------------散标放款时间处理结束--------------------------------" + this.toString());
    }
}
