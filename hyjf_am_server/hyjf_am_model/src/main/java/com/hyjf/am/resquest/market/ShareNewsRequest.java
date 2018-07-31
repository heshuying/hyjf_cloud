package com.hyjf.am.resquest.market;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/26 16:31
 * @Description: ShareNewsRequest
 */
public class ShareNewsRequest {
    // 版本号
    private String version ;
    // 网络状态
    private String netStatus;
    // 平台
    private String platform ;
    // 唯一标识
    private String sign ;

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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
