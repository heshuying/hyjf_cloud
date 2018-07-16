/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.handle.FddUserInfoChangeMessageHandle;
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

import java.util.List;

/**
 * @author: sunpeikai
 * @version: FddUserInfoChangeMessageConsumer, v0.1 2018/7/16 9:30
 */
@Component
public class FddUserInfoChangeMessageConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(FddUserInfoChangeMessageConsumer.class);

    @Autowired
    private FddUserInfoChangeMessageHandle fddUserInfoChangeMessageHandle;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.FDD_USERINFO_CHANGE_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.FDD_USERINFO_CHANGE_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====fdd userInfo change consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            logger.info("法大大修改客户信息 收到消息，开始处理....msgs is :{}", msgs);

            MessageExt msg = null;
            if (msgs != null && msgs.size() > 0) {
                msg = msgs.get(0);
            }

            // 如果msg为空
            if (msg == null || msg.getBody() == null) {
                logger.error("【法大大修改客户信息】接收到的消息为null");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            // 定义参数接收
            UserInfoVO userInfoVO = null;
            // --> 消息转换
            String msgBody = new String(msg.getBody());
            logger.info("【法大大修改客户信息】接收到的消息：" + msgBody);

            try {
                userInfoVO = JSONObject.parseObject(msgBody, UserInfoVO.class);
                if (null == userInfoVO || null == userInfoVO.getUserId() || userInfoVO.getUserId() == 0) {
                    logger.error("【法大大修改客户信息】参数错误");
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            } catch (Exception e) {
                logger.error("【法大大修改客户信息】参数解析错误");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            logger.info("【法大大修改客户信息】逻辑处理开始...");

            Integer userId = userInfoVO.getUserId();

            try {
                UserVO userVO = fddUserInfoChangeMessageHandle.getUserByUserId(userId);
                if (userVO == null) {
                    logger.info("【法大大修改客户信息】根据用户ID查询用户信息失败,用户ID:[" + userId + "].");
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                UserInfoVO userInfo = fddUserInfoChangeMessageHandle.getUserInfoByUserId(userId);
                if (userInfo == null) {
                    logger.info("【法大大修改客户信息】根据用户ID查询用户详情信息失败,用户ID:[" + userId + "].");
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                // 根据用户ID查询法大大CA认证相关信息
                CertificateAuthorityVO certificateAuthorityVO = fddUserInfoChangeMessageHandle.getCertificateAuthorityByUserId(userId);
                if (certificateAuthorityVO == null) {
                    logger.info("【法大大修改客户信息】根据用户ID获取用户CA认证信息失败,用户ID:[" + userId + "].");
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                // 获取手机号
                String mobile = certificateAuthorityVO.getMobile();
                // 获取用户当前手机号
                String nowMobile = userVO.getMobile();
                // 如果手机号相等,不需要重新请求法大大,返回消费成功
                if (mobile.equals(nowMobile)) {
                    logger.info("手机号相等,不需要重新请求法大大,用户ID:[" + userId + "],CA认证时手机号:[" + mobile + "],最新手机号:[" + nowMobile + "].");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                // 调用大大客户信息修改接口,修改用户CA认证相关信息
                boolean isUpdateFlag = fddUserInfoChangeMessageHandle.updateUserCAInfo(userId);
                if(isUpdateFlag){
                    // 成功更新，返回消费成功
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }else{
                    // 更新失败，返回稍后消费
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            } catch (Exception e) {
                logger.info("【法大大修改客户信息】逻辑处理出现异常:[{}]", e.getMessage());
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }


        }
    }

}
