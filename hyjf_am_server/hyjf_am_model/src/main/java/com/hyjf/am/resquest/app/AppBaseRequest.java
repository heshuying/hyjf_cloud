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

    @ApiModelProperty(value = "平台web.app.weChat.api")
    private String platform;

    @ApiModelProperty(value = "随机字符串app")
    private String randomString ;

    @ApiModelProperty(value = "Order app")
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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
