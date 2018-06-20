/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.CouponConfig;

import java.util.List;

/**
 * @author yaoy
 * @version CouponConfigService, v0.1 2018/6/19 19:25
 */
public interface CouponConfigService {
    List<CouponConfig> selectCouponConfig(String couponCode);
}
