/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.mq.consumer;

import org.apache.commons.lang3.StringUtils;
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

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.service.front.user.RefereeService;
import com.hyjf.common.constants.MQConstant;

/**
 * @author wangjun
 * @version RefereeConsumer, v0.1 2018/7/27 13:43
 */
@Service
@RocketMQMessageListener(topic = MQConstant.CRM_REFEREE_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.CRM_REFEREE_GROUP)
public class RefereeConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(RefereeConsumer.class);

    @Autowired
    RefereeService refereeService;
    
    @Override
	public void onMessage(MessageExt  message) {
        // --> 消息检查
        MessageExt msg = message;
        if (msg == null || msg.getBody() == null) {
            logger.error("【客户修改推荐人】接收到的消息为null");
            return;
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
            return;
        }

        //用户ID不为数字时，不再重发
        if (!StringUtils.isNumeric(userId)) {
            logger.error("【客户修改推荐人】接收到的用户ID不是数字！");
            return;
        }

        //推荐人ID不为数字时，不再重发
        if (!StringUtils.isNumeric(spreadsUserId)) {
            logger.error("【客户修改推荐人】接收到的推荐人ID不是数字！");
            return;
        }

        // 根据userId检索用户是否存在
        int count = refereeService.countUserById(userId);
        if (count == 0) {
            logger.error("【客户修改推荐人】用户不存在！用户ID：" + userId);
            return;
        }

        // 根据推荐人ID检索推荐人是否存在
        count = refereeService.countUserById(spreadsUserId);
        if (count == 0) {
            logger.error("【客户修改推荐人】推荐人不存在！推荐人ID：" + spreadsUserId);
            return;
        }

        //更新客户推荐人信息
        try {
            refereeService.updateSpreadsUsers(userId, spreadsUserId, operationName, ip);
        } catch (Exception e) {
            logger.error("【客户修改推荐人】消费出错", e);
            return;
        }
        logger.info("********************更新客户推荐人信息结束*************************");
        return;
    
	}

	@Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====客户修改推荐人 消费端开始执行=====");
    }
	
}
