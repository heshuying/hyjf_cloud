/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.customize.AleveLogCustomize;
import com.hyjf.am.trade.service.task.AleveLogFileService;
import com.hyjf.common.validator.Validator;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjun
 * @version AutoCorrectionConsumer, v0.1 2018/6/26 15:06
 */
@Component
public class AutoCorrectionConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(AutoCorrectionConsumer.class);

    @Autowired
    private AleveLogFileService aleveLogFileService;

    @Override
    public void onMessage(MessageExt messageExt) {
        // --> 消息检查
        MessageExt msg = messageExt;
        if(msg == null || msg.getBody() == null){
            logger.error("【自动冲正】接收到的消息为null");
            return;// ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        // --> 消息转换
        String msgBody = new String(msg.getBody());
        logger.info("【自动冲正】接收到的消息：" + msgBody);

        JSONObject json = JSONObject.parseObject(msgBody);
        String status = json.getString("status");
        //验证请求参数
        if (Validator.isNull(status) || !"1".equals(status)) {
            logger.error("【自动冲正】接收到的消息不正确");
            return;
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
                return;
            }
            //自动冲正
            aleveLogFileService.updateAutoCorretion(aleveLogCustomizes);

        } catch (Exception e) {
            //异常时重发
            logger.error("【自动冲正异常】处理失败！", e);
            return;// ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return;
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====AutoCorrection consumer=====");
    }
}
