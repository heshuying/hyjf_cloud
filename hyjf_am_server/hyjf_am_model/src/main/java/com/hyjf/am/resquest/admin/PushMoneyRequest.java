/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import java.io.Serializable;
import java.util.Date;

import com.hyjf.am.resquest.Request;

/**
 * @author fuqiang
 * @version PushMoneyRequest, v0.1 2018/7/10 19:26
 */
public class PushMoneyRequest extends Request implements Serializable {


	private Integer id;

	private String type;

	private Integer projectType;

	private Integer rewardSend;

	private String dayTender;

	private String monthTender;

	private Integer createBy;

	private Integer updateBy;

	private Date createTime;

	private Date updateTime;

	private String remark;

	//项目编号
	public String borrowNid;
	// 项目标题
	public String borrowName;
	// 项目还款方式  = endday 天   !=endday 个月
	public String borrowStyle;
	// 融资期限
	public String borrowPeriod;
	// 融资金额
	public String account;
	// 提成总额
	public String commission;
	// 放款时间
	public String recoverLastTime;
	public String recoverLastTimeStart;
	public String recoverLastTimeEnd;


	public int limit;

	private int paginatorPage = 1;



	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getRecoverLastTime() {
		return recoverLastTime;
	}

	public void setRecoverLastTime(String recoverLastTime) {
		this.recoverLastTime = recoverLastTime;
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

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPaginatorPage() {
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	public Integer getRewardSend() {
		return rewardSend;
	}

	public void setRewardSend(Integer rewardSend) {
		this.rewardSend = rewardSend;
	}

	public String getDayTender() {
		return dayTender;
	}

	public void setDayTender(String dayTender) {
		this.dayTender = dayTender == null ? null : dayTender.trim();
	}

	public String getMonthTender() {
		return monthTender;
	}

	public void setMonthTender(String monthTender) {
		this.monthTender = monthTender == null ? null : monthTender.trim();
	}

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}
