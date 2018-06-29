/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.BorrowTenderCpn;
import com.hyjf.am.trade.dao.model.customize.trade.CouponCustomize;
import com.hyjf.am.vo.trade.CouponTenderVO;

/**
 * @Description 优惠券相关
 * @Author sunss
 * @Version v0.1
 * @Date 2018/6/19 16:50
 */
public interface CouponService {

    /**
     * @Description 根据用户ID和优惠券编号查询优惠券
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 16:51
     */
    CouponCustomize getCouponUser(String couponGrantId, Integer userId);

    /**
     * 优惠券投资
     * @param couponTender
     */
    void updateCouponTender(CouponTenderVO couponTender);

    /**
     * 获取优惠券投资
     * @param userId
     * @param borrowNid
     * @param logOrdId
     * @param couponGrantId
     * @return
     */
    BorrowTenderCpn getCouponTenderByTender(Integer userId, String borrowNid, String logOrdId, Integer couponGrantId);
}
