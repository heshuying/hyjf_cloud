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

import com.hyjf.am.user.service.front.user.UserLeaveService;
import com.hyjf.common.constants.MQConstant;

/**
 * @author wangjun
 * @version UserLeaveConsumer, v0.1 2018/7/26 11:52
 */
@Service
@RocketMQMessageListener(topic = MQConstant.CRM_USER_LEAVE_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.CRM_USER_LEAVE_GROUP)
public class UserLeaveConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(UserLeaveConsumer.class);

    @Autowired
    UserLeaveService userLeaveService;
    
    @Override
	public void onMessage(MessageExt  message) {
        // --> 消息检查
        MessageExt msg = message;
        if (msg == null || msg.getBody() == null) {
            logger.error("【更新离职员工信息(CRM)】接收到的消息为null");
            return;
        }

        // --> 消息转换
        String userId = new String(msg.getBody());
        logger.info("【更新离职员工信息(CRM)】接收到的userId：" + userId);

        //用户ID为空时，不再重发
        if (StringUtils.isBlank(userId)) {
            logger.error("【更新离职员工信息(CRM)】接收到的userId为null");
            return;
        }

        //用户ID不为数字时，不再重发
        if (!StringUtils.isNumeric(userId)) {
            logger.error("【更新离职员工信息(CRM)】接收到的userId不是数字");
            return;
        }

        //更新离职员工信息
        try {
            boolean updateResult = userLeaveService.updateUserLeaveInfoFromCrm(userId);
            logger.info("更新离职员工信息(CRM)结束，CRM_ID:{} , 更新结果:{}", userId, updateResult);
        } catch (Exception e) {
            logger.error("员工离职信息更新失败, CRM_ID:" + userId, e);
            return;
        }
        logger.info("********************更新离职员工信息(CRM)结束*************************");
        return;
    }

	@Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====更新离职员工信息(CRM) 消费端开始执行=====");
    }
}
