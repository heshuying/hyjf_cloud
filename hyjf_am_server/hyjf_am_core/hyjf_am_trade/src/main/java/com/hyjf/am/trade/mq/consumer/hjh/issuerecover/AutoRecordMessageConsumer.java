package com.hyjf.am.trade.mq.consumer.hjh.issuerecover;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.trade.mq.base.Consumer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.hjh.issuerecover.AutoBailMessageProducer;
import com.hyjf.am.trade.service.task.issuerecover.AutoIssueRecoverService;
import com.hyjf.am.trade.service.task.issuerecover.AutoRecordService;
import com.hyjf.am.vo.trade.hjh.issuerecover.AutoIssuerecoverVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
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

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 自动备案
 * @author walter.limeng
 * @version AutoSendMessageConsumer, v0.1 2018/7/11 16:30
 */
@Component
public class AutoRecordMessageConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(AutoRecordMessageConsumer.class);

    @Resource
    private AutoRecordService autoRecordService;
    @Resource
    private AutoBailMessageProducer autoBailMessageProducer;
    @Autowired
    private AutoIssueRecoverService autoIssueRecoverService;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.ROCKETMQ_BORROW_RECORD_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.ROCKETMQ_BORROW_RECORD_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new AutoRecordMessageConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
    }


    /**
     *
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

                    if(mqHjhPlanAsset != null){
                        logger.info(mqHjhPlanAsset.getAssetId()+" 该资产在表里不存在！！");
                        // 原有三方资产推送处理不变
                        if (StringUtils.isNotBlank(mqHjhPlanAsset.getAssetId())) {
                            // 资产自动备案
                            logger.info(mqHjhPlanAsset.getAssetId()+" 开始自动备案 "+ mqHjhPlanAsset.getInstCode());

                            // redis 放重复检查
                            String redisKey = "borrowrecord:" + mqHjhPlanAsset.getInstCode()+mqHjhPlanAsset.getAssetId();
                            boolean result = RedisUtils.tranactionSet(redisKey, 300);
                            if(!result){
                                logger.info(mqHjhPlanAsset.getInstCode()+" 正在备案(redis) "+mqHjhPlanAsset.getAssetId());
                                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                            }

                            // 业务校验
                            if(mqHjhPlanAsset.getStatus() != null && mqHjhPlanAsset.getStatus().intValue() != 3 &&
                                    mqHjhPlanAsset.getVerifyStatus() != null && mqHjhPlanAsset.getVerifyStatus().intValue() == 1){
                                logger.info(mqHjhPlanAsset.getAssetId()+" 该资产状态不是备案状态");
                                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                            }

                            //判断该资产是否可以自动备案，是否关联计划
                            BorrowInfo borrowInfo = new BorrowInfo();
                            borrowInfo.setInstCode(mqHjhPlanAsset.getInstCode());
                            borrowInfo.setAssetType(mqHjhPlanAsset.getAssetType());
                            HjhAssetBorrowtype hjhAssetBorrowType = autoRecordService.selectAssetBorrowType(borrowInfo);
                            if(hjhAssetBorrowType == null || hjhAssetBorrowType.getAutoRecord() == null || hjhAssetBorrowType.getAutoRecord() != 1){
                                logger.info(mqHjhPlanAsset.getAssetId()+" 该资产不能自动备案,原因自动备案未配置");
                                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                            }

                            boolean flag = autoRecordService.updateRecordBorrow(mqHjhPlanAsset, hjhAssetBorrowType);
                            if (!flag) {
                                logger.error("自动备案失败！" + "[资产编号：" + mqHjhPlanAsset.getAssetId() + "]");
                            }else{
                                // 成功后到初审队列
                                if(mqHjhPlanAsset.getEntrustedFlg() != null && mqHjhPlanAsset.getEntrustedFlg().intValue() ==1){
                                    logger.info(mqHjhPlanAsset.getAssetId()+"  未推送，等待授权");
                                }else{
                                    JSONObject params = new JSONObject();
                                    params.put("assetId", mqHjhPlanAsset.getAssetId());
                                    params.put("instCode",mqHjhPlanAsset.getInstCode());
                                    //modify by yangchangwei 防止队列触发太快，导致无法获得本事务变泵的数据，延时级别为2 延时5秒
                                    autoBailMessageProducer.messageSendDelay(new MessageContent(MQConstant.ROCKETMQ_BORROW_PREAUDIT_TOPIC, UUID.randomUUID().toString(), JSONObject.toJSONBytes(params)),2);

                                    logger.info(mqHjhPlanAsset.getAssetId()+" 成功发送到初审队列");
                                }

                                // 备案成功后随机睡0.2到0.5秒
                                try {
                                    Random random = new Random();
                                    int rand = (random.nextInt(4)+2)*100;
                                    Thread.sleep(rand);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            logger.info(mqHjhPlanAsset.getAssetId()+" 结束自动备案");
                        }
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }else{
                        // 手动推送标的
                        if (StringUtils.isNotBlank(autoIssuerecoverVO.getBorrowNid())) {
                            logger.info(autoIssuerecoverVO.getBorrowNid()+" 开始自动备案 "+ autoIssuerecoverVO.getInstCode());
                            // redis 放重复检查
                            String redisKey = "borrowrecord:" + autoIssuerecoverVO.getInstCode()+autoIssuerecoverVO.getBorrowNid();
                            boolean result = RedisUtils.tranactionSet(redisKey, 300);
                            if(!result){
                                logger.info(autoIssuerecoverVO.getInstCode()+" 正在备案(redis) "+autoIssuerecoverVO.getBorrowNid());
                                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                            }
                            // 获取当前标的详情
                            Borrow borrow = autoRecordService.getBorrowByBorrowNid(autoIssuerecoverVO.getBorrowNid());
                            BorrowInfo borrowInfo = autoRecordService.getBorrowInfoById(borrow.getBorrowNid());
                            // 标的状态位判断
                            if (null == borrow.getStatus() || borrow.getStatus() != 0 ||
                                    null == borrow.getRegistStatus() || borrow.getRegistStatus() != 0) {
                                logger.info("标的："+borrow.getBorrowNid()+" 不是备案状态");
                                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                            }

                            //判断该资产是否可以自动备案，是否关联计划
                            HjhAssetBorrowtype hjhAssetBorrowType = autoRecordService.selectAssetBorrowType(borrowInfo);
                            if(hjhAssetBorrowType == null || hjhAssetBorrowType.getAutoRecord() == null || hjhAssetBorrowType.getAutoRecord() != 1){
                                logger.info(borrow.getBorrowNid()+" 标的不能自动备案,原因自动备案未配置");
                                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                            }

                            boolean flag = autoRecordService.updateRecordBorrow(borrow,borrowInfo);
                            if (!flag) {
                                logger.error("自动备案失败！" + "[资产/标的借款编号：" + borrow.getBorrowNid() + "]");
                            }else{
                                // 成功后到初审队列
                                if(borrowInfo.getEntrustedFlg() != null && borrowInfo.getEntrustedFlg().intValue() ==1){
                                    logger.info(borrow.getBorrowNid()+"  未推送，等待授权");
                                }else{
                                    // 审核保证金的标的发送MQ到消息队列
                                    if (null != hjhAssetBorrowType && null != hjhAssetBorrowType.getAutoBail() && hjhAssetBorrowType.getAutoBail() == 1) {
                                        // 加入到消息队列
                                        try {
                                            JSONObject params = new JSONObject();
                                            params.put("borrowNid", borrow.getBorrowNid());
                                            //modify by yangchangwei 防止队列触发太快，导致无法获得本事务变泵的数据，延时级别为2 延时5秒
                                            autoBailMessageProducer.messageSendDelay(new MessageContent(MQConstant.ROCKETMQ_BORROW_BAIL_TOPIC, UUID.randomUUID().toString(), JSONObject.toJSONBytes(params)),2);
                                        } catch (MQException e) {
                                            logger.error("发送【审核保证金队列】MQ失败...");
                                        }
                                        logger.info(borrow.getBorrowNid()+" 成功发送到审核保证金队列");
                                    }
                                }

                                // 备案成功后随机睡0.2到0.5秒
                                try {
                                    Random random = new Random();
                                    int rand = (random.nextInt(4)+2)*100;
                                    Thread.sleep(rand);
                                } catch (InterruptedException e) {
                                    logger.error("备案成功后随机睡异常！",e);
                                }

                            }

                            logger.info(borrow.getBorrowNid()+" 结束自动备案");

                        }
                    }

                } catch (Exception e) {
                    logger.error("自动备案异常！",e);
                }

                // 如果没有return success ，consumer会重新消费该消息，直到return success
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e) {
                logger.error("自动备案异常！",e);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } finally {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        }
    }
}
