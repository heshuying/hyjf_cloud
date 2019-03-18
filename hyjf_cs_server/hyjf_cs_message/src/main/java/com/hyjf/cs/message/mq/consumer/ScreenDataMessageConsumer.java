package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.vo.datacollect.UserOperateListVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.message.bean.ic.BorrowUserStatistic;
import com.hyjf.cs.message.client.AmUserClient;
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

/**
 * 大屏数据统计
 * @author lisheng
 * @version ScreenDataMessageConsumer, v0.1 2019/3/15 14:37
 */
@Service
@RocketMQMessageListener(topic = MQConstant.SCREEN_DATA_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.SCREEN_DATA_GROUP)
public class ScreenDataMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(ScreenDataMessageConsumer.class);
    private static int MAX_RECONSUME_TIME = 3;
    @Autowired
    AmUserClient amUserClient;
    @Override
    public void onMessage(MessageExt messageExt) {
        logger.info("ScreenDataMessageConsumer 收到消息，开始处理....msgId is :{}", messageExt.getMsgId());
        UserOperateListVO data = JSONObject.parseObject(messageExt.getBody(), UserOperateListVO.class);
        //查询用户是否归属运营部

        switch (data.getOperating()) {
            //投资
            case 1:

                break;
            //充值
            case 2:

                break;
            //回款
            case 3:

                break;
            //提现
            case 4:

                break;
            default:
                logger.error("error ScreenData type...");
                return;
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
        logger.info("====大屏数据统计 消费端开始执行=====");
    }
}
