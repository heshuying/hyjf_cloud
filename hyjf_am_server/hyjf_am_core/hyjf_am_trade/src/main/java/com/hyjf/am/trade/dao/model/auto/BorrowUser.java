package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class BorrowUser implements Serializable {
    private Integer id;

    private String borrowNid;

    private String username;

    private String area;

    private String currencyName;

    private String regCaptial;

    private String industry;

    private String litigation;

    private String creReport;

    private Integer credit;

    private Integer staff;

    private String comRegTime;

    private String legalPerson;

    private String socialCreditCode;

    private String registCode;

    private String mainBusiness;

    private String overdueTimes;

    private String overdueAmount;

    private Integer isCertificate;

    private Integer isOperation;

    private Integer isFinance;

    private Integer isEnterpriseCreidt;

    private Integer isLegalPerson;

    private Integer isAsset;

    private Integer isPurchaseContract;

    private String isSupplyContract;

    private String overdueReport;

    private String debtSituation;

    private String otherBorrowed;

    private String isFunds;

    private String isManaged;

    private String isAbility;

    private String isOverdue;

    private String isComplaint;

    private String isPunished;

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

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo == null ? null : otherInfo.trim();
    }
}