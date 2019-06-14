/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 异常标的备案撤销
 * @author hesy
 */
public class BorrowRegistCancelRequestBean extends BaseRequest implements Serializable {

    @ApiModelProperty(value = "项目编号")
    private String borrowNid;

    @ApiModelProperty(value = "借款人电子账号")
    private String borrowAccountId;

    @ApiModelProperty(value = "募集日")
    private String raiseDate;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowAccountId() {
        return borrowAccountId;
    }

    public void setBorrowAccountId(String borrowAccountId) {
        this.borrowAccountId = borrowAccountId;
    }

    public String getRaiseDate() {
        return raiseDate;
    }

    public void setRaiseDate(String raiseDate) {
        this.raiseDate = raiseDate;
    }
}
