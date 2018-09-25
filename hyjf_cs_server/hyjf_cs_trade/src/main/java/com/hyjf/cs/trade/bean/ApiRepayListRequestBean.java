package com.hyjf.cs.trade.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * 回款记录
 * @version ApiRepayListRequestBean, v0.1 2018/9/1 13:01
 * @Author: Zha Daojian
 */
public class ApiRepayListRequestBean extends BaseBean {

    @ApiModelProperty(value = "机构编号（必填）")
    private String instCode;

    @ApiModelProperty(value = "开始时间（必填）")
    private String startTime;

    @ApiModelProperty(value = "结束时间（必填）")
    private String endTime;

    @ApiModelProperty(value = "电子账号（选填）")
    private String accountId;

    @ApiModelProperty(value = "标的编号（选填）")
    private String borrowNid;

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
