package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @version BorrowRepayAgreementRequest, v0.1 2018/8/10 15:05
 * @Author: Zha Daojian
 */
public class BorrowRepayAgreementRequest extends BaseRequest implements Serializable {
    @ApiModelProperty(value = "借款编号")
    private String borrowNidSrch;

    @ApiModelProperty(value = "垫付时间开始")
    private String timeStartSrch;

    @ApiModelProperty(value = "垫付时间结束")
    private String timeEndSrch;

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
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
}
