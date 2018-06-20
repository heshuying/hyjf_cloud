/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.CouponConfigVo;

import java.util.List;

/**
 * @author yaoy
 * @version CouponConfigClient, v0.1 2018/6/19 18:28
 */
public interface CouponConfigClient {
   CouponConfigVo selectCouponConfig(String couponCode);
}
