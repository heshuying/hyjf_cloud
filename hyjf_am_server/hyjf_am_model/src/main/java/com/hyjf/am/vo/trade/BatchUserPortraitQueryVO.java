/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: sunpeikai
 * @version: BatchUserPortraitQueryVO, v0.1 2018/6/28 11:25
 * UserPortrait
 */
public class BatchUserPortraitQueryVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer userId;

    private String userName;

    private Integer age;

    private String sex;

    private String education;

    private String occupation;

    private String city;

    private String interest;

    private BigDecimal interestSum;

    private BigDecimal investSum;

    private BigDecimal rechargeSum;

    private BigDecimal withdrawSum;

    private String loginActive;

    private String customerSource;

    private Integer lastLoginTime;

    private Integer lastRechargeTime;

    private Integer lastWithdrawTime;

    private Integer investPlatform;

    private Integer investAge;

    private Integer tradeNumber;

    private String currentOwner;

    private String addWechat;

    private String investProcess;

    private String customerComplaint;

    private Integer inviteCustomer;

    private String remark;

    private String mobile;

    private BigDecimal bankTotal;

    private BigDecimal fundRetention;

    private Integer lastRepayTime;

    private Integer inviteRegist;

    private Integer inviteRecharge;

    private Integer inviteTender;

    private BigDecimal yield;

    private Integer attribute;

    private BigDecimal bankBalance;

    private BigDecimal accountAwait;

    private BigDecimal bankFrost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation == null ? null : occupation.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest == null ? null : interest.trim();
    }

    public BigDecimal getInterestSum() {
        return interestSum;
    }

    public void setInterestSum(BigDecimal interestSum) {
        this.interestSum = interestSum;
    }

    public BigDecimal getInvestSum() {
        return investSum;
    }

    public void setInvestSum(BigDecimal investSum) {
        this.investSum = investSum;
    }

    public BigDecimal getRechargeSum() {
        return rechargeSum;
    }

    public void setRechargeSum(BigDecimal rechargeSum) {
        this.rechargeSum = rechargeSum;
    }

    public BigDecimal getWithdrawSum() {
        return withdrawSum;
    }

    public void setWithdrawSum(BigDecimal withdrawSum) {
        this.withdrawSum = withdrawSum;
    }

    public String getLoginActive() {
        return loginActive;
    }

    public void setLoginActive(String loginActive) {
        this.loginActive = loginActive == null ? null : loginActive.trim();
    }

    public String getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(String customerSource) {
        this.customerSource = customerSource == null ? null : customerSource.trim();
    }

    public Integer getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Integer lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getLastRechargeTime() {
        return lastRechargeTime;
    }

    public void setLastRechargeTime(Integer lastRechargeTime) {
        this.lastRechargeTime = lastRechargeTime;
    }

    public Integer getLastWithdrawTime() {
        return lastWithdrawTime;
    }

    public void setLastWithdrawTime(Integer lastWithdrawTime) {
        this.lastWithdrawTime = lastWithdrawTime;
    }

    public Integer getInvestPlatform() {
        return investPlatform;
    }

    public void setInvestPlatform(Integer investPlatform) {
        this.investPlatform = investPlatform;
    }

    public Integer getInvestAge() {
        return investAge;
    }

    public void setInvestAge(Integer investAge) {
        this.investAge = investAge;
    }

    public Integer getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(Integer tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public String getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner == null ? null : currentOwner.trim();
    }

    public String getAddWechat() {
        return addWechat;
    }

    public void setAddWechat(String addWechat) {
        this.addWechat = addWechat == null ? null : addWechat.trim();
    }

    public String getInvestProcess() {
        return investProcess;
    }

    public void setInvestProcess(String investProcess) {
        this.investProcess = investProcess == null ? null : investProcess.trim();
    }

    public String getCustomerComplaint() {
        return customerComplaint;
    }

    public void setCustomerComplaint(String customerComplaint) {
        this.customerComplaint = customerComplaint == null ? null : customerComplaint.trim();
    }

    public Integer getInviteCustomer() {
        return inviteCustomer;
    }

    public void setInviteCustomer(Integer inviteCustomer) {
        this.inviteCustomer = inviteCustomer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public BigDecimal getBankTotal() {
        return bankTotal;
    }

    public void setBankTotal(BigDecimal bankTotal) {
        this.bankTotal = bankTotal;
    }

    public BigDecimal getFundRetention() {
        return fundRetention;
    }

    public void setFundRetention(BigDecimal fundRetention) {
        this.fundRetention = fundRetention;
    }

    public Integer getLastRepayTime() {
        return lastRepayTime;
    }

    public void setLastRepayTime(Integer lastRepayTime) {
        this.lastRepayTime = lastRepayTime;
    }

    public Integer getInviteRegist() {
        return inviteRegist;
    }

    public void setInviteRegist(Integer inviteRegist) {
        this.inviteRegist = inviteRegist;
    }

    public Integer getInviteRecharge() {
        return inviteRecharge;
    }

    public void setInviteRecharge(Integer inviteRecharge) {
        this.inviteRecharge = inviteRecharge;
    }

    public Integer getInviteTender() {
        return inviteTender;
    }

    public void setInviteTender(Integer inviteTender) {
        this.inviteTender = inviteTender;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public BigDecimal getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(BigDecimal bankBalance) {
        this.bankBalance = bankBalance;
    }

    public BigDecimal getAccountAwait() {
        return accountAwait;
    }

    public void setAccountAwait(BigDecimal accountAwait) {
        this.accountAwait = accountAwait;
    }

    public BigDecimal getBankFrost() {
        return bankFrost;
    }

    public void setBankFrost(BigDecimal bankFrost) {
        this.bankFrost = bankFrost;
    }
    @Override
    public String toString() {
        return "BatchUserPortraitQueryVO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", education='" + education + '\'' +
                ", occupation='" + occupation + '\'' +
                ", city='" + city + '\'' +
                ", interest='" + interest + '\'' +
                ", interestSum=" + interestSum +
                ", investSum=" + investSum +
                ", rechargeSum=" + rechargeSum +
                ", withdrawSum=" + withdrawSum +
                ", loginActive='" + loginActive + '\'' +
                ", customerSource='" + customerSource + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", lastRechargeTime=" + lastRechargeTime +
                ", lastWithdrawTime=" + lastWithdrawTime +
                ", investPlatform=" + investPlatform +
                ", investAge=" + investAge +
                ", tradeNumber=" + tradeNumber +
                ", currentOwner='" + currentOwner + '\'' +
                ", addWechat='" + addWechat + '\'' +
                ", investProcess='" + investProcess + '\'' +
                ", customerComplaint='" + customerComplaint + '\'' +
                ", inviteCustomer=" + inviteCustomer +
                ", remark='" + remark + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
