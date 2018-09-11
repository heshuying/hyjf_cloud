package com.hyjf.am.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {

    @Value("${hyjf.fdd.app.id}")
    public  String faaAppUrl;

    @Value("${hyjf.fdd.version}")
    public String fddVersion;

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
}
