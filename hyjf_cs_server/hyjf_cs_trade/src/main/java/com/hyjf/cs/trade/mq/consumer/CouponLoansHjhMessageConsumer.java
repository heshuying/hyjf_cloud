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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/17 10:18
 * 汇计划优惠券放款
 * @Description: CouponLoansHjhMessageConsumer
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HJH_COUPON_LOAN_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.HJH_COUPON_LOAN_GROUP)
public class CouponLoansHjhMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(CouponLoansHjhMessageConsumer.class);

    @Autowired
    private CouponLoansService couponLoansService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====CouponLoansHjhMessageConsumer start=====");
    }

    @Override
    public void onMessage(MessageExt paramBean) {
        logger.info("CouponLoansHjhMessageConsumer 收到消息，开始处理....");
        CouponLoansBean couponLoansBean = null;
        String msgBody = new String(paramBean.getBody());
        try {
            couponLoansBean = JSONObject.parseObject(msgBody, CouponLoansBean.class);
            logger.info("-----------汇计划优惠券放款，msgBody:" + msgBody);
            //验证请求参数
            if (Validator.isNull(couponLoansBean.getOrderId()) && Validator.isNull(couponLoansBean.getOrderIdCoupon())) {
                logger.error("【汇计划优惠券放款】接收到的消息中信息不全");
                return;
            }
            logger.info("-----------汇计划优惠券放款，OrderId:"+couponLoansBean.getOrderId() + ",OrderIdCoupon:"+couponLoansBean.getOrderIdCoupon());
            String redisKey = RedisConstants.COUPON_LOANS_HJH + (Validator.isNull(couponLoansBean.getOrderId())? couponLoansBean.getOrderIdCoupon() : couponLoansBean.getOrderId());
            boolean result = RedisUtils.tranactionSet(redisKey, 300);
            if(!result){
                logger.error("【汇计划优惠券放款】当前标的正在放款....");
                return;
            }

            List<BorrowTenderCpnVO> listTenderCpn = new ArrayList<BorrowTenderCpnVO>();
            if(StringUtils.isNotEmpty(couponLoansBean.getOrderId())){
                listTenderCpn = couponLoansService.getBorrowTenderCpnHjhList(couponLoansBean.getOrderId());
            }else if(StringUtils.isNotEmpty(couponLoansBean.getOrderIdCoupon())){
                listTenderCpn = couponLoansService.getBorrowTenderCpnHjhCouponOnlyList(couponLoansBean.getOrderIdCoupon());
            }
            logger.info("-----------汇计划优惠券放款，listTenderCpn:"+listTenderCpn);
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
                    logger.info("【汇计划优惠券放款】优惠券使用订单号：" + borrowTenderCpn.getNid());

                    List<Map<String, String>> msgList =
                            couponLoansService.updateCouponRecoverHjh(borrowTenderCpn, couponLoansBean.getOrderId());
                    logger.info("汇计划优惠券放款短信和消息推送开始："+ msgList.size());
                    if (msgList != null && msgList.size() > 0) {
                        // 发送短信
                        couponLoansService.sendSmsCoupon(msgList);
                        // 发送push消息
                        logger.info("--------------准备调用sendAppMSCoupon方法推送push消息--------");
                        couponLoansService.sendAppMSCoupon(msgList);
                        logger.info("--------------调用sendAppMSCoupon方法发送push消息结束");
                    }
                    logger.info("汇计划优惠券放款短信和消息推送结束："+ msgList.size());
                } catch (Exception e) {
                    logger.error("---优惠券放款异常。。。"+"标的编号："+borrowTenderCpn.getBorrowNid() + " 优惠券使用订单号：" + borrowTenderCpn.getNid(), e);
                }
            }

            RedisUtils.del(redisKey);

            logger.info("----------------------------汇计划优惠券放款结束--------------------------------" + this.toString());

            return;
        } catch (Exception e) {
            logger.info("汇计划优惠券放款失败",e);
            return;
        }
    }
}
