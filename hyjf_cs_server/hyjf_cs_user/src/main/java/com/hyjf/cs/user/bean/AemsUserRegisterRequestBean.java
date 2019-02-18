/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * AEMS系统:用户注册请求Bean
 *
 * @author liuyang
 * @version AemsUserRegisterRequestBean, v0.1 2018/12/6 10:49
 */
public class AemsUserRegisterRequestBean extends BaseBean {

    // 手机号
    @ApiModelProperty(value = "手机号")
    private String mobile;

    // 推荐人
    @ApiModelProperty(value = "推荐人")
    private String recommended;

    // 身份证号
    @ApiModelProperty(value = "身份证号")
    private String idCard;

    // 真实姓名
    @ApiModelProperty(value = "真实姓名")
    private String trueName;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
}
