/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import java.io.Serializable;

import com.hyjf.am.vo.BasePage;

/**
 * @author libin
 * @version PlanListRequest.java, v0.1 2018年7月6日 上午9:38:47
 */
public class PlanListRequest extends BasePage implements Serializable{

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
	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;
	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	/*private Paginator paginator;*/
	/**
	 * 检索条件 计划编号
	 */
	private String planNidSrch;
	/**
	 * 检索条件 计划名称
	 */
	private String planNameSrch;
	/**
	 * 检索条件 投资状态 0 全部；1 启用；2 关闭；
	 */
	private String planStatusSrch;

	/**
	 * 检索条件 显示状态 0 全部；1 显示；2 隐藏；
	 */
	private String planDisplayStatusSrch;
	/**
	 * 检索条件 limitStart
	 */
	private int limitStart = -1;
	/**
	 * 检索条件 limitEnd
	 */
	private int limitEnd = -1;
	
	/** 以下详情所需字段 */
	/**
	 * 项目编号(详情检索用)
	 */
	private String borrowNidSrch;

	/**
	 * 还款方式(详情检索用)
	 */
	private String borrowStyleSrch;
	
	/**
	 * 检索条件锁定期
	 */
	private String lockPeriodSrch;

	/**
	 * 计划编号(详情检索用)
	 */
	private String debtPlanNidSrch;
	
	/**
	 * 检索条件 添加时间开始
	 */
	private String addTimeStart;

	/**
	 * 检索条件 添加时间结束
	 */
	private String addTimeEnd;

	/**
	 * tab名
	 */
	private String tabName;
	/**
	 * 计划编号
	 */
	private String debtPlanNid;

	/**
	 * 计划预编号
	 */
	private String debtPlanPreNid;

	/**
	 * 计划预编号
	 */
	private String debtPlanPreNidHid;

	/**
	 * 计划类型
	 */
	private String debtPlanType;

	/**
	 * 计划类型名称
	 */
	private String debtPlanTypeName;

	/**
	 * 计划名称
	 */
	private String debtPlanName;

	/**
	 * 计划金额
	 */
	private String debtPlanMoney;

	/**
	 * 锁定期
	 */
	private String lockPeriod;
	
	/**
	 * 锁定期(天)
	 */
	private String isMonth;

	/**
	 * 预期年化收益率
	 */
	private String expectApr;

	/**
	 * 退出方式
	 */
	private String debtQuitStyle;

	/**
	 * 退出所需天数
	 */
	private String debtQuitPeriod;

	/**
	 * 计划概念
	 */
	private String planConcept;

	/**
	 * 计划原理
	 */
	private String planPrinciple;

	/**
	 * 风控保障措施
	 */
	private String safeguardMeasures;

	/**
	 * 风险保证金措施
	 */
	private String marginMeasures;

	/**
	 * 计划状态
	 */
	private String debtPlanStatus;

	/**
	 * 申购开始时间
	 */
	private String buyBeginTime;
	
	private String addTime;
	/**
	 * 申购期限(天)
	 */
	private String buyPeriodDay;

	/**
	 * 申购期限(小时)
	 */
	private String buyPeriodHour;

	/**
	 * 申购总期限
	 */
	private String buyPeriod;

	/**
	 * 最低加入金额
	 */
	private String debtMinInvestment;

	/**
	 * 递增金额
	 */
	private String debtInvestmentIncrement;
	
	
	/**
	 * 最大投资金额
	 */
	private String debtMaxInvestment;

	/**
	 * 审核状态
	 */
	private String isAudits;
	
	/**
	 * 可用券配置
	 */
	private String couponConfig;
	
	/**
	 * 还款方式
	 */
	private String borrowStyle;
	/**
	 * 还款方式
	 */
	private String normalQuestion;
	
	/**
	 * 最小自动投资笔数
	 */
	private String minInvestCounts;

	public int limit;

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

	public int getPaginatorPage() {
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

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

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
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

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * 检索条件 计划类型 “配置中心-汇添金配置”中所有计划类型（暂时不用）
	 */
	/*private String planTypeSrch;*/
	/**
	 * 检索条件 发起时间（暂时不用）
	 */
	/*private String timeStartSrch;*/

	/**
	 * 检索条件 结束时间（暂时不用）
	 */
	/*private String timeEndSrch;*/
	/**
	 * 检索条件 画面迁移标识(暂时不用)
	 */
	/*private String moveFlag;*/
	/**
	 * 关联资产
	 */
	/*private List<DebtPlanBorrowCustomize> debtPlanBorrowList;*/

	/**
	 * 关联资产添加Flg
	 */
	/*private String isAddFlg;*/

	/**
	 * 资产配置关联的标的编号
	 */
    /*private String debtPlanBorrowNid;*/
	
	

}
