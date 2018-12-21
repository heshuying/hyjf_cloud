package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.coupon.UserCouponBean;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.security.util.MD5;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.coupon.CouponSendMessageService;
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

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 15:11
 * @Description: CouponSendMessagConsumer
 */
@Service
@RocketMQMessageListener(topic = MQConstant.GRANT_COUPON_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.GRANT_COUPON_GROUP)
public class CouponSendMessagConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(CouponSendMessagConsumer.class);
    @Autowired
    private CouponSendMessageService couponSendMessageService;
    @Autowired
    private SystemConfig systemConfig;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer)  {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====BatchCouponsConsumer start=====");
    }

    @Override
    public void onMessage(MessageExt paramBean) {
        logger.info("CouponTenderConsumer 收到消息，开始处理....");
        Map<String, Object> map = new HashMap<>();
        String msgBody = new String(paramBean.getBody());

        UserCouponBean userCouponBean;
        try {
            userCouponBean = JSONObject.parseObject(msgBody, UserCouponBean.class);

            //验证请求参数
            if (Validator.isNull(userCouponBean.getUserId()) && Validator.isNull(userCouponBean.getSendFlg())) {
                logger.error("【优惠券发放】接收到的消息中信息不全");
                return;
            }

            String redisKey = RedisConstants.COUPON_SEND + userCouponBean.getUserId() + (userCouponBean.getCouponCode()==null?"":userCouponBean.getCouponCode()) + (userCouponBean.getSendFlg()==null?"":userCouponBean.getSendFlg()) + (userCouponBean.getActivityId()==null?"":userCouponBean.getActivityId());
            boolean result = RedisUtils.tranactionSet(redisKey, 300);
            if(!result){
                logger.error("【优惠券发放】正在发放优惠券....");
                return;
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
            return;
        }
        return;
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
