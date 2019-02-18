/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.model.customize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合规数据上报 CERT 国家互联网应急中心
 */
public class CertSendUserCustomize implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer userId;

    private String username;

    private String mobile;

    private String email;

    private String password;

    private String paypassword;

    private Integer referrer;

    private String salt;

    private Integer logintime;

    private String regIp;

    private Date regTime;

    private String loginIp;

    private Integer loginTime;

    private String lastIp;

    private Integer lastTime;

    private Integer status;

    private Integer openAccount;

    private Integer borrowSms;

    private Integer rechargeSms;

    private Integer withdrawSms;

    private Integer isSmtp;

    private Integer ifReceiveNotice;

    private String iconurl;

    private BigDecimal version;

    private Integer investSms;

    private Integer recieveSms;

    private Integer regEsb;

    private String eprovince;

    private Integer sendSms;

    private Integer pid;

    private String usernamep;

    private Integer isInstFlag;

    private String instCode;

    private Integer ptype;

    private Integer accountEsb;

    private String referrerUserName;

    private Integer investflag;

    private Integer userType;

    private Integer authType;

    private Integer authStatus;

    private Integer paymentAuthStatus;

    private Date authTime;

    private Integer recodTotal;

    private Date recodTime;

    private Date recodTruncateTime;

    private Integer isSetPassword;

    private Integer bankOpenAccount;

    private Integer bankAccountEsb;

    private Integer isDataUpdate;

    private Integer isEvaluationFlag;

    private Date evaluationExpiredTime;

    private Integer isCaFlag;

    // 其他信息
    private Integer userAttr;
    private String truename;
    private String userIdcard;
    private String userPayAccount;
    private String userBank;
    private String userBankAccount;
    private String userIdcardValue;
    private String evalType;

    private String certMobile;
    private String hashSalt;
    private String phoneHash;
    // 旧数据上报排序 按月用
    private String groupByDate;


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
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPaypassword() {
        return paypassword;
    }

    public void setPaypassword(String paypassword) {
        this.paypassword = paypassword;
    }

    public Integer getReferrer() {
        return referrer;
    }

    public void setReferrer(Integer referrer) {
        this.referrer = referrer;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getLogintime() {
        return logintime;
    }

    public void setLogintime(Integer logintime) {
        this.logintime = logintime;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Integer getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Integer loginTime) {
        this.loginTime = loginTime;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public Integer getLastTime() {
        return lastTime;
    }

    public void setLastTime(Integer lastTime) {
        this.lastTime = lastTime;
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

    public Integer getBorrowSms() {
        return borrowSms;
    }

    public void setBorrowSms(Integer borrowSms) {
        this.borrowSms = borrowSms;
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

    public Integer getIsSmtp() {
        return isSmtp;
    }

    public void setIsSmtp(Integer isSmtp) {
        this.isSmtp = isSmtp;
    }

    public Integer getIfReceiveNotice() {
        return ifReceiveNotice;
    }

    public void setIfReceiveNotice(Integer ifReceiveNotice) {
        this.ifReceiveNotice = ifReceiveNotice;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
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
        this.eprovince = eprovince;
    }

    public Integer getSendSms() {
        return sendSms;
    }

    public void setSendSms(Integer sendSms) {
        this.sendSms = sendSms;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getUsernamep() {
        return usernamep;
    }

    public void setUsernamep(String usernamep) {
        this.usernamep = usernamep;
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
        this.instCode = instCode;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public Integer getAccountEsb() {
        return accountEsb;
    }

    public void setAccountEsb(Integer accountEsb) {
        this.accountEsb = accountEsb;
    }

    public String getReferrerUserName() {
        return referrerUserName;
    }

    public void setReferrerUserName(String referrerUserName) {
        this.referrerUserName = referrerUserName;
    }

    public Integer getInvestflag() {
        return investflag;
    }

    public void setInvestflag(Integer investflag) {
        this.investflag = investflag;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }

    public Integer getPaymentAuthStatus() {
        return paymentAuthStatus;
    }

    public void setPaymentAuthStatus(Integer paymentAuthStatus) {
        this.paymentAuthStatus = paymentAuthStatus;
    }

    public Date getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    public Integer getRecodTotal() {
        return recodTotal;
    }

    public void setRecodTotal(Integer recodTotal) {
        this.recodTotal = recodTotal;
    }

    public Date getRecodTime() {
        return recodTime;
    }

    public void setRecodTime(Date recodTime) {
        this.recodTime = recodTime;
    }

    public Date getRecodTruncateTime() {
        return recodTruncateTime;
    }

    public void setRecodTruncateTime(Date recodTruncateTime) {
        this.recodTruncateTime = recodTruncateTime;
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

    public Integer getIsDataUpdate() {
        return isDataUpdate;
    }

    public void setIsDataUpdate(Integer isDataUpdate) {
        this.isDataUpdate = isDataUpdate;
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

    public Integer getUserAttr() {
        return userAttr;
    }

    public void setUserAttr(Integer userAttr) {
        this.userAttr = userAttr;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getUserIdcard() {
        return userIdcard;
    }

    public void setUserIdcard(String userIdcard) {
        this.userIdcard = userIdcard;
    }

    public String getUserPayAccount() {
        return userPayAccount;
    }

    public void setUserPayAccount(String userPayAccount) {
        this.userPayAccount = userPayAccount;
    }

    public String getUserBank() {
        return userBank;
    }

    public void setUserBank(String userBank) {
        this.userBank = userBank;
    }

    public String getUserBankAccount() {
        return userBankAccount;
    }

    public void setUserBankAccount(String userBankAccount) {
        this.userBankAccount = userBankAccount;
    }

    public String getUserIdcardValue() {
        return userIdcardValue;
    }

    public void setUserIdcardValue(String userIdcardValue) {
        this.userIdcardValue = userIdcardValue;
    }

    public String getEvalType() {
        return evalType;
    }

    public void setEvalType(String evalType) {
        this.evalType = evalType;
    }

    public String getCertMobile() {
        return certMobile;
    }

    public void setCertMobile(String certMobile) {
        this.certMobile = certMobile;
    }

    public String getHashSalt() {
        return hashSalt;
    }

    public void setHashSalt(String hashSalt) {
        this.hashSalt = hashSalt;
    }

    public String getPhoneHash() {
        return phoneHash;
    }

    public void setPhoneHash(String phoneHash) {
        this.phoneHash = phoneHash;
    }

    public String getGroupByDate() {
        return groupByDate;
    }

    public void setGroupByDate(String groupByDate) {
        this.groupByDate = groupByDate;
    }
}
