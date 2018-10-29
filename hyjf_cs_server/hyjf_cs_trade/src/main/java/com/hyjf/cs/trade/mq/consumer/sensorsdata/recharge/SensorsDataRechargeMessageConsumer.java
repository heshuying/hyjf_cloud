/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.sensorsdata.recharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.mq.base.Consumer;
import com.hyjf.cs.trade.service.consumer.SensorsDataRechargeService;
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

/**
 * 神策数据统计:用户充值相关
 *
 * @author liuyang
 * @version SensorsDataRechargeMessageConsumer, v0.1 2018/10/22 15:04
 */
@Component
public class SensorsDataRechargeMessageConsumer extends Consumer {

    private static final Logger logger = LoggerFactory.getLogger(SensorsDataRechargeMessageConsumer.class);

    @Autowired
    private SensorsDataRechargeService sensorsDataRechargeService;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.SENSORSDATA_RECHARGE_GROUP);
        //订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.SENSORSDATA_RECHARGE_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new SensorsDataRechargeMessageConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可
        defaultMQPushConsumer.start();
        logger.info("====SensorsDataRechargeMessageConsumer start=====");
    }

    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
            logger.info("神策数据统计:用户充值相关,收到消息，开始处理....");

            MessageExt paramBean = list.get(0);
            SensorsDataBean sensorsDataBean = null;
            String msgBody = new String(paramBean.getBody());
            try {
                sensorsDataBean = JSONObject.parseObject(msgBody, SensorsDataBean.class);
                if (sensorsDataBean == null) {
                    logger.info("神策数据采集:充值相关接收到的消息Bean为空");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                // 充值订单号
                String orderId = sensorsDataBean.getOrderId();
                if (StringUtils.isBlank(orderId)) {
                    logger.info("神策数据采集:充值订单号为空");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                logger.info("神策数据统计:用户充值相关,充值订单号:[" + orderId + "]");
                // 事件类型
                String evenCode = sensorsDataBean.getEventCode();
                if (StringUtils.isBlank(evenCode)) {
                    logger.info("神策数据采集:事件类型为空");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                // 发送神策数据
                sensorsDataRechargeService.sendSensorsData(sensorsDataBean);
            } catch (Exception e) {
                logger.info("神策数据统计:用户充值相关统计异常:" + e.getMessage());
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
