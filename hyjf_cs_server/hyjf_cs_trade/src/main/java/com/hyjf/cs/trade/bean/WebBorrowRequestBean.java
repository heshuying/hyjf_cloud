package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class WebBorrowRequestBean  extends BasePage implements Serializable {

    @ApiModelProperty(value = "标的编号")
    private String borrowNid;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
