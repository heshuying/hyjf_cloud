/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author hesy
 */
public class BorrowRepayInfoCurrentRequest extends BasePage implements Serializable{
    /** 项目编号*/
    @ApiModelProperty(value = "项目编号")
    private String borrowNid;
    /** 总记录数*/
    @ApiModelProperty(value = "总记录数")
    private Integer count;
    /** 还款期数*/
    @ApiModelProperty(value = "还款期数")
    private String repayPeriod;
    /** 出借订单号*/
    @ApiModelProperty(value = "出借订单号")
    private String tenderOrderId;
    /** 承接订单号*/
    @ApiModelProperty(value = "承接订单号")
    private String assignOrderId;
    /** 还款状态*/
    @ApiModelProperty(value = "还款状态")
    private String repayStatus;
    /** 应还开始时间*/
    @ApiModelProperty(value = "应还开始时间")
    private String repayTimeStart;
    /** 应还结束时间*/
    @ApiModelProperty(value = "应还结束时间")
    private String repayTimeEnd;
    /** 实还开始时间*/
    @ApiModelProperty(value = "实还开始时间")
    private String repayedTimeStart;
    /** 实还结束时间*/
    @ApiModelProperty(value = "实还结束时间")
    private String repayedTimeEnd;
    @ApiModelProperty(value = "是否具有组织架构查看权限")
    private String isOrganizationView;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(String repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public String getTenderOrderId() {
        return tenderOrderId;
    }

    public void setTenderOrderId(String tenderOrderId) {
        this.tenderOrderId = tenderOrderId;
    }

    public String getAssignOrderId() {
        return assignOrderId;
    }

    public void setAssignOrderId(String assinOrderId) {
        this.assignOrderId = assinOrderId;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getRepayTimeStart() {
        return repayTimeStart;
    }

    public void setRepayTimeStart(String repayTimeStart) {
        this.repayTimeStart = repayTimeStart;
    }

    public String getRepayTimeEnd() {
        return repayTimeEnd;
    }

    public void setRepayTimeEnd(String repayTimeEnd) {
        this.repayTimeEnd = repayTimeEnd;
    }

    public String getRepayedTimeStart() {
        return repayedTimeStart;
    }

    public void setRepayedTimeStart(String repayedTimeStart) {
        this.repayedTimeStart = repayedTimeStart;
    }

    public String getRepayedTimeEnd() {
        return repayedTimeEnd;
    }

    public void setRepayedTimeEnd(String repayedTimeEnd) {
        this.repayedTimeEnd = repayedTimeEnd;
    }

    public String getIsOrganizationView() {
        return isOrganizationView;
    }

    public void setIsOrganizationView(String isOrganizationView) {
        this.isOrganizationView = isOrganizationView;
    }
}
