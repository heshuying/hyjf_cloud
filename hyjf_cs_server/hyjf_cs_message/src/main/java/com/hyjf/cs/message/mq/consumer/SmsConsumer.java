package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.cs.message.handler.SmsHandler;
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
 * @author xiasq
 * @version SmsConsumer, v0.1 2018/4/12 14:58
 */

@Service
@RocketMQMessageListener(topic = MQConstant.SMS_CODE_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.SMS_CODE_GROUP)
public class SmsConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(SmsConsumer.class);

    private static int MAX_RECONSUME_TIME = 3;

    @Autowired
    private SmsHandler smsHandler;

    @Override
    public void onMessage(MessageExt message) {
        MessageExt msg = message;
        SmsMessage smsMessage = JSONObject.parseObject(msg.getBody(), SmsMessage.class);
        logger.info("SmsConsumer 收到消息，开始处理....smsMessage is :{}", smsMessage);
        if (null != smsMessage) {
            switch (smsMessage.getServiceType()) {
                case MessageConstant.SMS_SEND_FOR_MANAGER:
                    smsHandler.sendMessages(smsMessage.getTplCode(), smsMessage.getReplaceStrs(), smsMessage.getSender(),
                            smsMessage.getChannelType());
                    break;
                case MessageConstant.SMS_SEND_FOR_MOBILE:
                    smsHandler.sendMessages(smsMessage.getMobile(), smsMessage.getTplCode(), smsMessage.getReplaceStrs(),
                            smsMessage.getChannelType());
                    break;
                case MessageConstant.SMS_SEND_FOR_USER:
                    smsHandler.sendMessages(smsMessage.getUserId(), smsMessage.getTplCode(), smsMessage.getReplaceStrs(),
                            smsMessage.getChannelType());
                    break;
                case MessageConstant.SMS_SEND_FOR_USERS_NO_TPL:
                    try {
                        smsHandler.sendMessage(smsMessage.getMobile(), smsMessage.getMessage(),
                                smsMessage.getServiceType(), null, smsMessage.getChannelType(), smsMessage.getIsDisplay());
                    } catch (Exception e) {
                        logger.error("send sms error....");
                    }
                    break;
                case MessageConstant.SMS_SEND_FOR_BIRTHDAY:
                    try {
                        smsHandler.sendMessage(smsMessage.getMobile(), smsMessage.getMessage(),
                                smsMessage.getServiceType(), null, smsMessage.getChannelType(), smsMessage.getIsDisplay());
                    } catch (Exception e) {
                        logger.error("send sms error....");
                    }
                    break;
                default:
                    logger.error("error sms type...");
            }
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        // MQ默认集群消费
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        //设置最大重试次数
        defaultMQPushConsumer.setMaxReconsumeTimes(MAX_RECONSUME_TIME);
        logger.info("====sms consumer=====");
    }
}
