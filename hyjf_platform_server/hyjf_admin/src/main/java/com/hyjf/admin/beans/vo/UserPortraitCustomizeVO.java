package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author nxl
 * @version UserPortraitResponse, v0.1 2018/6/28 14:27
 */
public class UserPortraitCustomizeVO implements Serializable {
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "性别")
    private String sex;
    @ApiModelProperty(value = "学历")
    private String education;
    @ApiModelProperty(value = "职业")
    private String occupation;
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "爱好")
    private String interest;
    @ApiModelProperty(value = "累计收益")
    private BigDecimal interestSum;
    @ApiModelProperty(value = "累计年化投资金额")
    private BigDecimal investSum;
    @ApiModelProperty(value = "累计充值金额")
    private BigDecimal rechargeSum;
    @ApiModelProperty(value = "累计提取金额")
    private BigDecimal withdrawSum;
    @ApiModelProperty(value = "登陆活跃")
    private String loginActive;
    @ApiModelProperty(value = "客户来源")
    private String customerSource;
    @ApiModelProperty(value = "最后一次登陆时间")
    private Integer lastLoginTime;
    @ApiModelProperty(value = "最后一次充值时间")
    private Integer lastRechargeTime;
    @ApiModelProperty(value = "最后一次提现时间")
    private Integer lastWithdrawTime;
    @ApiModelProperty(value = "同时投资平台数")
    private Integer investPlatform;
    @ApiModelProperty(value = "投龄")
    private Integer investAge;
    @ApiModelProperty(value = "交易笔数")
    private Integer tradeNumber;
    @ApiModelProperty(value = "当前拥有人")
    private String currentOwner;
    @ApiModelProperty(value = "是否加微信")
    private String addWechat;
    @ApiModelProperty(value = "投资进程")
    private String investProcess;
    @ApiModelProperty(value = "客户投诉")
    private String customerComplaint;
    @ApiModelProperty(value = "邀约客户数")
    private Integer inviteCustomer;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "手机号")
    private String mobile;

    private static final long serialVersionUID = 1L;

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
}