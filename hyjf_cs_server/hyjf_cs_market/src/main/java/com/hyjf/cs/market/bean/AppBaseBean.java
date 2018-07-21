package com.hyjf.cs.market.bean;

/**
 * <p>
 * AppBaseBean
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
public class AppBaseBean {

    /**
     * 版本号
     */
    private String version;
    /**
     * 网络状态
     */
    private String netStatus;
    /**
     * 平台
     */
    private String platform;
    /**
     * 随机字符串
     */
    private String randomString;
    /**
     * 安全码
     */
    private String secretKey;
    /**
     * 唯一标识
     */
    private String sign;
    /**
     * 令牌
     */
    private String token;
    /**
     * order
     */
    private String order;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(String netStatus) {
        this.netStatus = netStatus;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRandomString() {
        return randomString;
    }

    public void setRandomString(String randomString) {
        this.randomString = randomString;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
    
}
