/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.controller.websocket.WebSocketServer;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.resquest.admin.ReturnCashRequest;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.ActivityDateUtil;
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

import java.io.IOException;
import java.math.BigDecimal;

/**
 * 大屏三消息
 * @author tanyy
 * @version UserScreenPictureThreeConsumer,
 */
@Service
@RocketMQMessageListener(topic = MQConstant.USER_SCREEN_PICTURE3_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.USER_SCREEN_PICTURE3_GROUP)
public class UserScreenPictureThreeConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private Logger _log = LoggerFactory.getLogger(UserScreenPictureThreeConsumer.class);

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        _log.info("====大屏三消息开始运行=====");
    }

    @Override
    public void onMessage(MessageExt message) {
        if(message == null || message.getBody() == null){
            _log.error("【大屏三消息】接收到的消息为null");
            return;
        }

        String msgBody = new String(message.getBody());
        _log.info("【大屏三消息】接收到的消息：" + msgBody);
        try {
            WebSocketServer.sendInfo(msgBody,"user_picture_1");
        }catch (IOException e){
            _log.info("大屏三消息出现异常");
        }

    }
}
