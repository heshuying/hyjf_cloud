/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.resquest.Request;
import com.hyjf.am.vo.BasePage;
import com.hyjf.common.paginator.Paginator;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fuqiang
 * @version PushMoneyRequest, v0.1 2018/7/10 19:26
 */
public class PushMoneyRequest extends BasePage implements Serializable {

	/**
	 * 检索条件-项目编号
	 */
	public String borrowNid;
	/**
	 * 检索条件-项目标题
	 */
	public String borrowName;
	/**
	 * 检索条件-项目还款方式  = endday 天   !=endday 个月
	 */
	public String borrowStyle;
	/**
	 * 检索条件- 融资期限
	 */
	public String borrowPeriod;
	/**
	 * 检索条件- 融资金额
	 */
	public String account;
	/**
	 * j检索条件-提成总额
	 */
	public String commission;
	/**
	 * 检索条件-放款开始时间
	 */
	public String recoverLastTimeStart;

	/**
	 * 检索条件-放款结束时间
	 */
	public String recoverLastTimeEnd;

	/**
	 * 检索条件-项目类型
	 */
	public Integer projectType;

	public int limit;

	public int limitStart;

	public int limitEnd;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	public int getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}

	private static final long serialVersionUID = 1L;

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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	private com.hyjf.common.paginator.Paginator paginator;

	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}
}
