/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import java.io.Serializable;

import com.hyjf.admin.beans.BaseRequest;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author libin
 * @version PlanListViewRequest.java, v0.1 2018年7月13日 下午3:11:08
 */
public class PlanListViewRequest extends BaseRequest implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "计划编号查询")
	private String planNidSrch;
	
	@ApiModelProperty(value = "计划名称查询")
	private String planNameSrch;
	
	@ApiModelProperty(value = "投资状态:0 全部,1 启用,2 关闭")
	private String planStatusSrch;
	
	@ApiModelProperty(value = "显示状态 :0 全部,1 显示,2 隐藏")
	private String planDisplayStatusSrch;
	
	@ApiModelProperty(value = "项目编号(详情检索用)查询")
	private String borrowNidSrch;
	
	@ApiModelProperty(value = "还款方式(详情检索用)查询")
	private String borrowStyleSrch;
	
	@ApiModelProperty(value = "锁定期(详情检索用)查询")
	private String lockPeriodSrch;
	
	@ApiModelProperty(value = "计划编号(详情检索用)查询")
	private String debtPlanNidSrch;
	
	@ApiModelProperty(value = "添加时间开始查询")
	private String addTimeStart;
	
	@ApiModelProperty(value = "添加时间结束查询")
	private String addTimeEnd;
	
	@ApiModelProperty(value = "tab名")
	private String tabName;
	
	@ApiModelProperty(value = "计划编号")
	private String debtPlanNid;
	
	@ApiModelProperty(value = "计划预编号")
	private String debtPlanPreNid;
	
	@ApiModelProperty(value = "计划预编号")
	private String debtPlanPreNidHid;
	
	@ApiModelProperty(value = "计划类型")
	private String debtPlanType;
	
	@ApiModelProperty(value = "计划类型名称")
	private String debtPlanTypeName;
	
	@ApiModelProperty(value = "计划名称")
	private String debtPlanName;
	
	@ApiModelProperty(value = "计划金额")
	private String debtPlanMoney;
	
	@ApiModelProperty(value = "锁定期")
	private String lockPeriod;
	
	@ApiModelProperty(value = "锁定期类型")
	private String isMonth;
	
	@ApiModelProperty(value = "锁定期类型")
	private String expectApr;
	
	@ApiModelProperty(value = "退出方式")
	private String debtQuitStyle;
	
	@ApiModelProperty(value = "退出所需天数")
	private String debtQuitPeriod;
	
	@ApiModelProperty(value = "计划概念")
	private String planConcept;
	
	@ApiModelProperty(value = "计划原理")
	private String planPrinciple;
	
	@ApiModelProperty(value = "风控保障措施")
	private String safeguardMeasures;
	
	@ApiModelProperty(value = "风险保证金措施")
	private String marginMeasures;
	
	@ApiModelProperty(value = "计划状态")
	private String debtPlanStatus;
	
	@ApiModelProperty(value = "申购开始时间")
	private String buyBeginTime;
	
	@ApiModelProperty(value = "申购期限(天)")
	private String buyPeriodDay;
	
	@ApiModelProperty(value = "申购期限(天)")
	private String buyPeriodHour;
	
	@ApiModelProperty(value = "申购总期限")
	private String buyPeriod;
	
	@ApiModelProperty(value = "最低加入金额")
	private String debtMinInvestment;

	@ApiModelProperty(value = "递增金额")
	private String debtInvestmentIncrement;
	
	@ApiModelProperty(value = "最大投资金额")
	private String debtMaxInvestment;

	@ApiModelProperty(value = "审核状态")
	private String isAudits;
	
	@ApiModelProperty(value = "可用券配置")
	private String couponConfig;
	
	@ApiModelProperty(value = "还款方式")
	private String borrowStyle;

	@ApiModelProperty(value = "问题")
	private String normalQuestion;
	
	@ApiModelProperty(value = "最小投资笔数")
	private String minInvestCounts;

	@ApiModelProperty(value = "登陆者userid")
	private int userid;
	/**
	 * 排序
	 */
	private String sort;
	/**
	 * 排序列
	 */
	private String col;
	
	private String addTime;
	
	public int limit;

	public String getPlanNidSrch() {
		return planNidSrch;
	}

	public void setPlanNidSrch(String planNidSrch) {
		this.planNidSrch = planNidSrch;
	}

	public String getPlanNameSrch() {
		return planNameSrch;
	}

	public void setPlanNameSrch(String planNameSrch) {
		this.planNameSrch = planNameSrch;
	}

	public String getPlanStatusSrch() {
		return planStatusSrch;
	}

	public void setPlanStatusSrch(String planStatusSrch) {
		this.planStatusSrch = planStatusSrch;
	}

	public String getPlanDisplayStatusSrch() {
		return planDisplayStatusSrch;
	}

	public void setPlanDisplayStatusSrch(String planDisplayStatusSrch) {
		this.planDisplayStatusSrch = planDisplayStatusSrch;
	}

	public String getBorrowNidSrch() {
		return borrowNidSrch;
	}

	public void setBorrowNidSrch(String borrowNidSrch) {
		this.borrowNidSrch = borrowNidSrch;
	}

	public String getBorrowStyleSrch() {
		return borrowStyleSrch;
	}

	public void setBorrowStyleSrch(String borrowStyleSrch) {
		this.borrowStyleSrch = borrowStyleSrch;
	}

	public String getLockPeriodSrch() {
		return lockPeriodSrch;
	}

	public void setLockPeriodSrch(String lockPeriodSrch) {
		this.lockPeriodSrch = lockPeriodSrch;
	}

	public String getDebtPlanNidSrch() {
		return debtPlanNidSrch;
	}

	public void setDebtPlanNidSrch(String debtPlanNidSrch) {
		this.debtPlanNidSrch = debtPlanNidSrch;
	}

	public String getAddTimeStart() {
		return addTimeStart;
	}

	public void setAddTimeStart(String addTimeStart) {
		this.addTimeStart = addTimeStart;
	}

	public String getAddTimeEnd() {
		return addTimeEnd;
	}

	public void setAddTimeEnd(String addTimeEnd) {
		this.addTimeEnd = addTimeEnd;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getDebtPlanNid() {
		return debtPlanNid;
	}

	public void setDebtPlanNid(String debtPlanNid) {
		this.debtPlanNid = debtPlanNid;
	}

	public String getDebtPlanPreNid() {
		return debtPlanPreNid;
	}

	public void setDebtPlanPreNid(String debtPlanPreNid) {
		this.debtPlanPreNid = debtPlanPreNid;
	}

	public String getDebtPlanPreNidHid() {
		return debtPlanPreNidHid;
	}

	public void setDebtPlanPreNidHid(String debtPlanPreNidHid) {
		this.debtPlanPreNidHid = debtPlanPreNidHid;
	}

	public String getDebtPlanType() {
		return debtPlanType;
	}

	public void setDebtPlanType(String debtPlanType) {
		this.debtPlanType = debtPlanType;
	}

	public String getDebtPlanTypeName() {
		return debtPlanTypeName;
	}

	public void setDebtPlanTypeName(String debtPlanTypeName) {
		this.debtPlanTypeName = debtPlanTypeName;
	}

	public String getDebtPlanName() {
		return debtPlanName;
	}

	public void setDebtPlanName(String debtPlanName) {
		this.debtPlanName = debtPlanName;
	}

	public String getDebtPlanMoney() {
		return debtPlanMoney;
	}

	public void setDebtPlanMoney(String debtPlanMoney) {
		this.debtPlanMoney = debtPlanMoney;
	}

	public String getLockPeriod() {
		return lockPeriod;
	}

	public void setLockPeriod(String lockPeriod) {
		this.lockPeriod = lockPeriod;
	}

	public String getIsMonth() {
		return isMonth;
	}

	public void setIsMonth(String isMonth) {
		this.isMonth = isMonth;
	}

	public String getExpectApr() {
		return expectApr;
	}

	public void setExpectApr(String expectApr) {
		this.expectApr = expectApr;
	}

	public String getDebtQuitStyle() {
		return debtQuitStyle;
	}

	public void setDebtQuitStyle(String debtQuitStyle) {
		this.debtQuitStyle = debtQuitStyle;
	}

	public String getDebtQuitPeriod() {
		return debtQuitPeriod;
	}

	public void setDebtQuitPeriod(String debtQuitPeriod) {
		this.debtQuitPeriod = debtQuitPeriod;
	}

	public String getPlanConcept() {
		return planConcept;
	}

	public void setPlanConcept(String planConcept) {
		this.planConcept = planConcept;
	}

	public String getPlanPrinciple() {
		return planPrinciple;
	}

	public void setPlanPrinciple(String planPrinciple) {
		this.planPrinciple = planPrinciple;
	}

	public String getSafeguardMeasures() {
		return safeguardMeasures;
	}

	public void setSafeguardMeasures(String safeguardMeasures) {
		this.safeguardMeasures = safeguardMeasures;
	}

	public String getMarginMeasures() {
		return marginMeasures;
	}

	public void setMarginMeasures(String marginMeasures) {
		this.marginMeasures = marginMeasures;
	}

	public String getDebtPlanStatus() {
		return debtPlanStatus;
	}

	public void setDebtPlanStatus(String debtPlanStatus) {
		this.debtPlanStatus = debtPlanStatus;
	}

	public String getBuyBeginTime() {
		return buyBeginTime;
	}

	public void setBuyBeginTime(String buyBeginTime) {
		this.buyBeginTime = buyBeginTime;
	}

	public String getBuyPeriodDay() {
		return buyPeriodDay;
	}

	public void setBuyPeriodDay(String buyPeriodDay) {
		this.buyPeriodDay = buyPeriodDay;
	}

	public String getBuyPeriodHour() {
		return buyPeriodHour;
	}

	public void setBuyPeriodHour(String buyPeriodHour) {
		this.buyPeriodHour = buyPeriodHour;
	}

	public String getBuyPeriod() {
		return buyPeriod;
	}

	public void setBuyPeriod(String buyPeriod) {
		this.buyPeriod = buyPeriod;
	}

	public String getDebtMinInvestment() {
		return debtMinInvestment;
	}

	public void setDebtMinInvestment(String debtMinInvestment) {
		this.debtMinInvestment = debtMinInvestment;
	}

	public String getDebtInvestmentIncrement() {
		return debtInvestmentIncrement;
	}

	public void setDebtInvestmentIncrement(String debtInvestmentIncrement) {
		this.debtInvestmentIncrement = debtInvestmentIncrement;
	}

	public String getDebtMaxInvestment() {
		return debtMaxInvestment;
	}

	public void setDebtMaxInvestment(String debtMaxInvestment) {
		this.debtMaxInvestment = debtMaxInvestment;
	}

	public String getIsAudits() {
		return isAudits;
	}

	public void setIsAudits(String isAudits) {
		this.isAudits = isAudits;
	}

	public String getCouponConfig() {
		return couponConfig;
	}

	public void setCouponConfig(String couponConfig) {
		this.couponConfig = couponConfig;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getNormalQuestion() {
		return normalQuestion;
	}

	public void setNormalQuestion(String normalQuestion) {
		this.normalQuestion = normalQuestion;
	}

	public String getMinInvestCounts() {
		return minInvestCounts;
	}

	public void setMinInvestCounts(String minInvestCounts) {
		this.minInvestCounts = minInvestCounts;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
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

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
}
