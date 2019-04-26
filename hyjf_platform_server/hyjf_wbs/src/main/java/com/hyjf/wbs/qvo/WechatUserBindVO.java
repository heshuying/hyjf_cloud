/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.qvo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 微信用户绑定返回
 * @author cui
 * @version WechatUserBindVO, v0.1 2019/4/22 11:34
 */
public class WechatUserBindVO extends WbsUserBindVO{

    @ApiModelProperty(value = "用户ID")
    private String userId;
    @ApiModelProperty(value = "令牌")
    private String sign;
    @ApiModelProperty(value = "类型")
    private Integer userType;
    @ApiModelProperty(value = "成功后返回地址")
    private String retUrl;

    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getRetUrl() {
        return retUrl;
    }

    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
    }
}
