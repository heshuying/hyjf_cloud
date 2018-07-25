/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowFirstRequestBean, v0.1 2018/7/3 14:50
 */
public class BorrowFirstRequestBean extends BaseRequest implements Serializable {
    @ApiModelProperty(value = "借款编号(初审接口用到借款编号的都在这个字段赋值)")
    private String borrowNidSrch;

    @ApiModelProperty(value = "借款期限")
    private String borrowPeriod;

    @ApiModelProperty(value = "用户名")
    private String usernameSrch;

    @ApiModelProperty(value = "资金来源")
    private String instCodeSrch;

    @ApiModelProperty(value = "标的初审状态(初审接口用到标的初审状态的都在这个字段赋值)")
    private String verifyStatusSrch;

    @ApiModelProperty(value = "发布时间开始")
    private String timeStartSrch;

    @ApiModelProperty(value = "发布时间结束")
    private String timeEndSrch;

    @ApiModelProperty(value = "定时发标时间")
    private String ontime;

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getUsernameSrch() {
        return usernameSrch;
    }

    public void setUsernameSrch(String usernameSrch) {
        this.usernameSrch = usernameSrch;
    }

    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }

    public String getVerifyStatusSrch() {
        return verifyStatusSrch;
    }

    public void setVerifyStatusSrch(String verifyStatusSrch) {
        this.verifyStatusSrch = verifyStatusSrch;
    }

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public String getOntime() {
        return ontime;
    }

    public void setOntime(String ontime) {
        this.ontime = ontime;
    }
}
