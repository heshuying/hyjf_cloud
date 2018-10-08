/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task;

import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountBorrow;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestInvest;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @author dxj
 * @version IncreaseinterestLoansService.java, v0.1 2018年6月19日 上午11:28:09
 */
public interface IncreaseinterestLoansService extends BaseService {

	/**
	 * 自动放款（本金）
	 *
	 * @param apicron
	 * @return
	 */
	public List<Map<String, String>> updateBorrowLoans(BorrowApicron apicron, IncreaseInterestInvest borrowTender) throws Exception;

	/**
	 * 取得借款API任务表
	 *
	 * @return
	 */
	public List<BorrowApicron> getBorrowApicronList(Integer status, Integer apiType);

	/**
	 * 取得借款API任务表
	 *
	 * @return
	 */
	public List<BorrowApicron> getBorrowApicronListWithRepayStatus(Integer status, Integer apiType);

	/**
	 * 更新借款API任务表
	 *
	 * @return
	 */
	public int updateBorrowApicron(Integer id, Integer status);

	/**
	 * 更新借款API任务表
	 *
	 * @return
	 */
	public int updateBorrowApicron(Integer id, Integer status, String data);

	/**
	 * 更新借款API任务表
	 *
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateBorrowApicronOfRepayStatus(Integer id, Integer status);

	public AccountBorrow getAccountBorrow(String borrowNid);

	/**
	 * 取出账户信息
	 *
	 * @param userId
	 * @return
	 */
	public Account getAccountByUserId(Integer userId);

	/**
	 * 取得借款列表
	 *
	 * @return
	 */
	public List<IncreaseInterestInvest> getBorrowTenderList(String borrowNid);

	/**
	 * 更新放款状态
	 *
	 * @param accountList
	 * @return
	 */
	public int updateBorrowTender(IncreaseInterestInvest borrowTender);

	/**
	 * 发送短信(投资成功)
	 *
	 * @param userId
	 */
	public void sendSms(List<Map<String, String>> msgList);

	/**
	 * 发送邮件(投资成功)
	 *
	 * @param userId
	 */
	public void sendMail(List<Map<String, String>> msgList, String borrowNid);

	/**
	 * @param msgList
	 */

	public void sendMessage(List<Map<String, String>> msgList);

	/**
	 * 加息放款接口
	 * @return
	 */
	boolean loans();

}
