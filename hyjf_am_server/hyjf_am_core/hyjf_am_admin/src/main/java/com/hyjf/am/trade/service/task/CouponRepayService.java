/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task;

import com.hyjf.am.resquest.trade.CouponRecoverCustomizeRequest;
import com.hyjf.am.trade.dao.model.auto.CouponRecover;
import com.hyjf.am.trade.dao.model.customize.BatchCouponTimeoutCommonCustomize;
import com.hyjf.am.trade.dao.model.customize.CouponRecoverCustomize;
import com.hyjf.common.exception.MQException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    /**
     * 优惠券单独投资放款
     *
     * @param paramMap
     * @return
     */
    List<String> selectNidForCouponOnly(Map<String, Object> paramMap);

    /**
     * 根据订单编号取得该订单的还款列表
     * @param map
     * @return
     */
    CouponRecoverCustomize selectCurrentCouponRecover(Map<String, Object> map);

    /**
     * 更新优惠券还款
     * @param couponRecover
     */
    void updateCouponRecover(CouponRecover couponRecover);

    /**
     * 体验金按收益期限还款
     * @param request
     * @return
     */
    Integer updateCouponOnlyRecover(CouponRecoverCustomizeRequest request) throws MQException;
}
