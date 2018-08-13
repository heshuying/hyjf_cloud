package com.hyjf.admin.beans.request;

import io.swagger.annotations.ApiModelProperty;

public class DayCreditDetailRequestBean {

    //转让状态
    @ApiModelProperty(value = "转让状态")
    private String creditStatus;

    //还款状态
    @ApiModelProperty(value = "还款状态")
    private String repayStatus;

    //检索条件 项目还款方式
    @ApiModelProperty(value = "项目还款方式")
    private String repayStyle;

    //检索条件 清算时间开始
    @ApiModelProperty(value = "清算时间开始")
    private String liquidatesTimeStart;

    //检索条件 清算时间结束
    @ApiModelProperty(value = "清算时间结束")
    private String liquidatesTimeEnd;

    //检索条件 还款时间开始
    @ApiModelProperty(value = "转让状态")
    private String repayNextTimeStart;

    //检索条件 还款时间结束
    @ApiModelProperty(value = "还款时间结束")
    private String repayNextTimeEnd;

    //检索条件 债转结束时间开始
    @ApiModelProperty(value = "债转结束时间开始")
    private String endTimeStart;

    //检索条件 债转结束时间结束
    @ApiModelProperty(value = "债转结束时间结束")
    private String endTimeEnd;

    //前画面传入检索条件 清算日期
    @ApiModelProperty(value = "清算日期")
    private String date;

    //画面传入检索条件 转让人标的Nid
    @ApiModelProperty(value = "转让人标的Nid")
    private String planNid;

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getRepayStyle() {
        return repayStyle;
    }

    public void setRepayStyle(String repayStyle) {
        this.repayStyle = repayStyle;
    }

    public String getLiquidatesTimeStart() {
        return liquidatesTimeStart;
    }

    public void setLiquidatesTimeStart(String liquidatesTimeStart) {
        this.liquidatesTimeStart = liquidatesTimeStart;
    }

    public String getLiquidatesTimeEnd() {
        return liquidatesTimeEnd;
    }

    public void setLiquidatesTimeEnd(String liquidatesTimeEnd) {
        this.liquidatesTimeEnd = liquidatesTimeEnd;
    }

    public String getRepayNextTimeStart() {
        return repayNextTimeStart;
    }

    public void setRepayNextTimeStart(String repayNextTimeStart) {
        this.repayNextTimeStart = repayNextTimeStart;
    }

    public String getRepayNextTimeEnd() {
        return repayNextTimeEnd;
    }

    public void setRepayNextTimeEnd(String repayNextTimeEnd) {
        this.repayNextTimeEnd = repayNextTimeEnd;
    }

    public String getEndTimeStart() {
        return endTimeStart;
    }

    public void setEndTimeStart(String endTimeStart) {
        this.endTimeStart = endTimeStart;
    }

    public String getEndTimeEnd() {
        return endTimeEnd;
    }

    public void setEndTimeEnd(String endTimeEnd) {
        this.endTimeEnd = endTimeEnd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }
}
