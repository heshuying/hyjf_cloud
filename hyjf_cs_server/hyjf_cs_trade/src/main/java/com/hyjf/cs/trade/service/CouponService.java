/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.trade.CouponUserVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

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
     * @param account
     * @param cuc
     * @param tenderAccount
     */
    void couponTender(TenderRequest request, HjhPlanVO plan, BankOpenAccountVO account, CouponUserVO cuc, AccountVO tenderAccount);

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
    void borrowTenderCouponUse(String couponGrantId, BorrowVO borrow, BankCallBean bean);
}
