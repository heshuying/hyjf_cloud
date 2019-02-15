package com.hyjf.cs.message.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiasq
 * @version SmsConfig, v0.1 2019/1/2 9:56
 */
@ConfigurationProperties(prefix = "sms.send")
public class SmsProperties {

    private String title;
    private String suffix;

    /**
     * 普通短信通道
     */
    private String url;
    private String softSerialNo;
    private String key;

    /**
     * 营销短信通道
     */
    private String marketUrl;
    private String marketSoftSerialNo;
    private String marketKey;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSoftSerialNo() {
        return softSerialNo;
    }

    public void setSoftSerialNo(String softSerialNo) {
        this.softSerialNo = softSerialNo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMarketUrl() {
        return marketUrl;
    }

    public void setMarketUrl(String marketUrl) {
        this.marketUrl = marketUrl;
    }

    public String getMarketSoftSerialNo() {
        return marketSoftSerialNo;
    }

    public void setMarketSoftSerialNo(String marketSoftSerialNo) {
        this.marketSoftSerialNo = marketSoftSerialNo;
    }

    public String getMarketKey() {
        return marketKey;
    }

    public void setMarketKey(String marketKey) {
        this.marketKey = marketKey;
    }
}
