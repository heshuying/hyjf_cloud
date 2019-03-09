package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class HjhReInvestDebtRequest extends BasePage implements Serializable {

    @ApiModelProperty(value = "计划编号")
    private String planNid;

    @ApiModelProperty(value = "日期开始")
    private String date;

    @ApiModelProperty(value = "计划订单号")
    private String assignPlanOrderIdSrch;

    @ApiModelProperty(value = "承接计划编号")
    private String assignPlanNidSrch;

    @ApiModelProperty(value = "承接订单号")
    private String assignOrderIdSrch;

    @ApiModelProperty(value = "承接人")
    private String userNameSrch;

    @ApiModelProperty(value = "出让人")
    private String creditUserNameSrch;

    @ApiModelProperty(value = "债转编号")
    private String creditNidSrch;

    @ApiModelProperty(value = "原项目编号")
    private String borrowNidSrch;

    @ApiModelProperty(value = "承接方式")
    private String assignTypeSrch;

    @ApiModelProperty(value = "还款方式")
    private String borrowStyleSrch;


    /**
     * 分页变量
     */
    private int limitStart = -1;
    private int limitEnd = -1;

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAssignPlanOrderIdSrch() {
        return assignPlanOrderIdSrch;
    }

    public void setAssignPlanOrderIdSrch(String assignPlanOrderIdSrch) {
        this.assignPlanOrderIdSrch = assignPlanOrderIdSrch;
    }

    public String getAssignPlanNidSrch() {
        return assignPlanNidSrch;
    }

    public void setAssignPlanNidSrch(String assignPlanNidSrch) {
        this.assignPlanNidSrch = assignPlanNidSrch;
    }

    public String getAssignOrderIdSrch() {
        return assignOrderIdSrch;
    }

    public void setAssignOrderIdSrch(String assignOrderIdSrch) {
        this.assignOrderIdSrch = assignOrderIdSrch;
    }

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getCreditUserNameSrch() {
        return creditUserNameSrch;
    }

    public void setCreditUserNameSrch(String creditUserNameSrch) {
        this.creditUserNameSrch = creditUserNameSrch;
    }

    public String getCreditNidSrch() {
        return creditNidSrch;
    }

    public void setCreditNidSrch(String creditNidSrch) {
        this.creditNidSrch = creditNidSrch;
    }

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getAssignTypeSrch() {
        return assignTypeSrch;
    }

    public void setAssignTypeSrch(String assignTypeSrch) {
        this.assignTypeSrch = assignTypeSrch;
    }

    public String getBorrowStyleSrch() {
        return borrowStyleSrch;
    }

    public void setBorrowStyleSrch(String borrowStyleSrch) {
        this.borrowStyleSrch = borrowStyleSrch;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
}
