package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserPortrait implements Serializable {
    private Integer id;

    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 年龄
     *
     * @mbggenerated
     */
    private Integer age;

    /**
     * 性别：男,女
     *
     * @mbggenerated
     */
    private String sex;

    /**
     * 学历
     *
     * @mbggenerated
     */
    private String education;

    /**
     * 职业
     *
     * @mbggenerated
     */
    private String occupation;

    /**
     * 城市
     *
     * @mbggenerated
     */
    private String city;

    /**
     * 爱好
     *
     * @mbggenerated
     */
    private String interest;

    /**
     * 累计收益
     *
     * @mbggenerated
     */
    private BigDecimal interestSum;

    /**
     * 累计年化投资金额
     *
     * @mbggenerated
     */
    private BigDecimal investSum;

    /**
     * 累计充值金额
     *
     * @mbggenerated
     */
    private BigDecimal rechargeSum;

    /**
     * 累计提取金额
     *
     * @mbggenerated
     */
    private BigDecimal withdrawSum;

    /**
     * 登陆活跃
     *
     * @mbggenerated
     */
    private String loginActive;

    /**
     * 客户来源
     *
     * @mbggenerated
     */
    private String customerSource;

    /**
     * 最后一次登陆时间
     *
     * @mbggenerated
     */
    private Integer lastLoginTime;

    /**
     * 最后一次充值时间
     *
     * @mbggenerated
     */
    private Integer lastRechargeTime;

    /**
     * 最后一次提现时间
     *
     * @mbggenerated
     */
    private Integer lastWithdrawTime;

    /**
     * 同时投资平台数
     *
     * @mbggenerated
     */
    private Integer investPlatform;

    /**
     * 投龄
     *
     * @mbggenerated
     */
    private Integer investAge;

    /**
     * 交易笔数
     *
     * @mbggenerated
     */
    private Integer tradeNumber;

    /**
     * 当前拥有人
     *
     * @mbggenerated
     */
    private String currentOwner;

    /**
     * 是否加微信：否,是
     *
     * @mbggenerated
     */
    private String addWechat;

    /**
     * 投资进程
     *
     * @mbggenerated
     */
    private String investProcess;

    /**
     * 客户投诉
     *
     * @mbggenerated
     */
    private String customerComplaint;

    /**
     * 邀约客户数
     *
     * @mbggenerated
     */
    private Integer inviteCustomer;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 江西银行总资产
     *
     * @mbggenerated
     */
    private BigDecimal bankTotal;

    /**
     * 资金存留比
     *
     * @mbggenerated
     */
    private BigDecimal fundRetention;

    /**
     * 最后一笔回款时间
     *
     * @mbggenerated
     */
    private Integer lastRepayTime;

    /**
     * 邀约注册客户数
     *
     * @mbggenerated
     */
    private Integer inviteRegist;

    /**
     * 邀约充值客户数
     *
     * @mbggenerated
     */
    private Integer inviteRecharge;

    /**
     * 邀约投资客户数
     *
     * @mbggenerated
     */
    private Integer inviteTender;

    /**
     * 客均收益率
     *
     * @mbggenerated
     */
    private BigDecimal yield;

    /**
     * 是否有主单
     *
     * @mbggenerated
     */
    private Integer attribute;

    /**
     * 江西银行可用余额
     *
     * @mbggenerated
     */
    private BigDecimal bankBalance;

    /**
     * 银行账户待还金额
     *
     * @mbggenerated
     */
    private BigDecimal accountAwait;

    /**
     * 银行冻结金额
     *
     * @mbggenerated
     */
    private BigDecimal bankFrost;

    private static final long serialVersionUID = 1L;

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
}