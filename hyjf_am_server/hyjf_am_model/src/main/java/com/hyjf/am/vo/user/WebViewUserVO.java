/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

/**
 * WEB页面展示用户实体
 *
 * @author zhangqingqing
 * @version hyjf 1.0
 */
public class WebViewUserVO extends BaseVO implements Serializable {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 6315004857843065077L;

    /**
     * token 登录生成令牌
     */
    private String token;
    private Integer userId;

    private String username = "";

    private String mobile = "";

    /**
     * 银行预留手机号
     */
    private String bankMobile = "";

    private String iconUrl ="";

    private Date evaluationExpiredTime;

    private Integer sex = 0;

    /**
     * 昵称
     */
    private String nickname = "";

    /**
     * 真实姓名
     */
    private String truename = "";

    /**
     * 身份证
     */
    private String idcard = "";

    /**
     * email
     */
    private String email = "";

    /**
     * 是否汇付开户
     */
    private boolean openAccount = false;
    
    /**
     * 是否银行开户
     */
    private boolean bankOpenAccount = false;
    
    /**
     * 银行电子账号
     */
    private String bankAccount = "";
    
    /**
     * 汇付账号,咱平台的zsc_xxxxx
     */
    private String chinapnrUsrid = "";

    /**
     * 汇付账号,汇付平台的6000xxxxx
     */
    private Long chinapnrUsrcustid;

    /**
     * 用户类型，标识是借款用户还是出借用户
     */
    private String roleId;

    /**
     * 充值成功短信 0发送1不发送
     */
    private Integer rechargeSms;

    /**
     * 提现成功短线 0发送1不发送
     */
    private Integer withdrawSms;

    /**
     * 投标成功短信 0发送1不发送
     */
    private Integer investSms;

    /**
     * 是否发送出借协议邮件 0发送 1不发送
     */
    private Integer isSmtp;
    /**
     * 回收成功短信 0发送1不发送
     */
    private Integer recieveSms;

    /**
     * 借款人机构类型
     */
    private Integer borrowerType;
    
    /**
     * 是否设置过交易密码 0未设置 1已设置
     */
    private Integer isSetPassword;
    /**
     * 用户类型  0普通用户 1企业账户
     */
    private Integer userType;

    /**
     * 是否测评:0:未测评,1:已测评
     */
    private Integer isEvaluationFlag;

    /**
     * 缴费授权状态  0：未授权1：已授权
     */
    private Integer paymentAuthStatus;

    private UsersContactVO usersContact;

    private Integer couponSendCount = 0;

    public Integer getCouponSendCount() {
        return couponSendCount;
    }

    public void setCouponSendCount(Integer couponSendCount) {
        this.couponSendCount = couponSendCount;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Date getEvaluationExpiredTime() {
        return evaluationExpiredTime;
    }

    public void setEvaluationExpiredTime(Date evaluationExpiredTime) {
        this.evaluationExpiredTime = evaluationExpiredTime;
    }

    public UsersContactVO getUsersContact() {
        return usersContact;
    }

    public void setUsersContact(UsersContactVO usersContact) {
        this.usersContact = usersContact;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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


    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOpenAccount() {
		return openAccount;
	}

	public void setOpenAccount(boolean openAccount) {
		this.openAccount = openAccount;
	}

	public String getChinapnrUsrid() {
        return chinapnrUsrid;
    }

    public void setChinapnrUsrid(String chinapnrUsrid) {
        this.chinapnrUsrid = chinapnrUsrid;
    }

    public Long getChinapnrUsrcustid() {
        return chinapnrUsrcustid;
    }

    public void setChinapnrUsrcustid(Long chinapnrUsrcustid) {
        this.chinapnrUsrcustid = chinapnrUsrcustid;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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

    public Integer getBorrowerType() {
        return borrowerType;
    }

    public void setBorrowerType(Integer borrowerType) {
        this.borrowerType = borrowerType;
    }

	public Integer getIsSetPassword() {
		return isSetPassword;
	}

	public void setIsSetPassword(Integer isSetPassword) {
		this.isSetPassword = isSetPassword;
	}

	public boolean isBankOpenAccount() {
		return bankOpenAccount;
	}

	public void setBankOpenAccount(boolean bankOpenAccount) {
		this.bankOpenAccount = bankOpenAccount;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

    public Integer getIsSmtp() {
        return isSmtp;
    }

    public void setIsSmtp(Integer isSmtp) {
        this.isSmtp = isSmtp;
    }

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

    public Integer getIsEvaluationFlag() {
        return isEvaluationFlag;
    }

    public void setIsEvaluationFlag(Integer isEvaluationFlag) {
        this.isEvaluationFlag = isEvaluationFlag;
    }

	public Integer getPaymentAuthStatus() {
		return paymentAuthStatus;
	}

	public void setPaymentAuthStatus(Integer paymentAuthStatus) {
		this.paymentAuthStatus = paymentAuthStatus;
	}

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }
}
