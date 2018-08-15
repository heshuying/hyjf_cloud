package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class NifaContractEssence implements Serializable {
    private Integer id;

    private String platformNo;

    private String platformName;

    private String projectNo;

    private String contractName;

    private String contractNo;

    private String contractSigner;

    private String contractTime;

    private Integer borrowerType;

    private String borrowerCertType;

    private String borrowerCertNo;

    private String borrowerName;

    private String borrowerAddress;

    private String borrowerNacaoNo;

    private String borrowerOrgcodeNo;

    private String borrowerCompany;

    private Integer investorType;

    private String investorCertType;

    private String investorCertNo;

    private String investorName;

    private String investorNacaoNo;

    private String investorOrgcodeNo;

    private String investorCompany;

    private String investAmount;

    private String borrowRate;

    private String borrowUse;

    private String borrowUseLimit;

    private String loanDate;

    private String loanDateBasis;

    private String startDate;

    private String expiryDate;

    private Integer repayType;

    private String repayFormula;

    private String repayDateRule;

    private Integer repayNum;

    private String repayPlan;

    private String overdueRepayDef;

    private String overdueRepayResp;

    private String overdueRepayProc;

    private String noticeAddress;

    private String contractEffectiveDate;

    private String contractTemplateNo;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(String platformNo) {
        this.platformNo = platformNo == null ? null : platformNo.trim();
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName == null ? null : platformName.trim();
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo == null ? null : projectNo.trim();
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public String getContractSigner() {
        return contractSigner;
    }

    public void setContractSigner(String contractSigner) {
        this.contractSigner = contractSigner == null ? null : contractSigner.trim();
    }

    public String getContractTime() {
        return contractTime;
    }

    public void setContractTime(String contractTime) {
        this.contractTime = contractTime == null ? null : contractTime.trim();
    }

    public Integer getBorrowerType() {
        return borrowerType;
    }

    public void setBorrowerType(Integer borrowerType) {
        this.borrowerType = borrowerType;
    }

    public String getBorrowerCertType() {
        return borrowerCertType;
    }

    public void setBorrowerCertType(String borrowerCertType) {
        this.borrowerCertType = borrowerCertType == null ? null : borrowerCertType.trim();
    }

    public String getBorrowerCertNo() {
        return borrowerCertNo;
    }

    public void setBorrowerCertNo(String borrowerCertNo) {
        this.borrowerCertNo = borrowerCertNo == null ? null : borrowerCertNo.trim();
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName == null ? null : borrowerName.trim();
    }

    public String getBorrowerAddress() {
        return borrowerAddress;
    }

    public void setBorrowerAddress(String borrowerAddress) {
        this.borrowerAddress = borrowerAddress == null ? null : borrowerAddress.trim();
    }

    public String getBorrowerNacaoNo() {
        return borrowerNacaoNo;
    }

    public void setBorrowerNacaoNo(String borrowerNacaoNo) {
        this.borrowerNacaoNo = borrowerNacaoNo == null ? null : borrowerNacaoNo.trim();
    }

    public String getBorrowerOrgcodeNo() {
        return borrowerOrgcodeNo;
    }

    public void setBorrowerOrgcodeNo(String borrowerOrgcodeNo) {
        this.borrowerOrgcodeNo = borrowerOrgcodeNo == null ? null : borrowerOrgcodeNo.trim();
    }

    public String getBorrowerCompany() {
        return borrowerCompany;
    }

    public void setBorrowerCompany(String borrowerCompany) {
        this.borrowerCompany = borrowerCompany == null ? null : borrowerCompany.trim();
    }

    public Integer getInvestorType() {
        return investorType;
    }

    public void setInvestorType(Integer investorType) {
        this.investorType = investorType;
    }

    public String getInvestorCertType() {
        return investorCertType;
    }

    public void setInvestorCertType(String investorCertType) {
        this.investorCertType = investorCertType == null ? null : investorCertType.trim();
    }

    public String getInvestorCertNo() {
        return investorCertNo;
    }

    public void setInvestorCertNo(String investorCertNo) {
        this.investorCertNo = investorCertNo == null ? null : investorCertNo.trim();
    }

    public String getInvestorName() {
        return investorName;
    }

    public void setInvestorName(String investorName) {
        this.investorName = investorName == null ? null : investorName.trim();
    }

    public String getInvestorNacaoNo() {
        return investorNacaoNo;
    }

    public void setInvestorNacaoNo(String investorNacaoNo) {
        this.investorNacaoNo = investorNacaoNo == null ? null : investorNacaoNo.trim();
    }

    public String getInvestorOrgcodeNo() {
        return investorOrgcodeNo;
    }

    public void setInvestorOrgcodeNo(String investorOrgcodeNo) {
        this.investorOrgcodeNo = investorOrgcodeNo == null ? null : investorOrgcodeNo.trim();
    }

    public String getInvestorCompany() {
        return investorCompany;
    }

    public void setInvestorCompany(String investorCompany) {
        this.investorCompany = investorCompany == null ? null : investorCompany.trim();
    }

    public String getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(String investAmount) {
        this.investAmount = investAmount == null ? null : investAmount.trim();
    }

    public String getBorrowRate() {
        return borrowRate;
    }

    public void setBorrowRate(String borrowRate) {
        this.borrowRate = borrowRate == null ? null : borrowRate.trim();
    }

    public String getBorrowUse() {
        return borrowUse;
    }

    public void setBorrowUse(String borrowUse) {
        this.borrowUse = borrowUse == null ? null : borrowUse.trim();
    }

    public String getBorrowUseLimit() {
        return borrowUseLimit;
    }

    public void setBorrowUseLimit(String borrowUseLimit) {
        this.borrowUseLimit = borrowUseLimit == null ? null : borrowUseLimit.trim();
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate == null ? null : loanDate.trim();
    }

    public String getLoanDateBasis() {
        return loanDateBasis;
    }

    public void setLoanDateBasis(String loanDateBasis) {
        this.loanDateBasis = loanDateBasis == null ? null : loanDateBasis.trim();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate == null ? null : expiryDate.trim();
    }

    public Integer getRepayType() {
        return repayType;
    }

    public void setRepayType(Integer repayType) {
        this.repayType = repayType;
    }

    public String getRepayFormula() {
        return repayFormula;
    }

    public void setRepayFormula(String repayFormula) {
        this.repayFormula = repayFormula == null ? null : repayFormula.trim();
    }

    public String getRepayDateRule() {
        return repayDateRule;
    }

    public void setRepayDateRule(String repayDateRule) {
        this.repayDateRule = repayDateRule == null ? null : repayDateRule.trim();
    }

    public Integer getRepayNum() {
        return repayNum;
    }

    public void setRepayNum(Integer repayNum) {
        this.repayNum = repayNum;
    }

    public String getRepayPlan() {
        return repayPlan;
    }

    public void setRepayPlan(String repayPlan) {
        this.repayPlan = repayPlan == null ? null : repayPlan.trim();
    }

    public String getOverdueRepayDef() {
        return overdueRepayDef;
    }

    public void setOverdueRepayDef(String overdueRepayDef) {
        this.overdueRepayDef = overdueRepayDef == null ? null : overdueRepayDef.trim();
    }

    public String getOverdueRepayResp() {
        return overdueRepayResp;
    }

    public void setOverdueRepayResp(String overdueRepayResp) {
        this.overdueRepayResp = overdueRepayResp == null ? null : overdueRepayResp.trim();
    }

    public String getOverdueRepayProc() {
        return overdueRepayProc;
    }

    public void setOverdueRepayProc(String overdueRepayProc) {
        this.overdueRepayProc = overdueRepayProc == null ? null : overdueRepayProc.trim();
    }

    public String getNoticeAddress() {
        return noticeAddress;
    }

    public void setNoticeAddress(String noticeAddress) {
        this.noticeAddress = noticeAddress == null ? null : noticeAddress.trim();
    }

    public String getContractEffectiveDate() {
        return contractEffectiveDate;
    }

    public void setContractEffectiveDate(String contractEffectiveDate) {
        this.contractEffectiveDate = contractEffectiveDate == null ? null : contractEffectiveDate.trim();
    }

    public String getContractTemplateNo() {
        return contractTemplateNo;
    }

    public void setContractTemplateNo(String contractTemplateNo) {
        this.contractTemplateNo = contractTemplateNo == null ? null : contractTemplateNo.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
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
}