/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.market.service.DuiBaOrderService;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.pay.lib.duiba.sdk.CreditConsumeParams;
import org.apache.commons.lang3.StringUtils;
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
 * @author wangjun
 * @version DuiBaOrderConsumer, v0.1 2019/6/10 9:16
 */
@Service
@RocketMQMessageListener(topic = MQConstant.DUIBA_ORDER_TOPIC, consumerGroup = MQConstant.DUIBA_ORDER_GROUP)
public class DuiBaOrderConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(DuiBaOrderConsumer.class);
    @Autowired
    DuiBaOrderService duiBaOrderService;

    @Override
    public void onMessage(MessageExt messageExt){
        logger.info("兑吧生成订单收到消息，开始处理.....");
        CreditConsumeParams creditConsumeParams;
        // 兑吧订单号
        String duiBaOrderId = "";
        try {
            creditConsumeParams = JSONObject.parseObject(messageExt.getBody(), CreditConsumeParams.class);
            // 判断几个主要参数
            if(null == creditConsumeParams || StringUtils.isBlank(creditConsumeParams.getUid())
                    || StringUtils.isBlank(creditConsumeParams.getOrderNum())){
                logger.error("兑吧扣积分回调参数错误");
                return;
            }
            // 用兑吧的订单号查询订单
            duiBaOrderId = creditConsumeParams.getOrderNum();
            int count = duiBaOrderService.countDuiBaOrder(duiBaOrderId);
            // 没有查到订单则插入订单，否则打错误日志
            if(count == 0){
                duiBaOrderService.insertDuiBaOrder(creditConsumeParams);
            } else {
                logger.error("兑吧订单号已存在相应订单，兑吧订单号:{}", duiBaOrderId);
                return;
            }

        } catch (Exception e){
            logger.error("兑吧生成订单失败，订单号:{}", duiBaOrderId, e);
            throw new RuntimeException("兑吧生成订单失败");
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer){
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        // 最多重试3次
        defaultMQPushConsumer.setMaxReconsumeTimes(3);
        logger.info("====兑吧生成订单消费端启动=====");
    }
}
