/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.mq.base.Consumer;
import com.hyjf.cs.trade.service.consumer.CouponService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 优惠券使用
 * @Author sunss
 * @Date 2018/7/11 14:26
 */
@Component
public class CouponTenderConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(CouponTenderConsumer.class);

    @Autowired
    private CouponService couponService;
    @Autowired
    private AmTradeClient borrowClient;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.HZT_COUPON_TENDER_GROUP);
        //订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.HZT_COUPON_TENDER_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可
        defaultMQPushConsumer.start();
        logger.info("====散标优惠券投资 start=====");
    }

    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
            logger.info("散标优惠券投资 收到消息，开始处理....");
            MessageExt paramBean = list.get(0);
            Map<String, Object> map = new HashMap<>();
            String msgBody = new String(paramBean.getBody());
            map = JSONObject.parseObject(msgBody, Map.class);
            logger.info("散标优惠券投资请求参数：{}",JSONObject.toJSONString(map));
            String couponGrantId = (String) map.get("couponGrantId");
            try {
                String borrowNid = (String) map.get("borrowNid");
                String money = (String) map.get("money");
                String platform = (String) map.get("platform");
                String ip = "";
                if(map.containsKey("ip")){
                    ip = (String) map.get("ip");
                }
                String ordId = (String) map.get("ordId");
                String userId = (String) map.get("userId");
                BankCallBean bean = new BankCallBean();
                bean.setLogOrderId(ordId);
                bean.setLogIp(ip);
                bean.setLogUserId(userId);
                bean.setLogClient(Integer.parseInt(platform));
                bean.setTxAmount(money);
                BorrowAndInfoVO borrow = borrowClient.selectBorrowByNid(borrowNid);
                BorrowInfoVO borrowInfoVO = borrowClient.getBorrowInfoByNid(borrowNid);
                couponService.borrowTenderCouponUse(couponGrantId, borrow, bean,borrowInfoVO);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("操作失败了:"+JSONObject.toJSONString(e));
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }finally {
                if(couponGrantId!=null){
                    RedisUtils.del(RedisConstants.COUPON_TENDER_KEY+couponGrantId);
                }
            }
        }
    }
}
