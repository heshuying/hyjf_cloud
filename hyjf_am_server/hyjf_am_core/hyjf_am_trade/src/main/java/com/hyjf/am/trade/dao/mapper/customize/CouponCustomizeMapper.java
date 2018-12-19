package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.auto.BorrowTenderCpn;
import com.hyjf.am.trade.dao.model.customize.CouponCustomize;
import com.hyjf.am.vo.trade.coupon.AppCouponCustomizeVO;
import com.hyjf.am.vo.trade.coupon.AppCouponInfoCustomizeVO;
import com.hyjf.am.vo.trade.repay.CurrentHoldRepayMentPlanListVO;

import java.util.List;
import java.util.Map;
/**
 * @Description 优惠券相关
 * @Author sunss
 * @Version v0.1
 * @Date 2018/6/19 17:09
 */
public interface CouponCustomizeMapper {

    /**
     * @Description 根据userid和优惠券ID查询优惠券信息
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 17:12
     */
    List<CouponCustomize> getCouponUser(Map<String,Object> paraMap);

    /**
     * 获取优惠券出借信息
     * @param paraMap
     * @return
     */
    BorrowTenderCpn getCouponTenderByTender(Map<String,Object> paraMap);

    /**
     * 根据userId和orderId获取优惠券出借信息
     * @author zhangyk
     * @date 2018/7/31 13:55
     */
    AppCouponInfoCustomizeVO getCouponTenderListByUserIdAndOrderId(Map<String,Object> paraMap);

    /**
     * 根据nid获取优惠券还款列表
     * @author zhangyk
     * @date 2018/7/31 14:31
     */
    List<CurrentHoldRepayMentPlanListVO> couponRepaymentPlanList(String nid);

    /**
     * 获取优惠券出借记录
     * @author zhangyk
     * @date 2018/8/1 14:32
     */
    List<AppCouponCustomizeVO> getMyCouponPlanList(Map<String,Object> paraMap);
}
