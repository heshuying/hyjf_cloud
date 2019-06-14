/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.qvo;

import io.swagger.annotations.ApiModelProperty;

/**
 *  免登录重定向请求
 * @author cui
 * @version WbsRedirectQO, v0.1 2019/4/24 10:25
 */
public class WbsRedirectQO {

    @ApiModelProperty(value = "主页=index,产品详情=borrow,个人中心=pandect")
    private String type;

    @ApiModelProperty(value = "财富端Id")
    private String entId;

    @ApiModelProperty(value = "财富端令牌")
    private String token;

    @ApiModelProperty(value = "标的号")
    private String borrowNid;

    @ApiModelProperty(value = "神策预置属性")
    private String presetProps;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntId() {
        return entId;
    }

    public void setEntId(String entId) {
        this.entId = entId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getPresetProps() {
        return presetProps;
    }

    public void setPresetProps(String presetProps) {
        this.presetProps = presetProps;
    }
}
