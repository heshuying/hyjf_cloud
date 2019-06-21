/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.qvo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 微信绑定请求数据
 * @author cui
 * @version WechatUserBindQO, v0.1 2019/4/22 11:00
 */
public class WechatUserBindQO {

    @ApiModelProperty(value = "财富端用户id")
    private String customerId;

    @ApiModelProperty(value = "财富端id（渠道id）")
    private String utmId;

    @ApiModelProperty(value = "登录用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "神策预置属性")
    private String presetProps;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUtmId() {
        return utmId;
    }

    public void setUtmId(String utmId) {
        this.utmId = utmId;
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

    public String getPresetProps() {
        return presetProps;
    }

    public void setPresetProps(String presetProps) {
        this.presetProps = presetProps;
    }
}
