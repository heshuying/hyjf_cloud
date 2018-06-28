/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import java.math.BigDecimal;

/**
 * @author: sunpeikai
 * @version: BatchUserPortraitQueryCustomizeMapper, v0.1 2018/6/28 13:48
 */
public interface BatchUserPortraitQueryCustomizeMapper {
    /**
     * 获取用户累计收益
     * @param userId
     * @return
     */
    BigDecimal getInterestSum(Integer userId);

    /**
     * 获取用户累计充值金额
     * @param userId
     * @return
     */
    BigDecimal getRechargeSum(Integer userId);

    /**
     * 获取用户累计提现金额
     * @param userId
     * @return
     */
    BigDecimal getWithdrawSum(Integer userId);

    /**
     * 获取用户累计交易笔数
     * @param userId
     * @return
     */
    int getTradeNumber(Integer userId);

    /**
     * 获取用户年化投资金额
     * @param userId
     * @return
     */
    BigDecimal getInvestSum(Integer userId);

    /**
     * 获取计划标年化收益
     * @param userId
     * @return
     */
    BigDecimal getPlanSum(Integer userId);
}
