/**
 * Description:用户开户列表前端显示查询所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 *           Created at: 2015年11月11日 下午2:17:31
 *           Modification History:
 *           Modified by :
 */

package com.hyjf.admin.beans.repaybean;

import com.hyjf.admin.beans.BaseResultBean;

import java.util.List;

/**
 * @author cwyang
 */

public class UserRepayProjectBean extends BaseResultBean {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4205305842872752342L;

	// 项目编号
	private String borrowNid;

	// 项目年华收益率
	private String borrowInterest;

	// 项目期限
	private String borrowPeriod;

	// 借款金额
	private String borrowAccount;

	// 还款金额
	private String borrowTotal;

	// 还款时间
	private String repayTime;

	// 已还总额
	private String repayTotal;

	// 实际还款时间
	private String repayYesTime;
	/**
	 * 到账金额
	 */
	private String yesAccount;
	/**
	 * 到账时间
	 */
	private String yesAccountTime;

	/**
	 * 还款详情列表
	 */
	private List<ProjectRepayListBean> detailList;
	/**
	 * 构造方法
	 */
	public UserRepayProjectBean() {
		super();
	}
	
	public List<ProjectRepayListBean> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<ProjectRepayListBean> detailList) {
		this.detailList = detailList;
	}



	public String getYesAccount() {
		return yesAccount;
	}

	public void setYesAccount(String yesAccount) {
		this.yesAccount = yesAccount;
	}

	public String getYesAccountTime() {
		return yesAccountTime;
	}

	public void setYesAccountTime(String yesAccountTime) {
		this.yesAccountTime = yesAccountTime;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getBorrowInterest() {
		return borrowInterest;
	}

	public void setBorrowInterest(String borrowInterest) {
		this.borrowInterest = borrowInterest;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getBorrowAccount() {
		return borrowAccount;
	}

	public void setBorrowAccount(String borrowAccount) {
		this.borrowAccount = borrowAccount;
	}

	public String getBorrowTotal() {
		return borrowTotal;
	}

	public void setBorrowTotal(String borrowTotal) {
		this.borrowTotal = borrowTotal;
	}

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public String getRepayTotal() {
		return repayTotal;
	}

	public void setRepayTotal(String repayTotal) {
		this.repayTotal = repayTotal;
	}

	public String getRepayYesTime() {
		return repayYesTime;
	}

	public void setRepayYesTime(String repayYesTime) {
		this.repayYesTime = repayYesTime;
	}

}
