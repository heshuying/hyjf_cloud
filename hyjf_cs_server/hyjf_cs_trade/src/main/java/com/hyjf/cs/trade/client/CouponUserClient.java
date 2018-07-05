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

    /**
     * 查询用户有效的优惠券数目
     * @author zhangyk
     * @date 2018/7/4 15:31
     */
    Integer getUserCouponCount(Integer userId, String usedFlag);
}
