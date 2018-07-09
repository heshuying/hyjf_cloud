/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.consumer;

import java.util.ArrayList;
import java.util.List;

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

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.customize.trade.AleveLogCustomize;
import com.hyjf.am.trade.mq.base.Consumer;
import com.hyjf.am.trade.service.task.AleveLogFileService;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.validator.Validator;

/**
 * @author wangjun
 * @version AutoCorrectionConsumer, v0.1 2018/6/26 15:06
 */
@Component
public class AutoCorrectionConsumer extends Consumer{
    private static final Logger logger = LoggerFactory.getLogger(AutoCorrectionConsumer.class);

    @Autowired
    AleveLogFileService aleveLogFileService;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.AUTO_CORRECTION_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.AUTO_CORRECTION_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new AutoCorrectionConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====AutoCorrection consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

            // --> 消息检查
            MessageExt msg = list.get(0);
            if(msg == null || msg.getBody() == null){
                logger.error("【自动冲正】接收到的消息为null");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            // --> 消息转换
            String msgBody = new String(msg.getBody());
            logger.info("【自动冲正】接收到的消息：" + msgBody);

            JSONObject json = JSONObject.parseObject(msgBody);
            String status = json.getString("status");
            //验证请求参数
            if (Validator.isNull(status) || !"1".equals(status)) {
                logger.error("【自动冲正】接收到的消息不正确");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            try {
                //aleve表中获取冲正数据：调账交易类型为7616、7820且含有冲正标识
                List<String> tranStyle = new ArrayList<String>();
                //活期收益 银联通道提现 单边账调账
                tranStyle.add("7616");
                //靠档计息 人行通道提现 单边账调账
                tranStyle.add("7820");
                List<AleveLogCustomize> aleveLogCustomizes = aleveLogFileService.selectAleveReverseList(tranStyle);
                //无利息数据、打印log并返回
                if (null == aleveLogCustomizes || aleveLogCustomizes.size() == 0) {
                    logger.info("【自动冲正】未查询到冲正信息");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                //循环遍历冲正数据
                for (AleveLogCustomize aleveLogCustomize : aleveLogCustomizes) {
                    if (!"1".equals(aleveLogCustomize.getRevind().toString())) {
                        //处理完成flg变为1下次处理不再抽出
                        if(!aleveLogFileService.updateAleveLog(aleveLogCustomize)) {
                            logger.error("【自动冲正异常】：非冲正flg数据处理完成字段更新失败，银行账号：" + aleveLogCustomize.getCardnbr() + "----Seqno:" + aleveLogCustomize.getSeqno() + "----CreateTime:" + aleveLogCustomize.getCreateTime());
                            continue;
                        }
                        //非冲正交易的场合处理下一条
                        logger.info("【自动冲正】非冲正交易，银行账号：" + aleveLogCustomize.getCardnbr());
                        continue;
                    }
                    //白名单校验订单号+用户名存在的情况不再自动冲正
                    boolean isExists = aleveLogFileService.countManualReverse(aleveLogCustomize) > 0 ? true : false;
                    if (isExists) {
                        //处理完成flg变为1下次处理不再抽出
                        if(!aleveLogFileService.updateAleveLog(aleveLogCustomize)) {
                            logger.error("【自动冲正异常】：手动冲正数据处理完成字段更新失败，银行账号：" + aleveLogCustomize.getCardnbr() + "----Seqno:" + aleveLogCustomize.getSeqno() + "----CreateTime:" + aleveLogCustomize.getCreateTime());
                            continue;
                        }
                        //白名单手动冲正的数据不再处理
                        logger.info("【自动冲正】白名单数据不再处理，银行账号：" + aleveLogCustomize.getCardnbr());
                        continue;
                    }
                    //冲正出错的情况打印log处理下一条
                    if (!aleveLogFileService.updateAutoCorretion(aleveLogCustomize)) {
                        logger.error("【自动冲正异常】用户资产/余额处理失败，银行账号：" + aleveLogCustomize.getCardnbr());
                    }
                }

            } catch (Exception e) {
                logger.error("【自动冲正异常】处理失败！", e);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
