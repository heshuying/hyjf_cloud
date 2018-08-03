/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author zdj
 * @version PushMoneyRequestBean.java, v0.1 2018年7月13日 下午3:11:08
 */
public class PushMoneyRequestBean extends BaseRequest implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 排序
	 */
	private String sort;
	/**
	 * 排序列
	 */
	private String col;

	public int limit;
	@ApiModelProperty(value = "检索条件-项目编号")
	private String borrowNid;

	@ApiModelProperty(value = "检索条件-项目标题")
	private String borrowName;

	@ApiModelProperty(value = "检索条件-项目还款方式  = endday 天   !=endday 个月")
	private String borrowStyle;

	@ApiModelProperty(value = "检索条件- 融资期限")
	private String borrowPeriod;

	@ApiModelProperty(value = "检索条件- 融资金额")
	private String account;

	@ApiModelProperty(value = "检索条件-提成总额")
	private String commission;

	@ApiModelProperty(value = "检索条件-放款开始时间")
	private String recoverLastTimeStart;

	@ApiModelProperty(value = "检索条件-放款结束时间")
	private String recoverLastTimeEnd;

	@ApiModelProperty(value = "检索条件-项目类型")
	private int projectType;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
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

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getRecoverLastTimeStart() {
		return recoverLastTimeStart;
	}

	public void setRecoverLastTimeStart(String recoverLastTimeStart) {
		this.recoverLastTimeStart = recoverLastTimeStart;
	}

	public String getRecoverLastTimeEnd() {
		return recoverLastTimeEnd;
	}

	public void setRecoverLastTimeEnd(String recoverLastTimeEnd) {
		this.recoverLastTimeEnd = recoverLastTimeEnd;
	}

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}
}
