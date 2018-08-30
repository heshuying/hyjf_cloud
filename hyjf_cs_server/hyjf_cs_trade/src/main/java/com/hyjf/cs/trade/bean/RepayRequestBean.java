package com.hyjf.cs.trade.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * @version RepayRequestBean, v0.1 2018/8/27 14:46
 * @Author: Zha Daojian
 */
public class RepayRequestBean extends BaseBean {

    @ApiModelProperty(value = "查询类型 0:待还款 1:已还款")
    private String repayType;

    @ApiModelProperty(value = "标的号")
    private String borrowNid;

    @ApiModelProperty(value = "资产编号")
    private String productId;

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
