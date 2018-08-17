/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wangjun
 * @version InvestorRequest, v0.1 2018/7/11 17:07
 */
public class InvestorRequest implements Serializable {

    @ApiModelProperty(value = "标的编号")
    private String borrowNid;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "投资订单号")
    private String nid;

    @ApiModelProperty(value = "邮箱")
    private String email;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
