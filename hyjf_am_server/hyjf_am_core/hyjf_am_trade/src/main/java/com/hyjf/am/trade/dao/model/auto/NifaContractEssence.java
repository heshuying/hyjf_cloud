package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class NifaContractEssence implements Serializable {
    private Integer id;

    /**
     * 统一社会信用代码
     *
     * @mbggenerated
     */
    private String platformNo;

    /**
     * 从业机构名称
     *
     * @mbggenerated
     */
    private String platformName;

    /**
     * 项目编号
     *
     * @mbggenerated
     */
    private String projectNo;

    /**
     * 合同名称
     *
     * @mbggenerated
     */
    private String contractName;

    /**
     * 合同编号
     *
     * @mbggenerated
     */
    private String contractNo;

    /**
     * 合同签署方
     *
     * @mbggenerated
     */
    private String contractSigner;

    /**
     * 合同签署日 格式为：YYYY-MM-DD
     *
     * @mbggenerated
     */
    private String contractTime;

    /**
     * 借款人类型 01-自然人 02-机构
     *
     * @mbggenerated
     */
    private Integer borrowerType;

    /**
     * 借款人证件类型 0-身份证
     *
     * @mbggenerated
     */
    private String borrowerCertType;

    /**
     * 借款人证件号码 当借款人类型为01-自然人时，填写该字段
     *
     * @mbggenerated
     */
    private String borrowerCertNo;

    /**
     * 借款人姓名 当借款人类型为01-自然人时，填写该字段
     *
     * @mbggenerated
     */
    private String borrowerName;

    /**
     * 借款人地址 当借款人类型为01-自然人时，填写该字段
     *
     * @mbggenerated
     */
    private String borrowerAddress;

    /**
     * 借款人统一社会信用代码 当借款人类型为02-机构时，填写该字段
     *
     * @mbggenerated
     */
    private String borrowerNacaoNo;

    /**
     * 借款人组织机构代码 当借款人类型为02-机构时，填写该字段
     *
     * @mbggenerated
     */
    private String borrowerOrgcodeNo;

    /**
     * 借款人名称 当借款人类型为02-机构时，填写该字段
     *
     * @mbggenerated
     */
    private String borrowerCompany;

    /**
     * 出借人类型 01-自然人 02-机构
     *
     * @mbggenerated
     */
    private Integer investorType;

    /**
     * 出借人证件类型 0-身份证
     *
     * @mbggenerated
     */
    private String investorCertType;

    /**
     * 出借人证件号码
     *
     * @mbggenerated
     */
    private String investorCertNo;

    /**
     * 出借人姓名
     *
     * @mbggenerated
     */
    private String investorName;

    /**
     * 出借人统一社会信用代码
     *
     * @mbggenerated
     */
    private String investorNacaoNo;

    /**
     * 出借人组织机构代码
     *
     * @mbggenerated
     */
    private String investorOrgcodeNo;

    /**
     * 出借人名称
     *
     * @mbggenerated
     */
    private String investorCompany;

    /**
     * 借款金额
     *
     * @mbggenerated
     */
    private String investAmount;

    /**
     * 年化利率
     *
     * @mbggenerated
     */
    private String borrowRate;

    /**
     * 借款用途
     *
     * @mbggenerated
     */
    private String borrowUse;

    /**
     * 借款用途限制
     *
     * @mbggenerated
     */
    private String borrowUseLimit;

    /**
     * 借款放款日
     *
     * @mbggenerated
     */
    private String loanDate;

    /**
     * 借款放款日判断依据
     *
     * @mbggenerated
     */
    private String loanDateBasis;

    /**
     * 起息日
     *
     * @mbggenerated
     */
    private String startDate;

    /**
     * 到期日
     *
     * @mbggenerated
     */
    private String expiryDate;

    /**
     * 还款方式
     *
     * @mbggenerated
     */
    private Integer repayType;

    /**
     * 还款方式含义及计算公式
     *
     * @mbggenerated
     */
    private String repayFormula;

    /**
     * 还款规则
     *
     * @mbggenerated
     */
    private String repayDateRule;

    /**
     * 还款期数
     *
     * @mbggenerated
     */
    private Integer repayNum;

    /**
     * 还款计划
     *
     * @mbggenerated
     */
    private String repayPlan;

    /**
     * 逾期还款定义
     *
     * @mbggenerated
     */
    private String overdueRepayDef;

    /**
     * 逾期还款责任
     *
     * @mbggenerated
     */
    private String overdueRepayResp;

    /**
     * 逾期还款流程
     *
     * @mbggenerated
     */
    private String overdueRepayProc;

    /**
     * 通知与送达
     *
     * @mbggenerated
     */
    private String noticeAddress;

    /**
     * 合同生效日
     *
     * @mbggenerated
     */
    private String contractEffectiveDate;

    /**
     * 合同模板编号
     *
     * @mbggenerated
     */
    private String contractTemplateNo;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 最后修改时间
     *
     * @mbggenerated
     */
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