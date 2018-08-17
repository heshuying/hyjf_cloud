/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.mq.base.Consumer;
import com.hyjf.am.user.service.front.user.RefereeService;
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
 * @version RefereeConsumer, v0.1 2018/7/27 13:43
 */
@Component
public class RefereeConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(RefereeConsumer.class);

    @Autowired
    RefereeService refereeService;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.CRM_REFEREE_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.CRM_REFEREE_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new RefereeConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可
        defaultMQPushConsumer.start();
        logger.info("====客户修改推荐人 消费端开始执行=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
            // --> 消息检查
            MessageExt msg = list.get(0);
            if (msg == null || msg.getBody() == null) {
                logger.error("【客户修改推荐人】接收到的消息为null");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            // --> 消息转换
            String msgBody = new String(msg.getBody());
            logger.info("【客户修改推荐人】接收到的消息：" + msgBody);

            JSONObject json = JSONObject.parseObject(msgBody);
            //用户ID
            String userId = json.getString("userId");
            //推荐人ID
            String spreadsUserId = json.getString("spreadsUserId");
            //操作人
            String operationName = json.getString("operationName");
            //发送MQ端的IP
            String ip = json.getString("ip");

            //某个项目为空时，不再重发
            if (StringUtils.isBlank(userId) || StringUtils.isBlank(spreadsUserId) ||
                    StringUtils.isBlank(operationName) || StringUtils.isBlank(ip)) {
                logger.error("【客户修改推荐人】接收到的信息不正确！");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            //用户ID不为数字时，不再重发
            if (!StringUtils.isNumeric(userId)) {
                logger.error("【客户修改推荐人】接收到的用户ID不是数字！");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            //推荐人ID不为数字时，不再重发
            if (!StringUtils.isNumeric(spreadsUserId)) {
                logger.error("【客户修改推荐人】接收到的推荐人ID不是数字！");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            // 根据userId检索用户是否存在
            int count = refereeService.countUserById(userId);
            if (count == 0) {
                logger.error("【客户修改推荐人】用户不存在！用户ID：" + userId);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            // 根据推荐人ID检索推荐人是否存在
            count = refereeService.countUserById(spreadsUserId);
            if (count == 0) {
                logger.error("【客户修改推荐人】推荐人不存在！推荐人ID：" + spreadsUserId);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            //更新客户推荐人信息
            try {
                refereeService.updateSpreadsUsers(userId, spreadsUserId, operationName, ip);
            } catch (Exception e) {
                //异常时重发
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            logger.info("********************更新客户推荐人信息结束*************************");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
