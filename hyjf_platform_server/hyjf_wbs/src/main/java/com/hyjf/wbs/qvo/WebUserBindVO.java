/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.qvo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Web用户绑定返回
 * @author cui
 * @version WebUserBindVO, v0.1 2019/4/22 9:40
 */
public class WebUserBindVO {

    @ApiModelProperty(value = "汇盈金服（资产端）用户名")
    private String username;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "令牌")
    private String token;
    @ApiModelProperty(value = "用户角色")
    private String roleId;
    @ApiModelProperty(value = "头像地址")
    private String iconUrl;
    @ApiModelProperty(value = "成功返回url")
    private String retUrl;
    @ApiModelProperty(value = "用户id")
    private String userId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getRetUrl() {
        return retUrl;
    }

    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
