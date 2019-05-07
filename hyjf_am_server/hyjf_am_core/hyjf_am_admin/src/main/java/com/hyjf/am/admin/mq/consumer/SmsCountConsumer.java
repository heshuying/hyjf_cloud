package com.hyjf.am.admin.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.market.service.UserLargeScreenTwoService;
import com.hyjf.am.user.dao.model.customize.SmsCountCustomize;
import com.hyjf.am.user.service.admin.message.SmsCountService;
import com.hyjf.am.vo.api.OperMonthPerformanceDataVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetDate;
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

import java.util.Date;
import java.util.Map;

/**
 * 短信统计
 */
@Service
@RocketMQMessageListener(topic = MQConstant.SMS_COUNT_MESSAGE_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.SMS_COUNT_MESSAGE_GROUP)
public class SmsCountConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(SmsCountConsumer.class);
    private static int MAX_RECONSUME_TIME = 3;

    @Autowired
    private SmsCountService smsCountService;

    @Override
    public void onMessage(MessageExt messageExt) {
        logger.info("【短信统计】SmsCountConsumer 收到消息，开始处理....msgId is :{}", messageExt.getMsgId());
        Map<String,String> map = JSONObject.parseObject(messageExt.getBody(), Map.class);
        if(map == null){
            logger.error("【短信统计】SmsCountConsumer 收到空消息 msgId is :{}", messageExt.getMsgId());
            return;
        }

        try{
            Map<String, SmsCountCustomize> smsCountCustomizeMap =
                    smsCountService.getSmsCount(map.get("mobile"),map.get("messageStr"));
            if(smsCountCustomizeMap == null ){
                logger.info("【短信统计】SmsCountConsumer 需要更新的数据为0, mobile is :{}", map.get("mobile"));
                return;
            }

            logger.info("【短信统计】SmsCountConsumer 需要更新的条数为 is :{}", smsCountCustomizeMap.size());
            smsCountService.addSmsCount(smsCountCustomizeMap);
        }catch (Exception e){
            logger.error("【短信统计】发生异常："+e.getMessage());
        }
        
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        //设置最大重试次数
        defaultMQPushConsumer.setMaxReconsumeTimes(MAX_RECONSUME_TIME);

        logger.info("====短信统计 消费端开始执行=====");
    }
}
