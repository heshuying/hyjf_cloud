package com.hyjf.am.user.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.mq.base.Consumer;
import com.hyjf.am.user.mq.base.MessageContent;
import com.hyjf.am.user.mq.producer.CrmBankOpenMessageProducer;
import com.hyjf.am.user.service.front.ca.FddCertificateService;
import com.hyjf.am.vo.user.FddCertificateAuthorityVO;
import com.hyjf.common.constants.MQConstant;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhangqingqing
 * @version FddCertificateConsumer, v0.1 2018/6/19 16:46
 */
@Component
public class FddCertificateConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(FddCertificateConsumer.class);

    @Autowired
    FddCertificateService fddCertificateService;

    @Autowired
    CrmBankOpenMessageProducer crmBankOpenMessageProducer;
    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.FDD_CERTIFICATE_AUTHORITY_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.FDD_CERTIFICATE_AUTHORITY_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new FddCertificateConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====FddCertificateConsumer consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            logger.info("FddCertificateConsumer 收到消息，开始处理....msgs is :{}", msgs);

            for (MessageExt msg : msgs) {
                // --> 消息转换
                String msgBody = new String(msg.getBody());
                logger.info("法大大CA认证接收消息：[" + msgBody + "].");
                FddCertificateAuthorityVO fddCertificateAuthorityVO = new FddCertificateAuthorityVO();
                try {
                    fddCertificateAuthorityVO = JSONObject.parseObject(msgBody, FddCertificateAuthorityVO.class);
                    if (fddCertificateAuthorityVO == null || fddCertificateAuthorityVO.getUserId() == null || fddCertificateAuthorityVO.getUserId() == 0) {
                       logger.info("解析为空：" + msgBody);
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                } catch (Exception e) {
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                logger.info("法大大CA认证逻辑处理开始,用户ID:[" + fddCertificateAuthorityVO.getUserId() + "].");

                try {
                    Integer userId = fddCertificateAuthorityVO.getUserId();
                    // 根据用户ID获取用户信息
                    User user = fddCertificateService.findUserByUserId(userId);
                    if (user == null) {
                        logger.info("根据用户ID获取用户信息失败,用户ID:[" + userId + "].");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    UserInfo userInfo = fddCertificateService.findUsersInfo(userId);
                    if (userInfo == null) {
                        logger.info("根据用户ID获取用户详情信息失败,用户ID:[" + userId + "].");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    // 调用法大大CA认证接口
                   fddCertificateService.updateUserCAInfo(userId,user,userInfo);
                    Map<String, String> map = Maps.newHashMap();
                    map.put("userId", String.valueOf(userId).trim());
                    //crm投资推送
                    crmBankOpenMessageProducer.messageSend(new MessageContent(MQConstant.CRM_ROUTINGKEY_BANCKOPEN_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(map)));
                    logger.info("开户发送MQ时间【{}】",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
            // 如果没有return success ，consumer会重新消费该消息，直到return success
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}