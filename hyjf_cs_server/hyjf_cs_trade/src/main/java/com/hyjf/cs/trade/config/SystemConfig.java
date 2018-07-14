package com.hyjf.cs.trade.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {
    @Value("${hyjf.web.host}")
    public String webHost;

    @Value("${http.hyjf.web.host}")
    public String httpWebHost;

    @Value("${hyjf.web.bank.forgetpassword}")
    public String forgetPassword;

    @Value("${hyjf.web.user.host}")
    public String webUserHost;

    @Value("${hyjf.front.host}")
    public String frontHost;

    @Value("${hyjf.web.bank.forgetpassword}")
    public String forgetpassword;

    @Value("${file.domain.url}")
    public String fileDomainUrl;

    @Value("hyjf.notice.status")
    public String noticeStatus;

    @Value("hyjf.notice.requesturl.ios")
    public String iosNoticeRequestUrl;

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

    public String getHttpWebHost() {
        return httpWebHost;
    }

    public void setHttpWebHost(String httpWebHost) {
        this.httpWebHost = httpWebHost;
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
}
