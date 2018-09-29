/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task;

import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestLoan;
import com.hyjf.am.trade.service.BaseService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author dxj
 * @version IncreaseInterestRepayService.java, v0.1 2018年6月19日 上午11:28:09
 */
public interface IncreaseInterestRepayService extends BaseService {

	/**
	 * 检索未执行的任务
	 * 
	 * @Title selectBorrowApicronList
	 * @param statusWait
	 * @param status
	 * @return
	 */
	List<BorrowApicron> selectBorrowApicronList(Integer statusWait, int status);

	/**
	 * 更新还款任务
	 * 
	 * @Title updateBorrowApicron
	 * @param id
	 * @param statusError
	 * @param errorMsg
	 */
	public int updateBorrowApicron(Integer id, Integer statusError, String errorMsg);

	/**
	 * 更新还款任务
	 * 
	 * @Title updateBorrowApicron
	 * @param id
	 * @param statusWait
	 */
	public int updateBorrowApicron(Integer id, Integer statusWait);

	/**
	 * 根据借款编号检索还款信息
	 * 
	 * @Title selectIncreaseInterestLoanList
	 * @param borrowNid
	 * @return
	 */
	public List<IncreaseInterestLoan> selectIncreaseInterestLoanList(String borrowNid);

	/**
	 * 根据借款人userId检索借款人账款信息
	 * 
	 * @Title selectAccountByUserId
	 * @param borrowUserId
	 * @return
	 */
	public Account selectAccountByUserId(Integer borrowUserId);

	/**
	 * 根据借款编号,期数 ,借款方式取得还款金额
	 * 
	 * @Title selectBorrowAccountWithPeriod
	 * @param borrowNid
	 * @param borrowStyle
	 * @param periodNow
	 * @return
	 */
	public BigDecimal selectBorrowAccountWithPeriod(String borrowNid, String borrowStyle, Integer periodNow);

	/**
	 * 检索融通宝加息子账户剩余金额
	 * 
	 * @Title selectCompanyAccount
	 * @return
	 */
	public BigDecimal selectCompanyAccount();

	/**
	 * 自动还款
	 * 
	 * @Title updateBorrowRepay
	 * @param apicron
	 * @param increaseInterestLoan
	 * @param borrowUserCust
	 * @return
	 */
	List<Map<String, String>> updateBorrowRepay(BorrowApicron apicron,Borrow borrow, IncreaseInterestLoan increaseInterestLoan, String borrowUserCust);

	/**
	 * 还款成功后,更新标的状态
	 * 
	 * @Title updateBorrowStatus
	 * @param borrowNid
	 * @param periodNow
	 * @param borrowUserId
	 */
	public void updateBorrowStatus(Borrow borrow, Integer periodNow, Integer borrowUserId);

	/**
	 * 还款成功后,发送短信
	 * 
	 * @Title sendSms
	 * @param msgList
	 */
	public void sendSms(List<Map<String, String>> msgList);

	/**
	 * 还款成功后,发送消息推送
	 * 
	 * @Title sendMessage
	 * @param msgList
	 */
	public void sendMessage(List<Map<String, String>> msgList);

	/**
	 * 一次性还款的情况下获取优先处理任务
	 * @param borrowNid
	 * @return
	 */
    BorrowApicron getRepayPeriodSort(String borrowNid);

    /**
	 * 调用还款接口
	 *
	 * @return
	 */
	boolean repay();
}
