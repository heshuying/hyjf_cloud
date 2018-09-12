/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.nifa;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.mq.base.Consumer;
import com.hyjf.cs.trade.service.consumer.NifaRepayInfoMessageService;
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
 * @author PC-LIUSHOUYI
 * @version NifaRepayInfoMessageConsumer, v0.1 2018/9/11 16:42
 */
@Component
public class NifaRepayInfoMessageConsumer extends Consumer {

    private static final Logger logger = LoggerFactory.getLogger(NifaContractEssenceMessageConsumer.class);

    @Autowired
    NifaRepayInfoMessageService nifaRepayInfoMessageService;

    private String thisMessName = "【生成还款记录、合同状态、出借人回款信息】";

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.NIFA_REPAY_INFO_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.NIFA_REPAY_INFO_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new NifaRepayInfoMessageConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====NifaContractEssence consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

            // --> 消息检查
            MessageExt msg = list.get(0);
            if (msg == null || msg.getBody() == null) {
                logger.error(thisMessName + "接收到的消息为null");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            // --> 消息转换
            String msgBody = new String(msg.getBody());
            logger.info(thisMessName + "接收到的消息：" + msgBody);

            JSONObject json = null;
            try {
                json = JSONObject.parseObject(msgBody);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(thisMessName + "解析消息体失败...");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            String borrowNid = json.getString("borrowNid");
            Integer repayPeriod = json.getInteger("repayPeriod");
            if (StringUtils.isBlank(borrowNid) || null == repayPeriod) {
                logger.error(thisMessName + "接收到的borrowNid为null");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            // --> 消息处理

            try {
                // 借款人项目还款记录数据生成
                boolean repayInfoResult = nifaRepayInfoMessageService.insertNifaRepayInfo(borrowNid, repayPeriod);
                if (!repayInfoResult) {
                    logger.error(thisMessName + "借款人项目还款记录数据生成失败！！borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
                }

                // 合同状态变更数据生成
                boolean contractStatusResult = nifaRepayInfoMessageService.insertNifaContractStatus(borrowNid, repayPeriod);
                if (!contractStatusResult) {
                    logger.error(thisMessName + "合同状态变更数据生成失败！！borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
                }

                // 出借人回款记录生成
                boolean receivedPaymentsResult = nifaRepayInfoMessageService.insertNifaReceivedPayments(borrowNid, repayPeriod);
                if (!receivedPaymentsResult) {
                    logger.error(thisMessName + "出借人回款记录生成失败！！borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
                }
            } catch (Exception e) {
                logger.info(thisMessName + "处理失败.....,借款编号:" + borrowNid);
                e.printStackTrace();
            } finally {
                logger.info(thisMessName + "处理结束,借款编号:" + borrowNid);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        }
    }
}
