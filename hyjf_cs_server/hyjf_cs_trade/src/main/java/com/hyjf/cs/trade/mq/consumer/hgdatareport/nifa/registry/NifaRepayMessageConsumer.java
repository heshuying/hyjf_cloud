/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.hgdatareport.nifa.registry;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.service.consumer.hgdatareport.nifa.NifaRepayMessageService;
import org.apache.commons.lang3.StringUtils;
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
 * @author PC-LIUSHOUYI
 * @version NifaRepayInfoMessageConsumer, v0.1 2018/9/11 16:42
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HYJF_TOPIC, selectorExpression = MQConstant.REPAY_SINGLE_SUCCESS_TAG, consumerGroup = MQConstant.NIFA_REPAY_INFO_GROUP)
public class NifaRepayMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(NifaRepayMessageConsumer.class);

    @Autowired
    NifaRepayMessageService nifaRepayMessageService;

    private String thisMessName = "【生成还款记录、合同状态、出借人回款信息】";

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====NifaRepayInfoMessageConsumer consumer=====");
    }

    @Override
    public void onMessage(MessageExt msg) {
        // --> 消息检查
        if (msg == null || msg.getBody() == null) {
            logger.error(thisMessName + "接收到的消息为null");
            return;
        }

        // --> 消息转换
        String msgBody = new String(msg.getBody());
        logger.info(thisMessName + "接收到的消息：" + msgBody);

        JSONObject json = null;
        try {
            json = JSONObject.parseObject(msgBody);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(thisMessName + "解析消息体失败...");
            return;
        }
        String borrowNid = json.getString("borrowNid");
        Integer repayPeriod = json.getInteger("repayPeriod");
        if (StringUtils.isBlank(borrowNid) || null == repayPeriod) {
            logger.error(thisMessName + "接收到的borrowNid为null");
            return;
        }

        // --> 消息处理
        try {
            // 借款人项目还款记录数据生成
            boolean repayInfoResult = nifaRepayMessageService.insertNifaRepayInfo(borrowNid, repayPeriod);
            if (!repayInfoResult) {
                logger.error(thisMessName + "借款人项目还款记录数据生成失败！！borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
            }

            // 合同状态变更数据生成
            boolean contractStatusResult = nifaRepayMessageService.insertNifaContractStatus(borrowNid, repayPeriod);
            if (!contractStatusResult) {
                logger.error(thisMessName + "合同状态变更数据生成失败！！borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
            }

            // 出借人回款记录生成
            boolean receivedPaymentsResult = nifaRepayMessageService.insertNifaReceivedPayments(borrowNid, repayPeriod);
            if (!receivedPaymentsResult) {
                logger.error(thisMessName + "出借人回款记录生成失败！！borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
            }
        } catch (Exception e) {
            logger.error(thisMessName + "处理失败.....,借款编号:" + borrowNid, e);
        } finally {
            logger.info(thisMessName + "处理结束,借款编号:" + borrowNid);
        }
    }
}
