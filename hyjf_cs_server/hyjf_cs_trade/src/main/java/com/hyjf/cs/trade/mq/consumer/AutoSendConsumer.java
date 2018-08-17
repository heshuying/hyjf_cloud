/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.client.ApiAssetClient;
import com.hyjf.cs.trade.client.AutoSendClient;
import com.hyjf.cs.trade.mq.base.Consumer;
import com.hyjf.cs.trade.service.borrow.ApiAssetPushService;
import com.hyjf.cs.trade.service.borrow.AutoSendService;
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
public class AutoSendConsumer extends Consumer {

    private static final Logger _log = LoggerFactory.getLogger(AutoSendConsumer.class);

    @Autowired
    private AutoSendClient autoSendClient;

    @Autowired
    private ApiAssetClient apiAssetClient;

    @Autowired
    private AutoSendService autoSendService;

    @Autowired
    private ApiAssetPushService apiAssetPushService;

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
    }

    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

            // --> 消息检查
            MessageExt msg = list.get(0);
            if(msg == null || msg.getBody() == null){
                _log.error("【自动录标任务】接收到的消息为null");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            // --> 消息转换
            String msgBody = new String(msg.getBody());
            _log.info("【自动录标请求】接收到的消息：" + msgBody);

            HjhPlanAssetVO mqHjhPlanAsset;
            try {
                mqHjhPlanAsset = JSONObject.parseObject(msgBody, HjhPlanAssetVO.class);
                if(mqHjhPlanAsset == null || mqHjhPlanAsset.getAssetId() == null){
                    _log.info("解析为空：" + msgBody);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                String assetId = mqHjhPlanAsset.getAssetId();
                String instCode = mqHjhPlanAsset.getInstCode();
                // 资产自动录标
                _log.info(assetId + " 开始自动录标 " + instCode);

                HjhPlanAssetVO hjhPlanAssetVO = autoSendClient.selectPlanAsset(assetId, instCode);
                if (hjhPlanAssetVO == null) {
                    _log.info(assetId + " 该资产在表里不存在！！");
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                // redis 放重复检查
                String redisKey = "borrowsend:" + hjhPlanAssetVO.getInstCode() + hjhPlanAssetVO.getAssetId();
                boolean result = RedisUtils.tranactionSet(redisKey, 300);
                if (!result) {
                    _log.info(hjhPlanAssetVO.getInstCode() + " 正在录标 (redis)" + hjhPlanAssetVO.getAssetId());
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                // 业务校验
                if (hjhPlanAssetVO.getStatus() != null && hjhPlanAssetVO.getStatus().intValue() != 0 &&
                        hjhPlanAssetVO.getVerifyStatus() != null && hjhPlanAssetVO.getVerifyStatus().intValue() == 1) {
                    _log.info(assetId + " 该资产状态不是录标状态");
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                //判断该资产是否可以自动录标，是否关联计划
                HjhAssetBorrowTypeVO hjhAssetBorrowTypeVO = apiAssetClient.selectAssetBorrowType(hjhPlanAssetVO.getInstCode(), hjhPlanAssetVO.getAssetType());
                if (hjhAssetBorrowTypeVO == null || hjhAssetBorrowTypeVO.getAutoAdd() != 1) {
                    _log.info(hjhPlanAssetVO.getAssetId() + " 该资产不能自动录标,流程配置未启用");
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                boolean flag = false;
                try {
                    flag = autoSendService.insertSendBorrow(hjhPlanAssetVO, hjhAssetBorrowTypeVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!flag) {
                    _log.info("自动录标失败！" + "[资产编号：" + hjhPlanAssetVO.getAssetId() + "]");
                } else {
                    // 成功后到备案队列
                    apiAssetPushService.sendToMQ(hjhPlanAssetVO, MQConstant.ROCKETMQ_BORROW_RECORD_GROUP);
                }

                _log.info(hjhPlanAssetVO.getAssetId() + " 结束自动录标");
            } catch (Exception e1) {
                e1.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }finally {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        }
    }
}
