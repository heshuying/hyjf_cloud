/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.hgreportdata.nifa;

import com.hyjf.cs.message.bean.hgreportdata.BaseHgDataReportEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author PC-LIUSHOUYI
 * @version NifaCreditInfoEntity, v0.1 2018/11/30 14:35
 */
@Document(collection = "ht_nifa_credit_info")
public class NifaCreditInfoEntity extends BaseHgDataReportEntity implements Serializable {
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
    private String plantformNo;
    /**
     * 项目编号
     */
    private String creditNid;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 项目转让类型
     */
    private String creditType;
    /**
     * 项目来源
     */
    private String creditFrom;
    /**
     * 平台自身债权转让原项目编号
     */
    private String borrowNid;
    /**
     * 平台自身债权转让原出借人ID（出让人）
     */
    private String creditUserId;
    /**
     * 来自外部机构转让的机构名称
     */
    private String outOrgName;
    /**
     * 来自外部机构转让的机构证件类型
     */
    private String outOrgType;
    /**
     * 来自外部机构转让的机构证件号码
     */
    private String outOrgCradNo;
    /**
     * 原借款剩余期数
     */
    private String repayPeriod;
    /**
     * 原借款剩余本金
     */
    private String assignCapital;
    /**
     * 原借款剩余收益
     */
    private String assignInterestAdvance;
    /**
     * 原借款还款方式
     */
    private String borrowType;
    /**
     * 原借款用途
     */
    private String borrowUse;
    /**
     * 原借款项目状态
     */
    private String oldBorrowStatus;
    /**
     * 原还款保证措施
     */
    private String oldBorrowType;
    /**
     * 担保方式
     */
    private String guaranteeType;
    /**
     * 担保公司名称
     */
    private String guaranteeCompany;
    /**
     * 转让金额
     */
    private String assignAccount;
    /**
     * 转让币种
     */
    private String currency;
    /**
     * 转让利率
     */
    private String assignServiceRate;
    /**
     * 转让费率
     */
    private String assignServiceApr;
    /**
     * 转让费用
     */
    private String assignServiceFee;
    /**
     * 转让时间
     */
    private String assignServiceTime;
    /**
     * 受让人个数
     */
    private String userCount;

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

    public String getPlantformNo() {
        return plantformNo;
    }

    public void setPlantformNo(String plantformNo) {
        this.plantformNo = plantformNo;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditType() {
        return creditType;
    }

    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    public String getCreditFrom() {
        return creditFrom;
    }

    public void setCreditFrom(String creditFrom) {
        this.creditFrom = creditFrom;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getCreditUserId() {
        return creditUserId;
    }

    public void setCreditUserId(String creditUserId) {
        this.creditUserId = creditUserId;
    }

    public String getOutOrgName() {
        return outOrgName;
    }

    public void setOutOrgName(String outOrgName) {
        this.outOrgName = outOrgName;
    }

    public String getOutOrgType() {
        return outOrgType;
    }

    public void setOutOrgType(String outOrgType) {
        this.outOrgType = outOrgType;
    }

    public String getOutOrgCradNo() {
        return outOrgCradNo;
    }

    public void setOutOrgCradNo(String outOrgCradNo) {
        this.outOrgCradNo = outOrgCradNo;
    }

    public String getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(String repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public String getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(String assignCapital) {
        this.assignCapital = assignCapital;
    }

    public String getAssignInterestAdvance() {
        return assignInterestAdvance;
    }

    public void setAssignInterestAdvance(String assignInterestAdvance) {
        this.assignInterestAdvance = assignInterestAdvance;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }

    public String getBorrowUse() {
        return borrowUse;
    }

    public void setBorrowUse(String borrowUse) {
        this.borrowUse = borrowUse;
    }

    public String getOldBorrowStatus() {
        return oldBorrowStatus;
    }

    public void setOldBorrowStatus(String oldBorrowStatus) {
        this.oldBorrowStatus = oldBorrowStatus;
    }

    public String getOldBorrowType() {
        return oldBorrowType;
    }

    public void setOldBorrowType(String oldBorrowType) {
        this.oldBorrowType = oldBorrowType;
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

    public String getAssignAccount() {
        return assignAccount;
    }

    public void setAssignAccount(String assignAccount) {
        this.assignAccount = assignAccount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAssignServiceRate() {
        return assignServiceRate;
    }

    public void setAssignServiceRate(String assignServiceRate) {
        this.assignServiceRate = assignServiceRate;
    }

    public String getAssignServiceApr() {
        return assignServiceApr;
    }

    public void setAssignServiceApr(String assignServiceApr) {
        this.assignServiceApr = assignServiceApr;
    }

    public String getAssignServiceFee() {
        return assignServiceFee;
    }

    public void setAssignServiceFee(String assignServiceFee) {
        this.assignServiceFee = assignServiceFee;
    }

    public String getAssignServiceTime() {
        return assignServiceTime;
    }

    public void setAssignServiceTime(String assignServiceTime) {
        this.assignServiceTime = assignServiceTime;
    }

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }
}
