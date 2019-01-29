/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.hgreportdata.nifa;

import com.hyjf.am.vo.hgreportdata.BaseHgDataReportVO;

import java.io.Serializable;

/**
 * @author PC-LIUSHOUYI
 * @version NifaBorrowInfoEntity, v0.1 2018/11/27 14:56
 */
public class NifaBorrowInfoVO extends BaseHgDataReportVO implements Serializable {

    /**
     * 项目唯一编号
     */
    private String projectNo;

    /**
     * 社会信用代码
     */
    private String socialCreditCode;

    /**
     * 平台序号
     */
    private String platformNo;

    /**
     * 项目编号
     */
    private String organizationNo;

    /**
     * 项目类型
     */
    private String borrowType;

    /**
     * 项目名称
     */
    private String borrowName;

    /**
     * 项目成立日期
     */
    private String recoverTime;

    /**
     * 借款金额
     */
    private String account;

    /**
     * 借款币种
     */
    private String currency;

    /**
     * 借款起息日
     */
    private String borrowInterestTime;

    /**
     * 借款到期日期
     */
    private String borrowEndTime;

    /**
     * 借款期限
     */
    private String borrowDays;

    /**
     * 出借利率
     */
    private String borrowApr;

    /**
     * 项目费率
     */
    private String projectFeeRate;

    /**
     * 项目费用
     */
    private String projectFee;

    /**
     * 其他费用
     */
    private String otherFee;

    /**
     * 还款保证措施
     */
    private String measures;

    /**
     * 还款期数
     */
    private String repayPeriod;

    /**
     * 担保方式
     */
    private String guaranteeType;

    /**
     * 担保公司名称
     */
    private String guaranteeCompany;

    /**
     * 约定还款计划
     */
    private String repayPlan;

    /**
     * 实际还款记录
     */
    private String repayDetials;

    /**
     * 实际累计本金偿还额
     */
    private String repayCaptialYes;

    /**
     * 实际累计利息偿还额
     */
    private String repayInterestYes;

    /**
     * 借款剩余本金余额
     */
    private String repayCaptitalWait;

    /**
     * 借款剩余应付利息
     */
    private String repayInterestWait;

    /**
     * 是否支持转让
     */
    private String isCredit;

    /**
     * 项目状态
     */
    private String reverifyStatus;

    /**
     * 逾期原因
     */
    private String lateReason;

    /**
     * 逾期次数
     */
    private String lateCounts;

    /**
     * 还款方式
     */
    private String borrowStyle;

    /**
     * 借款用途
     */
    private String financePurpose;

    /**
     * 出借人个数
     */
    private String lenderCounts;

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    public String getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(String platformNo) {
        this.platformNo = platformNo;
    }

    public String getOrganizationNo() {
        return organizationNo;
    }

    public void setOrganizationNo(String organizationNo) {
        this.organizationNo = organizationNo;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(String recoverTime) {
        this.recoverTime = recoverTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBorrowInterestTime() {
        return borrowInterestTime;
    }

    public void setBorrowInterestTime(String borrowInterestTime) {
        this.borrowInterestTime = borrowInterestTime;
    }

    public String getBorrowEndTime() {
        return borrowEndTime;
    }

    public void setBorrowEndTime(String borrowEndTime) {
        this.borrowEndTime = borrowEndTime;
    }

    public String getBorrowDays() {
        return borrowDays;
    }

    public void setBorrowDays(String borrowDays) {
        this.borrowDays = borrowDays;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getProjectFeeRate() {
        return projectFeeRate;
    }

    public void setProjectFeeRate(String projectFeeRate) {
        this.projectFeeRate = projectFeeRate;
    }

    public String getProjectFee() {
        return projectFee;
    }

    public void setProjectFee(String projectFee) {
        this.projectFee = projectFee;
    }

    public String getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(String otherFee) {
        this.otherFee = otherFee;
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    public String getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(String repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public String getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(String guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public String getGuaranteeCompany() {
        return guaranteeCompany;
    }

    public void setGuaranteeCompany(String guaranteeCompany) {
        this.guaranteeCompany = guaranteeCompany;
    }

    public String getRepayPlan() {
        return repayPlan;
    }

    public void setRepayPlan(String repayPlan) {
        this.repayPlan = repayPlan;
    }

    public String getRepayDetials() {
        return repayDetials;
    }

    public void setRepayDetials(String repayDetials) {
        this.repayDetials = repayDetials;
    }

    public String getRepayCaptialYes() {
        return repayCaptialYes;
    }

    public void setRepayCaptialYes(String repayCaptialYes) {
        this.repayCaptialYes = repayCaptialYes;
    }

    public String getRepayInterestYes() {
        return repayInterestYes;
    }

    public void setRepayInterestYes(String repayInterestYes) {
        this.repayInterestYes = repayInterestYes;
    }

    public String getRepayCaptitalWait() {
        return repayCaptitalWait;
    }

    public void setRepayCaptitalWait(String repayCaptitalWait) {
        this.repayCaptitalWait = repayCaptitalWait;
    }

    public String getRepayInterestWait() {
        return repayInterestWait;
    }

    public void setRepayInterestWait(String repayInterestWait) {
        this.repayInterestWait = repayInterestWait;
    }

    public String getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(String isCredit) {
        this.isCredit = isCredit;
    }

    public String getReverifyStatus() {
        return reverifyStatus;
    }

    public void setReverifyStatus(String reverifyStatus) {
        this.reverifyStatus = reverifyStatus;
    }

    public String getLateReason() {
        return lateReason;
    }

    public void setLateReason(String lateReason) {
        this.lateReason = lateReason;
    }

    public String getLateCounts() {
        return lateCounts;
    }

    public void setLateCounts(String lateCounts) {
        this.lateCounts = lateCounts;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public String getFinancePurpose() {
        return financePurpose;
    }

    public void setFinancePurpose(String financePurpose) {
        this.financePurpose = financePurpose;
    }

    public String getLenderCounts() {
        return lenderCounts;
    }

    public void setLenderCounts(String lenderCounts) {
        this.lenderCounts = lenderCounts;
    }
}
