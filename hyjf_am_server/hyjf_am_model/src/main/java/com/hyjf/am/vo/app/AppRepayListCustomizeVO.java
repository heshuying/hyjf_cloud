/**
 * Description:用户开户列表前端显示查询所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.vo.app;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author jijun
 * @date 20180728
 */

public class AppRepayListCustomizeVO extends BaseVO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 146083082142516546L;
	// 回款项目id
	private String borrowNid;
	// 回款项目名称
	private String borrowName;
	// 回款投资金额
	private String account;
	// 订单编号
	private String orderId;
	// 回款待收金额
	private String interest;
	// 应还时间
	private String repayTime;
	// 回款状态
	private String status;
	// 投资合同url
	private String contactUrl = "";
	// 项目转让状态 0 未转让 1部分转让 2完全转让
	private String transStatus;
	// 转让金额
	private String transAccount;
	// 项目详情url
	private String borrowUrl = "";
	// 优惠券投资时的优惠券类型，费优惠券投资则为空字符串
	private String label;
	// 融通宝加息类型为3 普通标1 优惠券2
	private String investType;
	// RTB融通宝
	private String projectType;

	/**
	 * 构造方法
	 */
	public AppRepayListCustomizeVO() {
		super();
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContactUrl() {
		return contactUrl;
	}

	public void setContactUrl(String contactUrl) {
		this.contactUrl = contactUrl;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getTransAccount() {
		return transAccount;
	}

	public void setTransAccount(String transAccount) {
		this.transAccount = transAccount;
	}

	public String getBorrowUrl() {
		return borrowUrl;
	}

	public void setBorrowUrl(String borrowUrl) {
		this.borrowUrl = borrowUrl;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getInvestType() {
		return investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

}
