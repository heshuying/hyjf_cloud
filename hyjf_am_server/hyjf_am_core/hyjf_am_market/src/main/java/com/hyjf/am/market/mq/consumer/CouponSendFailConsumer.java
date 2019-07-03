package com.hyjf.am.market.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.market.service.CouponSendFailService;
import com.hyjf.am.vo.coupon.UserCouponBean;
import com.hyjf.common.constants.MQConstant;
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
 * 保存优惠券发放失败记录
 * @author xiasq
 * @version CouponSendFailServiceImpl, v0.1 2019/5/20 11:43
 */
@Service
@RocketMQMessageListener(topic = MQConstant.GRANT_COUPON_FAIL_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.GRANT_COUPON_FAIL_GROUP)
public class CouponSendFailConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(CouponSendFailConsumer.class);

    @Autowired
    private CouponSendFailService couponSendFailService;


    @Override
    public void onMessage(MessageExt message) {
        logger.info("CouponSendFailConsumer 收到消息，开始处理....");
        String msgBody = new String(message.getBody());

        UserCouponBean userCouponBean;
        try {
            userCouponBean = JSONObject.parseObject(msgBody, UserCouponBean.class);
            if (userCouponBean == null) {
                logger.error("userCouponBean must be not null....");
                return;
            }

            couponSendFailService.save(userCouponBean.getUserId(), userCouponBean.getActivityId(),
                    userCouponBean.getCouponCode(), userCouponBean.getSendFlg(), userCouponBean.getRemark());
        } catch (Exception e) {
            logger.error("save CouponSendFail fail....");
        }

    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        // MQ默认集群消费
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====CouponSendFailConsumer start=====");
    }

}
