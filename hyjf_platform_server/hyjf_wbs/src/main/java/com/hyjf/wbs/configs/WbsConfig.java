/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author cui
 * @version WbsConfig, v0.1 2019/4/16 15:41
 */
@Configuration
public class WbsConfig {

    @Value("${wbs.appkey}")
    private String appKey;

    @Value("${wbs.secret}")
    private String appSecret;

    @Value("${wbs.url.sync_customer}")
    private String syncCustomerUrl;

    @Value("${wbs.url.sync_product_info}")
    private String syncProductInfoUrl;

    @Value("${third.property.ids}")
    private String thridPropertyIds;

    @Value("${web.bind.returl}")
    private String webBindRetUrl;

    @Value("${wechat.bind.returl}")
    private String wechatBingRetUrl;

    @Value("${utmid.namizixun}")
    private Integer utmNami;

    @Value("${utmid.yufengrui}")
    private Integer utmYufengrui;

    @Value("${utmid.datanghuijin}")
    private Integer utmDatang;

    @Value("${utmid.qianle}")
    private Integer utmQianle;

    @Value("${pushdata.flag}")
    private Integer pushDataFlag;

    public Integer getPushDataFlag() {
        return pushDataFlag;
    }

    public void setPushDataFlag(Integer pushDataFlag) {
        this.pushDataFlag = pushDataFlag;
    }

    public Integer getUtmNami() {
        return utmNami;
    }

    public void setUtmNami(Integer utmNami) {
        this.utmNami = utmNami;
    }

    public Integer getUtmYufengrui() {
        return utmYufengrui;
    }

    public void setUtmYufengrui(Integer utmYufengrui) {
        this.utmYufengrui = utmYufengrui;
    }

    public Integer getUtmDatang() {
        return utmDatang;
    }

    public void setUtmDatang(Integer utmDatang) {
        this.utmDatang = utmDatang;
    }

    public Integer getUtmQianle() {
        return utmQianle;
    }

    public void setUtmQianle(Integer utmQianle) {
        this.utmQianle = utmQianle;
    }

    public String getSyncProductInfoUrl() {
        return syncProductInfoUrl;
    }

    public void setSyncProductInfoUrl(String syncProductInfoUrl) {
        this.syncProductInfoUrl = syncProductInfoUrl;
    }

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

    public String getThridPropertyIds() {
        return thridPropertyIds;
    }

    public void setThridPropertyIds(String thridPropertyIds) {
        this.thridPropertyIds = thridPropertyIds;
    }

    public String getWebAuthorizeRetUrl() {
        return webAuthorizeRetUrl;
    }

    public void setWebAuthorizeRetUrl(String webAuthorizeRetUrl) {
        this.webAuthorizeRetUrl = webAuthorizeRetUrl;
    }

    public String getWebBindRetUrl() {
        return webBindRetUrl;
    }

    public void setWebBindRetUrl(String webBindRetUrl) {
        this.webBindRetUrl = webBindRetUrl;
    }

    public String getWechatBingRetUrl() {
        return wechatBingRetUrl;
    }

    public void setWechatBingRetUrl(String wechatBingRetUrl) {
        this.wechatBingRetUrl = wechatBingRetUrl;
    }
}
