/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.trade.CouponTenderCustomizeVO;

import java.util.List;

/**
 * @author yaoy
 * @version RepayDataRecoverService, v0.1 2018/6/25 11:25
 */
public interface RepayDataRecoverService {
    /**
     * 根据borrowNid获取优惠券投资列表
     * @param borrowNid
     * @return
     */
    List<CouponTenderCustomizeVO> getCouponTenderList(String borrowNid);

    void couponRepayDataRecover(String borrowNid, int i, CouponTenderCustomizeVO ct);
}
