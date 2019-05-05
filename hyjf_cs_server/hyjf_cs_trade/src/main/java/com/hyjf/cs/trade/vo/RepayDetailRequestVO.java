package com.hyjf.cs.trade.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author hesy
 * @version RepayDetailRequestVO, v0.1 2018/8/4 14:09
 */
public class RepayDetailRequestVO {
    @ApiModelProperty(value = "标的编号")
    String borrowNid;
    @ApiModelProperty(value = "是否一次性还款：1 是 0 否")
    String isAllRepay;
    @ApiModelProperty(value = "逾期还款选择的期数")
    Integer latePeriod;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getIsAllRepay() {
        return isAllRepay;
    }

    public void setIsAllRepay(String isAllRepay) {
        this.isAllRepay = isAllRepay;
    }

    public Integer getLatePeriod() {
        return latePeriod;
    }

    public void setLatePeriod(Integer latePeriod) {
        this.latePeriod = latePeriod;
    }
}
