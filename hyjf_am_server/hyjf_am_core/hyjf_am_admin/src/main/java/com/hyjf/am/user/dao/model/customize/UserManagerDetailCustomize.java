/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.model.customize;

/**
 * @author nxl
 * @version UserManagerCustomize, v0.1 2018/6/21 14:59
 */
public class UserManagerDetailCustomize {
    /** 用户id */
    private String userId;
    /** 用户名 */
    public String userName;
    /** 用户昵称 */
    public String userNickName;
    /**用户属性*/
    public String userProperty;
    /**用户类型*/
    public String userType;
    /**用户角色*/
    public String role;
    /**推荐人*/
    public String recommendName;
    /**推荐人部门名称*/
    public String departName;
    /**注册渠道名称 第三方平台名称*/
    public String registPidName;
    /**注册平台 包括pc android ios 微信*/
    public String registPlat;
    /**开户平台 包括pc android ios 微信 */
    public String openAccountPlat;
    /** 注册IP */
    public String registIP;
    /** 注册时间 */
    public String registTime;
    /** 开户时间时间 */
    public String openAccountTime;
    /** 最后登陆时间 */
    public String lastLoginTime;
    /** 最后登陆IP */
    public String lastLoginIP;
    /** 紧急联络人关系 */
    public String emRealtion;
    /** 紧急联络人 姓名*/
    public String emName;
    /** 紧急联络人电话*/
    public String emPhone;
    /** 二维码*/
    public String QRCode;
    /** 邀请码*/
    public String inviteCode;
    /** 邀请链接*/
    public String inviteURL;
    /** 用户邮箱 */
    public String email;
    /** 邮箱认证状态 */
    public String emailStatus;
    /** 手机号码 */
    public String mobile;
    /** 银行预留手机号 */
    private String bankMobile;
    /** 手机号码认证状态 */
    public String mobileStatus;
    /** 用户真实姓名 */
    public String realName;
    /** 实名认证状态 */
    public String realNameStatus;
    /** 账户号 */
    public String account;
    /** 客户号 */
    public String customerAccount;
    /** 用户账号状态 */
    public String accountStatus;
    /** 用户身份证号码*/
    public String idCard;
    /** 头像地址*/
    public String iconUrl;
    /** vipid*/
    public Integer vipId;
    /** vip过期日期*/
    public String vipExpDate;
    /** v值*/
    public Integer vipValue;
    /** 借款人类型*/
    public String borrowerType;
    /** CA申请状态*/
    public Integer isCaFlag;
    private Integer isSetPassword;

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

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserProperty() {
        return userProperty;
    }

    public void setUserProperty(String userProperty) {
        this.userProperty = userProperty;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getRegistPidName() {
        return registPidName;
    }

    public void setRegistPidName(String registPidName) {
        this.registPidName = registPidName;
    }

    public String getRegistPlat() {
        return registPlat;
    }

    public void setRegistPlat(String registPlat) {
        this.registPlat = registPlat;
    }

    public String getOpenAccountPlat() {
        return openAccountPlat;
    }

    public void setOpenAccountPlat(String openAccountPlat) {
        this.openAccountPlat = openAccountPlat;
    }

    public String getRegistIP() {
        return registIP;
    }

    public void setRegistIP(String registIP) {
        this.registIP = registIP;
    }

    public String getRegistTime() {
        return registTime;
    }

    public void setRegistTime(String registTime) {
        this.registTime = registTime;
    }

    public String getOpenAccountTime() {
        return openAccountTime;
    }

    public void setOpenAccountTime(String openAccountTime) {
        this.openAccountTime = openAccountTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public String getEmRealtion() {
        return emRealtion;
    }

    public void setEmRealtion(String emRealtion) {
        this.emRealtion = emRealtion;
    }

    public String getEmName() {
        return emName;
    }

    public void setEmName(String emName) {
        this.emName = emName;
    }

    public String getEmPhone() {
        return emPhone;
    }

    public void setEmPhone(String emPhone) {
        this.emPhone = emPhone;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteURL() {
        return inviteURL;
    }

    public void setInviteURL(String inviteURL) {
        this.inviteURL = inviteURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileStatus() {
        return mobileStatus;
    }

    public void setMobileStatus(String mobileStatus) {
        this.mobileStatus = mobileStatus;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealNameStatus() {
        return realNameStatus;
    }

    public void setRealNameStatus(String realNameStatus) {
        this.realNameStatus = realNameStatus;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public String getVipExpDate() {
        return vipExpDate;
    }

    public void setVipExpDate(String vipExpDate) {
        this.vipExpDate = vipExpDate;
    }

    public Integer getVipValue() {
        return vipValue;
    }

    public void setVipValue(Integer vipValue) {
        this.vipValue = vipValue;
    }

    public String getBorrowerType() {
        return borrowerType;
    }

    public void setBorrowerType(String borrowerType) {
        this.borrowerType = borrowerType;
    }

    public Integer getIsCaFlag() {
        return isCaFlag;
    }

    public void setIsCaFlag(Integer isCaFlag) {
        this.isCaFlag = isCaFlag;
    }

    public Integer getIsSetPassword() {
        return isSetPassword;
    }

    public void setIsSetPassword(Integer isSetPassword) {
        this.isSetPassword = isSetPassword;
    }

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }
}
