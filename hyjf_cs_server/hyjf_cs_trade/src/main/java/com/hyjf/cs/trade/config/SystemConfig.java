package com.hyjf.cs.trade.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {
    @Value("${hyjf.web.host}")
    public String webHost;
    @Value("${hyjf.app.host}")
    public String appHost;
    /**
     *  app 3.1.1 注册协议链接
     */
    @Value("${hyjf.app.regist.agreement.url}")
    public String appRegistAgreementUrl;
    /**
     *app 3.1.1 公告内容链接
     */
    @Value("${hyjf.app.push.manager.url}")
    public String appPushManagerUrl;



    @Value("${hyjf.weChat.host}")
    public String wechatHost;

    @Value("${hyjf.web.bank.forgetpassword}")
    public String forgetPassword;

    @Value("${hyjf.web.user.host}")
    public String webUserHost;

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

    @Value("${hyjf.web.bank.forgetpassword}")
    public String forgetpassword;

    @Value("${file.domain.url}")
    public String fileDomainUrl;

    @Value("hyjf.notice.status")
    public String noticeStatus;

    @Value("hyjf.notice.requesturl.ios")
    public String iosNoticeRequestUrl;

    @Value("${hyjf.notice.ios.version}")
    public String iosNoticeVersion;

    @Value("${hyjf.pay.fdd.nofify.url}")
    private String hyjfPayFddNotifyUrl;

    @Value("${hyjf.fdd.customerid}")
    private String hyjfFddCustomerid;

    @Value("${hyjf.ftp.ip}")
    private String hyjfFtpIp;

    @Value("${hyjf.ftp.port}")
    private String hyjfFtpPort;

    @Value("${hyjf.ftp.basepath.img}")
    private String hyjfFtpBasepathImg;

    @Value("${hyjf.ftp.basepath.pdf}")
    private String hyjfFtpBasepathPdf;

    @Value("${hyjf.ftp.username}")
    private String hyjfFtpUsername;

    @Value("${hyjf.ftp.password}")
    private String hyjfFtpPassword;

    @Value("${hyjf.web.bank.batch.creditend.verify.url}")
    private String notifyUrl;

    @Value("${hyjf.web.bank.batch.creditend.result.url}")
    private String retNotifyUrl;
    @Value("${hyjf.wdzj.username}")
    private String userNameWDZJ;
    @Value("${hyjf.wdzj.password}")
    private String passwordWDZJ;

    @Value("${hyjf.bank.instcode}")
    private String bankInstcode;

    @Value("${hyjf.bank.bankcode}")
    private String bankBankcode;

    @Value("${hyjf.bank.merrp.account}")
    private String BANK_MERRP_ACCOUNT;

    @Value("${aop.interface.accesskey}")
    public String aopAccesskey;

    @Value("${crm.investmentdetails.url}")
    private String crmTenderUrl;

    //私钥请求地址
    @Value("${hyjf.req.pri.key}")
    private String hyjfReqPrimaryKeyPath;

    // 请求密码请求地址
    @Value("${hyjf.req.password}")
    private String hyjfReqPasswordPath;
    //是否是测试环境 true为测试环境  false为正式环境
    @Value("${hyjf.env.test}")
    private boolean hyjfEnvTest;
    
    /*需要在cs-trade的工程配置里面添加     #ftp图片存放路径    hyjf.ftp.basepath.img=hyjfdata/upfiles/contract/img/    */
    @Value("${hyjf.ftp.basepath.img}")
    private String basePathImage;
    
    /*需要在cs-trade的工程配置里面添加     #ftp图片映射路径    hyjf.ftp.url=http://testftp.hyjf.com  */
    @Value("${hyjf.ftp.url}")
    private String basePathurl;

    /*需要在cs-trade的工程配置里面添加 注册送券活动ID */
    @Value("${hyjf.register.888.activity.id}")
    private String registerActivityId;

    /*需要在cs-trade的工程配置里面添加 活动ID */
    @Value("${hyjf.register.888.coupon.code}")
    private String registerCouponCode;

    /*需要在cs-trade的工程配置里面添加 测评送加息券活动ID */
    @Value("${hyjf.coupon.id}")
    private String couponCodeId;

    /*需要在cs-trade的工程配置里面添加 活动ID */
    @Value("${hyjf.activity.id}")
    private String activityId;

    /*需要在cs-trade的工程配置里面添加 CouponAccesskey */
    @Value("${release.coupon.accesskey}")
    private String couponAccesskey;
    /*角色验证是否开启 add by cwyang 合规角色验证需求 */
    @Value("${hyjf.role.isopen}")
    private String roleIsopen;
    public String getAppRegistAgreementUrl() {
        return appRegistAgreementUrl;
    }

    public void setAppRegistAgreementUrl(String appRegistAgreementUrl) {
        this.appRegistAgreementUrl = appRegistAgreementUrl;
    }

    public String getAppPushManagerUrl() {
        return appPushManagerUrl;
    }

    public void setAppPushManagerUrl(String appPushManagerUrl) {
        this.appPushManagerUrl = appPushManagerUrl;
    }

    public String getWechatHost() {
        return wechatHost;
    }

    /*需要在cs-trade的工程配置里面添加 sensorsDataLogPath */
    @Value("${sensors.data.log.path}")
    private String sensorsDataLogPath;

    public void setWechatHost(String wechatHost) {
        this.wechatHost = wechatHost;
    }

    public String getBankInstcode() {
        return bankInstcode;
    }

    public void setBankInstcode(String bankInstcode) {
        this.bankInstcode = bankInstcode;
    }

    public String getBankBankcode() {
        return bankBankcode;
    }

    public void setBankBankcode(String bankBankcode) {
        this.bankBankcode = bankBankcode;
    }

    public String getHyjfPayFddNotifyUrl() {
        return hyjfPayFddNotifyUrl;
    }

    public void setHyjfPayFddNotifyUrl(String hyjfPayFddNotifyUrl) {
        this.hyjfPayFddNotifyUrl = hyjfPayFddNotifyUrl;
    }

    public String getHyjfFddCustomerid() {
        return hyjfFddCustomerid;
    }

    public void setHyjfFddCustomerid(String hyjfFddCustomerid) {
        this.hyjfFddCustomerid = hyjfFddCustomerid;
    }

    public String getHyjfFtpIp() {
        return hyjfFtpIp;
    }

    public void setHyjfFtpIp(String hyjfFtpIp) {
        this.hyjfFtpIp = hyjfFtpIp;
    }

    public String getHyjfFtpPort() {
        return hyjfFtpPort;
    }

    public void setHyjfFtpPort(String hyjfFtpPort) {
        this.hyjfFtpPort = hyjfFtpPort;
    }

    public String getHyjfFtpBasepathImg() {
        return hyjfFtpBasepathImg;
    }

    public void setHyjfFtpBasepathImg(String hyjfFtpBasepathImg) {
        this.hyjfFtpBasepathImg = hyjfFtpBasepathImg;
    }

    public String getHyjfFtpBasepathPdf() {
        return hyjfFtpBasepathPdf;
    }

    public void setHyjfFtpBasepathPdf(String hyjfFtpBasepathPdf) {
        this.hyjfFtpBasepathPdf = hyjfFtpBasepathPdf;
    }

    public String getHyjfFtpUsername() {
        return hyjfFtpUsername;
    }

    public void setHyjfFtpUsername(String hyjfFtpUsername) {
        this.hyjfFtpUsername = hyjfFtpUsername;
    }

    public String getHyjfFtpPassword() {
        return hyjfFtpPassword;
    }

    public void setHyjfFtpPassword(String hyjfFtpPassword) {
        this.hyjfFtpPassword = hyjfFtpPassword;
    }

    public String getWebHost() {
        return webHost;
    }

    public void setWebHost(String webHost) {
        this.webHost = webHost;
    }

    public String getForgetPassword() {
        return forgetPassword;
    }

    public void setForgetPassword(String forgetPassword) {
        this.forgetPassword = forgetPassword;
    }

    public String getWebUserHost() {
        return webUserHost;
    }

    public void setWebUserHost(String webUserHost) {
        this.webUserHost = webUserHost;
    }

    public String getFrontHost() {
        return frontHost;
    }

    public void setFrontHost(String frontHost) {
        this.frontHost = frontHost;
    }

    public String getForgetpassword() {
        return forgetpassword;
    }

    public void setForgetpassword(String forgetpassword) {
        this.forgetpassword = forgetpassword;
    }

    public String getFileDomainUrl() {
        return fileDomainUrl;
    }

    public void setFileDomainUrl(String fileDomainUrl) {
        this.fileDomainUrl = fileDomainUrl;
    }

    public String getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(String noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public String getIosNoticeRequestUrl() {
        return iosNoticeRequestUrl;
    }

    public void setIosNoticeRequestUrl(String iosNoticeRequestUrl) {
        this.iosNoticeRequestUrl = iosNoticeRequestUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getRetNotifyUrl() {
        return retNotifyUrl;
    }

    public void setRetNotifyUrl(String retNotifyUrl) {
        this.retNotifyUrl = retNotifyUrl;
    }

    public String getUserNameWDZJ() {
        return userNameWDZJ;
    }

    public void setUserNameWDZJ(String userNameWDZJ) {
        this.userNameWDZJ = userNameWDZJ;
    }

    public String getPasswordWDZJ() {
        return passwordWDZJ;
    }

    public void setPasswordWDZJ(String passwordWDZJ) {
        this.passwordWDZJ = passwordWDZJ;
    }

    public String getBANK_MERRP_ACCOUNT() {
        return BANK_MERRP_ACCOUNT;
    }

    public void setBANK_MERRP_ACCOUNT(String BANK_MERRP_ACCOUNT) {
        this.BANK_MERRP_ACCOUNT = BANK_MERRP_ACCOUNT;
    }

    public String getCrmTenderUrl() {
        return crmTenderUrl;
    }

    public void setCrmTenderUrl(String crmTenderUrl) {
        this.crmTenderUrl = crmTenderUrl;
    }

    public String getHyjfReqPrimaryKeyPath() {
        return hyjfReqPrimaryKeyPath;
    }

    public void setHyjfReqPrimaryKeyPath(String hyjfReqPrimaryKeyPath) {
        this.hyjfReqPrimaryKeyPath = hyjfReqPrimaryKeyPath;
    }

    public String getHyjfReqPasswordPath() {
        return hyjfReqPasswordPath;
    }

    public void setHyjfReqPasswordPath(String hyjfReqPasswordPath) {
        this.hyjfReqPasswordPath = hyjfReqPasswordPath;
    }
    public String getAopAccesskey() {
        return aopAccesskey;
    }

    public void setAopAccesskey(String aopAccesskey) {
        this.aopAccesskey = aopAccesskey;
    }
    public boolean isHyjfEnvTest() {
        return hyjfEnvTest;
    }

    public void setHyjfEnvTest(boolean hyjfEnvTest) {
        this.hyjfEnvTest = hyjfEnvTest;
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

	public String getBasePathImage() {
		return basePathImage;
	}

	public void setBasePathImage(String basePathImage) {
		this.basePathImage = basePathImage;
	}

	public String getBasePathurl() {
		return basePathurl;
	}

	public void setBasePathurl(String basePathurl) {
		this.basePathurl = basePathurl;
	}

    public String getRegisterActivityId() {
        return registerActivityId;
    }

    public void setRegisterActivityId(String registerActivityId) {
        this.registerActivityId = registerActivityId;
    }

    public String getCouponCodeId() {
        return couponCodeId;
    }

    public void setCouponCodeId(String couponCodeId) {
        this.couponCodeId = couponCodeId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getRegisterCouponCode() {
        return registerCouponCode;
    }

    public void setRegisterCouponCode(String registerCouponCode) {
        this.registerCouponCode = registerCouponCode;
    }

    public String getCouponAccesskey() {
        return couponAccesskey;
    }

    public void setCouponAccesskey(String couponAccesskey) {
        this.couponAccesskey = couponAccesskey;
    }

    public String getAppHost() {
        return appHost;
    }

    public void setAppHost(String appHost) {
        this.appHost = appHost;
    }

    public String getIosNoticeVersion() {
        return iosNoticeVersion;
    }

    public void setIosNoticeVersion(String iosNoticeVersion) {
        this.iosNoticeVersion = iosNoticeVersion;
    }

    public String getRoleIsopen() {
        return roleIsopen;
    }

    public void setRoleIsopen(String roleIsopen) {
        this.roleIsopen = roleIsopen;
    }



    public String getSensorsDataLogPath() {
        return sensorsDataLogPath;
    }

    public void setSensorsDataLogPath(String sensorsDataLogPath) {
        this.sensorsDataLogPath = sensorsDataLogPath;
    }
}
