/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.sensorsdata.login;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.service.consumer.SensorsDataLoginService;
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
 * 神策数据统计:用户登陆相关
 *
 * @author liuyang
 * @version SensorsDataLoginMessageConsumer, v0.1 2018/10/19 14:08
 */
@Service
@RocketMQMessageListener(topic = MQConstant.SENSORSDATA_LOGIN_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.SENSORSDATA_LOGIN_GROUP)
public class SensorsDataLoginMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(SensorsDataLoginMessageConsumer.class);

    @Autowired
    private SensorsDataLoginService sensorsDataLoginService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====SensorsDataLoginMessageConsumer start=====");
    }

    @Override
    public void onMessage(MessageExt paramBean) {
        logger.info("神策数据采集:登录相关");
        SensorsDataBean sensorsDataBean = null;
        String msgBody = new String(paramBean.getBody());

        logger.info("msgBody:[" + msgBody + "].");
        try {

            sensorsDataBean = JSONObject.parseObject(msgBody, SensorsDataBean.class);
            // 预置属性
            Map<String, Object> presetProps = sensorsDataBean.getPresetProps();
            logger.info("用户登陆预置属性:[" + presetProps.toString() + "],用户ID:[" + sensorsDataBean.getUserId() + "].");

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
                logger.error("神策数据采集:登录相关,匿名ID,设备ID为空");
                return;
            }

            // 用户ID
            Integer userId = sensorsDataBean.getUserId();
            if (userId == null || userId == 0) {
                logger.error("神策数据采集:登录相关,获取用户ID为空");
                return;
            }
            // 发送神策数据
            sensorsDataLoginService.sendSensorsData(sensorsDataBean);
        } catch (Exception e) {
            logger.info("神策数据统计:用户登陆相关统计异常:" + e.getMessage());
            return;
        }
        return;
    }
}
