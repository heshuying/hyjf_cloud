package com.hyjf.am.user.config;

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
