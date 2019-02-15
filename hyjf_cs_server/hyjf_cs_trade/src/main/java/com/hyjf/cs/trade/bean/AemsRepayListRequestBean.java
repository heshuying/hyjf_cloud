/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * AEMS系统:回款记录ReqeustBean
 *
 * @author liuyang
 * @version AemsRepayListRequestBean, v0.1 2018/12/13 17:11
 */
public class AemsRepayListRequestBean extends BaseBean {

    @ApiModelProperty(value = "开始时间（必填）")
    private String startTime;

    @ApiModelProperty(value = "结束时间（必填）")
    private String endTime;

    @ApiModelProperty(value = "电子账号（选填）")
    private String accountId;

    @ApiModelProperty(value = "标的编号（选填）")
    private String borrowNid;

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

    @Override
    public String getAccountId() {
        return accountId;
    }

    @Override
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
