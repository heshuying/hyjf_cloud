package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author : huanghui
 */
public class HjhRepayRequestBean extends BasePage {

    @ApiModelProperty(value = "计划订单号")
    private String accedeOrderIdSrch;

    @ApiModelProperty(value = "计划编号")
    private String planNidSrch;

    @ApiModelProperty(value = "用户名")
    private String userNameSrch;

    @ApiModelProperty(value = "锁定期")
    private String debtLockPeriodSrch;

    @ApiModelProperty(value = "推荐人")
    private String refereeNameSrch;

    @ApiModelProperty(value = "订单状态")
    private String orderStatusSrch;

    @ApiModelProperty(value = "清算开始日期")
    private String repayTimeStart;

    @ApiModelProperty(value = "清算结束日期")
    private String repayTimeEnd;

    @ApiModelProperty(value = "实际退出开始日期")
    private String actulRepayTimeStart;

    @ApiModelProperty(value = "实际退出结束日期")
    private String actulRepayTimeEnd;
    /**
     * 检索条件 还款方式
     */
    private String borrowStyleSrch;

    /**
     * 检索条件 回款状态：0 未回款，1 部分回款 2 已回款'
     */
    private String repayStatusSrch;

    public String getAccedeOrderIdSrch() {
        return accedeOrderIdSrch;
    }

    public void setAccedeOrderIdSrch(String accedeOrderIdSrch) {
        this.accedeOrderIdSrch = accedeOrderIdSrch;
    }

    public String getPlanNidSrch() {
        return planNidSrch;
    }

    public void setPlanNidSrch(String planNidSrch) {
        this.planNidSrch = planNidSrch;
    }

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getDebtLockPeriodSrch() {
        return debtLockPeriodSrch;
    }

    public void setDebtLockPeriodSrch(String debtLockPeriodSrch) {
        this.debtLockPeriodSrch = debtLockPeriodSrch;
    }

    public String getRefereeNameSrch() {
        return refereeNameSrch;
    }

    public void setRefereeNameSrch(String refereeNameSrch) {
        this.refereeNameSrch = refereeNameSrch;
    }

    public String getOrderStatusSrch() {
        return orderStatusSrch;
    }

    public void setOrderStatusSrch(String orderStatusSrch) {
        this.orderStatusSrch = orderStatusSrch;
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

    public String getActulRepayTimeStart() {
        return actulRepayTimeStart;
    }

    public void setActulRepayTimeStart(String actulRepayTimeStart) {
        this.actulRepayTimeStart = actulRepayTimeStart;
    }

    public String getActulRepayTimeEnd() {
        return actulRepayTimeEnd;
    }

    public void setActulRepayTimeEnd(String actulRepayTimeEnd) {
        this.actulRepayTimeEnd = actulRepayTimeEnd;
    }

    public String getBorrowStyleSrch() {
        return borrowStyleSrch;
    }

    public void setBorrowStyleSrch(String borrowStyleSrch) {
        this.borrowStyleSrch = borrowStyleSrch;
    }

    public String getRepayStatusSrch() {
        return repayStatusSrch;
    }

    public void setRepayStatusSrch(String repayStatusSrch) {
        this.repayStatusSrch = repayStatusSrch;
    }
}
