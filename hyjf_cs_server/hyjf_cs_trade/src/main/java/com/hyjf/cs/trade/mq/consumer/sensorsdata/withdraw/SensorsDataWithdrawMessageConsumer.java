/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.sensorsdata.withdraw;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.service.consumer.SensorsDataWithdrawService;
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
 * 神策数据统计:用户提现相关
 *
 * @author liuyang
 * @version SensorsDataWithdrawMessageConsumer, v0.1 2018/10/22 16:46
 */
@Service
@RocketMQMessageListener(topic = MQConstant.SENSORSDATA_WITHDRAW_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.SENSORSDATA_WITHDRAW_GROUP)
public class SensorsDataWithdrawMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(SensorsDataWithdrawMessageConsumer.class);

    @Autowired
    private SensorsDataWithdrawService sensorsDataWithdrawService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====SensorsDataWithdrawMessageConsumer start=====");
    }

    @Override
    public void onMessage(MessageExt paramBean) {
        logger.info("神策数据采集:提现相关");
        SensorsDataBean sensorsDataBean = null;
        String msgBody = new String(paramBean.getBody());
        try {
            sensorsDataBean = JSONObject.parseObject(msgBody, SensorsDataBean.class);
            if (sensorsDataBean == null) {
                logger.info("神策数据采集:提现相关接收到的消息Bean为空");
                return;
            }
            // 提现订单号
            String orderId = sensorsDataBean.getOrderId();
            if (StringUtils.isBlank(orderId)) {
                logger.info("神策数据采集:提现订单号为空");
                return;
            }
            logger.info("神策数据采集:提现相关,提现订单号:[" + orderId + "].");
            // 事件类型
            String evenCode = sensorsDataBean.getEventCode();
            if (StringUtils.isBlank(evenCode)) {
                logger.info("神策数据采集:事件类型为空");
                return;
            }
            // 发送神策数据
            sensorsDataWithdrawService.sendSensorsData(sensorsDataBean);
        } catch (Exception e) {
            logger.info("神策数据统计:用户注册相关统计异常:" + e.getMessage());
            return;
        }
        return;
    }
}
