/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.qvo.csuser;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangqingqing
 * @version LoginVO, v0.1 2018/6/13 13:59
 */
public class LoginRequestVO {

    @ApiModelProperty(value = "用户名 web")
    private String username;
    @ApiModelProperty(value = "密码 web")
    private String password;
    @ApiModelProperty(value = "平台 web")
    private String platform;
    @ApiModelProperty(value = "版本号")
    private String version;
    @ApiModelProperty(value ="网络状态")
    private String netStatus;
    @ApiModelProperty(value = "")
    private String env;

    @ApiModelProperty(value = "神策预置属性")
    private String presetProps;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getPresetProps() {
        return presetProps;
    }

    public void setPresetProps(String presetProps) {
        this.presetProps = presetProps;
    }
}
