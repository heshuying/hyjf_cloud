/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Description 优惠券投资相关
 * @Author sunss
 * @Date 2018/6/23 11:52
 */
public interface CouponService {
    /**
     * 加入计划  体验金投资
     * @param request
     * @param plan
     * @param cuc
     */
    void couponTender(TenderRequest request, HjhPlanVO plan, CouponUserVO cuc,Integer userId);

    /**
     * 优惠券投资校验
     * @param userId
     * @param accountStr
     * @param couponGrantId
     * @param platform
     * @param period
     * @return
     */
    Map<String, String> validateCoupon(Integer userId, String accountStr, Integer couponGrantId, String platform, Integer period,String config);

    /**
     * 散标投资优惠券使用
     * @param couponGrantId
     * @param borrow
     * @param bean
     */
    void borrowTenderCouponUse(String couponGrantId, BorrowAndInfoVO borrow, BankCallBean bean);

    /**
     * 计算预期收益
     * @param couponQuota
     * @param couponProfitTime
     * @param borrowApr
     * @return
     */
    BigDecimal getInterestDj(BigDecimal couponQuota, Integer couponProfitTime, BigDecimal borrowApr);

    /**
     * 计算得到优惠券预期收益
     * @param borrowStyle
     * @param couponAccount
     * @param couponRate
     * @param borrowPeriod
     * @return
     */
    BigDecimal calculateCouponInterest(String borrowStyle, BigDecimal couponAccount, BigDecimal couponRate, Integer borrowPeriod);

    /**
     * 获取预期收益
     * @param borrowStyle
     * @param couponType
     * @param borrowApr
     * @param couponQuota
     * @param money
     * @param borrowPeriod
     * @return
     */
    BigDecimal getInterest(String borrowStyle, Integer couponType, BigDecimal borrowApr, BigDecimal couponQuota, String money, Integer borrowPeriod);

}
