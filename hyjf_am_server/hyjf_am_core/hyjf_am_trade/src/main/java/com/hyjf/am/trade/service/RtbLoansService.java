/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.*;

import java.util.List;

/**
 * @author ${yaoy}
 * @version RtbLoansService, v0.1 2018/6/14 10:38
 */
public interface RtbLoansService {

    List<IncreaseInterestInvest> getBorrowTenderList(String borrowNid);

    int updateBorrowTender(IncreaseInterestInvest increaseInterestInvest);

    int insertBorrowRecover(IncreaseInterestLoan increaseInterestLoan);

    int insertIncreaseInterestRepay(IncreaseInterestRepay increaseInterestRepay);

    int updateIncreaseInterestRepay(IncreaseInterestRepay increaseInterestRepay);

    IncreaseInterestRepay getBorrowRepay(String borrowNid);

    int IncreaseInterestLoanDetail(IncreaseInterestLoanDetail increaseInterestLoanDetail);

    IncreaseInterestRepayDetail getBorrowRepayPlan(String borrowNid, Integer period);

    int insertIncreaseInterestRepayDetail(IncreaseInterestRepayDetail increaseInterestRepayDetail);

    int updateIncreaseInterestRepayDetail(IncreaseInterestRepayDetail increaseInterestRepayDetail);
}
