/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.bean.MQBorrow;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.ApiAssetClient;
import com.hyjf.cs.trade.mq.base.Consumer;
import com.hyjf.cs.trade.service.borrow.AutoPreAuditService;
import org.apache.commons.lang3.StringUtils;
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
 * @author fuqiang
 * @version AutoPreAuditConsumer, v0.1 2018/6/14 14:30
 */
@Component
public class AutoPreAuditConsumer extends Consumer {

    private static final Logger logger = LoggerFactory.getLogger(AutoPreAuditConsumer.class);

    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AutoPreAuditService autoPreAuditService;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.ROCKETMQ_BORROW_PREAUDIT_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.ROCKETMQ_BORROW_PREAUDIT_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new AutoPreAuditConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
    }

    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

            // --> 消息检查
            MessageExt msg = list.get(0);
            if(msg == null || msg.getBody() == null){
                logger.error("【自动初审任务】接收到的消息为null");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            // --> 消息转换
            String msgBody = new String(msg.getBody());
            logger.info("【自动初审请求】接收到的消息：" + msgBody);

            HjhPlanAssetVO mqHjhPlanAsset;
            try {
                mqHjhPlanAsset = JSONObject.parseObject(msgBody, HjhPlanAssetVO.class);
                if(mqHjhPlanAsset == null || (mqHjhPlanAsset.getAssetId() == null && mqHjhPlanAsset.getBorrowNid() == null)){
                    logger.info("解析为空：" + msgBody);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                // --> 消息处理
                if (StringUtils.isNotBlank(mqHjhPlanAsset.getAssetId())) {
                    String assetId = mqHjhPlanAsset.getAssetId();
                    String instCode = mqHjhPlanAsset.getInstCode();
                    // 资产自动初审
                    logger.info(assetId + " 开始自动初审 " + instCode);
                    HjhPlanAssetVO hjhPlanAssetVO = amTradeClient.selectPlanAsset(assetId, instCode);
                    if (hjhPlanAssetVO == null) {
                        logger.info(assetId + " 该资产在表里不存在！！");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    // redis 放重复检查
                    String redisKey = "borrowpreaudit:" + hjhPlanAssetVO.getInstCode() + hjhPlanAssetVO.getAssetId();
                    boolean result = RedisUtils.tranactionSet(redisKey, 300);
                    if (!result) {
                        logger.info(hjhPlanAssetVO.getInstCode() + " 正在初审(redis) " + hjhPlanAssetVO.getAssetId());
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    // 业务校验
                    if (hjhPlanAssetVO.getStatus() != null && hjhPlanAssetVO.getStatus().intValue() != 5 &&
                            hjhPlanAssetVO.getVerifyStatus() != null && hjhPlanAssetVO.getVerifyStatus().intValue() == 1) {
                        logger.info(assetId + " 该资产状态不是初审状态");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    //判断该资产是否可以自动初审，是否关联计划
                    HjhAssetBorrowTypeVO hjhAssetBorrowType = amTradeClient.selectAssetBorrowType(hjhPlanAssetVO.getInstCode(), hjhPlanAssetVO.getAssetType());
                    boolean flag = autoPreAuditService.updateRecordBorrow(hjhPlanAssetVO, hjhAssetBorrowType);
                    if (!flag) {
                        logger.error("自动初审失败！" + "[资产编号：" + hjhPlanAssetVO.getAssetId() + "]");
                    } else {
                        // 成功后到关联计划队列
                        MQBorrow mqBorrow = new MQBorrow();
                        mqBorrow.setBorrowNid(hjhPlanAssetVO.getBorrowNid());
                        autoPreAuditService.sendToMQ(mqBorrow, MQConstant.ROCKETMQ_BORROW_ISSUE_GROUP);
                    }

                    logger.info(hjhPlanAssetVO.getAssetId() + " 结束自动初审");
                } else if (StringUtils.isNotBlank(mqHjhPlanAsset.getBorrowNid())) {
                    logger.info(mqHjhPlanAsset.getBorrowNid()+" 开始自动初审 "+ mqHjhPlanAsset.getInstCode());

                    // redis 放重复检查
                    String redisKey = "borrowpreaudit:" + mqHjhPlanAsset.getInstCode()+mqHjhPlanAsset.getBorrowNid();
                    boolean result = RedisUtils.tranactionSet(redisKey, 300);
                    if(!result){
                        logger.info(mqHjhPlanAsset.getInstCode()+" 正在初审(redis) "+mqHjhPlanAsset.getBorrowNid());
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    BorrowVO borrow = autoPreAuditService.getBorrowByNid(mqHjhPlanAsset.getBorrowNid());
                    if(borrow == null){
                        logger.info(mqHjhPlanAsset.getBorrowNid()+" 该标的在表里不存在！！");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    // 业务校验
                    if(borrow.getStatus() != null && borrow.getStatus().intValue() != 1 &&
                            borrow.getVerifyStatus() != null && borrow.getVerifyStatus().intValue() != 1){
                        logger.info(borrow.getBorrowNid()+" 该资产状态不是初审(已审核保证金)状态");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    //判断该资产是否可以自动初审，是否关联计划
                    HjhAssetBorrowTypeVO hjhAssetBorrowType = amTradeClient.selectAssetBorrowType(borrow.getInstCode(), borrow.getAssetType());
                    if(hjhAssetBorrowType == null || hjhAssetBorrowType.getAutoAudit() == null || hjhAssetBorrowType.getAutoAudit() != 1){
                        logger.info(borrow.getBorrowNid()+" 标的不能自动初审,原因自动初审未配置");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    boolean flag = autoPreAuditService.updateRecordBorrow(borrow);
                    if (!flag) {
                        logger.error("自动初审失败！" + "[项目编号：" + borrow.getBorrowNid() + "]");
                    }else{
                        if (borrow.getIsEngineUsed() == 1) {
                            // 成功后到关联计划队列
                            MQBorrow mqBorrow = new MQBorrow();
                            mqBorrow.setBorrowNid(borrow.getBorrowNid());
                            autoPreAuditService.sendToMQ(mqBorrow, MQConstant.ROCKETMQ_BORROW_ISSUE_GROUP);
                        } else {
                            // 散标修改redis：借款的borrowNid,account借款总额
                            RedisUtils.set(borrow.getBorrowNid(), borrow.getAccount().toString());
                        }
                    }

                    logger.info(borrow.getBorrowNid()+" 结束自动初审");
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e) {
                logger.error("自动初审失败...", e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
    }
}
