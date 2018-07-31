/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: sunpeikai
 * @version: BaseRequestBean, v0.1 2018/7/27 11:36
 */
@ApiModel(value = "app请求基类")
public class BaseRequestBean {
    @ApiModelProperty(value = "版本号")
    private String version;

    @ApiModelProperty(value = "网络状态")
    private String netStatus;

    @ApiModelProperty(value = "平台")
    private String platform;

    @ApiModelProperty(value = "随机字符串")
    private String randomString;

    @ApiModelProperty(value = "安全码")
    private String secretKey;

    @ApiModelProperty(value = "数据加密秘钥")
    private String key;

    @ApiModelProperty(value = "唯一标识")
    private String sign;

    @ApiModelProperty(value = "令牌")
    private String token;

    @ApiModelProperty(value = "order")
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
