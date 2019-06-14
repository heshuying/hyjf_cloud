/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.service.front.user.WjtBorrowUserModifyService;
import com.hyjf.common.constants.MQConstant;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 温金投发标借款人修改机构编号
 *
 * @author liuyang
 * @version WjtBorrowUserModifyConsumer, v0.1 2019/5/14 14:49
 */
@Service
@RocketMQMessageListener(topic = MQConstant.WJT_BORROW_USER_MODIFY_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.WJT_BORROW_USER_MODIFY_GROUP)
public class WjtBorrowUserModifyConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(WjtBorrowUserModifyConsumer.class);

    @Autowired
    private WjtBorrowUserModifyService wjtBorrowUserModifyService;

    @Value("${wjt.instCode}")
    private String wjtInstCode;

    @Value("${wjt.channel}")
    private String wjtChannel;

    @Override
    public void onMessage(MessageExt message) {
        logger.info("WjtBorrowUserModifyConsumer 收到消息，开始处理....");
        // --> 消息检查
        MessageExt msg = message;
        if (msg == null || msg.getBody() == null) {
            logger.error("温金投发标借款人修改机构编号,接收到的消息为null");
            return;
        }
        // --> 消息转换
        String msgBody = new String(msg.getBody());
        JSONObject json = JSONObject.parseObject(msgBody);
        // 用户ID
        String userId = json.getString("userId");
        // 根据用户ID查询用户信息
        User borrowUser = this.wjtBorrowUserModifyService.findUserByUserId(Integer.parseInt(userId));
        if (borrowUser == null) {
            logger.error("温金投发标借款人修改机构编号,根据用户ID查询用户信息失败,用户ID:[" + userId + "].");
            return;
        }
        // 如果机构编号已经修改
        if (wjtInstCode.equals(borrowUser.getInstCode())) {
            logger.info("温金投发标借款人修改机构编号,用户已为温金投用户,用户ID:[" + userId + "].");
            return;
        }
        borrowUser.setInstCode(wjtInstCode);
        // 更新用户机构编号
        this.wjtBorrowUserModifyService.modifyUserInstCode(borrowUser);
        // 如果渠道不存在,插入将借款人插入渠道
        if (!wjtBorrowUserModifyService.utmRegByUserId(userId)) {
            this.wjtBorrowUserModifyService.insertBorrowUserUtmReg(userId, wjtChannel);
        }
        return;
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        // MQ默认集群消费
        consumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====WjtBorrowUserModifyConsumer start=====");
    }
}
