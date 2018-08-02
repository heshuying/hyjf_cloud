/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.mq.base.Consumer;
import com.hyjf.am.user.service.front.user.UserLeaveService;
import com.hyjf.common.constants.MQConstant;
import org.apache.commons.lang3.StringUtils;
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
 * @author wangjun
 * @version UserLeaveConsumer, v0.1 2018/7/26 11:52
 */
@Component
public class UserLeaveConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(UserLeaveConsumer.class);

    @Autowired
    UserLeaveService userLeaveService;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.CRM_USER_LEAVE_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.CRM_USER_LEAVE_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new UserLeaveConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可
        defaultMQPushConsumer.start();
        logger.info("====更新离职员工信息(CRM) 消费端开始执行=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
            // --> 消息检查
            MessageExt msg = list.get(0);
            if (msg == null || msg.getBody() == null) {
                logger.error("【更新离职员工信息(CRM)】接收到的消息为null");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            // --> 消息转换
            String msgBody = new String(msg.getBody());
            logger.info("【更新离职员工信息(CRM)】接收到的消息：" + msgBody);

            JSONObject json = JSONObject.parseObject(msgBody);
            String userId = json.getString("userId");
            //用户ID为空时，不再重发
            if (StringUtils.isBlank(userId)) {
                logger.error("【更新离职员工信息(CRM)】接收到的userId为null");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            //用户ID不为数字时，不再重发
            if (!StringUtils.isNumeric(userId)) {
                logger.error("【更新离职员工信息(CRM)】接收到的userId不是数字");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            //更新离职员工信息
            try {
                userLeaveService.updateUserLeaveInfoFromCrm(userId);
            } catch (Exception e) {
                //异常时重发
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            logger.info("********************更新离职员工信息(CRM)结束*************************");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
