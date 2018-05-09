package com.hyjf.pay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {
    
    @Value("${hyjf.bank.page.return.url}")
    private String returnUrl;
    
    @Value("${hyjf.bank.page.callback.url}")
    private String callbackUrl;
    
    @Value("${hyjf.bank.page.forgotpwd.url}")
    private String forgotpwdUrl;
    
    @Value("${hyjf.bank.page.url}")
    private String bankPageUrl;
    
    /** 银行代码 */
    @Value("${hyjf.bank.bankcode}")
	private String bankCode;
    
	/** 交易渠道 */
    @Value("${hyjf.bank.coinst.channel}")
	private String bankChannel;
    
	/** 接口默认版本号 */
    @Value("${hyjf.bank.version}")
	private String bankVersion;
    
	/** 平台机构代码 */
    @Value("${hyjf.bank.instcode}")
	private String bankInstCode;
    
	/** 银行接口联机url */
    @Value("${hyjf.bank.online.url}")
	private String bankOnlineUrl;
    
	/** 公钥地址 */
    @Value("${hyjf.bank.pubkey.path}")
	private String bankPubkeyPath;
    
	/** 私钥地址 */
    @Value("${hyjf.bank.prikey.path}")
	private String bankPrikeyPath;
    
	/** 私钥密码 */
    @Value("${hyjf.bank.prikey.pass}")
	private String bankPrikeyPass;

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

	public String getBankPageUrl() {
		return bankPageUrl;
	}

	public void setBankPageUrl(String bankPageUrl) {
		this.bankPageUrl = bankPageUrl;
	}

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

	public String getBankOnlineUrl() {
		return bankOnlineUrl;
	}

	public void setBankOnlineUrl(String bankOnlineUrl) {
		this.bankOnlineUrl = bankOnlineUrl;
	}

	public String getBankPubkeyPath() {
		return bankPubkeyPath;
	}

	public void setBankPubkeyPath(String bankPubkeyPath) {
		this.bankPubkeyPath = bankPubkeyPath;
	}

	public String getBankPrikeyPath() {
		return bankPrikeyPath;
	}

	public void setBankPrikeyPath(String bankPrikeyPath) {
		this.bankPrikeyPath = bankPrikeyPath;
	}

	public String getBankPrikeyPass() {
		return bankPrikeyPass;
	}

	public void setBankPrikeyPass(String bankPrikeyPass) {
		this.bankPrikeyPass = bankPrikeyPass;
	}
    
    
}
