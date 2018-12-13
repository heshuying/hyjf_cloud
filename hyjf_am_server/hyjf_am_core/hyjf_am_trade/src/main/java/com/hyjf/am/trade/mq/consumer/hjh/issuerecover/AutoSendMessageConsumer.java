package com.hyjf.am.trade.mq.consumer.hjh.issuerecover;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.task.issuerecover.AutoIssueRecoverService;
import com.hyjf.am.trade.service.task.issuerecover.AutoPreAuditMessageService;
import com.hyjf.am.vo.trade.hjh.issuerecover.AutoIssuerecoverVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 自动录标
 * @author walter.limeng
 * @version AutoSendMessageConsumer, v0.1 2018/7/11 10:30
 */
@Component
public class AutoSendMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(AutoSendMessageConsumer.class);
    @Autowired
    private AutoIssueRecoverService autoIssueRecoverService;
    @Autowired
    private CommonProducer commonProducer;
    @Autowired
    private AutoPreAuditMessageService autoPreAuditMessageService;

    @Override
    public void onMessage(MessageExt messageExt) {
        logger.info("AutoSendMessageConsumer 收到消息，开始处理....");
        MessageExt msg = messageExt;
        Integer planId = null;
        try {
            AutoIssuerecoverVO autoIssuerecoverVO = JSONObject.parseObject(msg.getBody(), AutoIssuerecoverVO.class);
            // 计划加入号
            planId = autoIssuerecoverVO.getPlanId();
            // --> 消息处理
            HjhPlanAsset mqHjhPlanAsset = null;
            if (null != planId) {
                // 汇计划自动发标修复
                mqHjhPlanAsset = autoIssueRecoverService.getHjhPlanAssetById(planId);
            } else {
                // 资产推送
                mqHjhPlanAsset = autoPreAuditMessageService.selectPlanAsset(autoIssuerecoverVO.getAssetId(), autoIssuerecoverVO.getInstCode());
            }
            try {
                // 资产自动录标
                // logger.info(mqHjhPlanAsset.getAssetId()+" 开始自动录标 "+ mqHjhPlanAsset.getInstCode());
                // HjhPlanAsset hjhPlanAsset = autoIssueRecoverService.selectPlanAsset(mqHjhPlanAsset.getAssetId(), mqHjhPlanAsset.getInstCode());
                if(mqHjhPlanAsset == null){
                    logger.info(" 该资产在表里不存在！！");
                    return;
                }
                // redis 防重复检查
                String redisKey = RedisConstants.BORROW_SEND + mqHjhPlanAsset.getInstCode()+mqHjhPlanAsset.getAssetId();
                boolean result = RedisUtils.tranactionSet(redisKey, 300);
                if(!result){
                    logger.info(mqHjhPlanAsset.getInstCode()+" 正在录标 (redis)"+mqHjhPlanAsset.getAssetId());
                    return;
                }
                // 业务校验
                if(mqHjhPlanAsset.getStatus() != null && mqHjhPlanAsset.getStatus().intValue() != 0 &&
                        mqHjhPlanAsset.getVerifyStatus() != null && mqHjhPlanAsset.getVerifyStatus().intValue() == 1){
                    logger.info(mqHjhPlanAsset.getAssetId()+" 该资产状态不是录标状态");
                    return;
                }
                //判断该资产是否可以自动录标，是否关联计划
                HjhAssetBorrowtype hjhAssetBorrowType = autoIssueRecoverService.selectAssetBorrowType(mqHjhPlanAsset);
                if(hjhAssetBorrowType==null || hjhAssetBorrowType.getAutoAdd() != 1){
                    logger.info(" 该资产不能自动录标,流程配置未启用");
                    return;
                }

                boolean flag = autoIssueRecoverService.insertSendBorrow(mqHjhPlanAsset,hjhAssetBorrowType);
                if (!flag) {
                    logger.info("自动录标失败！" + "[资产编号：" + mqHjhPlanAsset.getAssetId() + "]");
                }else{
                    // 成功后到备案队列
                    try {
                        JSONObject params = new JSONObject();
                        params.put("planId", mqHjhPlanAsset.getId());
                        //modify by yangchangwei 防止队列触发太快，导致无法获得本事务变泵的数据，延时级别为2 延时5秒
                        commonProducer.messageSendDelay(new MessageContent(MQConstant.ROCKETMQ_BORROW_RECORD_TOPIC, UUID.randomUUID().toString(), params),2);
                    } catch (MQException e) {
                        logger.error("发送【自动录标】MQ失败...");
                    }
                }
                logger.info(mqHjhPlanAsset.getAssetId()+" 结束自动录标");
            } catch (Exception e) {
                logger.error("自动录标异常！",e);
            }
            // 如果没有return success ，consumer会重新消费该消息，直到return success
            return;
        } catch (Exception e) {
            logger.error("自动录标异常！",e);
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
        logger.info("====AutoSendMessageConsumer start=====");
    }
}
