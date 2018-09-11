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
