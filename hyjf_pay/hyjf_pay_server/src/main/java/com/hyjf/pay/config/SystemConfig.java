package com.hyjf.pay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {
    @Value("${hyjf.bank.page.url}")
    public String pageUrl;
    @Value("${hyjf.bank.page.return.url}")
    public String returnUrl;
    @Value("${hyjf.bank.page.callback.url}")
    public String callbackUrl;
    @Value("${hyjf.bank.page.forgotpwd.url}")
    public String forgotpwdUrl;
    @Value("hyjf.bank.page.url=https://test.credit2go.cn/escrow")
    public String bankPageUrl;
    /** 银行代码 */
    @Value("${hyjf.bank.bankcode}")
	public String bankCode;
	/** 交易渠道 */
    @Value("${hyjf.bank.coinst.channel}")
	public String bankChannel;
	/** 接口默认版本号 */
    @Value("${hyjf.bank.version}")
	public String bankVersion;
	/** 平台机构代码 */
    @Value("${hyjf.bank.instcode}")
	public String bankInstCode;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankChannel() {
        return bankChannel;
    }

    public void setBankChannel(String bankChannel) {
        this.bankChannel = bankChannel;
    }

    public String getBankVersion() {
        return bankVersion;
    }

    public void setBankVersion(String bankVersion) {
        this.bankVersion = bankVersion;
    }

    public String getBankInstCode() {
        return bankInstCode;
    }

    public void setBankInstCode(String bankInstCode) {
        this.bankInstCode = bankInstCode;
    }

    public String getBankPageUrl() {
        return bankPageUrl;
    }

    public void setBankPageUrl(String bankPageUrl) {
        this.bankPageUrl = bankPageUrl;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getForgotpwdUrl() {
        return forgotpwdUrl;
    }

    public void setForgotpwdUrl(String forgotpwdUrl) {
        this.forgotpwdUrl = forgotpwdUrl;
    }
}
