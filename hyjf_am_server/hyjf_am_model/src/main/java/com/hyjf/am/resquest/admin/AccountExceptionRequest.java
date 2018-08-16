/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: AccountExceptionRequest, v0.1 2018/7/11 15:05
 */
@ApiModel(value = "汇付对账请求参数")
public class AccountExceptionRequest extends BasePage implements Serializable {
    @ApiModelProperty(value = "id ，处理异常用")
    private Integer id;
    @ApiModelProperty(value = "用户名检索用")
    private String username;
    @ApiModelProperty(value = "客户号检索用")
    private Long customId;
    @ApiModelProperty(value = "手机号检索用")
    private String mobile;

    private int limitStart = -1;

    private int limitEnd = -1;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCustomId() {
        return customId;
    }

    public void setCustomId(Long customId) {
        this.customId = customId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
}
