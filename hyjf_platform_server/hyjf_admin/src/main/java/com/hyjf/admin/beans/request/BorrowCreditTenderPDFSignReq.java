package com.hyjf.admin.beans.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class BorrowCreditTenderPDFSignReq implements Serializable {

    @ApiModelProperty(value = "标的编号")
    private String borrowNid;

    @ApiModelProperty(value = "承接订单号")
    private String assignNid;

    @ApiModelProperty(value = "原始投资订单号")
    private String creditTenderNid;

    @ApiModelProperty(value = "债转编号")
    private String creditNid;

    @ApiModelProperty(value = "用户id")
    private String userId;


    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getAssignNid() {
        return assignNid;
    }

    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid;
    }

    public String getCreditTenderNid() {
        return creditTenderNid;
    }

    public void setCreditTenderNid(String creditTenderNid) {
        this.creditTenderNid = creditTenderNid;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
