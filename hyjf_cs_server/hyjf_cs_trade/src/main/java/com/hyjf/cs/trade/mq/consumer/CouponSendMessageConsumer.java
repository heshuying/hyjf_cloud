package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.coupon.UserCouponBean;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.service.coupon.CouponSendMessageService;
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
 * @Auther: walter.limeng
 * @Date: 2018/7/16 15:11
 * @Description: CouponSendMessageConsumer
 */
@Service
@RocketMQMessageListener(topic = MQConstant.GRANT_COUPON_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.GRANT_COUPON_GROUP)
public class CouponSendMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
	private static final Logger logger = LoggerFactory.getLogger(CouponSendMessageConsumer.class);
	@Autowired
	private CouponSendMessageService couponSendMessageService;

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		logger.info("====CouponSendMessagConsumer start=====");
	}

	@Override
	public void onMessage(MessageExt paramBean) {
		logger.info("CouponTenderConsumer 收到消息，开始处理....");
		String msgBody = new String(paramBean.getBody());

		UserCouponBean userCouponBean;
		try {
			userCouponBean = JSONObject.parseObject(msgBody, UserCouponBean.class);
			if(userCouponBean == null){
			    logger.error("userCouponBean must be not null....");
			    return;
            }

            logger.info("userCouponBean is： {}", JSONObject.toJSONString(userCouponBean));
			// 验证请求参数
			if (Validator.isNull(userCouponBean.getUserId()) && Validator.isNull(userCouponBean.getSendFlg())) {
				logger.error("【优惠券发放】接收到的消息中信息不全");
				return;
			}

			String redisKey = RedisConstants.COUPON_SEND + userCouponBean.getUserId()
					+ (userCouponBean.getCouponCode() == null ? "" : userCouponBean.getCouponCode())
					+ (userCouponBean.getSendFlg() == null ? "" : userCouponBean.getSendFlg())
					+ (userCouponBean.getActivityId() == null ? "" : userCouponBean.getActivityId());
			boolean result = RedisUtils.tranactionSet(redisKey, 300);
			if (!result) {
				logger.error("【优惠券发放】正在发放优惠券....");
				return;
			}

			couponSendMessageService.insertUserCoupon(userCouponBean);

			RedisUtils.del(redisKey);
			logger.info("发放优惠券结束, userId: " + userCouponBean.getUserId());

		} catch (Exception e) {
			logger.error("优惠券发放异常！", e);
			return;
		}
		return;
	}
}
