package com.hyjf.cs.trade.bean;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class WebBorrowRequestBean implements Serializable {

    @ApiModelProperty(value = "标的编号")
    private String borrowNid;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
