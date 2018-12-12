package com.hyjf.am.config.mq.base;

import com.hyjf.common.exception.MQException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

/**
 * 通用发消息
 *
 * @author dxj
 */
@Component
public class CommonProducer {
    private static final Logger logger = LoggerFactory.getLogger(CommonProducer.class);

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送普通队列通用方法
     * @param messageContent
     * @return
     * @throws MQException
     */
    public boolean messageSend(MessageContent messageContent) throws MQException {
        try {
            SendResult sendResult = rocketMQTemplate.syncSend(messageContent.topic + ":" + messageContent.tag, messageContent.body);

            if (sendResult != null && sendResult.getSendStatus() == SendStatus.SEND_OK) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("通用发消息异常", e);
            throw new MQException("mq send error", e);
        }
    }

    /**
     * 发送延时队列通用方法
     * @param messageContent
     * @param delayLevel
     * @return
     * @throws MQException
     */
    public boolean messageSendDelay(MessageContent messageContent, int delayLevel) throws MQException {
        try {
            GenericMessage genericMessage = new GenericMessage(messageContent.body);
            SendResult sendResult = rocketMQTemplate.syncSend(messageContent.topic + ":" + messageContent.tag, genericMessage, rocketMQTemplate.getProducer().getSendMsgTimeout(), delayLevel);

            if (sendResult != null && sendResult.getSendStatus() == SendStatus.SEND_OK) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("通用延时消息发送异常", e);
            throw new MQException("mq send error", e);
        }
    }
}
