package com.hyjf.cs.message.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiasq
 * @version JPushProperties, v0.1 2019/1/3 11:22
 */
@ConfigurationProperties(prefix = "hyjf.jpush")
public class JPushProperties {

    /**
     * 所有安卓 ios,渠道号79
     */
    private String appKey;
    private String masterSecret;

    /**
     * ios-pro，渠道号39
     */
    private String proAppKey;
    private String proMasterSecret;

    /**
     * ios-测试版，渠道号149
     */
    private String testAppKey;
    private String testMasterSecret;

    /**
     * ios-悦享版，渠道号153
     */
    private String yxbAppKey;
    private String yxbMasterSecret;

    /**
     * ios-周年版，渠道号152
     */
    private String znbAppKey;
    private String znbMasterSecret;

    /**
     * ios-专业版，渠道号150
     */
    private String zybAppKey;
    private String zybMasterSecret;

    /**
     * ios-至尊版，渠道号151
     */
    private String zzbAppKey;
    private String zzbMasterSecret;


    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }

    public String getProAppKey() {
        return proAppKey;
    }

    public void setProAppKey(String proAppKey) {
        this.proAppKey = proAppKey;
    }

    public String getProMasterSecret() {
        return proMasterSecret;
    }

    public void setProMasterSecret(String proMasterSecret) {
        this.proMasterSecret = proMasterSecret;
    }

    public String getTestAppKey() {
        return testAppKey;
    }

    public void setTestAppKey(String testAppKey) {
        this.testAppKey = testAppKey;
    }

    public String getTestMasterSecret() {
        return testMasterSecret;
    }

    public void setTestMasterSecret(String testMasterSecret) {
        this.testMasterSecret = testMasterSecret;
    }

    public String getYxbAppKey() {
        return yxbAppKey;
    }

    public void setYxbAppKey(String yxbAppKey) {
        this.yxbAppKey = yxbAppKey;
    }

    public String getYxbMasterSecret() {
        return yxbMasterSecret;
    }

    public void setYxbMasterSecret(String yxbMasterSecret) {
        this.yxbMasterSecret = yxbMasterSecret;
    }

    public String getZnbAppKey() {
        return znbAppKey;
    }

    public void setZnbAppKey(String znbAppKey) {
        this.znbAppKey = znbAppKey;
    }

    public String getZnbMasterSecret() {
        return znbMasterSecret;
    }

    public void setZnbMasterSecret(String znbMasterSecret) {
        this.znbMasterSecret = znbMasterSecret;
    }

    public String getZybAppKey() {
        return zybAppKey;
    }

    public void setZybAppKey(String zybAppKey) {
        this.zybAppKey = zybAppKey;
    }

    public String getZybMasterSecret() {
        return zybMasterSecret;
    }

    public void setZybMasterSecret(String zybMasterSecret) {
        this.zybMasterSecret = zybMasterSecret;
    }

    public String getZzbAppKey() {
        return zzbAppKey;
    }

    public void setZzbAppKey(String zzbAppKey) {
        this.zzbAppKey = zzbAppKey;
    }

    public String getZzbMasterSecret() {
        return zzbMasterSecret;
    }

    public void setZzbMasterSecret(String zzbMasterSecret) {
        this.zzbMasterSecret = zzbMasterSecret;
    }
}
