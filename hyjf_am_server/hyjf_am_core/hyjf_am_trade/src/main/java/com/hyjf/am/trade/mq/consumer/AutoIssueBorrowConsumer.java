package com.hyjf.am.trade.mq.consumer;

import java.util.List;

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

import com.hyjf.am.trade.mq.base.Consumer;
import com.hyjf.am.trade.service.task.AleveLogFileService;
import com.hyjf.common.constants.MQConstant;

/**
 * @author xiasq
 * @version AutoIssueBorrowConsumer, v0.1 2018/7/11 10:51
 * 关联计划消息消费
 */
@Component
public class AutoIssueBorrowConsumer  extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(AutoIssueBorrowConsumer.class);

    @Autowired
    AleveLogFileService aleveLogFileService;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.AUTO_ISSUE_BORROW_JOIN_PLAN_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.AUTO_ISSUE_BORROW_JOIN_PLAN_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====AutoIssueBorrowConsumer consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

            // --> 消息检查
            MessageExt msg = list.get(0);
            if(msg == null || msg.getBody() == null){
                logger.error("【自动冲正】接收到的消息为null");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            //todo 先不实现


            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
