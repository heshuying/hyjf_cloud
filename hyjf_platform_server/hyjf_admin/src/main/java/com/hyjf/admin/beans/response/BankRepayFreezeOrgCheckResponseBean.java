package com.hyjf.admin.beans.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author hesy
 * @version BankRepayFreezeOrgCheckResponseBean, v0.1 2018/10/19 17:02
 */
public class BankRepayFreezeOrgCheckResponseBean {
    @ApiModelProperty(value = "冻结状态 0-正常 1-已撤销")
    private String state;
    @ApiModelProperty(value = "银行返回码")
    private String retCode;
    @ApiModelProperty(value = "借款编号")
    private String borrowNid;
    @ApiModelProperty(value = "冻结订单号")
    private String orderId;
    @ApiModelProperty(value = "冻结记录添加时间")
    private Date createTime;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
