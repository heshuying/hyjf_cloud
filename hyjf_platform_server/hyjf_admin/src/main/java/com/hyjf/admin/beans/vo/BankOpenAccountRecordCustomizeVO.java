/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author NXL
 * @version AccountRecordVO, v0.1 2018/6/23 17:17
 */
public class BankOpenAccountRecordCustomizeVO extends BaseVO implements Serializable {
    // 用戶id
    @ApiModelProperty(value = "用戶id")
    public String userId;
    // 用户名
    @ApiModelProperty(value = "用户名")
    public String userName;
    //用户手机号
    @ApiModelProperty(value = "用户手机号")
    private String mobile;
    /** 用户真实姓名 */
    @ApiModelProperty(value = "用户真实姓名")
    public String realName;
    /** 用户身份证号码 */
    @ApiModelProperty(value = "用户身份证号码")
    public String idCard;
    // 用户属性
    @ApiModelProperty(value = "用户属性")
    public String userProperty;
    // 汇付帐号
    @ApiModelProperty(value = "汇付帐号")
    public String account;
    // 汇付客户号
    @ApiModelProperty(value = "汇付客户号")
    public String customerAccount;
    // 开户状态
    @ApiModelProperty(value = "开户状态")
    public String accountStatus;
    // 开户状态
    @ApiModelProperty(value = "开户状态")
    public String accountStatusName;
    // 开户平台
    @ApiModelProperty(value = "开户平台")
    public String openAccountPlat;
    // 开户时间
    @ApiModelProperty(value = "开户时间")
    public String openTime;
    // 性别
    @ApiModelProperty(value = "性别")
    public String sex;
    // 生日
    @ApiModelProperty(value = "生日")
    public String birthday;
    // 注册所在地
    @ApiModelProperty(value = "注册所在地")
    public String registArea;
    // 身份证号
    @ApiModelProperty(value = "身份证号")
    public String idcard;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserProperty() {
        return userProperty;
    }

    public void setUserProperty(String userProperty) {
        this.userProperty = userProperty;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(String customerAccount) {
        this.customerAccount = customerAccount;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountStatusName() {
        return accountStatusName;
    }

    public void setAccountStatusName(String accountStatusName) {
        this.accountStatusName = accountStatusName;
    }

    public String getOpenAccountPlat() {
        return openAccountPlat;
    }

    public void setOpenAccountPlat(String openAccountPlat) {
        this.openAccountPlat = openAccountPlat;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRegistArea() {
        return registArea;
    }

    public void setRegistArea(String registArea) {
        this.registArea = registArea;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
}
