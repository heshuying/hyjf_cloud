package com.hyjf.pay.lib.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xiasq
 * @version FddSystemConfig, v0.1 2018/9/10 14:40
 */
@Component
public class FddSystemConfig {

    @Value("${hyjf.pay.fdd.url}")
    public String fddPayUrl;

    @Value("${hyjf.fdd.app.id}")
    public String fddAppId;

    @Value("${hyjf.fdd.app.secret}")
    public String fddAppSeret;

    @Value("${hyjf.fdd.version}")
    public String fddVersion;

    @Value("${hyjf.fdd.url}")
    public String fddVisitUrl;

    public String getFddPayUrl() {
        return fddPayUrl;
    }

    public void setFddPayUrl(String fddPayUrl) {
        this.fddPayUrl = fddPayUrl;
    }

    public String getFddAppId() {
        return fddAppId;
    }

    public void setFddAppId(String fddAppId) {
        this.fddAppId = fddAppId;
    }

    public String getFddAppSeret() {
        return fddAppSeret;
    }

    public void setFddAppSeret(String fddAppSeret) {
        this.fddAppSeret = fddAppSeret;
    }

    public String getFddVersion() {
        return fddVersion;
    }

    public void setFddVersion(String fddVersion) {
        this.fddVersion = fddVersion;
    }

    public String getFddVisitUrl() {
        return fddVisitUrl;
    }

    public void setFddVisitUrl(String fddVisitUrl) {
        this.fddVisitUrl = fddVisitUrl;
    }
}
