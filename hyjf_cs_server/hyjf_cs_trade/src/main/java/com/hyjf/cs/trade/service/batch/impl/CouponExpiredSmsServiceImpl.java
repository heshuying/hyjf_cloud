/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.BatchCouponTimeoutCommonCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.batch.CouponExpiredSmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author yaoy
 * @version CouponExpiredSmsServiceImpl, v0.1 2018/6/22 11:44
 */
@Service
public class CouponExpiredSmsServiceImpl implements CouponExpiredSmsService {

    private static final Logger logger = LoggerFactory.getLogger(CouponExpiredSmsServiceImpl.class);

    @Autowired
    private CommonProducer commonProducer;
    @Autowired
    private AmTradeClient couponExpiredSmsClient;
    @Autowired
    private AmUserClient amUserClient;

    /**
     * 检查优惠券的到期情况并发送短信提醒
     */
    @Override
    public void sendExpiredMsg() throws MQException {
        //一天后到期的开始时间和截止时间
//		int oneBeginDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf,1));
//		int oneEndDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 2));

        //三天后到期的开始时间和截止时间
        int threeBeginDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 3));
        int threeEndDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 4));

//		Map<String,Object> paramMapOne = new HashMap<String,Object>();
//		paramMapOne.put("startTime", oneBeginDate);
//		paramMapOne.put("endTime", oneEndDate);
//		List<BatchCouponTimeoutCommonCustomize> userCouponOneList = couponTimeoutCustomizeMapper.selectCouponQuota(paramMapOne);
//		// 一日到期短信提醒
//        this.sendSmsCoupon(userCouponOneList,1);


//        Map<String,Object> paramMapThr = new HashMap<String,Object>();
//        paramMapThr.put("startTime", threeBeginDate);
//        paramMapThr.put("endTime", threeEndDate);
        List<BatchCouponTimeoutCommonCustomizeVO> userCouponThrList = couponExpiredSmsClient.selectCouponQuota(threeBeginDate, threeEndDate);
        List<BatchCouponTimeoutCommonCustomizeVO> userCouponThrs = new ArrayList<>();  // 排除空指针
        if (!CollectionUtils.isEmpty(userCouponThrList)) {
            for (BatchCouponTimeoutCommonCustomizeVO userCoupon : userCouponThrList) {
                UserVO user = amUserClient.findUserById(userCoupon.getUserId());
                if(user != null && user.getMobile() !=null){
                    userCoupon.setMobile(user.getMobile());
                    userCouponThrs.add(userCoupon);
                }
            }
        }
        // 三日到期短信提醒
        this.sendSmsCoupon(userCouponThrs, 3);

    }

    /**
     * 优惠券过期短信提醒
     *
     * @param userCouponThrs
     */
    private void sendSmsCoupon(List<BatchCouponTimeoutCommonCustomizeVO> userCouponThrs, int flag) throws MQException {
        if (!CollectionUtils.isEmpty(userCouponThrs)) {
            for (BatchCouponTimeoutCommonCustomizeVO userCoupon : userCouponThrs) {
                if (Validator.isNotNull(userCoupon.getUserId()) && Validator.isNotNull(userCoupon.getCouponQuota()) && userCoupon.getCouponQuota() > 0 && Validator.isNotNull(userCoupon.getMobile())) {
                    Map<String, String> msg = new HashMap<String, String>();
                    msg.put("val_amount", userCoupon.getCouponQuota().toString());
                    SmsMessage smsMessage = null;
                    if (flag == 1) {
                        // 一日到期短信提醒
                        smsMessage = new SmsMessage(userCoupon.getUserId(), msg, userCoupon.getMobile(), null, "smsSendForMobile", null,
                                CustomConstants.PARAM_TPL_ONE_DEADLINE, CustomConstants.CHANNEL_TYPE_NORMAL);
                        commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),smsMessage));
                        logger.info("代金券一日到期短信提醒，用户编号：" + userCoupon.getUserId() + "体验金面值总额：" + userCoupon.getCouponQuota());
                    } else {
                        // 三日到期短信提醒
                        smsMessage = new SmsMessage(userCoupon.getUserId(), msg, userCoupon.getMobile(), null, "smsSendForMobile", null,
                                CustomConstants.PARAM_TPL_THREE_DEADLINE, CustomConstants.CHANNEL_TYPE_NORMAL);
                        commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),smsMessage));
                        logger.info("代金券三日到期短信提醒，用户编号：" + userCoupon.getUserId() + "体验金面值总额：" + userCoupon.getCouponQuota());
                    }

                }
            }
        }
    }
}

