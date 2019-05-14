package com.hyjf.am.resquest.trade;

import io.swagger.annotations.ApiModelProperty;

/**
 * 还款申请
 * @author hesy
 * @version RepayRequest, v0.1 2018/7/10 11:01
 */
public class RepayRequest {
    @ApiModelProperty(value = "标的编号")
    private String borrowNid;
    @ApiModelProperty(value = "平台登录密码")
    private String password;
    @ApiModelProperty(value = "是否一次性还款 0 否 1 是")
    private String isAllRepay;
    @ApiModelProperty(value = "逾期还款选择的期数")
    Integer latePeriod;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
