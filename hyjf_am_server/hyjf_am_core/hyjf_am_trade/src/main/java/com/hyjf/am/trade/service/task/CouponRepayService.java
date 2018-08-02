/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task;

import com.hyjf.am.trade.dao.model.customize.trade.BatchCouponTimeoutCommonCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.CouponRecoverCustomize;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yaoy
 * @version CouponRepayService, v0.1 2018/6/21 18:11
 */
public interface CouponRepayService {
    /**
     * 统计加息券每日待收收益
     *
     * @param
     * @return
     */
    List<CouponRecoverCustomize> selectCouponInterestWaitToday(long timeStart, long timeEnd);

    /**
     * 统计加息券每日已收收益
     *
     * @param
     * @return
     */
    BigDecimal selectCouponInterestReceivedToday(long timeStart, long timeEnd);

    /**
     * 查询优惠券到期情况
     *
     * @param threeBeginDate
     * @param threeEndDate
     * @return
     */
    List<BatchCouponTimeoutCommonCustomize> selectCouponQuota(int threeBeginDate, int threeEndDate);
}
