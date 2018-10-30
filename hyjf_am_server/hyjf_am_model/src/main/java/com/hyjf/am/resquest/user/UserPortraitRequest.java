package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BasePage;

import java.math.BigDecimal;
/**
 * @author nxl
 * @version UserPortraitResponse, v0.1 2018/6/28 14:27
 */
public class UserPortraitRequest extends BasePage {
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

    //    检索条件
    /**
     * 年龄开始
     */
    private Integer ageStart;
    /**
     * 年龄开始
     */
    private Integer ageEnd;
    /**
     * 账户总资产开始
     */
    private BigDecimal bankTotalStart;
    /**
     * 账户总资产结束
     */
    private BigDecimal bankTotalEnd;
    /**
     * 累计收益开始
     */
    private BigDecimal interestSumStart;
    /**
     * 累计收益结束
     */
    private BigDecimal interestSumEnd;
    /**
     * 交易笔数开始
     */
    private Integer tradeNumberStart;
    /**
     * 交易笔数结束
     */
    private Integer tradeNumberEnd;
    /**
     * 注册时间开始
     */
    private String regTimeStart;
    /**
     * 注册时间结束
     */
    private String regTimeEnd;
//    private List<UserPortraitCustomize> recordlist;

    private static final long serialVersionUID = 1L;
    //默认为true ,获取全部数据，为false时，获取部分数据
    public boolean limitFlg = false;
    //查询用
    public String yesterdayEndTime;
    public String yesterdayBeginTime;

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


    public String getYesterdayEndTime() {
        return yesterdayEndTime;
    }

    public void setYesterdayEndTime(String yesterdayEndTime) {
        this.yesterdayEndTime = yesterdayEndTime;
    }

    public String getYesterdayBeginTime() {
        return yesterdayBeginTime;
    }

    public void setYesterdayBeginTime(String yesterdayBeginTime) {
        this.yesterdayBeginTime = yesterdayBeginTime;
    }

    public boolean isLimitFlg() {
        return limitFlg;
    }

    public void setLimitFlg(boolean limitFlg) {
        this.limitFlg = limitFlg;
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

    public Integer getAgeStart() {
        return ageStart;
    }

    public void setAgeStart(Integer ageStart) {
        this.ageStart = ageStart;
    }

    public Integer getAgeEnd() {
        return ageEnd;
    }

    public void setAgeEnd(Integer ageEnd) {
        this.ageEnd = ageEnd;
    }

    public BigDecimal getBankTotalStart() {
        return bankTotalStart;
    }

    public void setBankTotalStart(BigDecimal bankTotalStart) {
        this.bankTotalStart = bankTotalStart;
    }

    public BigDecimal getBankTotalEnd() {
        return bankTotalEnd;
    }

    public void setBankTotalEnd(BigDecimal bankTotalEnd) {
        this.bankTotalEnd = bankTotalEnd;
    }

    public BigDecimal getInterestSumStart() {
        return interestSumStart;
    }

    public void setInterestSumStart(BigDecimal interestSumStart) {
        this.interestSumStart = interestSumStart;
    }

    public BigDecimal getInterestSumEnd() {
        return interestSumEnd;
    }

    public void setInterestSumEnd(BigDecimal interestSumEnd) {
        this.interestSumEnd = interestSumEnd;
    }

    public Integer getTradeNumberStart() {
        return tradeNumberStart;
    }

    public void setTradeNumberStart(Integer tradeNumberStart) {
        this.tradeNumberStart = tradeNumberStart;
    }

    public Integer getTradeNumberEnd() {
        return tradeNumberEnd;
    }

    public void setTradeNumberEnd(Integer tradeNumberEnd) {
        this.tradeNumberEnd = tradeNumberEnd;
    }

    public String getRegTimeStart() {
        return regTimeStart;
    }

    public void setRegTimeStart(String regTimeStart) {
        this.regTimeStart = regTimeStart;
    }

    public String getRegTimeEnd() {
        return regTimeEnd;
    }

    public void setRegTimeEnd(String regTimeEnd) {
        this.regTimeEnd = regTimeEnd;
    }
}