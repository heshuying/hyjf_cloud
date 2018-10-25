/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.sensorsdata.credit;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.mq.base.Consumer;
import com.hyjf.cs.trade.mq.consumer.sensorsdata.register.SensorsDataRegisterMessageConsumer;
import com.hyjf.cs.trade.mq.consumer.sensorsdata.withdraw.SensorsDataWithdrawMessageConsumer;
import com.hyjf.cs.trade.service.consumer.SensorsDataCreditSerivce;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 神策数据采集:债转相关
 *
 * @author liuyang
 * @version SensorsDataCreditMessageConsumer, v0.1 2018/10/22 16:55
 */
@Component
public class SensorsDataCreditMessageConsumer extends Consumer {

    private static final Logger logger = LoggerFactory.getLogger(SensorsDataCreditMessageConsumer.class);

    @Autowired
    private SensorsDataCreditSerivce sensorsDataCreditSerivce;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.SENSORSDATA_CREDIT_GROUP);
        //订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.SENSORSDATA_CREDIT_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new SensorsDataCreditMessageConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可
        defaultMQPushConsumer.start();
        logger.info("====SensorsDataCreditMessageConsumer start=====");
    }


    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
            logger.info("神策数据采集:债转相关");
            MessageExt paramBean = list.get(0);
            SensorsDataBean sensorsDataBean = null;
            String msgBody = new String(paramBean.getBody());
            try {
                sensorsDataBean = JSONObject.parseObject(msgBody, SensorsDataBean.class);
                if (sensorsDataBean == null) {
                    logger.info("神策数据采集:债转相关接收到的消息Bean为空");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                // 预置属性
                // 预置属性只有在发起转让时传
                Map<String, Object> presetProps = sensorsDataBean.getPresetProps();
                if (presetProps != null) {
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
                        logger.error("神策数据采集:债转相关,获取匿名ID为空");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                }

                // 用户ID
                Integer userId = sensorsDataBean.getUserId();
                if (userId == null || userId == 0) {
                    logger.error("神策数据采集:债转相关,获取用户ID为空");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                // 事件类型
                String eventCode = sensorsDataBean.getEventCode();
                if (StringUtils.isBlank(eventCode)) {
                    logger.error("神策数据采集:事件类型为空");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                // 发送神策数据
                sensorsDataCreditSerivce.sendSensorsData(sensorsDataBean);

            } catch (Exception e) {
                logger.info("神策数据统计:债转相关:" + e.getMessage());
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
