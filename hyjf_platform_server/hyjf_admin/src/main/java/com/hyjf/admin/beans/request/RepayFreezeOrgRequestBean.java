package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author hesy
 * @version RepayFreezeOrgRequestBean, v0.1 2018/10/19 9:53
 */
public class RepayFreezeOrgRequestBean extends BasePage {
    @ApiModelProperty(value = "借款编号")
    private String borrowNid;
    @ApiModelProperty(value = "资产来源列表")
    private String instCode;
    @ApiModelProperty(value = "计划编号")
    private String planNid;
    @ApiModelProperty(value = "借款人用户名")
    private String borrowUserName;
    @ApiModelProperty(value = "还款人用户名")
    private String repayUserName;
    @ApiModelProperty(value = "冻结订单号")
    private String orderId;
    @ApiModelProperty(value = "还款申请提交开始时间")
    private String submitTimeStartSrch;
    @ApiModelProperty(value = "还款申请提交结束时间")
    private String submitTimeEndSrch;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public String getRepayUserName() {
        return repayUserName;
    }

    public void setRepayUserName(String repayUserName) {
        this.repayUserName = repayUserName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSubmitTimeStartSrch() {
        return submitTimeStartSrch;
    }

    public void setSubmitTimeStartSrch(String submitTimeStartSrch) {
        this.submitTimeStartSrch = submitTimeStartSrch;
    }

    public String getSubmitTimeEndSrch() {
        return submitTimeEndSrch;
    }

    public void setSubmitTimeEndSrch(String submitTimeEndSrch) {
        this.submitTimeEndSrch = submitTimeEndSrch;
    }
}
