/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.sensorsdata.hzt;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.service.consumer.SensorsDataHztInvestService;
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
 * 神策数据统计:汇直投相关
 *
 * @author liuyang
 * @version SensorsDataHztInvestMessageConsumer, v0.1 2018/10/23 16:09
 */
@Service
@RocketMQMessageListener(topic = MQConstant.SENSORSDATA_HZT_INVEST_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.SENSORSDATA_HZT_INVEST_GROUP)
public class SensorsDataHztInvestMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(SensorsDataHztInvestMessageConsumer.class);

    @Autowired
    private SensorsDataHztInvestService sensorsDataHztInvestService;


    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====SensorsDataHztInvestMessageConsumer start=====");
    }

    @Override
    public void onMessage(MessageExt paramBean) {
        logger.info("神策数据采集:汇直投相关");
        SensorsDataBean sensorsDataBean = null;
        String msgBody = new String(paramBean.getBody());
        try {
            sensorsDataBean = JSONObject.parseObject(msgBody, SensorsDataBean.class);
            if (sensorsDataBean == null) {
                logger.info("神策数据采集:汇直投相关接收到的消息Bean为空");
                return;
            }

            // 订单号
            String orderId = sensorsDataBean.getOrderId();
            if (StringUtils.isBlank(orderId)) {
                logger.error("神策数据采集:汇直投相关,获取投资订单号为空");
                return;
            }

            logger.info("神策数据采集:汇直投相关,投资订单号:[" + orderId + "].");
            // 发送神策数据
            sensorsDataHztInvestService.sendSensorsData(sensorsDataBean);
        } catch (Exception e) {
            logger.error("神策数据统计:汇直投相关统计异常:" + e.getMessage());
            return;
        }
        return;
    }
}
