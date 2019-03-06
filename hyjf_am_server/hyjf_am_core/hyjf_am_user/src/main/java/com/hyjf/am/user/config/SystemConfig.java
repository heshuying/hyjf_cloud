package com.hyjf.am.user.config;

import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {

    @Value("${hyjf.fdd.app.id}")
    public  String faaAppUrl;

    @Value("${hyjf.fdd.version}")
    public String fddVersion;
    @Value("${hyjf.fdd.app.secret}")
    public String fddSecret;
    @Value("${hyjf.fdd.url}")
    public String fddUrl;

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

    public String getFileUpload(String channel) {
        switch (channel){
            case BankCallConstant.CHANNEL_WEI:
                return UploadFileUtils.getDoPath(this.getWeChatFileUpload());
            case BankCallConstant.CHANNEL_APP:
                return UploadFileUtils.getDoPath(this.getAppFileUpload());
            case BankCallConstant.CHANNEL_PC:
                return UploadFileUtils.getDoPath(this.getFileUpload());
            case BankCallConstant.CHANNEL_OTHER:
                return UploadFileUtils.getDoPath(this.getApiFileUpload());
        }
        return UploadFileUtils.getDoPath(this.getFileUpload());
    }

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

    public String getFaaAppUrl() {
        return faaAppUrl;
    }

    public void setFaaAppUrl(String faaAppUrl) {
        this.faaAppUrl = faaAppUrl;
    }

    public String getFddVersion() {
        return fddVersion;
    }

    public void setFddVersion(String fddVersion) {
        this.fddVersion = fddVersion;
    }

    public String getFddSecret() {
        return fddSecret;
    }

    public void setFddSecret(String fddSecret) {
        this.fddSecret = fddSecret;
    }

    public String getFddUrl() {
        return fddUrl;
    }

    public void setFddUrl(String fddUrl) {
        this.fddUrl = fddUrl;
    }
}
