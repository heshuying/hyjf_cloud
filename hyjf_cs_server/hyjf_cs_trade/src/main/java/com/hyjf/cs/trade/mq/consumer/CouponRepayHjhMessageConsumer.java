package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.CouponRepayBean;
import com.hyjf.am.vo.trade.coupon.CouponTenderCustomizeVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.mq.base.Consumer;
import com.hyjf.cs.trade.service.consumer.CouponRepayService;
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

import java.util.List;

/**
 * @Auther: walter.limeng
 * 汇计划优惠券还款
 * @Date: 2018/7/17 16:42
 * @Description: CouponRepayHjhMessageConsumer
 */
@Component
public class CouponRepayHjhMessageConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(CouponLoansHjhMessageConsumer.class);
    @Autowired
    private CouponRepayService couponRepayService;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.HJH_COUPON_REPAY_GROUP);
        //订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.HJH_COUPON_REPAY_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new CouponRepayHjhMessageConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可
        defaultMQPushConsumer.start();
        logger.info("====CouponRepayHjhMessageConsumer start=====");
    }

    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
            logger.info("汇计划优惠券还款开始....");
            MessageExt paramBean = list.get(0);
            CouponRepayBean repayBean = null;
            String msgBody = new String(paramBean.getBody());
            try {
                repayBean = JSONObject.parseObject(msgBody, CouponRepayBean.class);

                //验证请求参数
                if (Validator.isNull(repayBean.getOrderId())) {
                    logger.error("【汇计划优惠券还款】接收到的消息中信息不全");
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                String redisKey = "couponrepayhjh:" + repayBean.getOrderId();
                boolean result = RedisUtils.tranactionSet(redisKey, 300);
                if(!result){
                    logger.error("【汇计划优惠券还款】当前标的正在还款....");
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
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
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                RedisUtils.del(redisKey);
                logger.info("----------------------------汇计划优惠券还款结束--------------------------------");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e) {
                logger.info("汇计划优惠券放款失败");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
    }
}
