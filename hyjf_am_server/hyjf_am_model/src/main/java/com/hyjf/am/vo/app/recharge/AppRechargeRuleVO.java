package com.hyjf.am.vo.app.recharge;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wgx
 * @date 2019/4/28
 */
@ApiModel(value = "充值规则", description = "充值规则")
public class AppRechargeRuleVO implements Serializable {
    private static final long serialVersionUID = 3846040213031580739L;
    @ApiModelProperty(value = "规则标题", example = "充值绑卡")
    private String title;
    @ApiModelProperty(value = "规则内容", example = "充值前，须先确认...")
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
