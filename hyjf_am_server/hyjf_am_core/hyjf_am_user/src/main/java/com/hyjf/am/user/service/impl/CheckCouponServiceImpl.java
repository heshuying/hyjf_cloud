/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.BatchCouponsRequest;
import com.hyjf.am.user.mq.Producer;
import com.hyjf.am.user.mq.SmsProducer;
import com.hyjf.am.user.service.CheckCouponService;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.security.util.MD5;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author yaoyong
 * @version CheckCouponServiceImpl, v0.1 2018/7/6 16:22
 */
@Service
public class CheckCouponServiceImpl implements CheckCouponService {
    /** 优惠券发放接口 */
    private static final String COUPON_BATCH_SEND_USER = "/userCouponServer/batchUserCouponSend.json";

    @Autowired
    SmsProducer smsProducer;
    @Value("${release.coupon.accesskey}")
    public static  String SOA_COUPON_KEY;
    @Value("${hyjf.api.web.url}")
    public static String HYJF_API_WEB_URL;

    @Override
    public JSONObject getBatchCoupons(Map<String, Object> map) throws MQException {
        String userId = map.get("userId").toString();
        String timestamp = String.valueOf(GetDate.getNowTime10());
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_COUPON_KEY + userId + timestamp + SOA_COUPON_KEY));
        BatchCouponsRequest batchCouponsRequest = new BatchCouponsRequest();
        JSONObject jsonObject = new JSONObject();
        // 用户id
        batchCouponsRequest.setUserId(userId);
        // 时间戳
        batchCouponsRequest.setTimestamp(timestamp);
        // 签名
        batchCouponsRequest.setChkValue(chkValue);
        // 数据
        batchCouponsRequest.setUsercoupons(map.get("usercoupons").toString());
        // 请求路径
        String requestUrl = HYJF_API_WEB_URL + COUPON_BATCH_SEND_USER;
        batchCouponsRequest.setRequestUrl(requestUrl);

        smsProducer.messageSend(new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(batchCouponsRequest)));
        return jsonObject;
    }
}
