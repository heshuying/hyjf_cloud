package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.admin.coupon.CouponTenderCustomize;
import com.hyjf.am.vo.trade.coupon.CouponTenderCustomizeVO;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/17 16:45
 * @Description: CouponRepayService
 */
public interface CouponRepayService {
    
    /**
     * @Author walter.limeng
     * @Description  根据订单ID查询所有的优惠券还款
     * @Date 16:55 2018/7/17
     * @Param orderId
     * @return 
     */
    List<CouponTenderCustomizeVO> getCouponTenderListHjh(String orderId);

    /**
     * @Author walter.limeng
     * @Description  汇计划优惠券还款
     * @Date 17:07 2018/7/17
     * @Param borrowNid
     * @Param CouponTenderCustomizeVO
     * @return
     */
    void updateCouponRecoverHjh(String borrowNid, CouponTenderCustomizeVO ct) throws Exception;
}
