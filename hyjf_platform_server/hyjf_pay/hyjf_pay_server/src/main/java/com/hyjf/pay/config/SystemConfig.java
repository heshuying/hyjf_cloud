package com.hyjf.pay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {

    @Value("${hyjf.bank.page.return.url}")
    private String returnUrl;

    @Value("${hyjf.bank.page.callback.url}")
    private String callbackUrl;
	@Value("${hyjf.bank.page.success.return.url}")
	private String callbackSuccessUrl;

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

	/** 汇付天下地址 */
	@Value("${hyjf.chinapnr.url}")
	private String chinapnrUrl;

	@Value("${hyjf.anrong.req.query.url}")
	public String anrongqueryurl;
	@Value("${hyjf.anrong.req.send.url}")
	public String anrongsendurl;
	@Value("${hyjf.anrong.member}")
	public String anrongmembe;
	@Value("${hyjf.anrong.sign}")
	public String anrongsign;

	//汇付参数
	/** 商户客户号 */
	@Value("${hyjf.chinapnr.mercustid}")
	public String merCustId;

	/** 商户子账户号 */
	@Value("${hyjf.chinapnr.meracctId}")
	public String merAcctId;

	/** 版本号 */
	@Value("${hyjf.chinapnr.version}")
	public String version;

	@Value("${hyjf.chinapnr.callback.url}")
	public String chinapnrCallBack;


	/** 商户客户号 **/
	public static String chinapnrMerId;

	/** 商户私钥文件地址 **/
	public static  String chinapnrPrikey;


	/** 商户公钥文件地址 **/
	public static String chinapnrPubkey;


	public String getChinapnrCallBack() {
		return chinapnrCallBack;
	}

	public void setChinapnrCallBack(String chinapnrCallBack) {
		this.chinapnrCallBack = chinapnrCallBack;
	}

	public String getMerCustId() {
		return merCustId;
	}

	public void setMerCustId(String merCustId) {
		this.merCustId = merCustId;
	}

	public String getMerAcctId() {
		return merAcctId;
	}

	public void setMerAcctId(String merAcctId) {
		this.merAcctId = merAcctId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAnrongmembe() {
		return anrongmembe;
	}

	public void setAnrongmembe(String anrongmembe) {
		this.anrongmembe = anrongmembe;
	}

	public String getAnrongsign() {
		return anrongsign;
	}

	public void setAnrongsign(String anrongsign) {
		this.anrongsign = anrongsign;
	}

	public String getAnrongqueryurl() {
		return anrongqueryurl;
	}

	public void setAnrongqueryurl(String anrongqueryurl) {
		this.anrongqueryurl = anrongqueryurl;
	}

	public String getAnrongsendurl() {
		return anrongsendurl;
	}

	public void setAnrongsendurl(String anrongsendurl) {
		this.anrongsendurl = anrongsendurl;
	}
	public String getChinapnrUrl() {
		return chinapnrUrl;
	}

	public void setChinapnrUrl(String chinapnrUrl) {
		this.chinapnrUrl = chinapnrUrl;
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

	public String getCallbackSuccessUrl() {
		return callbackSuccessUrl;
	}

	public void setCallbackSuccessUrl(String callbackSuccessUrl) {
		this.callbackSuccessUrl = callbackSuccessUrl;
	}
}
