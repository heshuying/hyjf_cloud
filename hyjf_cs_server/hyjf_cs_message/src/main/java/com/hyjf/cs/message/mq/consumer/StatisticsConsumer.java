package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.datacollect.OperationMongoGroupEntityVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.message.bean.ic.OperationGroupReport;
import com.hyjf.cs.message.mongo.mc.OperationMongoGroupDao;
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
 * @author：yinhui
 * @Date: 2018/9/1  15:10
 */
@Service
@RocketMQMessageListener(topic = MQConstant.STATISTICS_OPERATION_REPORT_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.STATISTICS_OPERATION_REPORT_GROUP)
public class StatisticsConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(SmsConsumer.class);

    @Autowired
    private OperationMongoGroupDao operationMongoGroupDao;

    @Override
    public void onMessage(MessageExt message) {
        MessageExt msg = message;
        OperationMongoGroupEntityVO vo = JSONObject.parseObject(msg.getBody(), OperationMongoGroupEntityVO.class);
        logger.info("StatisticsConsumer 收到消息，开始处理....vo is :{}", vo);
        if (null != vo) {
            OperationGroupReport groupEntity = new OperationGroupReport();
            BeanUtils.copyProperties(vo,groupEntity);
            operationMongoGroupDao.insert(groupEntity);
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====StatisticsConsumer consumer=====");
    }
}
