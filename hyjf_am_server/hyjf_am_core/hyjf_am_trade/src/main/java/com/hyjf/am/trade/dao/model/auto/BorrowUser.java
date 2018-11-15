package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class BorrowUser implements Serializable {
    private Integer id;

    /**
     * 标的编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 企业名称
     *
     * @mbggenerated
     */
    private String username;

    private String city;

    private String area;

    /**
     * 注册资本货币单位  元-美元
     *
     * @mbggenerated
     */
    private String currencyName;

    /**
     * 注册资本
     *
     * @mbggenerated
     */
    private String regCaptial;

    /**
     * 行业
     *
     * @mbggenerated
     */
    private String industry;

    /**
     * 涉诉
     *
     * @mbggenerated
     */
    private String litigation;

    /**
     * 征信
     *
     * @mbggenerated
     */
    private String creReport;

    /**
     * 授信额度
     *
     * @mbggenerated
     */
    private Integer credit;

    /**
     * 员工人数
     *
     * @mbggenerated
     */
    private Integer staff;

    /**
     * 企业注册时间
     *
     * @mbggenerated
     */
    private String comRegTime;

    /**
     * 法人
     *
     * @mbggenerated
     */
    private String legalPerson;

    /**
     * 统一社会信用代码
     *
     * @mbggenerated
     */
    private String socialCreditCode;

    /**
     * 注册号
     *
     * @mbggenerated
     */
    private String registCode;

    /**
     * 主营业务
     *
     * @mbggenerated
     */
    private String mainBusiness;

    /**
     * 在平台逾期次数
     *
     * @mbggenerated
     */
    private String overdueTimes;

    /**
     * 在平台逾期金额
     *
     * @mbggenerated
     */
    private String overdueAmount;

    /**
     * 企贷审核信息 企业证件 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isCertificate;

    /**
     * 企贷审核信息 经营状况 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isOperation;

    /**
     * 企贷审核信息 财务状况 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isFinance;

    /**
     * 企贷审核信息 企业信用 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isEnterpriseCreidt;

    /**
     * 企贷审核信息 法人信息 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isLegalPerson;

    /**
     * 企贷审核信息 资产状况 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isAsset;

    /**
     * 企贷审核信息 购销合同 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isPurchaseContract;

    /**
     * 企贷审核信息 供销合同 0未审核 1已审核
     *
     * @mbggenerated
     */
    private String isSupplyContract;

    /**
     * 征信报告逾期情况:暂未提供；无；已处理
     *
     * @mbggenerated
     */
    private String overdueReport;

    /**
     * 重大负债状况:无
     *
     * @mbggenerated
     */
    private String debtSituation;

    /**
     * 其他平台借款情况:无
     *
     * @mbggenerated
     */
    private String otherBorrowed;

    /**
     * 借款资金运用情况：不正常,正常
     *
     * @mbggenerated
     */
    private String isFunds;

    /**
     * 借款人经营状况及财务状况：不正常,正常
     *
     * @mbggenerated
     */
    private String isManaged;

    /**
     * 借款人还款能力变化情况：不正常,正常
     *
     * @mbggenerated
     */
    private String isAbility;

    /**
     * 借款人逾期情况：暂无,有
     *
     * @mbggenerated
     */
    private String isOverdue;

    /**
     * 借款人涉诉情况：暂无,有
     *
     * @mbggenerated
     */
    private String isComplaint;

    /**
     * 借款人受行政处罚情况：暂无,有
     *
     * @mbggenerated
     */
    private String isPunished;

    /**
     * 企业组织机构代码
     *
     * @mbggenerated
     */
    private String corporateCode;

    /**
     * 企业注册地
     *
     * @mbggenerated
     */
    private String registrationAddress;

    /**
     * 其他资料
     *
     * @mbggenerated
     */
    private String otherInfo;

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
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName == null ? null : currencyName.trim();
    }

    public String getRegCaptial() {
        return regCaptial;
    }

    public void setRegCaptial(String regCaptial) {
        this.regCaptial = regCaptial == null ? null : regCaptial.trim();
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public String getLitigation() {
        return litigation;
    }

    public void setLitigation(String litigation) {
        this.litigation = litigation == null ? null : litigation.trim();
    }

    public String getCreReport() {
        return creReport;
    }

    public void setCreReport(String creReport) {
        this.creReport = creReport == null ? null : creReport.trim();
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getStaff() {
        return staff;
    }

    public void setStaff(Integer staff) {
        this.staff = staff;
    }

    public String getComRegTime() {
        return comRegTime;
    }

    public void setComRegTime(String comRegTime) {
        this.comRegTime = comRegTime == null ? null : comRegTime.trim();
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode == null ? null : socialCreditCode.trim();
    }

    public String getRegistCode() {
        return registCode;
    }

    public void setRegistCode(String registCode) {
        this.registCode = registCode == null ? null : registCode.trim();
    }

    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness == null ? null : mainBusiness.trim();
    }

    public String getOverdueTimes() {
        return overdueTimes;
    }

    public void setOverdueTimes(String overdueTimes) {
        this.overdueTimes = overdueTimes == null ? null : overdueTimes.trim();
    }

    public String getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(String overdueAmount) {
        this.overdueAmount = overdueAmount == null ? null : overdueAmount.trim();
    }

    public Integer getIsCertificate() {
        return isCertificate;
    }

    public void setIsCertificate(Integer isCertificate) {
        this.isCertificate = isCertificate;
    }

    public Integer getIsOperation() {
        return isOperation;
    }

    public void setIsOperation(Integer isOperation) {
        this.isOperation = isOperation;
    }

    public Integer getIsFinance() {
        return isFinance;
    }

    public void setIsFinance(Integer isFinance) {
        this.isFinance = isFinance;
    }

    public Integer getIsEnterpriseCreidt() {
        return isEnterpriseCreidt;
    }

    public void setIsEnterpriseCreidt(Integer isEnterpriseCreidt) {
        this.isEnterpriseCreidt = isEnterpriseCreidt;
    }

    public Integer getIsLegalPerson() {
        return isLegalPerson;
    }

    public void setIsLegalPerson(Integer isLegalPerson) {
        this.isLegalPerson = isLegalPerson;
    }

    public Integer getIsAsset() {
        return isAsset;
    }

    public void setIsAsset(Integer isAsset) {
        this.isAsset = isAsset;
    }

    public Integer getIsPurchaseContract() {
        return isPurchaseContract;
    }

    public void setIsPurchaseContract(Integer isPurchaseContract) {
        this.isPurchaseContract = isPurchaseContract;
    }

    public String getIsSupplyContract() {
        return isSupplyContract;
    }

    public void setIsSupplyContract(String isSupplyContract) {
        this.isSupplyContract = isSupplyContract == null ? null : isSupplyContract.trim();
    }

    public String getOverdueReport() {
        return overdueReport;
    }

    public void setOverdueReport(String overdueReport) {
        this.overdueReport = overdueReport == null ? null : overdueReport.trim();
    }

    public String getDebtSituation() {
        return debtSituation;
    }

    public void setDebtSituation(String debtSituation) {
        this.debtSituation = debtSituation == null ? null : debtSituation.trim();
    }

    public String getOtherBorrowed() {
        return otherBorrowed;
    }

    public void setOtherBorrowed(String otherBorrowed) {
        this.otherBorrowed = otherBorrowed == null ? null : otherBorrowed.trim();
    }

    public String getIsFunds() {
        return isFunds;
    }

    public void setIsFunds(String isFunds) {
        this.isFunds = isFunds == null ? null : isFunds.trim();
    }

    public String getIsManaged() {
        return isManaged;
    }

    public void setIsManaged(String isManaged) {
        this.isManaged = isManaged == null ? null : isManaged.trim();
    }

    public String getIsAbility() {
        return isAbility;
    }

    public void setIsAbility(String isAbility) {
        this.isAbility = isAbility == null ? null : isAbility.trim();
    }

    public String getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(String isOverdue) {
        this.isOverdue = isOverdue == null ? null : isOverdue.trim();
    }

    public String getIsComplaint() {
        return isComplaint;
    }

    public void setIsComplaint(String isComplaint) {
        this.isComplaint = isComplaint == null ? null : isComplaint.trim();
    }

    public String getIsPunished() {
        return isPunished;
    }

    public void setIsPunished(String isPunished) {
        this.isPunished = isPunished == null ? null : isPunished.trim();
    }

    public String getCorporateCode() {
        return corporateCode;
    }

    public void setCorporateCode(String corporateCode) {
        this.corporateCode = corporateCode == null ? null : corporateCode.trim();
    }

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress == null ? null : registrationAddress.trim();
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo == null ? null : otherInfo.trim();
    }
}