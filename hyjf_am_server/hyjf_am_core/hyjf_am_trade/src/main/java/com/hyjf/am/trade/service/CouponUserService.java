/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.CouponUser;

import java.util.List;

/**
 * @author yaoy
 * @version CouponUserService, v0.1 2018/6/19 19:07
 */
public interface CouponUserService {
    List<CouponUser> selectCouponUser(int nowBeginDate, int nowEndDate);
}
