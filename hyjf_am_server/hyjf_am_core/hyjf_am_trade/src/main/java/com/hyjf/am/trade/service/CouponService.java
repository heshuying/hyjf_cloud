/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.customize.trade.CouponCustomize;

/**
 * @Description 优惠券相关
 * @Author sunss
 * @Version v0.1
 * @Date 2018/6/19 16:50
 */
public interface CouponService {

    /**
     * @Description 根据用户ID和优惠券编号查询优惠券
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 16:51
     */
    CouponCustomize getCouponUser(String couponGrantId, Integer userId);
}