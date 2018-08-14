/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowFirstFireRequestBean, v0.1 2018/8/14 16:20
 */
public class BorrowFirstFireRequestBean implements Serializable {
    @ApiModelProperty(value = "标的编号")
    private String borrowNid;

    @ApiModelProperty(value = "发标方式")
    private String verifyStatus;

    @ApiModelProperty(value = "定时发标时间 定时发标必填(yyyy-MM-dd HH:mm)")
    private String ontime;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getOntime() {
        return ontime;
    }

    public void setOntime(String ontime) {
        this.ontime = ontime;
    }
}
