package com.hyjf.cs.user.config;

import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.ClientConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {

    @Value("${hyjf.server.host}")
    public String serverHost;

    @Value("${hyjf.web.host}")
    public String webHost;

    /**
     * 转账指南
     */
    @Value("${hyjf.app.recharge.guide.url}")
    public String appRechangeGuideUrl;

    /** 从系统配置中获取最新版本号 */
    @Value("${hyjf.app.version.new}")
    private String newVersion;
    /** 从系统配置中获取测试环境地址 */
    @Value("${hyjf.app.serverip.test}")
    private String testServerIp;

    @Value("${aop.interface.accesskey}")
    public String aopAccesskey;

    @Value("${release.coupon.accesskey}")
    public String couponAccesskey;

    @Value("${hyjf.web.ui.bindemail}")
    public String webUIBindEmail;

    @Value("${hyjf.bank.instcode}")
    public String bankInstcode;

    @Value("${hyjf.bank.bankcode}")
    public String bankCode;

    /**
     * cds加速文件路径
     */
    @Value("${file.domain.url}")
    public String fileDomainUrl;

    @Value("${file.upload.real.path}")
    public String fileUpload;

    @Value("${file.upload.real.path.app}")
    public String appFileUpload;

    @Value("${file.upload.real.path.wechat}")
    public String weChatFileUpload;

    @Value("${file.upload.real.path.api}")
    public String apiFileUpload;

    @Value("${hyjf.app.update.android.jsp}")
    public String appUpdateAndroidJsp;

    /**
     * 获取文件url前缀
     *
     * @return
     */
    public String getFilePrefixUrl() {
        String host = UploadFileUtils.getDoPath(this.getFileDomainUrl());
        String fileUploadRealPath = UploadFileUtils.getDoPath(this.getAppFileUpload());
        return host + fileUploadRealPath;
    }

    public String getFileUpload(int platform) {
        switch (platform){
            case ClientConstants.WECHAT_CLIENT:
                return UploadFileUtils.getDoPath(this.getWeChatFileUpload());
            case ClientConstants.APP_CLIENT:
                return UploadFileUtils.getDoPath(this.getAppFileUpload());
            case ClientConstants.WEB_CLIENT:
                return UploadFileUtils.getDoPath(this.getFileUpload());
            case ClientConstants.API_CLIENT:
                return UploadFileUtils.getDoPath(this.getApiFileUpload());
        }
        return UploadFileUtils.getDoPath(this.getFileUpload());
    }

    @Value("${hyjf.front.app.host}")
    public String appServerHost;

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

    @Value("${hyjf.app.bank.forgetpassword}")
    public String appForgetpassword;

    @Value("${hyjf.activity.888.id}")
    public Integer activity888Id;

    @Value("${file.physical.path}")
    public String physicalPath;

    @Value("${hyjf.wechat.qrcode.url}")
    private String wechatQrcodeUrl;

    @Value("${hyjf.web.qrcode.url}")
    private String webQrcodeUrl;

    @Value("${wcsr.retranspassword}")
    /**
     需要在配置文件中 添加  wcsr.retranspassword = http://app.rongdongfeng.zhugedai.com/wcsr-app/userTransPassword/callBack
     */
    private String retransPassword;

    @Value("${wcsr.resetpassword}")
    /** 需要在配置文件中 添加 wcsr.resetpassword=http://app.rongdongfeng.zhugedai.com/wcsr-app/userTransPassword/callBackResetPwd
     */
    private String resetpassword;
    @Value("${wcsr.deletecard.callback}")
    /**
     * 需要在配置文件中 添加 wcsr.deletecard.callback=http://app.rongdongfeng.zhugedai.com/wcsr-app/deletecard/callBack
     */
    private String surongDeletecard;
    @Value("${hyjf.env.test}")
    private boolean hyjfEnvTest;

    @Value("${hyjf.chinapnr.mercustid}")
    private String chinapnrMercustid;
    @Value("${publickey.hjs}")
    private String publickeyhjs;
    @Value("${privatekey.hjs}")
    private String privatekeyhjs;
    @Value("${wrb.callback.bind.url}")
    private String wrncallbackbindurl;

    /*角色验证是否开启 add by cwyang 合规角色验证需求 */
    @Value("${hyjf.role.isopen}")
    private String roleIsopen;

    @Value("${hyjf.third.party.user.password}")
    private String apiPass;

    // add by pcc app3.1.1追加 20180823 start
    // 我的计划列表退出中标签显示标识（临时使用，功能上线以后可以删除）
    @Value("${hyjf.app.exit.label.show.flag}")
    private String exitLabelShowFlag;

    public String getAppFileUpload() {
        return appFileUpload;
    }

    public void setAppFileUpload(String appFileUpload) {
        this.appFileUpload = appFileUpload;
    }

    public String getWeChatFileUpload() {
        return weChatFileUpload;
    }

    public void setWeChatFileUpload(String weChatFileUpload) {
        this.weChatFileUpload = weChatFileUpload;
    }

    public String getApiFileUpload() {
        return apiFileUpload;
    }

    public void setApiFileUpload(String apiFileUpload) {
        this.apiFileUpload = apiFileUpload;
    }

    public String getAppForgetpassword() {
        return appForgetpassword;
    }

    public void setAppForgetpassword(String appForgetpassword) {
        this.appForgetpassword = appForgetpassword;
    }

    public String getApiPass() {
        return apiPass;
    }

    public void setApiPass(String apiPass) {
        this.apiPass = apiPass;
    }

    public String getWrncallbackbindurl() {
        return wrncallbackbindurl;
    }

    public void setWrncallbackbindurl(String wrncallbackbindurl) {
        this.wrncallbackbindurl = wrncallbackbindurl;
    }

    public String getAppServerHost() {
        return appServerHost;
    }

    public void setAppServerHost(String appServerHost) {
        this.appServerHost = appServerHost;
    }

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

    public String getWebHost() {
        return webHost;
    }

    public void setWebHost(String webHost) {
        this.webHost = webHost;
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

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
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

    public String getAopAccesskey() {
        return aopAccesskey;
    }

    public void setAopAccesskey(String aopAccesskey) {
        this.aopAccesskey = aopAccesskey;
    }

    public String getForgetpassword() {
        return forgetpassword;
    }

    public void setForgetpassword(String forgetpassword) {
        this.forgetpassword = forgetpassword;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
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

    public String getTestServerIp() {
        return testServerIp;
    }

    public void setTestServerIp(String testServerIp) {
        this.testServerIp = testServerIp;
    }

    public String getRoleIsopen() {
        return roleIsopen;
    }

    public void setRoleIsopen(String roleIsopen) {
        this.roleIsopen = roleIsopen;
    }

    public String getAppRechangeGuideUrl() {
        return appRechangeGuideUrl;
    }

    public void setAppRechangeGuideUrl(String appRechangeGuideUrl) {
        this.appRechangeGuideUrl = appRechangeGuideUrl;
    }

    public String getExitLabelShowFlag() {
        return exitLabelShowFlag;
    }

    public void setExitLabelShowFlag(String exitLabelShowFlag) {
        this.exitLabelShowFlag = exitLabelShowFlag;
    }

    public String getWebQrcodeUrl() {
        return webQrcodeUrl;
    }

    public void setWebQrcodeUrl(String webQrcodeUrl) {
        this.webQrcodeUrl = webQrcodeUrl;
    }

    public String getAppUpdateAndroidJsp() {
        return appUpdateAndroidJsp;
    }

    public void setAppUpdateAndroidJsp(String appUpdateAndroidJsp) {
        this.appUpdateAndroidJsp = appUpdateAndroidJsp;
    }
}
