/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.sensorsdata.recharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.service.consumer.SensorsDataRechargeService;
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
 * 神策数据统计:用户充值相关
 *
 * @author liuyang
 * @version SensorsDataRechargeMessageConsumer, v0.1 2018/10/22 15:04
 */
@Service
@RocketMQMessageListener(topic = MQConstant.SENSORSDATA_RECHARGE_TOPIC,selectorExpression = "*", consumerGroup = MQConstant.SENSORSDATA_RECHARGE_GROUP)
public class SensorsDataRechargeMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(SensorsDataRechargeMessageConsumer.class);

    @Autowired
    private SensorsDataRechargeService sensorsDataRechargeService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====SensorsDataRechargeMessageConsumer start=====");
    }

    @Override
    public void onMessage(MessageExt paramBean) {
        logger.info("神策数据统计:用户充值相关,收到消息，开始处理....");
        SensorsDataBean sensorsDataBean = null;
        String msgBody = new String(paramBean.getBody());
        try {
            sensorsDataBean = JSONObject.parseObject(msgBody, SensorsDataBean.class);
            if (sensorsDataBean == null) {
                logger.info("神策数据采集:充值相关接收到的消息Bean为空");
                return;
            }
            // 充值订单号
            String orderId = sensorsDataBean.getOrderId();
            if (StringUtils.isBlank(orderId)) {
                logger.info("神策数据采集:充值订单号为空");
                return;
            }
            logger.info("神策数据统计:用户充值相关,充值订单号:[" + orderId + "]");
            // 事件类型
            String evenCode = sensorsDataBean.getEventCode();
            if (StringUtils.isBlank(evenCode)) {
                logger.info("神策数据采集:事件类型为空");
                return;
            }
            // 发送神策数据
            sensorsDataRechargeService.sendSensorsData(sensorsDataBean);
        } catch (Exception e) {
            logger.error("神策数据统计:用户充值相关统计异常:" + e.getMessage());
            return;
        }
        return;
    }
}
