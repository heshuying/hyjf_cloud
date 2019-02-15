/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.resquest.admin.ReturnCashRequest;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.ActivityDateUtil;
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

import java.math.BigDecimal;

/**
 * 纳觅返现活动
 * @author tanyy
 * @version ReturnCashActivityMessageConsumer, v0.1 2018/11/8 14:04
 */
@Service
@RocketMQMessageListener(topic = MQConstant.RETURN_CASH_ACTIVITY_SAVE_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.RETURN_CASH_ACTIVITY_SAVE_GROUP)
public class ReturnCashActivityMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private Logger _log = LoggerFactory.getLogger(ReturnCashActivityMessageConsumer.class);

    @Autowired
    AmAdminClient amAdminClient;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        _log.info("====纳觅返现消费端开始运行=====");
    }

    @Override
    public void onMessage(MessageExt message) {
        if(message == null || message.getBody() == null){
            _log.error("【纳觅返现活动】接收到的消息为null");
            return;
        }

        String msgBody = new String(message.getBody());

        _log.info("【纳觅返现活动】接收到的消息：" + msgBody);

        JSONObject requestJson;
        Integer userId = null;
        String orderId = null;
        Integer productType = null;
        BigDecimal investMoney = BigDecimal.ZERO;
        try {
            requestJson = JSONObject.parseObject(msgBody);
            userId = (Integer) requestJson.get("userId");
            orderId = (String)requestJson.get("orderId");
            productType = (Integer) requestJson.get("productType");
            investMoney = new BigDecimal((String) requestJson.get("investMoney"));
        } catch (Exception e1) {
            _log.error("纳觅返现活动参数解释失败：" + msgBody, e1);
            return;
        }
        // 活动有效期校验
        StringResponse response = amAdminClient.checkActivityIfAvailable(Integer.valueOf(ActivityDateUtil.RETURNCASH_ACTIVITY_ID));
        //判断活动是否开始
        if (response==null||!"000".equals(response.getResultStr())) {
            _log.info("【纳觅返现活动】 活动有效期校验不对 orderId: " + orderId + "resultActivity:"+response.getResultStr());
            return ;
        }
        String redisKey = RedisConstants.RETURN_CASH_ACTIVITY + orderId;
        boolean result = RedisUtils.tranactionSet(redisKey, 60);
        if(!result){
            _log.error("【纳觅返现活动】正在处理....");
            return;
        }
        try {
            ReturnCashRequest returnCashRequest = new ReturnCashRequest();
            returnCashRequest.setUserId(userId);
            returnCashRequest.setOrderId(orderId);
            returnCashRequest.setProductType(productType);
            returnCashRequest.setInvestMoney(investMoney);
            IntegerResponse integerResponse = amAdminClient.saveReturnCash(returnCashRequest);
            if(integerResponse.getResultInt()==0){
                _log.error("【纳觅返现活动】保存失败，userId："  + userId + " orderId:" + orderId+" productType:"+productType);
            }
        } catch (Exception e) {
            _log.error("【纳觅返现活动】处理失败，userId："  + userId + " orderId:" + orderId+" productType:"+productType, e);
            return;
        }
        RedisUtils.del(redisKey);
        _log.info("----------------------------纳觅返现活动结束 (orderId: " + orderId + ")--------------------------------" + this.toString());

    }
}
