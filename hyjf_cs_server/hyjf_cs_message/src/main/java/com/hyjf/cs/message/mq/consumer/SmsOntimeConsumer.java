/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.admin.SmsOntimeVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.message.bean.mc.SmsOntime;
import com.hyjf.cs.message.mongo.mc.SmsOntimeMongoDao;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author fq
 * @version SmsOntimeConsumer, v0.1 2018/8/15 14:59
 */
@Service
@RocketMQMessageListener(topic = MQConstant.SMS_ONTIME_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.SMS_ONTIME_GROUP)
public class SmsOntimeConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(SmsConsumer.class);

    private static int MAX_RECONSUME_TIME = 3;

    @Autowired
    private SmsOntimeMongoDao smsOntimeMongoDao;

    @Override
    public void onMessage(MessageExt message) {
        MessageExt msg = message;
        SmsOntimeVO smsOntimeVO = JSONObject.parseObject(msg.getBody(), SmsOntimeVO.class);
        logger.info("SmsOntimeConsumer 收到消息，开始处理....smsOntimeVO is :{}", smsOntimeVO);
        if (null != smsOntimeVO) {
            SmsOntime smsOntime = new SmsOntime();
            BeanUtils.copyProperties(smsOntimeVO, smsOntime);
            smsOntimeMongoDao.save(smsOntime);
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        //设置最大重试次数
        defaultMQPushConsumer.setMaxReconsumeTimes(MAX_RECONSUME_TIME);
        logger.info("====sms consumer=====");
    }
}
