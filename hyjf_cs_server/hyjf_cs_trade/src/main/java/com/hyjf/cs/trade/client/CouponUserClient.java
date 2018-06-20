/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.CouponUserVo;

import java.util.List;

/**
 * @author yaoy
 * @version CouponUserClient, v0.1 2018/6/19 18:28
 */
public interface CouponUserClient {
    List<CouponUserVo> selectCouponUser(int nowBeginDate, int nowEndDate);
}
