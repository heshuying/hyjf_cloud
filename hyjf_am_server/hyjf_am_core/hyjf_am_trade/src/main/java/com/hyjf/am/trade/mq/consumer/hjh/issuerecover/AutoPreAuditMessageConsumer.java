package com.hyjf.am.trade.mq.consumer.hjh.issuerecover;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.trade.mq.base.Consumer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.hjh.issuerecover.AutoIssueMessageProducer;
import com.hyjf.am.trade.service.task.issuerecover.AutoBailMessageService;
import com.hyjf.am.trade.service.task.issuerecover.AutoPreAuditMessageService;
import com.hyjf.am.trade.service.task.issuerecover.AutoRecordService;
import com.hyjf.am.vo.trade.hjh.issuerecover.AutoIssuerecoverVO;
import com.hyjf.common.cache.RedisConstants;
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
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/11 19:24
 * 自动初审
 * @Description: autoPreAuditMessageConsumer
 */
@Component
public class AutoPreAuditMessageConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(AutoPreAuditMessageConsumer.class);

    @Resource
    private AutoPreAuditMessageService autoPreAuditMessageService;
    @Resource
    private AutoBailMessageService autoBailMessageService;
    @Resource
    private AutoRecordService autoRecordService;
    @Resource
    private AutoIssueMessageProducer autoIssueMessageProducer;

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
        defaultMQPushConsumer.registerMessageListener(new AutoPreAuditMessageConsumer.MessageListener());
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
            String borrowNid = null;
            try {
                AutoIssuerecoverVO autoIssuerecoverVO = JSONObject.parseObject(msg.getBody(), AutoIssuerecoverVO.class);
                // 计划加入号
                borrowNid = autoIssuerecoverVO.getBorrowNid();
                // 计划加入号为空
                if (autoIssuerecoverVO == null || (autoIssuerecoverVO.getAssetId() == null && autoIssuerecoverVO.getBorrowNid() == null)) {
                    logger.error("标的编号为空");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                // --> 消息处理

                Borrow borrow = autoBailMessageService.getBorrowByBorrowNidrowNid(borrowNid);
                BorrowInfo borrowInfo = autoBailMessageService.getById(borrow.getId());

                if(null != borrowNid){
                    // 自动初审
                    logger.info(borrow.getBorrowNid() + " 开始自动初审 " + borrowInfo.getInstCode());
                    if (borrow == null) {
                        logger.info(" 该资产在表里不存在！！");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }


                    // redis 放重复检查
                    String redisKey = "borrowpreaudit:" + borrowInfo.getInstCode() + borrow.getBorrowNid();
                    boolean result = RedisUtils.tranactionSet(redisKey, 300);
                    if (!result) {
                        logger.info(borrowInfo.getInstCode() + " 正在初审(redis) " + borrow.getBorrowNid());
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }


                    // 业务校验
                    if (borrow.getStatus() != null && borrow.getStatus().intValue() != 1 &&
                            borrow.getVerifyStatus() != null && borrow.getVerifyStatus().intValue() != 1) {
                        logger.info(borrow.getBorrowNid() + " 该资产状态不是初审(已审核保证金)状态");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    //判断该资产是否可以自动初审，是否关联计划
                    HjhAssetBorrowtype hjhAssetBorrowType = autoRecordService.selectAssetBorrowType(borrowInfo);
                    if (hjhAssetBorrowType == null || hjhAssetBorrowType.getAutoAudit() == null || hjhAssetBorrowType.getAutoAudit() != 1) {
                        logger.info(borrow.getBorrowNid() + " 标的不能自动初审,原因自动初审未配置");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    boolean flag = autoPreAuditMessageService.updateRecordBorrow(borrow,borrowInfo);
                    if (!flag) {
                        logger.error("自动初审失败！" + "[项目编号：" + borrow.getBorrowNid() + "]");
                    } else {
                        if (borrow.getIsEngineUsed() == 1) {
                            // 成功后到关联计划队列
                            try {
                                JSONObject params = new JSONObject();
                                params.put("borrowNid", borrow.getBorrowNid());
                                autoIssueMessageProducer.messageSend(new MessageContent(MQConstant.ROCKETMQ_BORROW_ISSUE_TOPIC, UUID.randomUUID().toString(), JSONObject.toJSONBytes(params)));
                            } catch (MQException e) {
                                logger.error("发送【关联计划队列】MQ失败...");
                            }
                        } else {
                            // 散标修改redis：借款的borrowNid,account借款总额
                            RedisUtils.set(RedisConstants.BORROW_NID+borrow.getBorrowNid(), borrow.getAccount().toString());
                        }
                    }
                }

                    logger.info(borrow.getBorrowNid() + " 结束自动初审");

                    /*--------------upd by liushouyi HJH3 End--------------*/

                    if (StringUtils.isNotBlank(autoIssuerecoverVO.getAssetId())) {
                        // 资产自动初审
                        logger.info(autoIssuerecoverVO.getAssetId() + " 开始自动初审 " + autoIssuerecoverVO.getInstCode());
                        HjhPlanAsset hjhPlanAsset = autoPreAuditMessageService.selectPlanAsset(autoIssuerecoverVO.getAssetId(), autoIssuerecoverVO.getInstCode());
                        if (hjhPlanAsset == null) {
                            logger.info(autoIssuerecoverVO.getAssetId() + " 该资产在表里不存在！！");
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }

                        // redis 放重复检查
                        String redisKeys = "borrowpreaudit:" + hjhPlanAsset.getInstCode() + hjhPlanAsset.getAssetId();
                        boolean results = RedisUtils.tranactionSet(redisKeys, 300);
                        if (!results) {
                            logger.info(hjhPlanAsset.getInstCode() + " 正在初审(redis) " + hjhPlanAsset.getAssetId());
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }

                        // 业务校验
                        if (hjhPlanAsset.getStatus() != null && hjhPlanAsset.getStatus().intValue() != 5 &&
                                hjhPlanAsset.getVerifyStatus() != null && hjhPlanAsset.getVerifyStatus().intValue() == 1) {
                            logger.info(autoIssuerecoverVO.getAssetId() + " 该资产状态不是初审状态");
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }

                        //判断该资产是否可以自动初审，是否关联计划t
                        HjhAssetBorrowtype hjhAssetBorrowType = autoPreAuditMessageService.selectAssetBorrowType(hjhPlanAsset);
                        boolean flags = autoPreAuditMessageService.updateRecordBorrow(hjhPlanAsset, hjhAssetBorrowType);
                        if (!flags) {
                            logger.error("自动初审失败！" + "[资产编号：" + hjhPlanAsset.getAssetId() + "]");
                        } else {
                            // 成功后到关联计划队列
                            try {
                                JSONObject params = new JSONObject();
                                params.put("borrowNid", borrow.getBorrowNid());
                                autoIssueMessageProducer.messageSend(new MessageContent(MQConstant.ROCKETMQ_BORROW_ISSUE_TOPIC, UUID.randomUUID().toString(), JSONObject.toJSONBytes(params)));
                            } catch (MQException e) {
                                logger.error("发送【关联计划队列】MQ失败...");
                            }
                        }

                        logger.info(hjhPlanAsset.getAssetId() + " 结束自动初审");

                        // 如果没有return success ，consumer会重新消费该消息，直到return success
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                } catch (Exception e) {
                    logger.error("自动初审异常！",e);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } finally {
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
        }
    }
}
