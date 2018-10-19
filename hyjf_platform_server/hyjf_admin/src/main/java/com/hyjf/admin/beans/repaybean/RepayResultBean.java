/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月20日 下午5:24:10
 * Modification History:
 * Modified by : 
 */

package com.hyjf.admin.beans.repaybean;

import com.hyjf.admin.beans.BaseResultBean;

import java.math.BigDecimal;

/**
 * @author Administrator
 */

public class RepayResultBean extends BaseResultBean {

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * 借款编号
	 */
	private String borrowNid;
	
	/**
	 * 批次编号
	 */
	private String batchNo;
	
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 当前期数
	 */
	private String periodNow;
	
	/**
	 * 总期数
	 */
	private String borrowPeriod;
	
	/**
	 * 借款金额
	 */
	private BigDecimal borrowAccount;
	
	/**
	 * 应还/应放款金额
	 */
	private BigDecimal batchAmount;

	/**
	 * 应收服务费
	 */
	private BigDecimal batchServiceFee;

	/**
	 * 成功金额
	 */
	private BigDecimal sucAmount;
	
	/**
	 * 失败金额
	 */
	private BigDecimal failAmount;
	
	/**
	 * 批次状态
	 */
	private String batchStatus;

    /**
     * 还款角色 0:借款人 1:担保机构
     */
	private String role;

	
	
	public String getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPeriodNow() {
		return periodNow;
	}

	public void setPeriodNow(String periodNow) {
		this.periodNow = periodNow;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public BigDecimal getBorrowAccount() {
		return borrowAccount;
	}

	public void setBorrowAccount(BigDecimal borrowAccount) {
		this.borrowAccount = borrowAccount;
	}

	public BigDecimal getBatchAmount() {
		return batchAmount;
	}

	public void setBatchAmount(BigDecimal batchAmount) {
		this.batchAmount = batchAmount;
	}

	
	public BigDecimal getBatchServiceFee() {
		return batchServiceFee;
	}

	public void setBatchServiceFee(BigDecimal batchServiceFee) {
		this.batchServiceFee = batchServiceFee;
	}

	public BigDecimal getSucAmount() {
		return sucAmount;
	}

	public void setSucAmount(BigDecimal sucAmount) {
		this.sucAmount = sucAmount;
	}


	public BigDecimal getFailAmount() {
		return failAmount;
	}

	public void setFailAmount(BigDecimal failAmount) {
		this.failAmount = failAmount;
	}

}
