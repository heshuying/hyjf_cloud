package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @version BorrowRepayAgreementRequest, v0.1 2018/8/10 15:05
 * @Author: Zha Daojian
 */
public class DownloadAgreementRequest{

    @ApiModelProperty(value = "期数")
    private String repayPeriod;
    @ApiModelProperty(value = "借款编号")
    private String borrowNid;

    @ApiModelProperty(value = "协议类型，1:脱敏，0：原始")
    private String status;

    public String getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(String repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
