/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.trade.dao.model.customize.trade.CouponRecoverCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.CouponTenderCustomize;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponRecoverCustomizeMapper, v0.1 2018/6/21 19:13
 */
public interface CouponRecoverCustomizeMapper {

    List<CouponRecoverCustomize> selectCouponInterestWaitToday(Map<String, Object> map);

    BigDecimal selectCouponInterestReceivedToday(Map<String, Object> map);

    /**
     * 取得标的下的优惠券投资列表
     * @param paramMap
     * @return
     */
    List<CouponTenderCustomize> selectCouponRecoverAll(Map<String, Object> paramMap);

    CouponRecoverCustomize selectCurrentCouponRecover(Map<String, Object> paramMap);

    /**
     * 获取优惠券投资还款列表(还款失败)
     * @param paramMap
     * @return
     */
    CouponRecoverCustomize selectCurrentCouponRecoverFailed(Map<String,Object> paramMap);

    /**
     * 更新还款期
     * @param paramMapAll
     */
    void crRecoverPeriod(Map<String,Object> paramMapAll);

    /**
     *
     * 获取某用户优惠券待收收益总和
     * @param userId
     * @return
     */
    String selectCouponInterestTotal(Integer userId);

    /**
     *
     * 获取某用户优惠券累计收益总和
     * @author hsy
     * @param userId
     * @return
     */
    String selectCouponReceivedInterestTotal(Integer userId);
}
