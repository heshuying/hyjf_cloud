package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/4
 * @Description: 汇计划-转让列表参数实体
 */
public class HjhDebtCreditListRequest extends BasePage{


    @ApiModelProperty(value = "出让人智投编号")
    private String planNid;
    @ApiModelProperty(value = "出让人智投订单号")
    private String planOrderId;
    @ApiModelProperty(value = "出让人出借订单号")
    private String orderId;
    @ApiModelProperty(value = "出让人承接订单号")
    private String assignId;
    @ApiModelProperty(value = "清算后智投编号")
    private String planNidNew;
    @ApiModelProperty(value = "出让人")
    private String userName;
    @ApiModelProperty(value = "债转编号")
    private String creditNid;
    @ApiModelProperty(value = "原项目编号")
    private String borrowNid;
    @ApiModelProperty(value = "还款方式")
    private String repayStyle;
    @ApiModelProperty(value = "转让状态")
    private String creditStatus;
    @ApiModelProperty(value = "还款状态")
    private String repayStatus;
    @ApiModelProperty(value = "实际清算开始时间")
    private String liquidatesTimeStart;
    @ApiModelProperty(value = "实际清算结束时间")
    private String liquidatesTimeEnd;
    @ApiModelProperty(value = "当期应还款开始时间")
    private String repayNextTimeStart;
    @ApiModelProperty(value = "当期应还款开始时间")
    private String repayNextTimeEnd;
    @ApiModelProperty(value = "预期承接收益率下限")
    private String actualAprStartSrch;
    @ApiModelProperty(value = "预期承接收益率上限")
    private String actualAprEndSrch;
    @ApiModelProperty(value = "转让标签")
    private String labelName;

    @ApiModelProperty(value = "预计开始退出时间——start")
    private String endDateStart;
    @ApiModelProperty(value = "预计开始退出时间--end")
    private String endDateEnd;


    public String getEndDateStart() {
        return endDateStart;
    }

    public void setEndDateStart(String endDateStart) {
        this.endDateStart = endDateStart;
    }

    public String getEndDateEnd() {
        return endDateEnd;
    }

    public void setEndDateEnd(String endDateEnd) {
        this.endDateEnd = endDateEnd;
    }

    public String getActualAprStartSrch() {
        return actualAprStartSrch;
    }

    public void setActualAprStartSrch(String actualAprStartSrch) {
        this.actualAprStartSrch = actualAprStartSrch;
    }

    public String getActualAprEndSrch() {
        return actualAprEndSrch;
    }

    public void setActualAprEndSrch(String actualAprEndSrch) {
        this.actualAprEndSrch = actualAprEndSrch;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getPlanOrderId() {
        return planOrderId;
    }

    public void setPlanOrderId(String planOrderId) {
        this.planOrderId = planOrderId;
    }

    public String getPlanNidNew() {
        return planNidNew;
    }

    public void setPlanNidNew(String planNidNew) {
        this.planNidNew = planNidNew;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getRepayStyle() {
        return repayStyle;
    }

    public void setRepayStyle(String repayStyle) {
        this.repayStyle = repayStyle;
    }

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAssignId() {
        return assignId;
    }

    public void setAssignId(String assignId) {
        this.assignId = assignId;
    }

    @Override
    public String toString() {
        return "HjhDebtCreditListRequest{" +
                "planNid='" + planNid + '\'' +
                ", planOrderId='" + planOrderId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", assignId='" + assignId + '\'' +
                ", planNidNew='" + planNidNew + '\'' +
                ", userName='" + userName + '\'' +
                ", creditNid='" + creditNid + '\'' +
                ", borrowNid='" + borrowNid + '\'' +
                ", repayStyle='" + repayStyle + '\'' +
                ", creditStatus='" + creditStatus + '\'' +
                ", repayStatus='" + repayStatus + '\'' +
                ", liquidatesTimeStart='" + liquidatesTimeStart + '\'' +
                ", liquidatesTimeEnd='" + liquidatesTimeEnd + '\'' +
                ", repayNextTimeStart='" + repayNextTimeStart + '\'' +
                ", repayNextTimeEnd='" + repayNextTimeEnd + '\'' +
                '}';
    }
}
