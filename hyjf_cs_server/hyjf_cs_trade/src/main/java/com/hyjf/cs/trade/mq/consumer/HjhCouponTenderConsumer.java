/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.mq.base.Consumer;
import com.hyjf.cs.trade.service.consumer.CouponService;
import org.apache.commons.collections.MapUtils;
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
 * @Description 计划类优惠券使用
 * @Author sunss
 * @Date 2018/7/11 14:26
 */
@Component
public class HjhCouponTenderConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(HjhCouponTenderConsumer.class);

    @Autowired
    private CouponService couponService;
    @Autowired
    private AmTradeClient borrowClient;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.HJH_COUPON_TENDER_GROUP);
        //订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.HJH_COUPON_TENDER_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可
        defaultMQPushConsumer.start();
        logger.info("====计划类优惠券使用 start=====");
    }

    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
            logger.info("计划类优惠券使用 收到消息，开始处理....");
            MessageExt paramBean = list.get(0);
            Map<String, Object> map = new HashMap<>();
            String msgBody = new String(paramBean.getBody());
            map = JSONObject.parseObject(msgBody, Map.class);
            logger.info("计划类优惠券使用 收到消息，参数为: {} " , JSONObject.toJSONString(map));
            JSONObject result = new JSONObject();
            try {
                Integer couponGrantId = (Integer) map.get("couponGrantId");
                String borrowNid = (String) map.get("borrowNid");
                String money = (String) map.get("money");
                String platform = (String) map.get("platform");
                String ip = (String) map.get("ip");
                // 真实订单号
                Integer userId = (Integer) map.get("userId");
                String mainTenderNid = (String) map.get("ordId");
                String account = (String) map.get("account");

                HjhPlanVO plan = borrowClient.getPlanByNid(borrowNid);

                // 检查优惠券能用不
                logger.info("优惠券投资校验开始,userId{},平台{},券为:{}", userId, platform, couponGrantId);
                Map<String, String> validateMap = couponService.validateCoupon(userId, money, couponGrantId,
                        platform, plan.getLockPeriod(),plan.getCouponConfig());
                logger.info("优惠券投资校验结果  "+JSONObject.toJSONString(validateMap));
                if (MapUtils.isEmpty(validateMap)) {
                    // 校验通过 进行优惠券投资投资
                    logger.info("优惠券投资校验成功,userId{},券为:{}", userId, couponGrantId);
                    CouponUserVO cuc = borrowClient.getCouponUser(couponGrantId, userId);
                    logger.info("优惠券投资开始,userId{},平台{},券为:{}", userId, platform, couponGrantId);
                    TenderRequest request = new TenderRequest();
                    request.setIp(ip);
                    request.setMainTenderNid(mainTenderNid);
                    request.setAccount(account);
                    couponService.couponTender(request, plan, cuc , userId);
                } else {
                    logger.error("优惠券投资校验失败返回结果{},userId{},券为:{}", validateMap.get("statusDesc"), userId, couponGrantId);
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e) {
                logger.info("操作失败");
                logger.info("计划优惠券投资失败    "+JSONObject.toJSONString(e));
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
    }
}
