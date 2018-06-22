/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.BatchCouponTimeoutCommonCustomizeVO;

import java.util.List;

/**
 * @author yaoy
 * @version CouponExpiredSmsClient, v0.1 2018/6/22 14:17
 */
public interface CouponExpiredSmsClient {
    List<BatchCouponTimeoutCommonCustomizeVO> selectCouponQuota(int threeBeginDate, int threeEndDate);
}
