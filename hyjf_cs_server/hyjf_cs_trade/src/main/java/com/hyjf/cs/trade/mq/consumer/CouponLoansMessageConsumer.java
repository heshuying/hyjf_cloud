package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.CouponLoansBean;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.service.consumer.CouponLoansService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/18 18:08
 * @Description: CouponLoansMessageConsumer
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HZT_COUPON_LOAN_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.HZT_COUPON_LOAN_GROUP)
public class CouponLoansMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(CouponLoansMessageConsumer.class);

    @Autowired
    CouponLoansService couponLoansService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====CouponLoansMessageConsumer start=====");
    }


    @Override
    public void onMessage(MessageExt paramBean) {
        logger.info("CouponLoansMessageConsumer 收到消息，开始处理....");
        CouponLoansBean loansBean = null;
        String msgBody = new String(paramBean.getBody());
        try {
            loansBean = JSONObject.parseObject(msgBody, CouponLoansBean.class);

            logger.info("----------------散标优惠券放款，msgBody:" + msgBody);
            //验证请求参数
            if (Validator.isNull(loansBean.getBorrowNid())) {
                logger.error("【优惠券放款】接收到的消息中信息不全");
                return;
            }

            String redisKey = RedisConstants.COUPON_LOANS + loansBean.getBorrowNid();
            boolean result = RedisUtils.tranactionSet(redisKey, 300);
            if(!result){
                logger.error("【优惠券放款】当前标的正在放款....");
                return;
            }

            List<BorrowTenderCpnVO> listTenderCpn = couponLoansService.getBorrowTenderCpnList(loansBean.getBorrowNid());

            if(listTenderCpn == null || listTenderCpn.isEmpty()){
                logger.info("----------散标优惠券放款没有投资详情，borrowNid：" + loansBean.getBorrowNid());
            }
            /** 循环优惠券投资详情列表 */
            for (BorrowTenderCpnVO borrowTenderCpn : listTenderCpn) {
                try {
                    if (Validator.isNull(borrowTenderCpn.getLoanOrdid())) {
                        // 设置放款订单号
                        borrowTenderCpn.setLoanOrdid(GetOrderIdUtils.getOrderId2(borrowTenderCpn
                                .getUserId()));
                        // 设置放款时间
                        borrowTenderCpn.setOrderDate(GetOrderIdUtils.getOrderDate());
                        // 更新放款信息
                        couponLoansService.updateBorrowTenderCpn(borrowTenderCpn);
                    }
                    logger.info("【优惠券放款】优惠券使用orderId: " + borrowTenderCpn.getNid() + " borrowNid: " + loansBean.getBorrowNid());
                    List<Map<String, String>> msgList =
                            couponLoansService.updateCouponRecover(borrowTenderCpn);
                    logger.info("散标优惠券放款短信和消息推送开始："+ msgList.size());
                    if (msgList != null && msgList.size() > 0) {
                        // 发送短信
                        couponLoansService.sendSmsCoupon(msgList);
                        // 发送push消息
                        logger.info("--------------准备调用sendAppMSCoupon方法推送push消息--------");
                        couponLoansService.sendAppMSCoupon(msgList);
                        logger.info("--------------调用sendAppMSCoupon方法发送push消息结束");
                    }
                    logger.info("散标优惠券放款短信和消息推送结束："+ msgList.size());
                } catch (Exception e) {
                    logger.error("---优惠券放款异常。。。"+"标的编号："+loansBean.getBorrowNid() + " 优惠券使用orderId: " + borrowTenderCpn.getNid(), e);
                }
            }

            RedisUtils.del(redisKey);

            logger.info("----------------------------优惠券放款结束--------------------------------");
            return;
        } catch (Exception e) {
            logger.error("散标优惠券放款失败");
            return;
        }
    }
}
