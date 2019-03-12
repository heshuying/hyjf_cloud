package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.cs.message.handler.MailHandler;
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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 * @author xiasq
 * @version MailConsumer, v0.1 2018/4/12 14:58
 */

@Service
@RocketMQMessageListener(topic = MQConstant.MAIL_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.MAIL_GROUP)
public class MailConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(MailConsumer.class);

    private static int MAX_RECONSUME_TIME = 3;

    @Autowired
    private MailHandler mailHandler;
    @Override
    public void onMessage(MessageExt  message) {
        MailMessage mailMessage = JSONObject.parseObject(message.getBody(), MailMessage.class);
        logger.info("MailConsumer 收到消息，开始处理....mailMessage is :{}", mailMessage);
        if (null != mailMessage) {
            switch (mailMessage.getServiceType()) {
                case MessageConstant.MAIL_SEND_FOR_USER:
                    mailHandler.sendMail(mailMessage.getUserId(), mailMessage.getSubject(), mailMessage.getBody(),
                            mailMessage.getFileNames());
                    break;
                case MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS:
                    mailHandler.sendMail(mailMessage.getToMailArray(), mailMessage.getSubject(),
                            mailMessage.getMailKbn(), mailMessage.getReplaceStrs(), mailMessage.getFileNames());
                    break;
                case MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS_MSG:
                    mailHandler.sendMail(mailMessage.getToMailArray(), mailMessage.getSubject(), mailMessage.getBody(),
                            mailMessage.getFileNames());
                    break;
                case MessageConstant.MAIL_SEND_FRO_SELL_DAILY:
                    String content = "";
                    String[] fileNames = mailMessage.getFileNames();
                    logger.info("销售日报发送邮件消费者接受参数 : {}", mailMessage.getToMailArray(), mailMessage.getSubject(), fileNames, mailMessage.getIs());
                    try {
                        mailHandler.sendMail(mailMessage.getToMailArray(), mailMessage.getSubject(),
                                content, fileNames, mailMessage.getIs());
                    } catch (Exception e) {
                        throw new MailSendException("发送销售邮件失败");
                    }
                    break;

                default:
                    logger.error("error mail type...");
                    return ;
            }
            if (null != mailMessage.getFileNames()) {
                if (mailMessage.getFileNames().length > 1) {
                    File f = new File(mailMessage.getFileNames()[0] + mailMessage.getFileNames()[1]);
                    if (f.exists()) {
                        f.delete();
                    }
                    File dir = new File(mailMessage.getFileNames()[0]);
                    if (dir.exists()) {
                        dir.delete();
                    }
                } else if (mailMessage.getFileNames().length == 1) {
                    File dir = new File(mailMessage.getFileNames()[0]);
                    if (dir.exists()) {
                        dir.delete();
                    }
                }
            }
        }
    }
    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        //设置最大重试次数
        defaultMQPushConsumer.setMaxReconsumeTimes(MAX_RECONSUME_TIME);
        logger.info("====FddCertificateConsumer consumer=====");
    }



}


