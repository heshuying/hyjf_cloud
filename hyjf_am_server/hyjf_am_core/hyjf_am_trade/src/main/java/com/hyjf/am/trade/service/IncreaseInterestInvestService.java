package com.hyjf.am.trade.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestLoan;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;

/**
 * @author xiasq
 * @version IncreaseInterestInvestService, v0.1 2018/6/19 11:57
 */
public interface IncreaseInterestInvestService {
	/**
	 * 根据借款编号检索还款信息
	 * 
	 * @param borrowNid
	 * @return
	 */
	List<IncreaseInterestLoan> selectIncreaseInterestLoanList(String borrowNid);

	/**
	 * 根据借款编号检索借款信息
	 * 
	 * @param borrowNid
	 * @return
	 */
	Borrow selectBorrowByNid(String borrowNid);

    /**
     * 根据借款编号,还款期数,还款方式取得还款金额
     * @param borrowNid
     * @param borrowStyle
     * @param periodNow
     * @return
     */
    BigDecimal selectBorrowAccountWithPeriod(String borrowNid, String borrowStyle, Integer periodNow);

    /**
     * 融通宝加息还款
     * @param apicron
     * @param increaseInterestLoan
	 * @param investUserCustId 投资人电子账号
     * @return
     */
	void increaseInterestRepay(BorrowApicronVO apicron, IncreaseInterestLoan increaseInterestLoan, String account, String companyAccount);

	/**
	 *  融通宝还款成功后,更新increase_interest_repay标的状态
	 * @param borrowNid
	 * @param periodNow
	 * @param borrowUserId
	 */
    void updateRepay(String borrowNid, Integer periodNow, Integer borrowUserId);
}
