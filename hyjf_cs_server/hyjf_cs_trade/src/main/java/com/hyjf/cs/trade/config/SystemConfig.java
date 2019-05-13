package com.hyjf.cs.trade.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {

    @Value("${hyjf.server.host}")
    public String serverHost;
    @Value("${hyjf.web.host}")
    public String webHost;
    @Value("${hyjf.app.host}")
    public String appHost;
    /**
     * app 3.1.1 注册协议链接
     */
    @Value("${hyjf.app.regist.agreement.url}")
    public String appRegistAgreementUrl;
    /**
     * app 3.1.1 服务协议
     */
    @Value("${hyjf.app.service.agreement.url}")
    public String appServiceAgreementUrl;
    /**
     * app 3.1.1 隐私政策
     */
    @Value("${hyjf.app.privacy.Policy.url}")
    public String appPrivacyPolicyUrl;
    /**
     * app 3.1.1 公告内容链接
     */
    @Value("${hyjf.app.push.manager.url}")
    public String appPushManagerUrl;

    @Value("${hyjf.weChat.host}")
    public String wechatHost;


    @Value("${fdd.file.upload.real.path}")
    public String fddFileUpload;
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

    @Value("${hyjf.app.bank.forgetpassword}")
    public String appForgetpassword;

    @Value("${file.domain.url}")
    public String fileDomainUrl;

    @Value("${file.physical.path}")
    public String filePhysicalPath;

    @Value("${file.upload.real.path}")
    public String fileUploadRealPath;

    @Value("${hyjf.notice.status}")
    public String noticeStatus;

    @Value("${hyjf.notice.requesturl.ios}")
    public String iosNoticeRequestUrl;

    @Value("${hyjf.notice.ios.version}")
    public String iosNoticeVersion;

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
    /**
     * wjt前端地址
     */
    @Value("${hyjf.front.wjt.host}")
    public String wjtFrontHost;

    /**
     * wjt 微信前端地址
     */
    @Value("${hyjf.front.wjt.wei.host}")
    public String wjtWeiFrontHost;

    public String getWjtFrontHost() {
        return wjtFrontHost;
    }

    public void setWjtFrontHost(String wjtFrontHost) {
        this.wjtFrontHost = wjtFrontHost;
    }

    public String getWjtWeiFrontHost() {
        return wjtWeiFrontHost;
    }

    public void setWjtWeiFrontHost(String wjtWeiFrontHost) {
        this.wjtWeiFrontHost = wjtWeiFrontHost;
    }

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


    @Value("${hyjf.contract.ftlpath}")
    private String contractFtlPath;

    @Value("${tender.contract.ftl.name}")
    private String tenderContractFtlName;

    @Value("${rtb.contract.ftl.name}")
    private String rtbContractFtlName;

    @Value("${rtbzsc.contract.ftl.name}")
    private String rtbzscContractFtlName;

    @Value("${new.hjh.invest.contract.ftl.name}")
    private String newHjhInvestContractFtlName;

    @Value("${credit.contract.ftl.name}")
    private String creditContractFtlName;

    @Value("${htj.tender.contract.ftl.name}")
    private String htjTenderContractFtlName;

    @Value("${tender.newcontract.ftl.name}")
    private String tenderNewContractFtlName;

    @Value("${borrower.contract.ftl.name}")
    private String borrowContractFtlName;

    @Value("${borrower.hxf.contract.ftl.name}")
    private String borrowHXFContractFtlName;

    @Value("${credit.hjh.contract.ftl.name}")
    private String creditHjhContractFtlName;

    @Value("${hyjf.contract.font}")
    private String hyjfContractFont;

    @Value("${hyjf.makepdf.temppath}")
    private String hyjfMakePdfPath;

    @Value("${hyjf.web.pdf.host}")
    private String hyjfWebPdfHost;

    @Value("${hyjf.temppdf.path}")
    private String hyjfTemppdfPath;

    @Value("${hyjf.seal.sysId}")
    private String sealSysId;

    @Value("${hyjf.seal.userid}")
    private String sealUserId;

    @Value("${hyjf.seal.password}")
    private String sealPassword;

    @Value("${hyjf.seal.url}")
    private String sealUrl;

    @Value("${hyjf.seal.address}")
    private String sealAddress;

    @Value("${hyjf.seal.port}")
    private String sealPort;

    @Value("${hyjf.seal.operate}")
    private String sealOperate;

    @Value("${plan.credit.contract.ftl.name}")
    private String planCreditContractFtlName;

    @Value("${htj.invest.contract.ftl.name}")
    private String htjInvestContractFtlName;

    @Value("${new.hjh.diary.ftl.name}")
    private String neweHhjDiaryFtlName;

    @Value("${hyjf.online.type}")
    private String hyjfOnlineType;

    @Value("${hyjf.cdn.domain.url}")
    private String hyjfCdnDomainUrl;

    //国家应急中心配置文件添加 add by nxl start
    /**
     * //合规数据上报 CERT 国家互联网应急中心 请求地址 https://api.ifcert.org.cn * 正式地址和测试地址区别 去掉【/test】 例 p2p/userInfo/test
     **/
    @Value("${hyjf.cert.sever.path}")
    public String certSeverPath;
    /**
     * //合规数据上报 CERT 国家互联网应急中心是否测试环境 true 测试环境 false 生产环境
     */
    @Value("${hyjf.cert.is.test}")
    public String certIsTest;
    /**
     * //合规数据上报 CERT 国家互联网应急中心 企业自己的sourceCode
     */
    @Value("${hyjf.cert.source.code}")
    public String certSourceCode;
    /**
     * //合规数据上报 CERT 国家互联网应急中心 企业自己的apiKey
     */
    @Value("${hyjf.cert.api.key}")
    public String certApiKey;
    /**
     * 合规数据上报 CERT 国家互联网应急中心 金协议地址
     */
    @Value("${hyjf.cert.crt.path}")
    public String certCrtpath;
    /**
     * 查询批次数据入库消息
     */
    @Value("${hyjf.cert.yibu.path}")
    public String certWebYibu;
    //国家应急中心配置文件添加 add by nxl end

    public String getHyjfOnlineType() {
        return hyjfOnlineType;
    }

    public void setHyjfOnlineType(String hyjfOnlineType) {
        this.hyjfOnlineType = hyjfOnlineType;
    }

    public String getHyjfCdnDomainUrl() {
        return hyjfCdnDomainUrl;
    }

    public void setHyjfCdnDomainUrl(String hyjfCdnDomainUrl) {
        this.hyjfCdnDomainUrl = hyjfCdnDomainUrl;
    }

    public String getAppForgetpassword() {
        return appForgetpassword;
    }

    public void setAppForgetpassword(String appForgetpassword) {
        this.appForgetpassword = appForgetpassword;
    }

    public String getFddFileUpload() {
        return fddFileUpload;
    }

    public void setFddFileUpload(String fddFileUpload) {
        this.fddFileUpload = fddFileUpload;
    }

    public String getNeweHhjDiaryFtlName() {
        return neweHhjDiaryFtlName;
    }

    public void setNeweHhjDiaryFtlName(String neweHhjDiaryFtlName) {
        this.neweHhjDiaryFtlName = neweHhjDiaryFtlName;
    }

    public String getHtjInvestContractFtlName() {
        return htjInvestContractFtlName;
    }

    public void setHtjInvestContractFtlName(String htjInvestContractFtlName) {
        this.htjInvestContractFtlName = htjInvestContractFtlName;
    }

    public String getPlanCreditContractFtlName() {
        return planCreditContractFtlName;
    }

    public void setPlanCreditContractFtlName(String planCreditContractFtlName) {
        this.planCreditContractFtlName = planCreditContractFtlName;
    }

    public String getSealUrl() {
        return sealUrl;
    }

    public void setSealUrl(String sealUrl) {
        this.sealUrl = sealUrl;
    }

    public String getSealAddress() {
        return sealAddress;
    }

    public void setSealAddress(String sealAddress) {
        this.sealAddress = sealAddress;
    }

    public String getSealPort() {
        return sealPort;
    }

    public void setSealPort(String sealPort) {
        this.sealPort = sealPort;
    }

    public String getSealOperate() {
        return sealOperate;
    }

    public void setSealOperate(String sealOperate) {
        this.sealOperate = sealOperate;
    }

    public String getSealSysId() {
        return sealSysId;
    }

    public void setSealSysId(String sealSysId) {
        this.sealSysId = sealSysId;
    }

    public String getSealUserId() {
        return sealUserId;
    }

    public void setSealUserId(String sealUserId) {
        this.sealUserId = sealUserId;
    }

    public String getSealPassword() {
        return sealPassword;
    }

    public void setSealPassword(String sealPassword) {
        this.sealPassword = sealPassword;
    }

    public String getHyjfTemppdfPath() {
        return hyjfTemppdfPath;
    }

    public void setHyjfTemppdfPath(String hyjfTemppdfPath) {
        this.hyjfTemppdfPath = hyjfTemppdfPath;
    }

    public String getHyjfWebPdfHost() {
        return hyjfWebPdfHost;
    }

    public void setHyjfWebPdfHost(String hyjfWebPdfHost) {
        this.hyjfWebPdfHost = hyjfWebPdfHost;
    }

    public String getHyjfMakePdfPath() {
        return hyjfMakePdfPath;
    }

    public void setHyjfMakePdfPath(String hyjfMakePdfPath) {
        this.hyjfMakePdfPath = hyjfMakePdfPath;
    }

    public String getHyjfContractFont() {
        return hyjfContractFont;
    }

    public void setHyjfContractFont(String hyjfContractFont) {
        this.hyjfContractFont = hyjfContractFont;
    }

    public String getCreditHjhContractFtlName() {
        return creditHjhContractFtlName;
    }

    public void setCreditHjhContractFtlName(String creditHjhContractFtlName) {
        this.creditHjhContractFtlName = creditHjhContractFtlName;
    }

    public String getBorrowHXFContractFtlName() {
        return borrowHXFContractFtlName;
    }

    public void setBorrowHXFContractFtlName(String borrowHXFContractFtlName) {
        this.borrowHXFContractFtlName = borrowHXFContractFtlName;
    }

    public String getBorrowContractFtlName() {
        return borrowContractFtlName;
    }

    public void setBorrowContractFtlName(String borrowContractFtlName) {
        this.borrowContractFtlName = borrowContractFtlName;
    }

    public String getTenderNewContractFtlName() {
        return tenderNewContractFtlName;
    }

    public void setTenderNewContractFtlName(String tenderNewContractFtlName) {
        this.tenderNewContractFtlName = tenderNewContractFtlName;
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

    public String getContractFtlPath() {
        return contractFtlPath;
    }

    public void setContractFtlPath(String contractFtlPath) {
        this.contractFtlPath = contractFtlPath;
    }

    public String getTenderContractFtlName() {
        return tenderContractFtlName;
    }

    public void setTenderContractFtlName(String tenderContractFtlName) {
        this.tenderContractFtlName = tenderContractFtlName;
    }

    public String getRtbContractFtlName() {
        return rtbContractFtlName;
    }

    public void setRtbContractFtlName(String rtbContractFtlName) {
        this.rtbContractFtlName = rtbContractFtlName;
    }

    public String getRtbzscContractFtlName() {
        return rtbzscContractFtlName;
    }

    public void setRtbzscContractFtlName(String rtbzscContractFtlName) {
        this.rtbzscContractFtlName = rtbzscContractFtlName;
    }

    public String getNewHjhInvestContractFtlName() {
        return newHjhInvestContractFtlName;
    }

    public void setNewHjhInvestContractFtlName(String newHjhInvestContractFtlName) {
        this.newHjhInvestContractFtlName = newHjhInvestContractFtlName;
    }

    public String getCreditContractFtlName() {
        return creditContractFtlName;
    }

    public void setCreditContractFtlName(String creditContractFtlName) {
        this.creditContractFtlName = creditContractFtlName;
    }

    public String getHtjTenderContractFtlName() {
        return htjTenderContractFtlName;
    }

    public void setHtjTenderContractFtlName(String htjTenderContractFtlName) {
        this.htjTenderContractFtlName = htjTenderContractFtlName;
    }

    public String getFilePhysicalPath() {
        return filePhysicalPath;
    }

    public void setFilePhysicalPath(String filePhysicalPath) {
        this.filePhysicalPath = filePhysicalPath;
    }

    public String getFileUploadRealPath() {
        return fileUploadRealPath;
    }

    public void setFileUploadRealPath(String fileUploadRealPath) {
        this.fileUploadRealPath = fileUploadRealPath;
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

    public String getServerHost() {
        return this.serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getAppServiceAgreementUrl() {
        return appServiceAgreementUrl;
    }

    public String getAppPrivacyPolicyUrl() {
        return appPrivacyPolicyUrl;
    }

    public String getCertSeverPath() {
        return certSeverPath;
    }

    public void setCertSeverPath(String certSeverPath) {
        this.certSeverPath = certSeverPath;
    }

    public String getCertIsTest() {
        return certIsTest;
    }

    public void setCertIsTest(String certIsTest) {
        this.certIsTest = certIsTest;
    }

    public String getCertSourceCode() {
        return certSourceCode;
    }

    public void setCertSourceCode(String certSourceCode) {
        this.certSourceCode = certSourceCode;
    }

    public String getCertApiKey() {
        return certApiKey;
    }

    public void setCertApiKey(String certApiKey) {
        this.certApiKey = certApiKey;
    }

    public String getCertCrtpath() {
        return certCrtpath;
    }

    public void setCertCrtpath(String certCrtpath) {
        this.certCrtpath = certCrtpath;
    }

    public String getCertWebYibu() {
        return certWebYibu;
    }

    public void setCertWebYibu(String certWebYibu) {
        this.certWebYibu = certWebYibu;
    }
}
