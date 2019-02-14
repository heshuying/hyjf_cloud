/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.hjh;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fuqiang
 * @version HjhPlanAssetVO, v0.1 2018/6/11 19:21
 */
public class HjhPlanAssetVO extends BaseVO implements Serializable {
    private Integer id;

    /**
     * 资产编号
     *
     * @mbggenerated
     */
    private String assetId;

    /**
     * 机构编号
     *
     * @mbggenerated
     */
    private String instCode;

    /**
     * 机构产品类型
     *
     * @mbggenerated
     */
    private Integer assetType;

    /**
     * 项目编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 计划编号
     *
     * @mbggenerated
     */
    private String planNid;

    /**
     * 借款用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 借款用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 江西银行电子账号
     *
     * @mbggenerated
     */
    private String accountId;

    /**
     * 真实姓名
     *
     * @mbggenerated
     */
    private String truename;

    /**
     * 身份证号
     *
     * @mbggenerated
     */
    private String idcard;

    /**
     * 借款金额
     *
     * @mbggenerated
     */
    private Long account;

    /**
     * 默认0 天标，1 月标
     *
     * @mbggenerated
     */
    private Integer isMonth;

    /**
     * 借款期限
     *
     * @mbggenerated
     */
    private Integer borrowPeriod;

    /**
     * 还款方式
     *
     * @mbggenerated
     */
    private String borrowStyle;

    /**
     * 审核状态
     *
     * @mbggenerated
     */
    private Integer verifyStatus;

    /**
     * 项目状态
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 推送时间
     *
     * @mbggenerated
     */
    private Integer recieveTime;

    /**
     * 标签ID
     *
     * @mbggenerated
     */
    private Integer labelId;

    /**
     * 标签名字
     *
     * @mbggenerated
     */
    private String labelName;

    /**
     * 性别
     *
     * @mbggenerated
     */
    private Integer sex;

    /**
     * 年龄
     *
     * @mbggenerated
     */
    private Integer age;

    /**
     * 婚姻状况
     *
     * @mbggenerated
     */
    private Integer marriage;

    /**
     * 受托支付标志
     *
     * @mbggenerated
     */
    private Integer entrustedFlg;

    /**
     * 受托支付用户ID
     *
     * @mbggenerated
     */
    private Integer entrustedUserId;

    /**
     * 受托支付用户名
     *
     * @mbggenerated
     */
    private String entrustedUserName;

    /**
     * 受托支付电子账号
     *
     * @mbggenerated
     */
    private String entrustedAccountId;

    /**
     * 工作城市
     *
     * @mbggenerated
     */
    private String workCity;

    /**
     * 岗位职业
     *
     * @mbggenerated
     */
    private String position;

    /**
     * 户籍地
     *
     * @mbggenerated
     */
    private String domicile;

    /**
     * 信用评级
     *
     * @mbggenerated
     */
    private String creditLevel;

    /**
     * 借款用途
     *
     * @mbggenerated
     */
    private String useage;

    /**
     * 月薪收入
     *
     * @mbggenerated
     */
    private String monthlyIncome;

    /**
     * 第一还款来源
     *
     * @mbggenerated
     */
    private String firstPayment;

    /**
     * 第二还款来源
     *
     * @mbggenerated
     */
    private String secondPayment;

    /**
     * 费用说明
     *
     * @mbggenerated
     */
    private String costIntrodution;

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
     * 涉诉情况
     *
     * @mbggenerated
     */
    private String litigation;

    /**
     * 项目信息
     *
     * @mbggenerated
     */
    private String assetInfo;

    /**
     * 个人年收入:10万以内；10万以上
     *
     * @mbggenerated
     */
    private String annualIncome;

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
     * 创建人id
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 更新人id
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
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 借款人地址
     *
     * @mbggenerated
     */
    private String address;

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
     * 借款类型 0:个人;1:企业
     *
     * @mbggenerated
     */
    private Integer borrowType;

    /**
     * 借款企业名称
     *
     * @mbggenerated
     */
    private String borrowCompanyName;

    /**
     * 财务状况
     *
     * @mbggenerated
     */
    private String financialSituation;

    /**
     * 法人
     *
     * @mbggenerated
     */
    private String legalPerson;

    /**
     * 注册地区
     *
     * @mbggenerated
     */
    private String registrationArea;

    /**
     * 注册时间
     *
     * @mbggenerated
     */
    private String registrationDate;

    /**
     * 主营业务
     *
     * @mbggenerated
     */
    private String mainBusiness;

    /**
     * 统一社会信用代码
     *
     * @mbggenerated
     */
    private String unifiedSocialCreditCode;

    /**
     * 注册资本
     *
     * @mbggenerated
     */
    private String registeredCapital;

    /**
     * 所属行业
     *
     * @mbggenerated
     */
    private String industryInvolved;

    /**
     * 资产属性 1:抵押标 2:质押标 3:信用标 4:债权转让标 5:净值标
     *
     * @mbggenerated
     */
    private Integer assetAttributes;

    private static final long serialVersionUID = 1L;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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

    public Integer getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(Integer borrowType) {
        this.borrowType = borrowType;
    }

    public String getBorrowCompanyName() {
        return borrowCompanyName;
    }

    public void setBorrowCompanyName(String borrowCompanyName) {
        this.borrowCompanyName = borrowCompanyName == null ? null : borrowCompanyName.trim();
    }

    public String getFinancialSituation() {
        return financialSituation;
    }

    public void setFinancialSituation(String financialSituation) {
        this.financialSituation = financialSituation == null ? null : financialSituation.trim();
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    public String getRegistrationArea() {
        return registrationArea;
    }

    public void setRegistrationArea(String registrationArea) {
        this.registrationArea = registrationArea == null ? null : registrationArea.trim();
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate == null ? null : registrationDate.trim();
    }

    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness == null ? null : mainBusiness.trim();
    }

    public String getUnifiedSocialCreditCode() {
        return unifiedSocialCreditCode;
    }

    public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
        this.unifiedSocialCreditCode = unifiedSocialCreditCode == null ? null : unifiedSocialCreditCode.trim();
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital == null ? null : registeredCapital.trim();
    }

    public String getIndustryInvolved() {
        return industryInvolved;
    }

    public void setIndustryInvolved(String industryInvolved) {
        this.industryInvolved = industryInvolved == null ? null : industryInvolved.trim();
    }

    public Integer getAssetAttributes() {
        return assetAttributes;
    }

    public void setAssetAttributes(Integer assetAttributes) {
        this.assetAttributes = assetAttributes;
    }
}
