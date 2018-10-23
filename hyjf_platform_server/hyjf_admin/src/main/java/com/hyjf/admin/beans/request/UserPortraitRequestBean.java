package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author nxl
 * @version UserPortraitResponse, v0.1 2018/6/28 14:27
 */
public class UserPortraitRequestBean extends BaseRequest implements Serializable {
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "学历")
    private String education;
    @ApiModelProperty(value = "职业")
    private String occupation;
    @ApiModelProperty(value = "爱好")
    private String interest;
    @ApiModelProperty(value = "登陆活跃")
    private String loginActive;
    @ApiModelProperty(value = "客户来源")
    private String customerSource;
    @ApiModelProperty(value = "同时投资平台数")
    private Integer investPlatform;
    @ApiModelProperty(value = "投龄")
    private Integer investAge;
    @ApiModelProperty(value = "当前拥有人")
    private String currentOwner;
    @ApiModelProperty(value = "是否加微信：0否,1是")
    private String addWechat;
    @ApiModelProperty(value = "投资进程")
    private String investProcess;
    @ApiModelProperty(value = "客户投诉")
    private String customerComplaint;
    @ApiModelProperty(value = "邀约客户数")
    private Integer inviteCustomer;
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 年龄开始
     */
    @ApiModelProperty(value = "年龄开始")
    private Integer ageStart;
    /**
     * 年龄开始
     */
    @ApiModelProperty(value = "年龄结束")
    private Integer ageEnd;
    /**
     * 账户总资产开始
     */
    @ApiModelProperty(value = "账户总资产开始")
    private BigDecimal bankTotalStart;
    /**
     * 账户总资产结束
     */
    @ApiModelProperty(value = "账户总资产结束")
    private BigDecimal bankTotalEnd;
    /**
     * 累计收益开始
     */
    @ApiModelProperty(value = "累计收益开始")
    private BigDecimal interestSumStart;
    /**
     * 累计收益结束
     */
    @ApiModelProperty(value = "累计收益结束")
    private BigDecimal interestSumEnd;
    /**
     * 交易笔数开始
     */
    @ApiModelProperty(value = "易笔数开始")
    private Integer tradeNumberStart;
    /**
     * 交易笔数结束
     */
    @ApiModelProperty(value = "交易笔数结束")
    private Integer tradeNumberEnd;
    /**
     * 是否有主单
     */
    @ApiModelProperty(value = "是否有主单")
    private Integer attribute;
    /**
     * 注册时间开始
     */
    @ApiModelProperty(value = "注册时间开始")
    private String regTimeStart;
    /**
     * 注册时间结束
     */
    @ApiModelProperty(value = "注册时间结束")
    private String regTimeEnd;
//    private List<UserPortraitCustomize> recordlist;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getLoginActive() {
        return loginActive;
    }

    public void setLoginActive(String loginActive) {
        this.loginActive = loginActive;
    }

    public String getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(String customerSource) {
        this.customerSource = customerSource;
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

    public String getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner;
    }

    public String getAddWechat() {
        return addWechat;
    }

    public void setAddWechat(String addWechat) {
        this.addWechat = addWechat;
    }

    public String getInvestProcess() {
        return investProcess;
    }

    public void setInvestProcess(String investProcess) {
        this.investProcess = investProcess;
    }

    public String getCustomerComplaint() {
        return customerComplaint;
    }

    public void setCustomerComplaint(String customerComplaint) {
        this.customerComplaint = customerComplaint;
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
        this.remark = remark;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}