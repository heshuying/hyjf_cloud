/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.app;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangqingqing
 * @version AppBaseRequest, v0.1 2018/7/2 14:00
 */
public class AppBaseRequest {

    @ApiModelProperty(value = "版本号")
    private String version;

    @ApiModelProperty(value = "网络状态")
    private String netStatus;

    @ApiModelProperty(value = "平台类型 0：PC，1：微官网，2：Android，3：IOS，4：其他")
    private String platform;

    @ApiModelProperty(value = "随机字符串app")
    private String randomString ;

    @ApiModelProperty(value = "Order app")
    private String order;

    @ApiModelProperty(value = "用户唯一标识")
    private String sign;

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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
