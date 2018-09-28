/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.coupon;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author yaoyong
 * @version CheckCouponService, v0.1 2018/7/6 16:21
 */
public interface CheckCouponService {
    /**
     *
     * 批量自动发放用户优惠券
     *
     * @throws Exception
     */
    JSONObject batchInsertUserCoupon(Map<String, Object> map) throws Exception;
}
