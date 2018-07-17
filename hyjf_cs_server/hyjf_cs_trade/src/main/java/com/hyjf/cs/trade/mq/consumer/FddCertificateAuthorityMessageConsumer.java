/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.am.vo.user.FddCertificateAuthorityVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.handle.FddCertificateAuthorityMessageHandle;
import com.hyjf.cs.trade.mq.base.Consumer;
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

/**
 * @author: sunpeikai
 * @version: FddCertificateAuthorityMessageConsumer, v0.1 2018/7/17 9:33
 */
@Component
public class FddCertificateAuthorityMessageConsumer extends Consumer {

    private static final Logger logger = LoggerFactory.getLogger(FddUserInfoChangeMessageConsumer.class);

    @Autowired
    private FddCertificateAuthorityMessageHandle fddCertificateAuthorityMessageHandle;

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
        defaultMQPushConsumer.registerMessageListener(new MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====fdd certificate authority consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            logger.info("法大大CA认证 收到消息，开始处理....msgs is :{}", msgs);
            MessageExt msg = null;
            if (msgs != null && msgs.size() > 0) {
                msg = msgs.get(0);
            }

            // 如果msg为空
            if (msg == null || msg.getBody() == null) {
                logger.error("【法大大CA认证】接收到的消息为null");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            // --> 消息转换
            String msgBody = new String(msg.getBody());
            logger.info("法大大CA认证接收消息：[" + msgBody + "].");

            FddCertificateAuthorityVO fddCertificateAuthorityVO = null;
            try{
                fddCertificateAuthorityVO = JSONObject.parseObject(msgBody,FddCertificateAuthorityVO.class);
                if (fddCertificateAuthorityVO == null || fddCertificateAuthorityVO.getUserId() == null || fddCertificateAuthorityVO.getUserId() == 0) {
                    logger.info("解析为空：" + msgBody);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }catch(Exception e){
                logger.error("【法大大CA认证】参数解析错误");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            Integer userId = fddCertificateAuthorityVO.getUserId();
            logger.info("【法大大CA认证】逻辑处理开始..用户ID:[{}]",userId);

            try{
                // 根据用户ID获取用户信息
                UserVO userVO = fddCertificateAuthorityMessageHandle.getUserByUserId(userId);
                if (userVO == null) {
                    logger.info("根据用户ID获取用户信息失败,用户ID:[" + userId + "].");
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                UserInfoVO userInfoVO = fddCertificateAuthorityMessageHandle.getUserInfoByUserId(userId);
                if (userInfoVO == null) {
                    logger.info("根据用户ID获取用户详情信息失败,用户ID:[" + userId + "].");
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                // 调用法大大CA认证接口
                boolean flag = fddCertificateAuthorityMessageHandle.updateUserCAInfo(userId);
                if(!flag){
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                Map<String, String> map = Maps.newHashMap();
                map.put("userId", String.valueOf(userId));

                //crm投资推送
                // rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_BANCKOPEN_CRM, JSON.toJSONString(map));
                logger.info("开户发送MQ时间【{}】",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            }catch(Exception e){

            }

            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}
