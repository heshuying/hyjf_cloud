/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.coupon.CouponUserVO;

import java.util.List;

/**
 * @author yaoy
 * @version CouponUserClient, v0.1 2018/6/19 18:28
 */
public interface CouponUserClient {
    List<CouponUserVO> selectCouponUser(int nowBeginDate, int nowEndDate);
}
