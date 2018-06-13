/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.borrow.mq;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.borrow.HjhPlanAssetVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.borrow.handle.AutoSendMessageHandle;
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

import java.util.List;

/**
 * 自动录标
 *
 * @author fuqiang
 * @version AssetPushConsumer, v0.1 2018/6/12 15:24
 */
@Component
public class AssetPushConsumer extends Consumer {

    private static final Logger logger = LoggerFactory.getLogger(AssetPushConsumer.class);

    @Autowired
    private AutoSendMessageHandle autoSendMessageHandle;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.ASSET_PUSH_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.ASSET_PUST_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====assetPush consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
            MessageExt msg = list.get(0);
            HjhPlanAssetVO mqHjhPlanAsset = JSONObject.parseObject(msg.getBody(), HjhPlanAssetVO.class);
            if (mqHjhPlanAsset != null) {
                autoSendMessageHandle.sendMessage(mqHjhPlanAsset.getAssetId(), mqHjhPlanAsset.getInstCode());
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
