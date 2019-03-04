package com.hyjf.admin.beans.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author hesy
 * @version BankRepayFreezeOrgCheckRequestBean, v0.1 2018/10/19 16:11
 */
public class BankRepayFreezeOrgCheckRequestBean {
    @ApiModelProperty(value = "项目编号")
    private String borrowNid;
    @ApiModelProperty(value = "冻结订单号")
    private String orderId;
    @ApiModelProperty(value = "当前期数")
    private Integer currentPeriod;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(Integer currentPeriod) {
        this.currentPeriod = currentPeriod;
    }
}
