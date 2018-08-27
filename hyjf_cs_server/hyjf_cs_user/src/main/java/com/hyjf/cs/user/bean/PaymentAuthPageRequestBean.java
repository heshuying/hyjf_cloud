package com.hyjf.cs.user.bean;

/**
 * 缴费授权请求Bean
 * 
 */
public class PaymentAuthPageRequestBean extends BaseBean {

	private String accoutnId;
	private String forgotPwdUrl;
	private String notifyUrl;
	
    public String getAccoutnId() {
        return accoutnId;
    }

    public void setAccoutnId(String accoutnId) {
        this.accoutnId = accoutnId;
    }

    public String getForgotPwdUrl() {
        return forgotPwdUrl;
    }

    public void setForgotPwdUrl(String forgotPwdUrl) {
        this.forgotPwdUrl = forgotPwdUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
