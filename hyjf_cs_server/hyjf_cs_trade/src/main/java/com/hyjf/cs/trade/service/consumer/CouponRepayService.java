package com.hyjf.cs.trade.service.consumer;

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
     * @Description  根据订单ID查询汇计划所有的优惠券还款
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

    /**
     * @Author walter.limeng
     * @Description  根据borrowNid查询所有优惠券散标还款
     * @Date 16:39 2018/7/18
     * @Param borrowNid
     * @return
     */
    List<CouponTenderCustomizeVO> getCouponTenderList(String borrowNid);

    /**
     * @Author walter.limeng
     * @Description  直投类优惠券还款
     * @Date 16:52 2018/7/18
     * @Param borrowNid
     * @Param periodNow
     * @Param CouponTenderCustomizeVO
     * @return
     */
    void updateCouponRecoverMoney(String borrowNid, Integer periodNow, CouponTenderCustomizeVO ct) throws Exception;

    /**
     * 优惠券单独投资放款
     * @return
     */
    List<String> selectNidForCouponOnly();

    /**
     * 体验金按受益期限还款
     * @param recoverNidList
     */
    void couponOnlyRepay(List<String> recoverNidList);
}
