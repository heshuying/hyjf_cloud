package com.hyjf.am.trade.mq.consumer.hjh.issuerecover;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.trade.mq.base.Consumer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.hjh.issuerecover.AutoIssueRecoverProducer;
import com.hyjf.am.trade.service.task.issuerecover.AutoIssueRecoverService;
import com.hyjf.am.vo.trade.hjh.issuerecover.AutoIssuerecoverVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
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
import java.util.UUID;

/**
 * 自动录标
 * @author walter.limeng
 * @version AutoSendMessageConsumer, v0.1 2018/7/11 10:30
 */
@Component
public class AutoSendMessageConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(AutoSendMessageConsumer.class);
    @Autowired
    private AutoIssueRecoverService autoIssueRecoverService;
    @Autowired
    private AutoIssueRecoverProducer autoIssueRecoverProducer;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.HJH_AUTO_ISSUERECOVER_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.HJH_AUTO_ISSUERECOVER_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new AutoSendMessageConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
    }


    /**
     * 汇计划加入订单计算公允价值逻辑处理
     */
    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

            logger.info("AutoSendMessageConsumer 收到消息，开始处理....");
            MessageExt msg = msgs.get(0);
            Integer planId = null;
            try {
                AutoIssuerecoverVO autoIssuerecoverVO = JSONObject.parseObject(msg.getBody(), AutoIssuerecoverVO.class);
                // 计划加入号
                planId = autoIssuerecoverVO.getPlanId();
                // 计划加入号为空
                if (null == planId) {
                    logger.error("计划加入号为空");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                // --> 消息处理

                HjhPlanAsset mqHjhPlanAsset = autoIssueRecoverService.getHjhPlanAssetById(planId);
                try {

                    // 资产自动录标
//                    logger.info(mqHjhPlanAsset.getAssetId()+" 开始自动录标 "+ mqHjhPlanAsset.getInstCode());
//                    HjhPlanAsset hjhPlanAsset = autoIssueRecoverService.selectPlanAsset(mqHjhPlanAsset.getAssetId(), mqHjhPlanAsset.getInstCode());
                    if(mqHjhPlanAsset == null){
                        logger.info(mqHjhPlanAsset.getAssetId()+" 该资产在表里不存在！！");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    // redis 放重复检查
                    String redisKey = "borrowsend:" + mqHjhPlanAsset.getInstCode()+mqHjhPlanAsset.getAssetId();
                    boolean result = RedisUtils.tranactionSet(redisKey, 300);
                    if(!result){
                        logger.info(mqHjhPlanAsset.getInstCode()+" 正在录标 (redis)"+mqHjhPlanAsset.getAssetId());
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    // 业务校验
                    if(mqHjhPlanAsset.getStatus() != null && mqHjhPlanAsset.getStatus().intValue() != 0 &&
                            mqHjhPlanAsset.getVerifyStatus() != null && mqHjhPlanAsset.getVerifyStatus().intValue() == 1){
                        logger.info(mqHjhPlanAsset.getAssetId()+" 该资产状态不是录标状态");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }


                    //判断该资产是否可以自动录标，是否关联计划
                    HjhAssetBorrowtype hjhAssetBorrowType = autoIssueRecoverService.selectAssetBorrowType(mqHjhPlanAsset);
                    if(hjhAssetBorrowType==null || hjhAssetBorrowType.getAutoAdd() != 1){
                        logger.info(mqHjhPlanAsset.getAssetId()+" 该资产不能自动录标,流程配置未启用");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    boolean flag = autoIssueRecoverService.insertSendBorrow(mqHjhPlanAsset,hjhAssetBorrowType);
                    if (!flag) {
                        logger.info("自动录标失败！" + "[资产编号：" + mqHjhPlanAsset.getAssetId() + "]");
                    }else{
                        // 成功后到备案队列
                        try {
                            JSONObject params = new JSONObject();
                            params.put("planId", mqHjhPlanAsset.getId());
                            autoIssueRecoverProducer.messageSend(new MessageContent(MQConstant.ROCKETMQ_BORROW_RECORD_TOPIC, UUID.randomUUID().toString(), JSONObject.toJSONBytes(params)));
                        } catch (MQException e) {
                            logger.error("发送【自动录标】MQ失败...");
                        }
                    }

                    logger.info(mqHjhPlanAsset.getAssetId()+" 结束自动录标");

                } catch (Exception e) {
                    logger.error("自动录标异常！",e);
                }

                // 如果没有return success ，consumer会重新消费该消息，直到return success
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e) {
                logger.error("自动录标异常！",e);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } finally {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        }
    }
}
