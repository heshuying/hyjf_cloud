package com.hyjf.cs.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {

    @Value("${aop.interface.accesskey}")
    public String aopAccesskey;

    @Value("${release.coupon.accesskey}")
    public String couponAccesskey;

    @Value("${hyjf.api.web.url}")
    public String apiWebUrl;

    @Value("${hyjf.web.host}")
    public String webHost;

    @Value("${hyjf.app.host}")
    public String appHost;

    @Value("${hyjf.weChat.host}")
    public String weChatHost;

    @Value("{hyjf.web.ui.bindemail}")
    public String webUIBindEmail;

    @Value("${http.hyjf.web.host}")
    public String httpWebHost;

    @Value("${hyjf.bank.instcode}")
    public String bankInstcode;

    @Value("${hyjf.bank.bankcode}")
    public String bankCode;

    @Value("${file.domain.head.url}")
    public String headUrl;

    @Value("${file.domain.url}")
    public String fileDomainUrl;

    @Value("${file.upload.real.path}")
    public String fileUpload;

    @Value("${file.upload.head.path}")
    public String uploadHeadPath;

    /**
     * pc前端地址
     */
    @Value("${hyjf.front.host}")
    public String frontHost;

    /**
     * app前端地址
     */
    @Value("${hyjf.front.app.host}")
    public String AppFrontHost;

    /**
     * 微信前端地址
     */
    @Value("${hyjf.front.wei.host}")
    public String WeiFrontHost;

    @Value("${hyjf.activity.id}")
    public String activityId;

    @Value("${hyjf.web.bank.forgetpassword}")
    public String forgetpassword;

    @Value("${hyjf.bank.instcode}")
    public String instcode;

    @Value("${hyjf.bank.bankcode}")
    public String bankcode;

    @Value("${file.domain.app.url}")
    public String domainAppUrl;

    @Value("${hyjf.activity.888.id}")
    public Integer activity888Id;

    @Value("${file.physical.path}")
    public String physicalPath;

    @Value("${hyjf.app.version.new}")
    private String newVersion;

    @Value("${hyjf.wechat.qrcode.url}")
    private String wechatQrcodeUrl;

    @Value("${wcsr.retranspassword}")
    // 需要在配置文件中 添加  wcsr.retranspassword = http://app.rongdongfeng.zhugedai.com/wcsr-app/userTransPassword/callBack
    private String retransPassword;

    @Value("${wcsr.resetpassword}")
    // 需要在配置文件中 添加 wcsr.resetpassword=http://app.rongdongfeng.zhugedai.com/wcsr-app/userTransPassword/callBackResetPwd
    private String resetpassword;
    @Value("${wcsr.deletecard.callback}")
    // 需要在配置文件中 添加 wcsr.deletecard.callback=http://app.rongdongfeng.zhugedai.com/wcsr-app/deletecard/callBack
    private String surongDeletecard;
    @Value("${hyjf.env.test}")
    private boolean hyjfEnvTest;

    @Value("${hyjf.chinapnr.mercustid}")
    private String chinapnrMercustid;
    @Value("${publickey.hjs}")
    private String publickeyhjs;
    @Value("${privatekey.hjs}")
    private String privatekeyhjs;

    public String getCouponAccesskey() {
        return couponAccesskey;
    }

    public void setCouponAccesskey(String couponAccesskey) {
        this.couponAccesskey = couponAccesskey;
    }

    public String getPublickeyhjs() {
		return publickeyhjs;
	}

	public void setPublickeyhjs(String publickeyhjs) {
		this.publickeyhjs = publickeyhjs;
	}

	public String getPrivatekeyhjs() {
		return privatekeyhjs;
	}

	public void setPrivatekeyhjs(String privatekeyhjs) {
		this.privatekeyhjs = privatekeyhjs;
	}
    public String getChinapnrMercustid() {
        return chinapnrMercustid;
    }

    public void setChinapnrMercustid(String chinapnrMercustid) {
        this.chinapnrMercustid = chinapnrMercustid;
    }

    public String getWechatQrcodeUrl() {
        return wechatQrcodeUrl;
    }

    public void setWechatQrcodeUrl(String wechatQrcodeUrl) {
        this.wechatQrcodeUrl = wechatQrcodeUrl;
    }

    public String getWeChatHost() {
        return weChatHost;
    }

    public void setWeChatHost(String weChatHost) {
        this.weChatHost = weChatHost;
    }

    public String getAppHost() {
        return appHost;
    }

    public void setAppHost(String appHost) {
        this.appHost = appHost;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getPhysicalPath() {
        return physicalPath;
    }

    public void setPhysicalPath(String physicalPath) {
        this.physicalPath = physicalPath;
    }

    public Integer getActivity888Id() {
        return activity888Id;
    }

    public void setActivity888Id(Integer activity888Id) {
        this.activity888Id = activity888Id;
    }

    public String getDomainAppUrl() {
        return domainAppUrl;
    }

    public void setDomainAppUrl(String domainAppUrl) {
        this.domainAppUrl = domainAppUrl;
    }

    public String getAopAccesskey() {
        return aopAccesskey;
    }

    public void setAopAccesskey(String aopAccesskey) {
        this.aopAccesskey = aopAccesskey;
    }

    public String getApiWebUrl() {
        return apiWebUrl;
    }

    public void setApiWebUrl(String apiWebUrl) {
        this.apiWebUrl = apiWebUrl;
    }

    public String getInstcode() {
        return instcode;
    }

    public void setInstcode(String instcode) {
        this.instcode = instcode;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getForgetpassword() {
        return forgetpassword;
    }

    public void setForgetpassword(String forgetpassword) {
        this.forgetpassword = forgetpassword;
    }

    public String getUploadHeadPath() {
        return uploadHeadPath;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public void setUploadHeadPath(String uploadHeadPath) {
        this.uploadHeadPath = uploadHeadPath;
    }

    public String getWebHost() {
        return webHost;
    }

    public void setWebHost(String webHost) {
        this.webHost = webHost;
    }

    public String getHttpWebHost() {
        return httpWebHost;
    }

    public void setHttpWebHost(String httpWebHost) {
        this.httpWebHost = httpWebHost;
    }

    public String getBankInstcode() {
        return bankInstcode;
    }

    public void setBankInstcode(String bankInstcode) {
        this.bankInstcode = bankInstcode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getFileDomainUrl() {
        return fileDomainUrl;
    }

    public void setFileDomainUrl(String fileDomainUrl) {
        this.fileDomainUrl = fileDomainUrl;
    }

    public String getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(String fileUpload) {
        this.fileUpload = fileUpload;
    }

	public String getWebUIBindEmail() {
		return webUIBindEmail;
	}

	public void setWebUIBindEmail(String webUIBindEmail) {
		this.webUIBindEmail = webUIBindEmail;
	}

    public String getFrontHost() {
        return frontHost;
    }

    public void setFrontHost(String frontHost) {
        this.frontHost = frontHost;
    }

    public String getAppFrontHost() {
        return AppFrontHost;
    }

    public void setAppFrontHost(String appFrontHost) {
        AppFrontHost = appFrontHost;
    }

    public String getWeiFrontHost() {
        return WeiFrontHost;
    }

    public void setWeiFrontHost(String weiFrontHost) {
        WeiFrontHost = weiFrontHost;
    }

	public String getRetransPassword() {
		return retransPassword;
	}

	public void setRetransPassword(String retransPassword) {
		this.retransPassword = retransPassword;
	}

	public String getResetpassword() {
		return resetpassword;
	}

    public void setResetpassword(String resetpassword) {
        this.resetpassword = resetpassword;
    }

    public boolean isHyjfEnvTest() {
        return hyjfEnvTest;
    }

    public void setHyjfEnvTest(boolean hyjfEnvTest) {
        this.hyjfEnvTest = hyjfEnvTest;
    }

    public String getSurongDeletecard() {
        return surongDeletecard;
    }

    public void setSurongDeletecard(String surongDeletecard) {
        this.surongDeletecard = surongDeletecard;
    }
}
