/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author cui
 * @version WbsConfig, v0.1 2019/4/16 15:41
 */
@Component
public class WbsConfig {

    @Value("${wbs.appKey}")
    private String appKey;

    @Value("${wbs.secret}")
    private String appSecret;

    @Value("${wbs.url.sync_customer}")
    private String syncCustomerUrl;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getSyncCustomerUrl() {
        return syncCustomerUrl;
    }

    public void setSyncCustomerUrl(String syncCustomerUrl) {
        this.syncCustomerUrl = syncCustomerUrl;
    }
}
