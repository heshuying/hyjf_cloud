/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.exception.MQException;

import java.util.Map;

/**
 * @author yaoyong
 * @version CheckCouponService, v0.1 2018/7/6 16:21
 */
public interface CheckCouponService {
    JSONObject getBatchCoupons(Map<String, Object> map) throws MQException;
}
