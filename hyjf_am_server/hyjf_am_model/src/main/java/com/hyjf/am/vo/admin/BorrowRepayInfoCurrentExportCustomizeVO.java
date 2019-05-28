package com.hyjf.am.vo.admin;

import java.math.BigDecimal;

/**
 * @author hesy
 */
public class BorrowRepayInfoCurrentExportCustomizeVO {
    /** 借款编号*/
    private String borrowNid;
    /** 出借订单号*/
    private String tenderOrdid;
    /** 承接订单号*/
    private String assignOrdid;
    /** 还款订单号*/
    private String repayOrdid;
    /** 计划编号*/
    private String planNid;
    /** 资产来源*/
    private String instName;
    /** 借款人用户id*/
    private String borrowUserId;
    /** 借款人用户名*/
    private String borrowUserName;
    /** 项目名称*/
    private String borrowName;
    /** 项目类别*/
    private String projectTypeName;
    /** 借款期限*/
    private String borrowPeriod;
    /** 出借利率*/
    private String borrowApr;
    /** 借款金额*/
    private String borrowAccount;
    /** 借到金额*/
    private String borrowAccountYes;
    /** 还款方式*/
    private String repayType;
    /** 出借人用户名*/
    private String recoverUserName;
    /** 出借人id*/
    private String recoverUserId;
    /** 出借人用户属性（当时）*/
    private String recoverUserAttribute;
    /** 出借人所属一级分部（当时）*/
    private String recoverRegionName;
    /** 出借人所属二级分部（当时）*/
    private String recoverBranchName;
    /** 出借人所属团队（当时）*/
    private String recoverDepartmentName;
    /** 推荐人用户名（当时）*/
    private String referrerName;
    /** 推荐人用户id*/
    private String referrerUserId;
    /** 推荐人真实姓名*/
    private String referrerTrueName;
    /** 推荐人所属一级分部（当时）*/
    private String referrerRegionName;
    /** 推荐人所属二级分部（当时）*/
    private String referrerBranchName;
    /** 推荐人所属团队（当时）*/
    private String referrerDepartmentName;
    /** 出借金额*/
    private String recoverTotal;
    /** 期数*/
    private String period;
    /** 总期数*/
    private String periodTotal;
    /** 持有金额*/
    private String amountHold;
    /** 应还本金*/
    private String recoverCapital;
    /** 应还利息*/
    private String recoverInterest;
    /** 应还本息*/
    private String recoverAccount;
    /** 应还管理费*/
    private String recoverFee;
    /** 到期日*/
    private String recoverLastTime;
    /** 当期应回本金*/
    private String recoverCapitalPeriod;
    /** 当期应回利息*/
    private String recoverInterestPeriod;
    /** 当期应回本息*/
    private String recoverAccountPeriod;
    /** 当期还款服务费*/
    private String recoverFeePeriod;
    /** 当期提前天数*/
    private String chargeDays;
    /** 提前还款减息*/
    private String chargeInterestReduce;
    /** 提前还款违约金*/
    private String chargePenaltyInterest;
    /** 逾期天数*/
    private String lateDays;
    /** 逾期违约金*/
    private String lateInterest;
    /** 当期实回本金*/
    private String recoverCapitalYesPeriod;
    /** 当期实回利息*/
    private String recoverInterestYesPeriod;
    /** 当期实还总额*/
    private String recoverAccountYesPeriod;
    /** 当期实还管理费*/
    private String recoverFeeYesPeriod;
    /** 剩余待回本金*/
    private String recoverCapitalWait;
    /** 剩余待回利息*/
    private String recoverInterestWait;
    /** 剩余待回本息*/
    private String recoverAccountWait;
    /** 当期还款人*/
    private String repayUserName;
    /** 平台还款方式*/
    private String autoRepay;
    /** 还款状态 1已还 0未还*/
    private String recoverStatus;
    /** 还款来源*/
    private String repayMoneySource;
    /** 发起人*/
    private String submitter;
    /** 实还总额*/
    private String recoverAmountTotal;
    /** 应还时间*/
    private String recoverTime;
    /** 实际还款时间*/
    private String repayActionTime;
    /** 记录类别 1 未分期原始出借 2 分期原始出借 3 汇转让承接 4 计划底层债转标的承接*/
    private String recordType;
    /** 还款方式*/
    private String borrowStyle;
    /** 配置的管理费率*/
    private BigDecimal feeRate;
    /** 差异费率*/
    private BigDecimal differentialRate;
    /** 初审时间*/
    private Integer verifyTime;
    /** 当前还款期数*/
    private Integer recoverPeriod;
    /** 原始出借金额*/
    private String account;
    /** 借款期限*/
    private Integer borrowPeriodInt;
    /** 出借人用户属性（当前）*/
    private String recoverUserAttributeNow;
    /** 推荐人所属一级分部（当前）*/
    private String referrerRegionNameNow;
    /** 推荐人所属二级分部（当前）*/
    private String referrerBranchNameNow;
    /** 推荐人所属团队（当前）*/
    private String referrerDepartmentNameNow;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getTenderOrdid() {
        return tenderOrdid;
    }

    public void setTenderOrdid(String tenderOrdid) {
        this.tenderOrdid = tenderOrdid;
    }

    public String getAssignOrdid() {
        return assignOrdid;
    }

    public void setAssignOrdid(String assignOrdid) {
        this.assignOrdid = assignOrdid;
    }

    public String getRepayOrdid() {
        return repayOrdid;
    }

    public void setRepayOrdid(String repayOrdid) {
        this.repayOrdid = repayOrdid;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public String getRecoverUserName() {
        return recoverUserName;
    }

    public void setRecoverUserName(String recoverUserName) {
        this.recoverUserName = recoverUserName;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getAmountHold() {
        return amountHold;
    }

    public void setAmountHold(String amountHold) {
        this.amountHold = amountHold;
    }

    public String getRecoverCapital() {
        return recoverCapital;
    }

    public void setRecoverCapital(String recoverCapital) {
        this.recoverCapital = recoverCapital;
    }

    public String getRecoverInterest() {
        return recoverInterest;
    }

    public void setRecoverInterest(String recoverInterest) {
        this.recoverInterest = recoverInterest;
    }

    public String getRecoverAccount() {
        return recoverAccount;
    }

    public void setRecoverAccount(String recoverAccount) {
        this.recoverAccount = recoverAccount;
    }

    public String getRecoverFee() {
        return recoverFee;
    }

    public void setRecoverFee(String recoverFee) {
        this.recoverFee = recoverFee;
    }

    public String getRecoverStatus() {
        return recoverStatus;
    }

    public void setRecoverStatus(String recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public String getRepayMoneySource() {
        return repayMoneySource;
    }

    public void setRepayMoneySource(String repayMoneySource) {
        this.repayMoneySource = repayMoneySource;
    }

    public String getRecoverAmountTotal() {
        return recoverAmountTotal;
    }

    public void setRecoverAmountTotal(String recoverAmountTotal) {
        this.recoverAmountTotal = recoverAmountTotal;
    }

    public String getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(String recoverTime) {
        this.recoverTime = recoverTime;
    }

    public String getRepayActionTime() {
        return repayActionTime;
    }

    public void setRepayActionTime(String repayActionTime) {
        this.repayActionTime = repayActionTime;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getBorrowUserId() {
        return borrowUserId;
    }

    public void setBorrowUserId(String borrowUserId) {
        this.borrowUserId = borrowUserId;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getBorrowAccount() {
        return borrowAccount;
    }

    public void setBorrowAccount(String borrowAccount) {
        this.borrowAccount = borrowAccount;
    }

    public String getBorrowAccountYes() {
        return borrowAccountYes;
    }

    public void setBorrowAccountYes(String borrowAccountYes) {
        this.borrowAccountYes = borrowAccountYes;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public String getRecoverUserId() {
        return recoverUserId;
    }

    public void setRecoverUserId(String recoverUserId) {
        this.recoverUserId = recoverUserId;
    }

    public String getRecoverUserAttribute() {
        return recoverUserAttribute;
    }

    public void setRecoverUserAttribute(String recoverUserAttribute) {
        this.recoverUserAttribute = recoverUserAttribute;
    }

    public String getRecoverRegionName() {
        return recoverRegionName;
    }

    public void setRecoverRegionName(String recoverRegionName) {
        this.recoverRegionName = recoverRegionName;
    }

    public String getRecoverBranchName() {
        return recoverBranchName;
    }

    public void setRecoverBranchName(String recoverBranchName) {
        this.recoverBranchName = recoverBranchName;
    }

    public String getRecoverDepartmentName() {
        return recoverDepartmentName;
    }

    public void setRecoverDepartmentName(String recoverDepartmentName) {
        this.recoverDepartmentName = recoverDepartmentName;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public String getReferrerUserId() {
        return referrerUserId;
    }

    public void setReferrerUserId(String referrerUserId) {
        this.referrerUserId = referrerUserId;
    }

    public String getReferrerTrueName() {
        return referrerTrueName;
    }

    public void setReferrerTrueName(String referrerTrueName) {
        this.referrerTrueName = referrerTrueName;
    }

    public String getReferrerRegionName() {
        return referrerRegionName;
    }

    public void setReferrerRegionName(String referrerRegionName) {
        this.referrerRegionName = referrerRegionName;
    }

    public String getReferrerBranchName() {
        return referrerBranchName;
    }

    public void setReferrerBranchName(String referrerBranchName) {
        this.referrerBranchName = referrerBranchName;
    }

    public String getReferrerDepartmentName() {
        return referrerDepartmentName;
    }

    public void setReferrerDepartmentName(String referrerDepartmentName) {
        this.referrerDepartmentName = referrerDepartmentName;
    }

    public String getRecoverTotal() {
        return recoverTotal;
    }

    public void setRecoverTotal(String recoverTotal) {
        this.recoverTotal = recoverTotal;
    }

    public String getRecoverLastTime() {
        return recoverLastTime;
    }

    public void setRecoverLastTime(String recoverLastTime) {
        this.recoverLastTime = recoverLastTime;
    }

    public String getRecoverCapitalPeriod() {
        return recoverCapitalPeriod;
    }

    public void setRecoverCapitalPeriod(String recoverCapitalPeriod) {
        this.recoverCapitalPeriod = recoverCapitalPeriod;
    }

    public String getRecoverInterestPeriod() {
        return recoverInterestPeriod;
    }

    public void setRecoverInterestPeriod(String recoverInterestPeriod) {
        this.recoverInterestPeriod = recoverInterestPeriod;
    }

    public String getRecoverAccountPeriod() {
        return recoverAccountPeriod;
    }

    public void setRecoverAccountPeriod(String recoverAccountPeriod) {
        this.recoverAccountPeriod = recoverAccountPeriod;
    }

    public String getRecoverFeePeriod() {
        return recoverFeePeriod;
    }

    public void setRecoverFeePeriod(String recoverFeePeriod) {
        this.recoverFeePeriod = recoverFeePeriod;
    }

    public String getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(String chargeDays) {
        this.chargeDays = chargeDays;
    }

    public String getChargeInterestReduce() {
        return chargeInterestReduce;
    }

    public void setChargeInterestReduce(String chargeInterestReduce) {
        this.chargeInterestReduce = chargeInterestReduce;
    }

    public String getChargePenaltyInterest() {
        return chargePenaltyInterest;
    }

    public void setChargePenaltyInterest(String chargePenaltyInterest) {
        this.chargePenaltyInterest = chargePenaltyInterest;
    }

    public String getLateDays() {
        return lateDays;
    }

    public void setLateDays(String lateDays) {
        this.lateDays = lateDays;
    }

    public String getLateInterest() {
        return lateInterest;
    }

    public void setLateInterest(String lateInterest) {
        this.lateInterest = lateInterest;
    }

    public String getRecoverCapitalYesPeriod() {
        return recoverCapitalYesPeriod;
    }

    public void setRecoverCapitalYesPeriod(String recoverCapitalYesPeriod) {
        this.recoverCapitalYesPeriod = recoverCapitalYesPeriod;
    }

    public String getRecoverInterestYesPeriod() {
        return recoverInterestYesPeriod;
    }

    public void setRecoverInterestYesPeriod(String recoverInterestYesPeriod) {
        this.recoverInterestYesPeriod = recoverInterestYesPeriod;
    }

    public String getRecoverAccountYesPeriod() {
        return recoverAccountYesPeriod;
    }

    public void setRecoverAccountYesPeriod(String recoverAccountYesPeriod) {
        this.recoverAccountYesPeriod = recoverAccountYesPeriod;
    }

    public String getRecoverFeeYesPeriod() {
        return recoverFeeYesPeriod;
    }

    public void setRecoverFeeYesPeriod(String recoverFeeYesPeriod) {
        this.recoverFeeYesPeriod = recoverFeeYesPeriod;
    }

    public String getRecoverCapitalWait() {
        return recoverCapitalWait;
    }

    public void setRecoverCapitalWait(String recoverCapitalWait) {
        this.recoverCapitalWait = recoverCapitalWait;
    }

    public String getRecoverInterestWait() {
        return recoverInterestWait;
    }

    public void setRecoverInterestWait(String recoverInterestWait) {
        this.recoverInterestWait = recoverInterestWait;
    }

    public String getRecoverAccountWait() {
        return recoverAccountWait;
    }

    public void setRecoverAccountWait(String recoverAccountWait) {
        this.recoverAccountWait = recoverAccountWait;
    }

    public String getRepayUserName() {
        return repayUserName;
    }

    public void setRepayUserName(String repayUserName) {
        this.repayUserName = repayUserName;
    }

    public String getAutoRepay() {
        return autoRepay;
    }

    public void setAutoRepay(String autoRepay) {
        this.autoRepay = autoRepay;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getPeriodTotal() {
        return periodTotal;
    }

    public void setPeriodTotal(String periodTotal) {
        this.periodTotal = periodTotal;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public BigDecimal getDifferentialRate() {
        return differentialRate;
    }

    public void setDifferentialRate(BigDecimal differentialRate) {
        this.differentialRate = differentialRate;
    }

    public Integer getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Integer verifyTime) {
        this.verifyTime = verifyTime;
    }

    public Integer getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(Integer recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getBorrowPeriodInt() {
        return borrowPeriodInt;
    }

    public void setBorrowPeriodInt(Integer borrowPeriodInt) {
        this.borrowPeriodInt = borrowPeriodInt;
    }

    public String getRecoverUserAttributeNow() {
        return recoverUserAttributeNow;
    }

    public void setRecoverUserAttributeNow(String recoverUserAttributeNow) {
        this.recoverUserAttributeNow = recoverUserAttributeNow;
    }

    public String getReferrerRegionNameNow() {
        return referrerRegionNameNow;
    }

    public void setReferrerRegionNameNow(String referrerRegionNameNow) {
        this.referrerRegionNameNow = referrerRegionNameNow;
    }

    public String getReferrerBranchNameNow() {
        return referrerBranchNameNow;
    }

    public void setReferrerBranchNameNow(String referrerBranchNameNow) {
        this.referrerBranchNameNow = referrerBranchNameNow;
    }

    public String getReferrerDepartmentNameNow() {
        return referrerDepartmentNameNow;
    }

    public void setReferrerDepartmentNameNow(String referrerDepartmentNameNow) {
        this.referrerDepartmentNameNow = referrerDepartmentNameNow;
    }
}
