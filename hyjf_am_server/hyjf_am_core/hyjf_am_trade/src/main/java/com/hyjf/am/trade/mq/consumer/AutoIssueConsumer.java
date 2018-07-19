package com.hyjf.am.trade.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.HjhLabel;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.trade.mq.base.Consumer;
import com.hyjf.am.trade.service.AssetPushService;
import com.hyjf.am.trade.service.admin.AdminAllocationEngineService;
import com.hyjf.am.trade.service.admin.AdminHjhLabelService;
import com.hyjf.am.vo.task.autoissue.AutoIssueMsg;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自动关联计划mq监听
 * (工作已经由其他同事完成, 该消费者不在启动监听和消费动作)
 * @author zhangyk
 * @date 2018/7/17 15:20
 */
@Component
public class AutoIssueConsumer extends Consumer {

    private static final Logger logger = LoggerFactory.getLogger(AutoIssueConsumer.class);

    private static final String TASK_NAME = "<<自动关联计划>>:";

    private static final String CONSUMER_QUIT = "消费退出";


    @Autowired
    private AssetPushService assetPushService;

    @Autowired
    private AdminHjhLabelService adminHjhLabelService;

    @Autowired
    private AdminAllocationEngineService adminAllocationEngineService;


    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setConsumerGroup(MQConstant.HYJF_BORROW_ISSUE_GROUP);
        defaultMQPushConsumer.subscribe(MQConstant.HYJF_BORROW_ISSUE_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        // 设置并发数
        defaultMQPushConsumer.setConsumeThreadMin(1);
        defaultMQPushConsumer.setConsumeThreadMax(1);
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);
        defaultMQPushConsumer.setConsumeTimeout(30);
        defaultMQPushConsumer.registerMessageListener(new MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        //defaultMQPushConsumer.start();   // 工作已经由其他同事完成,  该消费者暂时不用启动
        logger.info("====" + TASK_NAME + "监听初始化完成, 启动完毕=====");
    }


    // 监听器类
    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            logger.info("========" + TASK_NAME + "监听器收到消息:{}========", JSON.toJSONString(msgs));

            try {
                MessageExt msg = msgs.get(0);
                AutoIssueMsg autoIssueMsg = JSONObject.parseObject(msg.getBody(), AutoIssueMsg.class);
                // 参数完整性校验
                if (autoIssueMsg == null || (StringUtils.isBlank(autoIssueMsg.getBorrowNid()) && StringUtils.isBlank(autoIssueMsg.getCreditNid()))) {
                    logger.error("=====" + TASK_NAME + "消息体信息不完整, " + CONSUMER_QUIT + "===============");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                String borrowNid = autoIssueMsg.getBorrowNid();
                String creditNid = autoIssueMsg.getCreditNid();
                // 消息体是原始标的的情况
                if (StringUtils.isNotBlank(borrowNid)) {
                    logger.info("====" + TASK_NAME + "原始标的开始关联计划borrowNid= {}====", borrowNid);
                    Borrow borrow = null;
                    Boolean quitFlag = checkInfo(borrowNid, borrow);
                    // 校验参数不通过
                    if (quitFlag) {
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    // 拆库后，instcode字段放在了borrowInfo表，查询资产需要用instcode
                    BorrowInfo borrowInfo = assetPushService.getBorrowInfoByNid(borrowNid);
                    if (borrowInfo == null || StringUtils.isBlank(borrowInfo.getInstCode())) {
                        logger.info("=====" + TASK_NAME + "该标的[{}]详情不存在,或者机构编号为空," + CONSUMER_QUIT + "=====", borrowNid);
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    HjhPlanAsset asset = assetPushService.selectPlanAsset(borrowNid, borrowInfo.getInstCode());
                    if (asset != null) {  // 是第三方资金
                        if (StringUtils.isNotBlank(asset.getPlanNid()) || asset.getLabelId() == null || asset.getLabelId().intValue() == 0) {
                            logger.info("======" + TASK_NAME + " 该标的[{}]对应的资产已经绑定计划或无标签(状态异常)," + CONSUMER_QUIT + "=====", asset.getBorrowNid());
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                        if (asset.getStatus().intValue() != 7) {
                            logger.info("=====" + TASK_NAME + " 该标的[{}]对应资产不是投资中状态 =====", asset.getBorrowNid());
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                        // 散标过来的标的，没有标签先打上
                    } else if (borrow.getLabelId() != null && borrow.getLabelId().intValue() == 0) {
                        HjhLabel label = adminHjhLabelService.getBestLabel(borrow, borrowInfo, null);
                        if (label == null || label.getId() == null) {
                            logger.info("=====" + TASK_NAME + "该散标[{}]没有匹配到标签=====", borrow.getBorrowNid());
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                        borrow.setLabelId(label.getId());
                    }

                    // 分配计划引擎
                    String planNid = adminAllocationEngineService.getPlanNidByLable(borrow.getLabelId());
                    if(planNid == null || borrow.getLabelId() == null || borrow.getLabelId().intValue()==0){
                        logger.info("====="+TASK_NAME+" 该标的标签[{}]没有关联到计划=====", borrow.getLabelId());
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    // 债转标的的情况
                } else if (StringUtils.isNotBlank(creditNid)) {

                }


            } catch (Exception e) {

            }
            return null;
        }

        /**
         * 原始标的:简单业务校验
         *
         * @author zhangyk
         * @date 2018/7/17 17:26
         */
        private Boolean checkInfo(String borrowNid, Borrow borrow) {
            // redis 放重复检查
            String redisKey = RedisConstants.AUTO_ISSUE_REPEAT + ":" + borrowNid;
            boolean result = RedisUtils.tranactionSet(redisKey, 300);
            if (!result) {
                logger.info("=====" + TASK_NAME + "redis防重复判定命中:该标的[{}]已经在关联计划(redis)," + CONSUMER_QUIT + "=====", borrowNid);
                return Boolean.TRUE;
            }

            borrow = assetPushService.getBorrow(borrowNid);
            if (borrow == null) {
                logger.info("=====" + TASK_NAME + " 该标的[{}]在数据表不存在," + CONSUMER_QUIT + "=====", borrowNid);
                return Boolean.TRUE;
            }

            // 业务校验
            // 必须处于发标状态
            if (borrow.getStatus() != 2 || borrow.getVerifyStatus() != 4) {
                logger.info("=====" + TASK_NAME + " 该标的[{}]不是处于发标状态," + CONSUMER_QUIT + "=====", borrowNid);
                return Boolean.TRUE;
            }
            if (StringUtils.isNotBlank(borrow.getPlanNid())) {
                logger.info("=====" + TASK_NAME + " 该标的[{}]已经绑定过计划[{}]," + CONSUMER_QUIT + "=====", borrowNid, borrow.getLabelId());
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
    }
}
