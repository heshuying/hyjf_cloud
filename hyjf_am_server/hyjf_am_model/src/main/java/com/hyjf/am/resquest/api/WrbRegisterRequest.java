package com.hyjf.am.resquest.api;

import com.hyjf.am.vo.user.UtmPlatVO;

import java.io.Serializable;

/**
 * @author lisheng
 * @version WrbRegisterRequest, v0.1 2018/9/27 10:13
 */

public class WrbRegisterRequest implements Serializable {

    private String mobile;
    private String instCode;
    private String ipAddr ;
    private Integer instType;
    private UtmPlatVO utmPlat;
    private String platform;

    public WrbRegisterRequest(String mobile, String instCode, String ipAddr, Integer instType, UtmPlatVO utmPlat, String platform) {
        this.mobile = mobile;
        this.instCode = instCode;
        this.ipAddr = ipAddr;
        this.instType = instType;
        this.utmPlat = utmPlat;
        this.platform = platform;
    }

    public WrbRegisterRequest() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Integer getInstType() {
        return instType;
    }

    public void setInstType(Integer instType) {
        this.instType = instType;
    }

    public UtmPlatVO getUtmPlat() {
        return utmPlat;
    }

    public void setUtmPlat(UtmPlatVO utmPlat) {
        this.utmPlat = utmPlat;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
