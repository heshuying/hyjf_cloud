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
}
