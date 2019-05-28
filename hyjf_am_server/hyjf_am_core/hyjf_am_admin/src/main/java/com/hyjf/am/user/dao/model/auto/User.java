package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String username;

    /**
     * 手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 银行预留手机号
     *
     * @mbggenerated
     */
    private String bankMobile;

    /**
     * 邮箱
     *
     * @mbggenerated
     */
    private String email;

    /**
     * 密码
     *
     * @mbggenerated
     */
    private String password;

    /**
     * 加密验证字符
     *
     * @mbggenerated
     */
    private String salt;

    /**
     * 用户是否锁定,0未锁定,1锁定
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 是否开户,0未开户,1已开户
     *
     * @mbggenerated
     */
    private Integer openAccount;

    /**
     * 充值成功短信 0发送 1不发送
     *
     * @mbggenerated
     */
    private Integer rechargeSms;

    /**
     * 提现成功短信 0发送 1不发送
     *
     * @mbggenerated
     */
    private Integer withdrawSms;

    /**
     * app头像的url
     *
     * @mbggenerated
     */
    private String iconUrl;

    /**
     * 手机端是否接收推送通知 0：不接收，1：接收
     *
     * @mbggenerated
     */
    private Integer ifReceiveNotice;

    /**
     * 投标成功短信 0发送 1不发送
     *
     * @mbggenerated
     */
    private Integer investSms;

    /**
     * 回收成功短信 0发送 1不发送
     *
     * @mbggenerated
     */
    private Integer recieveSms;

    /**
     * 账户开通平台 0pc 1微信 2安卓 3IOS 4其他
     *
     * @mbggenerated
     */
    private Integer regEsb;

    private String eprovince;

    private Integer sendSms;

    /**
     * 是否是第三方推送用户(0:否,1:是)
     *
     * @mbggenerated
     */
    private Integer isInstFlag;

    /**
     * 第三方机构编号
     *
     * @mbggenerated
     */
    private String instCode;

    /**
     * 账户开通平台 0pc 1微信 2安卓 3IOS 4其他
     *
     * @mbggenerated
     */
    private Integer accountEsb;

    /**
     * 用户类型 0普通用户 1企业用户
     *
     * @mbggenerated
     */
    private Integer userType;

    /**
     * 缴费授权状态  0：未授权1：已授权
     *
     * @mbggenerated
     */
    private Integer paymentAuthStatus;

    /**
     * 是否设置了交易密码 0未设置 1已设置
     *
     * @mbggenerated
     */
    private Integer isSetPassword;

    /**
     * 是否银行开户,0未开户,1已开户
     *
     * @mbggenerated
     */
    private Integer bankOpenAccount;

    /**
     * 银行开户平台
     *
     * @mbggenerated
     */
    private Integer bankAccountEsb;

    /**
     * 是否已进行风险测评(0:未测评,1:已测评)
     *
     * @mbggenerated
     */
    private Integer isEvaluationFlag;

    /**
     * 测评到期时间
     *
     * @mbggenerated
     */
    private Date evaluationExpiredTime;

    /**
     * CA认证标识位(0:未认证,1:已认证)
     *
     * @mbggenerated
     */
    private Integer isCaFlag;

    /**
     * 是否发送出借协议邮件 0发送 1不发送
     *
     * @mbggenerated
     */
    private Integer isSmtp;

    /**
     * 注册ip
     *
     * @mbggenerated
     */
    private String regIp;

    /**
     * 注册时间
     *
     * @mbggenerated
     */
    private Date regTime;

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
     * 用户总获取积分
     *
     * @mbggenerated
     */
    private Integer pointsTotal;

    /**
     * 用户当前积分
     *
     * @mbggenerated
     */
    private Integer pointsCurrent;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile == null ? null : bankMobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(Integer openAccount) {
        this.openAccount = openAccount;
    }

    public Integer getRechargeSms() {
        return rechargeSms;
    }

    public void setRechargeSms(Integer rechargeSms) {
        this.rechargeSms = rechargeSms;
    }

    public Integer getWithdrawSms() {
        return withdrawSms;
    }

    public void setWithdrawSms(Integer withdrawSms) {
        this.withdrawSms = withdrawSms;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl == null ? null : iconUrl.trim();
    }

    public Integer getIfReceiveNotice() {
        return ifReceiveNotice;
    }

    public void setIfReceiveNotice(Integer ifReceiveNotice) {
        this.ifReceiveNotice = ifReceiveNotice;
    }

    public Integer getInvestSms() {
        return investSms;
    }

    public void setInvestSms(Integer investSms) {
        this.investSms = investSms;
    }

    public Integer getRecieveSms() {
        return recieveSms;
    }

    public void setRecieveSms(Integer recieveSms) {
        this.recieveSms = recieveSms;
    }

    public Integer getRegEsb() {
        return regEsb;
    }

    public void setRegEsb(Integer regEsb) {
        this.regEsb = regEsb;
    }

    public String getEprovince() {
        return eprovince;
    }

    public void setEprovince(String eprovince) {
        this.eprovince = eprovince == null ? null : eprovince.trim();
    }

    public Integer getSendSms() {
        return sendSms;
    }

    public void setSendSms(Integer sendSms) {
        this.sendSms = sendSms;
    }

    public Integer getIsInstFlag() {
        return isInstFlag;
    }

    public void setIsInstFlag(Integer isInstFlag) {
        this.isInstFlag = isInstFlag;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode == null ? null : instCode.trim();
    }

    public Integer getAccountEsb() {
        return accountEsb;
    }

    public void setAccountEsb(Integer accountEsb) {
        this.accountEsb = accountEsb;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getPaymentAuthStatus() {
        return paymentAuthStatus;
    }

    public void setPaymentAuthStatus(Integer paymentAuthStatus) {
        this.paymentAuthStatus = paymentAuthStatus;
    }

    public Integer getIsSetPassword() {
        return isSetPassword;
    }

    public void setIsSetPassword(Integer isSetPassword) {
        this.isSetPassword = isSetPassword;
    }

    public Integer getBankOpenAccount() {
        return bankOpenAccount;
    }

    public void setBankOpenAccount(Integer bankOpenAccount) {
        this.bankOpenAccount = bankOpenAccount;
    }

    public Integer getBankAccountEsb() {
        return bankAccountEsb;
    }

    public void setBankAccountEsb(Integer bankAccountEsb) {
        this.bankAccountEsb = bankAccountEsb;
    }

    public Integer getIsEvaluationFlag() {
        return isEvaluationFlag;
    }

    public void setIsEvaluationFlag(Integer isEvaluationFlag) {
        this.isEvaluationFlag = isEvaluationFlag;
    }

    public Date getEvaluationExpiredTime() {
        return evaluationExpiredTime;
    }

    public void setEvaluationExpiredTime(Date evaluationExpiredTime) {
        this.evaluationExpiredTime = evaluationExpiredTime;
    }

    public Integer getIsCaFlag() {
        return isCaFlag;
    }

    public void setIsCaFlag(Integer isCaFlag) {
        this.isCaFlag = isCaFlag;
    }

    public Integer getIsSmtp() {
        return isSmtp;
    }

    public void setIsSmtp(Integer isSmtp) {
        this.isSmtp = isSmtp;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp == null ? null : regIp.trim();
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
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

    public Integer getPointsTotal() {
        return pointsTotal;
    }

    public void setPointsTotal(Integer pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    public Integer getPointsCurrent() {
        return pointsCurrent;
    }

    public void setPointsCurrent(Integer pointsCurrent) {
        this.pointsCurrent = pointsCurrent;
    }
}