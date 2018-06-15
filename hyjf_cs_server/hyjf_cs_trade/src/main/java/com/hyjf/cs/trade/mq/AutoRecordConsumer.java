/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.HjhPlanAssetVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.handle.AutoRecordMessageHandle;
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

import java.util.List;

/**
 * 自动备案
 * @author fuqiang
 * @version AutoRecordConsumer, v0.1 2018/6/14 9:57
 */
public class AutoRecordConsumer extends Consumer {

    private static final Logger _log = LoggerFactory.getLogger(AutoRecordConsumer.class);

    @Autowired
    private AutoRecordMessageHandle autoRecordMessageHandle;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.BORROW_RECORD_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.ASSET_PUST_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new AutoRecordConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        _log.info("====autoRecord consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

            // --> 消息检查
            MessageExt msg = list.get(0);
            if(msg == null || msg.getBody() == null){
                _log.error("【自动备案任务】接收到的消息为null");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            // --> 消息转换
            String msgBody = new String(msg.getBody());
            _log.info("【自动备案请求】接收到的消息：" + msgBody);

            HjhPlanAssetVO mqHjhPlanAsset;
            try {
                mqHjhPlanAsset = JSONObject.parseObject(msgBody, HjhPlanAssetVO.class);
                if(mqHjhPlanAsset == null || mqHjhPlanAsset.getAssetId() == null){
                    _log.info("解析为空：" + msgBody);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            try {
                autoRecordMessageHandle.sendMessage(mqHjhPlanAsset.getAssetId(), mqHjhPlanAsset.getInstCode());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e){
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
    }
}
