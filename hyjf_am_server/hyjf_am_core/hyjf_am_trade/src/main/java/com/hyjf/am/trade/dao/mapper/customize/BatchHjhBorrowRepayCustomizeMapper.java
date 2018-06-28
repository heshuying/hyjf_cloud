/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.CalculateInvestInterest;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;

/**
 * @author PC-LIUSHOUYI
 * @version BatchHjhBorrowRepayCustomizeMapper, v0.1 2018/6/27 14:08
 */
public interface BatchHjhBorrowRepayCustomizeMapper {

    /**
     * 变更计划还款账户数据
     * @param account
     * @return
     */
    Integer updateOfPlanRepayAccount(Account account);

    /**
     * 更新待收收益
     * @param hjhAccede
     * @return
     */
    Integer updateHjhBorrowRepayInterest(HjhAccede hjhAccede);

    /**
     * 进入锁定期后 修改账户总资产 待收收益
     * @param account
     * @return
     */
    Integer updateBankTotalForLockPlan(Account account);

    /**
     * 累计为用户赚取
     * @param calculateInvestInterest
     * @return
     */
    Integer updateCalculateInvestByPrimaryKey(CalculateInvestInterest calculateInvestInterest);

}
