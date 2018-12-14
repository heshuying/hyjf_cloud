/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.consumer.hjh.calculatefairvalue;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.service.front.hjh.HjhCalculateFairValueService;
import com.hyjf.am.vo.trade.hjh.HjhCalculateFairValueVO;
import com.hyjf.common.constants.MQConstant;
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
 * 汇计划计算计划加入订单公允价值MQ消费者
 *
 * @author liuyang
 * @version HjhCalculateFairValueConsumer, v0.1 2018/6/26 17:12
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HJH_CALCULATE_FAIR_VALUE_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.HJH_CALCULATE_FAIR_VALUE_GROUP)
public class HjhCalculateFairValueConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(HjhCalculateFairValueConsumer.class);

    @Autowired
    private HjhCalculateFairValueService hjhCalculateFairValueService;

    @Override
    public void onMessage(MessageExt messageExt) {
        logger.info("HjhCalculateFairValueConsumer 收到消息，开始处理....");
        MessageExt msg = messageExt;
        try {
            HjhCalculateFairValueVO hjhCalculateFairValueVO = JSONObject.parseObject(msg.getBody(), HjhCalculateFairValueVO.class);
            // 计划加入订单号
            String accedeOrderId = hjhCalculateFairValueVO.getAccedeOrderId();
            // 计划订单号为空
            if (StringUtils.isBlank(accedeOrderId)) {
                logger.error("计划订单号为空");
                return;
            }
            // 计算类型
            Integer calculateType = hjhCalculateFairValueVO.getCalculateType();
            // 根据加入订单号查询计划订单
            HjhAccede hjhAccede = hjhCalculateFairValueService.selectHjhAccedeByAccedeOrderId(accedeOrderId);
            if (hjhAccede == null) {
                logger.error("根据计划加入订单号查询计划订单为空,计划加入订单号:[" + accedeOrderId + "].");
                return;
            }
            // 计划加入订单状态为:已退出
            if (hjhAccede.getOrderStatus() == 7) {
                logger.info("计划订单已退出,不需要计算公允价值,计划加入订单号:[" + accedeOrderId + "].");
                return;
            }
            logger.info("计划加入订单计算公允价值:计划加入订单号:[" + accedeOrderId + "].");
            // 计算加入订单的公允价值
            hjhCalculateFairValueService.calculateFairValue(hjhAccede, calculateType);
            // 如果没有return success ，consumer会重新消费该消息，直到return success
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====HjhCalculateFairValueConsumer start=====");
    }
}
