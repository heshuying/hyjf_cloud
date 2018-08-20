package com.hyjf.am.market.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/26 15:47
 * @Description: SystemConfig
 */
@Component
public class SystemConfig {
    @Value("${hyjf.web.host}")
    private String webHost;

    public String getWebHost() {
        return webHost;
    }

    public void setWebHost(String webHost) {
        this.webHost = webHost;
    }
}
