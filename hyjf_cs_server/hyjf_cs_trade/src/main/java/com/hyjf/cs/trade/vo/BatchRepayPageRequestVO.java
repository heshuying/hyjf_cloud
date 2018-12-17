package com.hyjf.cs.trade.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author hesy
 * @version RepayDetailRequestVO, v0.1 2018/8/4 14:09
 */
public class BatchRepayPageRequestVO {
    @ApiModelProperty(value = "用户id")
    String userId;
    @ApiModelProperty(value = "起始时间")
    String startDate;
    @ApiModelProperty(value = "结束时间")
    String endDate;
    @ApiModelProperty(value = "总的还款金额")
    String repayTotal;
    @ApiModelProperty(value = "平台登录密码")
    String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRepayTotal() {
        return repayTotal;
    }

    public void setRepayTotal(String repayTotal) {
        this.repayTotal = repayTotal;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
