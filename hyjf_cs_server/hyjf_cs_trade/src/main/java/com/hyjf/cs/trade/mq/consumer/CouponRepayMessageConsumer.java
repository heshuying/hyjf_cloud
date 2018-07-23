package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.CouponRepayBean;
import com.hyjf.am.vo.trade.coupon.CouponTenderCustomizeVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.mq.base.Consumer;
import com.hyjf.cs.trade.service.CouponRepayService;
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
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/18 16:23
 * @Description: CouponRepayMessageConsumer
 */
@Component
public class CouponRepayMessageConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(CouponRepayMessageConsumer.class);
    @Autowired
    private CouponRepayService repayService;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.HZT_COUPON_REPAY_GROUP);
        //订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.HZT_COUPON_REPAY_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new CouponRepayMessageConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可
        defaultMQPushConsumer.start();
        logger.info("====CouponRepayHjhMessageConsumer start=====");
    }

    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
            logger.info("直投类优惠券还款开始....");
            MessageExt paramBean = list.get(0);
            CouponRepayBean repayBean = null;
            String msgBody = new String(paramBean.getBody());
            try {
                repayBean = JSONObject.parseObject(msgBody, CouponRepayBean.class);
//验证请求参数
                if (Validator.isNull(repayBean.getBorrowNid()) || Validator.isNull(repayBean.getPeriodNow())) {
                    logger.error("【优惠券还款】接收到的消息中信息不全");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                String redisKey = "couponrepay:" + repayBean.getBorrowNid() + ":" + repayBean.getPeriodNow();
                boolean result = RedisUtils.tranactionSet(redisKey, 300);
                if(!result){
                    logger.error("【优惠券还款】当前标的正在还款....");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                int failTimes = 0;
                List<CouponTenderCustomizeVO> couponTenderList = repayService.getCouponTenderList(repayBean.getBorrowNid());
                for(CouponTenderCustomizeVO ct:couponTenderList){
                    try{
                        logger.info("【优惠券还款】优惠券使用订单号：" + ct.getOrderId() + " borrowNid:" + ct.getBorrowNid());
                        repayService.updateCouponRecoverMoney(repayBean.getBorrowNid(),repayBean.getPeriodNow(), ct);
                        // 避免给即信端造成请求压力导致还款失败
                        Thread.sleep(500);
                    }catch(CannotAcquireLockException e){
                        failTimes++;
                        logger.error("【优惠券还款】发生锁等待超时异常，当前锁等待超时次数：" + failTimes + " 优惠券投资编号:" +ct.getOrderId() + " borrowNid:" + ct.getBorrowNid() , e);
                    }catch(Exception e){
                        // 本次优惠券还款失败
                        logger.error("【优惠券还款】直投类优惠券还款失败，优惠券投资编号："+ct.getOrderId() + " borrowNid:" + ct.getBorrowNid(), e);
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                }

                RedisUtils.del(redisKey);

                if(failTimes > 0){
                    logger.info("【优惠券还款】锁等待超时次数：" + failTimes, "重新放回消息队列等待再次执行,borrowNid:" + repayBean.getBorrowNid());
                    Thread.sleep(1000*5);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                logger.info("----------------------------直投类优惠券还款结束--------------------------------");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e) {
                logger.info("直投类优惠券还款失败");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
    }
}
