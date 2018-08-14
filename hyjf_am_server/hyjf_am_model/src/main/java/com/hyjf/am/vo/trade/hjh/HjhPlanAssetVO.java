/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.hjh;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author fuqiang
 * @version HjhPlanAssetVO, v0.1 2018/6/11 19:21
 */
public class HjhPlanAssetVO extends BaseVO implements Serializable {
    private Integer id;

    private String assetId;

    private String instCode;

    private Integer assetType;

    private String borrowNid;

    private String planNid;

    private String userName;

    private Integer userId;

    private String mobile;

    private String accountId;

    private String truename;

    private String idcard;

    private Long account;

    private Integer isMonth;

    private Integer borrowPeriod;

    private String borrowStyle;

    private Integer verifyStatus;

    private Integer status;

    private Integer recieveTime;

    private Integer labelId;

    private String labelName;

    private Integer sex;

    private Integer age;

    private Integer marriage;

    private Integer entrustedFlg;

    private Integer entrustedUserId;

    private String entrustedUserName;

    private String entrustedAccountId;

    private String workCity;

    private String position;

    private String domicile;

    private String creditLevel;

    private String useage;

    private String monthlyIncome;

    private String firstPayment;

    private String secondPayment;

    private String costIntrodution;

    private String overdueTimes;

    private String overdueAmount;

    private String litigation;

    private String assetInfo;

    private Integer createUserId;

    private Integer createTime;

    private Integer updateUserId;

    private Integer updateTime;

    private String annualIncome;

    private String overdueReport;

    private String debtSituation;

    private String otherBorrowed;

    private String isFunds;

    private String isManaged;

    private String isAbility;

    private String isOverdue;

    private String isComplaint;

    private String isPunished;

    private static final long serialVersionUID = 1L;

    //企业推送-借款类型
    private String borrowType;

    //借款企业名称
    private String borrowCompanyName;

    //财务状况
    private String financialSituation;

    //法人
    private String legalPerson;

    //注册地区
    private String registrationArea;

    //注册时间
    private String registrationDate;

    //主营业务
    private String mainBusiness;

    //统一社会信用代码
    private String unifiedSocialCreditCode;

    //注册资本
    private String registeredCapital;

    //所属行业
    private String industryInvolved;

    private String registrationAddress;

    private String corporateCode;

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public String getCorporateCode() {
        return corporateCode;
    }

    public void setCorporateCode(String corporateCode) {
        this.corporateCode = corporateCode;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }

    public String getBorrowCompanyName() {
        return borrowCompanyName;
    }

    public void setBorrowCompanyName(String borrowCompanyName) {
        this.borrowCompanyName = borrowCompanyName;
    }

    public String getFinancialSituation() {
        return financialSituation;
    }

    public void setFinancialSituation(String financialSituation) {
        this.financialSituation = financialSituation;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getRegistrationArea() {
        return registrationArea;
    }

    public void setRegistrationArea(String registrationArea) {
        this.registrationArea = registrationArea;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    public String getUnifiedSocialCreditCode() {
        return unifiedSocialCreditCode;
    }

    public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
        this.unifiedSocialCreditCode = unifiedSocialCreditCode;
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getIndustryInvolved() {
        return industryInvolved;
    }

    public void setIndustryInvolved(String industryInvolved) {
        this.industryInvolved = industryInvolved;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId == null ? null : assetId.trim();
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode == null ? null : instCode.trim();
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename == null ? null : truename.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public Integer getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(Integer isMonth) {
        this.isMonth = isMonth;
    }

    public Integer getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(Integer borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle == null ? null : borrowStyle.trim();
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRecieveTime() {
        return recieveTime;
    }

    public void setRecieveTime(Integer recieveTime) {
        this.recieveTime = recieveTime;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName == null ? null : labelName.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getMarriage() {
        return marriage;
    }

    public void setMarriage(Integer marriage) {
        this.marriage = marriage;
    }

    public Integer getEntrustedFlg() {
        return entrustedFlg;
    }

    public void setEntrustedFlg(Integer entrustedFlg) {
        this.entrustedFlg = entrustedFlg;
    }

    public Integer getEntrustedUserId() {
        return entrustedUserId;
    }

    public void setEntrustedUserId(Integer entrustedUserId) {
        this.entrustedUserId = entrustedUserId;
    }

    public String getEntrustedUserName() {
        return entrustedUserName;
    }

    public void setEntrustedUserName(String entrustedUserName) {
        this.entrustedUserName = entrustedUserName == null ? null : entrustedUserName.trim();
    }

    public String getEntrustedAccountId() {
        return entrustedAccountId;
    }

    public void setEntrustedAccountId(String entrustedAccountId) {
        this.entrustedAccountId = entrustedAccountId == null ? null : entrustedAccountId.trim();
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity == null ? null : workCity.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile == null ? null : domicile.trim();
    }

    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel == null ? null : creditLevel.trim();
    }

    public String getUseage() {
        return useage;
    }

    public void setUseage(String useage) {
        this.useage = useage == null ? null : useage.trim();
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome == null ? null : monthlyIncome.trim();
    }

    public String getFirstPayment() {
        return firstPayment;
    }

    public void setFirstPayment(String firstPayment) {
        this.firstPayment = firstPayment == null ? null : firstPayment.trim();
    }

    public String getSecondPayment() {
        return secondPayment;
    }

    public void setSecondPayment(String secondPayment) {
        this.secondPayment = secondPayment == null ? null : secondPayment.trim();
    }

    public String getCostIntrodution() {
        return costIntrodution;
    }

    public void setCostIntrodution(String costIntrodution) {
        this.costIntrodution = costIntrodution == null ? null : costIntrodution.trim();
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

    public String getLitigation() {
        return litigation;
    }

    public void setLitigation(String litigation) {
        this.litigation = litigation == null ? null : litigation.trim();
    }

    public String getAssetInfo() {
        return assetInfo;
    }

    public void setAssetInfo(String assetInfo) {
        this.assetInfo = assetInfo == null ? null : assetInfo.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome == null ? null : annualIncome.trim();
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

    @Override
    public String toString() {
        return "HjhPlanAssetVO{" +
                "id=" + id +
                ", assetId='" + assetId + '\'' +
                ", instCode='" + instCode + '\'' +
                ", assetType=" + assetType +
                ", borrowNid='" + borrowNid + '\'' +
                ", planNid='" + planNid + '\'' +
                ", userName='" + userName + '\'' +
                ", userId=" + userId +
                ", mobile='" + mobile + '\'' +
                ", accountId='" + accountId + '\'' +
                ", truename='" + truename + '\'' +
                ", idcard='" + idcard + '\'' +
                ", account=" + account +
                ", isMonth=" + isMonth +
                ", borrowPeriod=" + borrowPeriod +
                ", borrowStyle='" + borrowStyle + '\'' +
                ", verifyStatus=" + verifyStatus +
                ", status=" + status +
                ", recieveTime=" + recieveTime +
                ", labelId=" + labelId +
                ", labelName='" + labelName + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", marriage=" + marriage +
                ", entrustedFlg=" + entrustedFlg +
                ", entrustedUserId=" + entrustedUserId +
                ", entrustedUserName='" + entrustedUserName + '\'' +
                ", entrustedAccountId='" + entrustedAccountId + '\'' +
                ", workCity='" + workCity + '\'' +
                ", position='" + position + '\'' +
                ", domicile='" + domicile + '\'' +
                ", creditLevel='" + creditLevel + '\'' +
                ", useage='" + useage + '\'' +
                ", monthlyIncome='" + monthlyIncome + '\'' +
                ", firstPayment='" + firstPayment + '\'' +
                ", secondPayment='" + secondPayment + '\'' +
                ", costIntrodution='" + costIntrodution + '\'' +
                ", overdueTimes='" + overdueTimes + '\'' +
                ", overdueAmount='" + overdueAmount + '\'' +
                ", litigation='" + litigation + '\'' +
                ", assetInfo='" + assetInfo + '\'' +
                ", createUserId=" + createUserId +
                ", createTime=" + createTime +
                ", updateUserId=" + updateUserId +
                ", updateTime=" + updateTime +
                ", annualIncome='" + annualIncome + '\'' +
                ", overdueReport='" + overdueReport + '\'' +
                ", debtSituation='" + debtSituation + '\'' +
                ", otherBorrowed='" + otherBorrowed + '\'' +
                ", isFunds='" + isFunds + '\'' +
                ", isManaged='" + isManaged + '\'' +
                ", isAbility='" + isAbility + '\'' +
                ", isOverdue='" + isOverdue + '\'' +
                ", isComplaint='" + isComplaint + '\'' +
                ", isPunished='" + isPunished + '\'' +
                '}';
    }
}
