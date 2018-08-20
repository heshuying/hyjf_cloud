package com.hyjf.am.resquest.admin;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class BorrowRepayAgreementAmRequest implements Serializable {

    @ApiModelProperty(value = "借款编号")
    private String borrowNidSrch;

    @ApiModelProperty(value = "垫付时间开始")
    private String timeStartSrch;

    @ApiModelProperty(value = "垫付时间结束")
    private String timeEndSrch;

    private Integer limitStart;

    private Integer limitEnd;

    /* 平台 */
    private String client;

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

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
