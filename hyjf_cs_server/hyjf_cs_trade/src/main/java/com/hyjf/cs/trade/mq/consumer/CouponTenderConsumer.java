/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.consumer.CouponService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
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
import java.util.List;
import java.util.Map;

/**
 * @Description 优惠券使用
 * @Author sunss
 * @Date 2018/7/11 14:26
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HZT_COUPON_TENDER_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.HZT_COUPON_TENDER_GROUP)
public class CouponTenderConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(CouponTenderConsumer.class);

    @Autowired
    private CouponService couponService;
    @Autowired
    private AmTradeClient borrowClient;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====散标优惠券投资 start=====");
    }

    @Override
    public void onMessage(MessageExt paramBean) {
        logger.info("散标优惠券投资 收到消息，开始处理....");
        Map<String, Object> map = new HashMap<>();
        String msgBody = new String(paramBean.getBody());
        map = JSONObject.parseObject(msgBody, Map.class);
        logger.info("散标优惠券投资请求参数：{}",JSONObject.toJSONString(map));
        String couponGrantId = (String) map.get("couponGrantId");
        try {
            String borrowNid = (String) map.get("borrowNid");
            String money = (String) map.get("money");
            String platform = (String) map.get("platform");
            String ip = "";
            if(map.containsKey("ip")){
                ip = (String) map.get("ip");
            }
            String ordId = (String) map.get("ordId");
            String userId = (String) map.get("userId");
            String userName = (String) map.get("userName");
            BankCallBean bean = new BankCallBean();
            bean.setLogOrderId(ordId);
            bean.setLogIp(ip);
            bean.setLogUserId(userId);
            bean.setLogClient(Integer.parseInt(platform));
            bean.setTxAmount(money);
            bean.setLogUserName(userName);
            BorrowAndInfoVO borrow = borrowClient.selectBorrowByNid(borrowNid);
            BorrowInfoVO borrowInfoVO = borrowClient.getBorrowInfoByNid(borrowNid);
            BorrowProjectTypeVO borrowProjectType = this.getProjectType(String.valueOf(borrow.getProjectType()));
            String nowType = "1";
            if (borrowProjectType.getInvestUserType().equals(1)) {
                nowType = "3";
            }
            couponService.borrowTenderCouponUse(couponGrantId, borrow, bean,borrowInfoVO,nowType);
            return;
        } catch (Exception e) {
            logger.error("操作失败了:", e);
            return;
        }finally {
            if(couponGrantId!=null){
                RedisUtils.del(RedisConstants.COUPON_TENDER_KEY+couponGrantId);
            }
        }
    }
    private BorrowProjectTypeVO getProjectType(String projectType) {
        BorrowProjectTypeVO borrowProjectType = null;
        List<BorrowProjectTypeVO> projectTypes = borrowClient.selectBorrowProjectByBorrowCd(projectType);
        if (projectTypes != null && projectTypes.size() == 1) {
            borrowProjectType = projectTypes.get(0);
        }
        return borrowProjectType;
    }
}
