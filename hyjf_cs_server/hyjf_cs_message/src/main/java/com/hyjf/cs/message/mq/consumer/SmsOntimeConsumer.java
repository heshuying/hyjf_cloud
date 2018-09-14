/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.admin.SmsOntimeVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.message.bean.mc.SmsOntime;
import com.hyjf.cs.message.mongo.mc.SmsOntimeMongoDao;
import com.hyjf.cs.message.mq.base.Consumer;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fq
 * @version SmsOntimeConsumer, v0.1 2018/8/15 14:59
 */
@Component
public class SmsOntimeConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(SmsConsumer.class);

    @Autowired
    private SmsOntimeMongoDao smsOntimeMongoDao;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.SMS_ONTIME_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.SMS_ONTIME_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new SmsOntimeConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====sms consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            MessageExt msg = msgs.get(0);
            SmsOntimeVO smsOntimeVO = JSONObject.parseObject(msg.getBody(), SmsOntimeVO.class);
            logger.info("SmsOntimeConsumer 收到消息，开始处理....smsOntimeVO is :{}", smsOntimeVO);
            if (null != smsOntimeVO) {
                SmsOntime smsOntime = new SmsOntime();
                BeanUtils.copyProperties(smsOntimeVO, smsOntime);
                smsOntimeMongoDao.save(smsOntime);
            }
            // 如果没有return success ，consumer会重新消费该消息，直到return success
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
