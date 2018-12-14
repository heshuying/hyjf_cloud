/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.sensorsdata.hjh;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.service.consumer.SensorsDataHjhInvestService;
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

import java.util.Map;

/**
 * 神策数据采集:汇计划提交结果
 *
 * @author liuyang
 * @version SensorsDataHjhInvestMessageConsumer, v0.1 2018/10/23 9:29
 */
@Service
@RocketMQMessageListener(topic = MQConstant.SENSORSDATA_HJH_INVEST_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.SENSORSDATA_HJH_INVEST_GROUP)
public class SensorsDataHjhInvestMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(SensorsDataHjhInvestMessageConsumer.class);

    @Autowired
    private SensorsDataHjhInvestService sensorsDataHjhInvestService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer){
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====SensorsDataHjhInvestMessageConsumer start=====");
    }

    @Override
    public void onMessage(MessageExt paramBean) {
        logger.info("神策数据采集:汇计划相关");
        SensorsDataBean sensorsDataBean = null;
        String msgBody = new String(paramBean.getBody());
        try {
            sensorsDataBean = JSONObject.parseObject(msgBody, SensorsDataBean.class);
            if (sensorsDataBean == null) {
                logger.info("神策数据采集:汇计划相关接收到的消息Bean为空");
                return;
            }

            // 预置属性
            Map<String, Object> presetProps = sensorsDataBean.getPresetProps();
            // 匿名ID
            String distinctId = "";
            if (presetProps.get("_distinct_id") != null) {
                distinctId = (String) presetProps.get("_distinct_id");
            }
            //  设备ID
            String deviceId = "";
            if (presetProps.get("$device_id") != null) {
                deviceId = (String) presetProps.get("$device_id");
            }
            if (StringUtils.isBlank(distinctId) && StringUtils.isBlank(deviceId)) {
                logger.error("神策数据采集:汇计划相关,获取匿名ID为空");
                return;
            }
            // 订单号
            String orderId = sensorsDataBean.getOrderId();
            if (StringUtils.isBlank(orderId)) {
                logger.error("神策数据采集:汇计划相关,获取加入订单号为空");
                return;
            }
            // 事件类型
            String eventCode = sensorsDataBean.getEventCode();
            if (StringUtils.isBlank(eventCode)) {
                logger.error("神策数据采集:汇计划相关,获取事件类型为空");
                return;
            }

            logger.info("神策数据采集:汇计划相关,投资订单号:[" + orderId + "].");
            // 发送神策数据
            sensorsDataHjhInvestService.sendSensorsData(sensorsDataBean);
        } catch (Exception e) {
            logger.error("神策数据统计:汇计划相关统计异常:" + e.getMessage());
            return;
        }
        return;
    }

}
