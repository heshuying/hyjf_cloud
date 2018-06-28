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

    Integer updateOfPlanRepayAccount(Account account);

    Integer updateHjhBorrowRepayInterest(HjhAccede hjhAccede);

    Integer updateBankTotalForLockPlan(Account account);

    /**
     * 累计为用户赚取
     * @param calculateInvestInterest
     * @return
     */
    Integer updateCalculateInvestByPrimaryKey(CalculateInvestInterest calculateInvestInterest);

}
