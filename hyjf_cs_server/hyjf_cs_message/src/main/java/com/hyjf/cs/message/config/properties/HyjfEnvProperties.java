package com.hyjf.cs.message.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiasq
 * @version HyjfEnvProperties, v0.1 2019/1/2 10:16
 */
@ConfigurationProperties(prefix = "hyjf.env")
public class HyjfEnvProperties {
    /**
     * 环境标识  false表示生产环境  true表示测试环境，  未配置参数，默认测试环境
     */
    private boolean test = true;

    /**
     * 测试环境短信和推送白名单
     */
    private String whiteList;

    /**
     * 测试环境邮件白名单
     */
    private String emailWhiteList;

    public String getEmailWhiteList() {
        return emailWhiteList;
    }

    public void setEmailWhiteList(String emailWhiteList) {
        this.emailWhiteList = emailWhiteList;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public String getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(String whiteList) {
        this.whiteList = whiteList;
    }
}
