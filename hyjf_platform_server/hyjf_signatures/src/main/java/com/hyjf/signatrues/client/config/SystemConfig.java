package com.hyjf.signatrues.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {


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

    @Value("${fdd.file.upload.real.path}")
    public String fddFileUpload;







    public String getFddFileUpload() {
        return fddFileUpload;
    }

    public void setFddFileUpload(String fddFileUpload) {
        this.fddFileUpload = fddFileUpload;
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


}
