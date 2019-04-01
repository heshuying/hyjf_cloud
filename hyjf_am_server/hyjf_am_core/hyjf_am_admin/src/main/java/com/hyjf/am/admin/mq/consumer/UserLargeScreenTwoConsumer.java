package com.hyjf.am.admin.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.market.service.UserLargeScreenTwoService;
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

@Service
@RocketMQMessageListener(topic = MQConstant.SCREEN_DATA_TWO_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.SCREEN_DATA_TWO_GROUP)
public class UserLargeScreenTwoConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(SellDailyConsumer.class);
    private static int MAX_RECONSUME_TIME = 3;

    @Autowired
    private UserLargeScreenTwoService userLargeScreenTwoService;

    @Override
    public void onMessage(MessageExt messageExt) {
        if (MQConstant.SCREEN_DATA_TWO_SELECT_TAG.equals(messageExt.getTags())) {
            JSONObject data = JSONObject.parseObject(messageExt.getBody(), JSONObject.class);
            String flag = data.getString("flag");
            logger.info("用户画像屏幕二运营部站岗资金获取请求参数:{}", flag);

            OperMonthPerformanceDataVO result;
            if ("now".equals(flag)){
                // 得到运营部用户当前站岗资金
                result = userLargeScreenTwoService.getOperMonthNowBalance();
                RedisUtils.setObjEx("USER_LARGE_SCREEN_TWO_MONTH:NOW_BALANCE_"+ GetDate.formatDate(), result.getMonthNowBalance(), 24 * 60 * 60);
            }
            if("all".equals(flag)){
                // 得到运营部用户当前站岗资金
                result = userLargeScreenTwoService.getOperMonthNowBalance();
                RedisUtils.setObjEx("USER_LARGE_SCREEN_TWO_MONTH:NOW_BALANCE_"+ GetDate.formatDate(), result.getMonthNowBalance(), 24 * 60 * 60);
                // 得到运营部用户月初站岗资金
                result = userLargeScreenTwoService.getOperMonthStartBalance();
                RedisUtils.setObjEx("USER_LARGE_SCREEN_TWO_MONTH:START_BALANCE_"+GetDate.formatDate(new Date(), GetDate.yyyyMM_key), result.getMonthStartBalance(), 31 * 24 * 60 * 60);
            }
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

        logger.info("====用户画像屏幕二运营部站岗资金获取 消费端开始执行=====");
    }
}
