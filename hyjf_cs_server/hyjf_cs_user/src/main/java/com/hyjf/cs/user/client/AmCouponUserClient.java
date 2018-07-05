/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.client;

import com.hyjf.am.vo.trade.coupon.CouponUserListCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author jun
 * @version AmCouponUserClient, v0.1 2018/7/3 18:11
 */
public interface AmCouponUserClient {

    Integer countCouponValid(Integer userId);

    List<CouponUserListCustomizeVO> selectCouponUserList(Map<String,Object> mapParameter);
}
