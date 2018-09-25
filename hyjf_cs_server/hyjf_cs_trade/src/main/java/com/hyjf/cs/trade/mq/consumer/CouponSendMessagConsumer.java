package com.hyjf.cs.trade.mq.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.coupon.UserCouponBean;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.security.util.MD5;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.Consumer;
import com.hyjf.cs.trade.service.coupon.CouponSendMessageService;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 15:11
 * @Description: CouponSendMessagConsumer
 */
public class CouponSendMessagConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(CouponSendMessagConsumer.class);
    @Autowired
    private CouponSendMessageService couponSendMessageService;
    @Autowired
    private SystemConfig systemConfig;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.GRANT_COUPON_GROUP);
        //订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.GRANT_COUPON_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new CouponSendMessagConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可
        defaultMQPushConsumer.start();
        logger.info("====BatchCouponsConsumer start=====");
    }

    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
            logger.info("CouponTenderConsumer 收到消息，开始处理....");
            MessageExt paramBean = list.get(0);
            Map<String, Object> map = new HashMap<>();
            String msgBody = new String(paramBean.getBody());

            UserCouponBean userCouponBean;
            try {
                userCouponBean = JSONObject.parseObject(msgBody, UserCouponBean.class);

                //验证请求参数
                if (Validator.isNull(userCouponBean.getUserId()) && Validator.isNull(userCouponBean.getSendFlg())) {
                    logger.error("【优惠券发放】接收到的消息中信息不全");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                String redisKey = "couponsend:" + userCouponBean.getUserId() + (userCouponBean.getCouponCode()==null?"":userCouponBean.getCouponCode()) + (userCouponBean.getSendFlg()==null?"":userCouponBean.getSendFlg()) + (userCouponBean.getActivityId()==null?"":userCouponBean.getActivityId());
                boolean result = RedisUtils.tranactionSet(redisKey, 300);
                if(!result){
                    logger.error("【优惠券发放】正在发放优惠券....");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                try {
                    if(checkSign(userCouponBean)){
                        couponSendMessageService.insertUserCoupon(userCouponBean);
                    }else{
                        logger.error("用户："+userCouponBean.getUserId()+",验签失败！");
                    }
                } catch (Exception e) {
                    logger.error("用户："+userCouponBean.getUserId()+",发放优惠券---失败", e);
                }

                RedisUtils.del(redisKey);

                logger.info("----------------------------发放优惠券结束 (userid: " + userCouponBean.getUserId() + ")--------------------------------" + this.toString());

            } catch (Exception e1) {
                logger.error("优惠券发放异常！",e1);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

    /**
     * 验证签名
     *
     * @param paramBean
     * @return
     */
    private boolean checkSign(UserCouponBean paramBean) {

        String userId = paramBean.getUserId();
        Integer sendFlg = paramBean.getSendFlg();
        String accessKey = systemConfig.getCouponAccesskey();
        String sign = StringUtils.lowerCase(MD5.toMD5Code(accessKey + userId + sendFlg + accessKey));
        return StringUtils.equals(sign, paramBean.getSign()) ? true : false;

    }
}
