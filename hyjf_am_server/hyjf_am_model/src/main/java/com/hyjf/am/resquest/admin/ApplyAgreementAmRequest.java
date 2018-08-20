package com.hyjf.am.resquest.admin;

import io.swagger.annotations.ApiModelProperty;

/**
 * @version ApplyAgreementAmRequest, v0.1 2018/8/10 16:28
 * @Author: Zha Daojian
 */
public class ApplyAgreementAmRequest {

    /**
     * 检索条件 借款编号
     */
    @ApiModelProperty(value = "借款编号")
    private String borrowNid;

    /**
     * 期数
     */
    @ApiModelProperty(value = "期数")
    private String borrowPeriod;

    /**
     * 检索条件 申请时间开始
     */
    @ApiModelProperty(value = "申请时间开始")
    private String timeStart;

    /**
     * 检索条件 申请时间结束
     */
    @ApiModelProperty(value = "申请时间结束")
    private String timeEnd;

    private Integer limitStart;

    private Integer limitEnd;

    /* 平台 */
    private String client;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
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
