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

package com.hyjf.am.trade.dao.model.customize.app;

import java.io.Serializable;

/**
 * @author 王坤
 */

public class AppProjectDetailCustomize implements Serializable {

	/** 序列化id */
	private static final long serialVersionUID = 4853144718353886143L;
	/* 项目大分类 projectType HZT 汇直投 HXF 汇消费 */
	private String projectType;
	/* 项目子分类代码 type 0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标 5汇租赁 6供应贷 7汇房贷 8汇消费 9汇资产 */
	private String type;
	/* 项目子分类名称 typeName 0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标 5汇租赁 6供应贷 7汇房贷 8汇消费 9汇资产 */
	private String typeName;
	/* 项目名称 borrowName */
	private String borrowName;
	/* 项目编号 borrowNid */
	private String borrowNid;
	/* 项目总额 account */
	private String account;
	/* 授信额度 userCredit */
	private String userCredit;
	/* 项目区分 comOrPer 项目是个人项目还是企业项目 1企业 2个人 */
	private String comOrPer;
	/* 还款方式 repayStyle */
	private String repayStyle;
	/* 授信额度 borrowAccount */
	private String borrowAccount;
	/* 项目年化收益 borrowApr */
	private String borrowApr;
	/* 项目期限 borrowPeriod */
	private String borrowPeriod;
	/* 可投金额 investAccount */
	private String investAccount;
	/* 项目进度 borrowSchedule */
	private String borrowSchedule;
	/* 开标时间 onTime */
	private String onTime;
	/* 发标时间 sendTime ----------- */
	private String sendTime;
	/* 满标时间 fullTime ------------ */
	private String fullTime;
	/* 项目状态 status 10 定时发标 11投资中 12复审中 13 还款中 14 已还款 15 已流标 */
	private String status;
	/* 倍增金额 increaseMoney ----------- */
	private String increaseMoney;
	/* 加息券 interestCoupon ----------- */
	private String interestCoupon;
	/* 体验金 tasteMoney ----------- */
	private String tasteMoney;
	/* 最小投资金额 tenderAccountMin ----------- */
	private String tenderAccountMin;
	/* 最大投资金额 TenderAccountMax ----------- */
	private String tenderAccountMax;
	/* 融通宝项目编号 ----------- */
	private String borrowAssetNumber;
	/* 项目来源 ----------- */
	private String borrowProjectSource;
	/* 起息时间（风险缓释金） ----------- */
	private String borrowInterestTime;
	/* 到期时间（风险缓释金） ----------- */
	private String borrowDueTime;
	/* 保障方式（风险缓释金） ----------- */
	private String borrowSafeguardWay;
	/* 收益说明（风险缓释金） ----------- */
	private String borrowIncomeDescription;
	/* 发行人（风险缓释金） borrowPublisher ----------- */
	private String borrowPublisher;
	/* 产品加息收益率（风险缓释金） borrowExtraYield ----------- */
	private String borrowExtraYield;
	/* 协议期限 ----------- */
	private String contractPeriod;
	/* 借款金额万 ----------- */
	private String borrowAccountWan;
	/* 投资金额万 ----------- */
	private String tenderAccountMinWan;
	/* 期限类型 ----------- */
	private String borrowPeriodType;

	private String recoverLastTime;

	private String borrowLevel;
	/** 放款时间*/
	private String reverifyTime;
	/** 标的状态*/
	private String borrowStatus;
	/** 项目进行状态*/
	private String projectStatus;
	/** 借款对象ID*/
	private String id;
	/** 借款人账号*/
	private String borrowUserName;
	/** 项目申请人*/
	private String applicant;
	
	/* -----网站改版添加------ */
	
	// 新老标的 0为新标 1为老标
	private Integer isNew;
	// 项目标题
	private String projectName;
	// 融资用途
    private String financePurpose;
    // 月薪收入
    private String monthlyIncome;
    // 还款来源
    private String payment;
    // 第一还款来源
    private String firstPayment;
    // 第二还款来源
    private String secondPayment;
    // 费用说明
    private String costIntrodution;
    // 财务状况
    private String fianceCondition;
    // 项目信息
    private String borrowContents;
    // 风控措施
    private String  borrowMeasuresMea;
	/* 项目还款方式 */
	private String borrowStyle;
    
	/* -----网站改版添加end------ */
	

	public AppProjectDetailCustomize() {
		super();
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserCredit() {
		return userCredit;
	}

	public void setUserCredit(String userCredit) {
		this.userCredit = userCredit;
	}

	public String getRepayStyle() {
		return repayStyle;
	}

	public void setRepayStyle(String repayStyle) {
		this.repayStyle = repayStyle;
	}

	public String getBorrowApr() {
		return borrowApr;
	}

	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getInvestAccount() {
		return investAccount;
	}

	public void setInvestAccount(String investAccount) {
		this.investAccount = investAccount;
	}

	public String getBorrowSchedule() {
		return borrowSchedule;
	}

	public void setBorrowSchedule(String borrowSchedule) {
		this.borrowSchedule = borrowSchedule;
	}

	public String getOnTime() {
		return onTime;
	}

	public void setOnTime(String onTime) {
		this.onTime = onTime;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getFullTime() {
		return fullTime;
	}

	public void setFullTime(String fullTime) {
		this.fullTime = fullTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComOrPer() {
		return comOrPer;
	}

	public void setComOrPer(String comOrPer) {
		this.comOrPer = comOrPer;
	}

	public String getIncreaseMoney() {
		return increaseMoney;
	}

	public void setIncreaseMoney(String increaseMoney) {
		this.increaseMoney = increaseMoney;
	}

	public String getInterestCoupon() {
		return interestCoupon;
	}

	public void setInterestCoupon(String interestCoupon) {
		this.interestCoupon = interestCoupon;
	}

	public String getTasteMoney() {
		return tasteMoney;
	}

	public void setTasteMoney(String tasteMoney) {
		this.tasteMoney = tasteMoney;
	}

	public String getTenderAccountMax() {
		return tenderAccountMax;
	}

	public void setTenderAccountMax(String tenderAccountMax) {
		this.tenderAccountMax = tenderAccountMax;
	}

	public String getBorrowAssetNumber() {
		return borrowAssetNumber;
	}

	public void setBorrowAssetNumber(String borrowAssetNumber) {
		this.borrowAssetNumber = borrowAssetNumber;
	}

	public String getBorrowProjectSource() {
		return borrowProjectSource;
	}

	public void setBorrowProjectSource(String borrowProjectSource) {
		this.borrowProjectSource = borrowProjectSource;
	}

	public String getBorrowInterestTime() {
		return borrowInterestTime;
	}

	public void setBorrowInterestTime(String borrowInterestTime) {
		this.borrowInterestTime = borrowInterestTime;
	}

	public String getBorrowDueTime() {
		return borrowDueTime;
	}

	public void setBorrowDueTime(String borrowDueTime) {
		this.borrowDueTime = borrowDueTime;
	}

	public String getBorrowSafeguardWay() {
		return borrowSafeguardWay;
	}

	public void setBorrowSafeguardWay(String borrowSafeguardWay) {
		this.borrowSafeguardWay = borrowSafeguardWay;
	}

	public String getBorrowIncomeDescription() {
		return borrowIncomeDescription;
	}

	public void setBorrowIncomeDescription(String borrowIncomeDescription) {
		this.borrowIncomeDescription = borrowIncomeDescription;
	}

	public String getBorrowPublisher() {
		return borrowPublisher;
	}

	public void setBorrowPublisher(String borrowPublisher) {
		this.borrowPublisher = borrowPublisher;
	}

	public String getBorrowExtraYield() {
		return borrowExtraYield;
	}

	public void setBorrowExtraYield(String borrowExtraYield) {
		this.borrowExtraYield = borrowExtraYield;
	}

	public String getBorrowAccount() {
		return borrowAccount;
	}

	public void setBorrowAccount(String borrowAccount) {
		this.borrowAccount = borrowAccount;
	}

	public String getTenderAccountMin() {
		return tenderAccountMin;
	}

	public void setTenderAccountMin(String tenderAccountMin) {
		this.tenderAccountMin = tenderAccountMin;
	}

	public String getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public String getBorrowAccountWan() {
		return borrowAccountWan;
	}

	public void setBorrowAccountWan(String borrowAccountWan) {
		this.borrowAccountWan = borrowAccountWan;
	}

	public String getTenderAccountMinWan() {
		return tenderAccountMinWan;
	}

	public void setTenderAccountMinWan(String tenderAccountMinWan) {
		this.tenderAccountMinWan = tenderAccountMinWan;
	}

	public String getBorrowPeriodType() {
		return borrowPeriodType;
	}

	public void setBorrowPeriodType(String borrowPeriodType) {
		this.borrowPeriodType = borrowPeriodType;
	}

	public String getRecoverLastTime() {
		return recoverLastTime;
	}

	public void setRecoverLastTime(String recoverLastTime) {
		this.recoverLastTime = recoverLastTime;
	}

	public String getBorrowLevel() {
		return borrowLevel;
	}

	public void setBorrowLevel(String borrowLevel) {
		this.borrowLevel = borrowLevel;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public String getFinancePurpose() {
		return financePurpose;
	}

	public void setFinancePurpose(String financePurpose) {
		this.financePurpose = financePurpose;
	}

	public String getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getFirstPayment() {
		return firstPayment;
	}

	public void setFirstPayment(String firstPayment) {
		this.firstPayment = firstPayment;
	}

	public String getSecondPayment() {
		return secondPayment;
	}

	public void setSecondPayment(String secondPayment) {
		this.secondPayment = secondPayment;
	}

	public String getCostIntrodution() {
		return costIntrodution;
	}

	public void setCostIntrodution(String costIntrodution) {
		this.costIntrodution = costIntrodution;
	}

	public String getFianceCondition() {
		return fianceCondition;
	}

	public void setFianceCondition(String fianceCondition) {
		this.fianceCondition = fianceCondition;
	}

	public String getBorrowContents() {
		return borrowContents;
	}

	public void setBorrowContents(String borrowContents) {
		this.borrowContents = borrowContents;
	}

	public String getBorrowMeasuresMea() {
		return borrowMeasuresMea;
	}

	public void setBorrowMeasuresMea(String borrowMeasuresMea) {
		this.borrowMeasuresMea = borrowMeasuresMea;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getReverifyTime() {
		return reverifyTime;
	}

	public void setReverifyTime(String reverifyTime) {
		this.reverifyTime = reverifyTime;
	}

	public String getBorrowStatus() {
		return borrowStatus;
	}

	public void setBorrowStatus(String borrowStatus) {
		this.borrowStatus = borrowStatus;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBorrowUserName() {
		return borrowUserName;
	}

	public void setBorrowUserName(String borrowUserName) {
		this.borrowUserName = borrowUserName;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	
	
	
}
