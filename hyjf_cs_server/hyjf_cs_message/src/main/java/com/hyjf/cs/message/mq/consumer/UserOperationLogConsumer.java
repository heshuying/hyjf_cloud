package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.message.bean.ic.userbehaviourn.UserOperationLog;
import com.hyjf.cs.message.mongo.ic.userbehaviourn.UserOperationLogMongDao;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author tyy
 * @version UserOperationLogConsumer, v0.1 2018/11/2 14:58
 */

@Service
@RocketMQMessageListener(topic = MQConstant.USER_OPERATION_LOG_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.USER_OPERATION_LOG_GROUP)
public class UserOperationLogConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(UserOperationLogConsumer.class);

    private static int MAX_RECONSUME_TIME = 3;

    @Autowired
    private UserOperationLogMongDao userOperationLogMongDao;

    @Override
    public void onMessage(MessageExt message) {
        MessageExt msg = message;
        UserOperationLogEntityVO userOperationLogEntity = JSONObject.parseObject(msg.getBody(), UserOperationLogEntityVO.class);
        logger.info("UserOperationLogConsumer 收到请求，userOperationLogEntity is：{}", userOperationLogEntity);
        if (null != userOperationLogEntity && StringUtils.isNotBlank(userOperationLogEntity.getUserName())) {
            userOperationLogEntity.setOperationTime(new Date());
            UserOperationLog operationLogEntity = new UserOperationLog();
            BeanUtils.copyProperties(userOperationLogEntity, operationLogEntity);
            userOperationLogMongDao.save(operationLogEntity);
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
        logger.info("====UserOperationLogConsumer consumer=====");
    }
}
