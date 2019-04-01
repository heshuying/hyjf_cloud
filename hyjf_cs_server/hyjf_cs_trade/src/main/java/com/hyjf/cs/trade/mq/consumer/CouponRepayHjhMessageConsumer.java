package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.CouponRepayBean;
import com.hyjf.am.vo.trade.coupon.CouponTenderCustomizeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.service.consumer.CouponRepayService;
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

import java.util.List;

/**
 * @Auther: walter.limeng
 * 汇计划优惠券还款
 * @Date: 2018/7/17 16:42
 * @Description: CouponRepayHjhMessageConsumer
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HJH_COUPON_REPAY_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.HJH_COUPON_REPAY_GROUP)
public class CouponRepayHjhMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(CouponLoansHjhMessageConsumer.class);
    @Autowired
    private CouponRepayService couponRepayService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置并发数 update by wgx 2019/03/22 防止消费过多导致即信处理失败
        defaultMQPushConsumer.setConsumeThreadMin(1);
        defaultMQPushConsumer.setConsumeThreadMax(1);
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);
        defaultMQPushConsumer.setConsumeTimeout(30);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====CouponRepayHjhMessageConsumer start=====");
    }

    @Override
    public void onMessage(MessageExt paramBean) {
        logger.info("汇计划优惠券还款开始....");
        CouponRepayBean repayBean = null;
        String msgBody = new String(paramBean.getBody());
        try {
            repayBean = JSONObject.parseObject(msgBody, CouponRepayBean.class);

            //验证请求参数
            if (Validator.isNull(repayBean.getOrderId())) {
                logger.error("【汇计划优惠券还款】接收到的消息中信息不全");
                return;
            }

            String redisKey = RedisConstants.COUPON_REPAY_HJH + repayBean.getOrderId();
            boolean result = RedisUtils.tranactionSet(redisKey, 300);
            if(!result){
                logger.error("【汇计划优惠券还款】当前标的正在还款....");
                return;
            }

            List<CouponTenderCustomizeVO> couponTenderList = couponRepayService.getCouponTenderListHjh(repayBean.getOrderId());

            for(CouponTenderCustomizeVO ct:couponTenderList){
                try{
                    logger.info("【汇计划优惠券还款】优惠券使用订单号：" + ct.getOrderId() + " borrowNid" + ct.getBorrowNid());
                    couponRepayService.updateCouponRecoverHjh(ct.getBorrowNid(), ct);
                    // 避免给即信端造成请求压力导致还款失败
                    Thread.sleep(500);
                }catch(Exception e){
                    // 本次优惠券还款失败
                    logger.error("【汇计划优惠券还款】汇计划优惠券还款失败，优惠券投资编号："+ct.getOrderId() + " borrowNid" + ct.getBorrowNid() , e);
                    return;
                }
            }
            RedisUtils.del(redisKey);
            logger.info("----------------------------汇计划优惠券还款结束--------------------------------");
            return;
        } catch (Exception e) {
            logger.error("汇计划优惠券放款失败");
            return;
        }
    }
}
