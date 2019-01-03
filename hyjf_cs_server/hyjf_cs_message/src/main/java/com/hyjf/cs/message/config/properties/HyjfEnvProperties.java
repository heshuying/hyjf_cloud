package com.hyjf.cs.message.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiasq
 * @version HyjfEnvProperties, v0.1 2019/1/2 10:16
 */
@ConfigurationProperties(prefix = "hyjf.env")
public class HyjfEnvProperties {
    /**
     * 环境标识  true表示生产环境  false表示测试环境
     */
    private boolean test;

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }
}
