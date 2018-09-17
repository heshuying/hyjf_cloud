/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.trade.PushMoneyVO;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author zdj
 * @version PushMoneyRequestBean.java, v0.1 2018年7月13日 下午3:11:08
 */
public class PushMoneyRequestBean extends PushMoneyVO implements Serializable{

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

	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	public int limit;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	private Paginator paginator;
	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	/**
	 * 当前页码
	 */
	@ApiModelProperty(value = "当前页")
	private int currPage;

	/**
	 * 当前页条数
	 */
	@ApiModelProperty(value = "当前页条数")
	private int pageSize = 10;

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

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

}
