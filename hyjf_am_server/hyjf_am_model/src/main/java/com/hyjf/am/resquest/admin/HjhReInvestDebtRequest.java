package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

public class HjhReInvestDebtRequest extends BasePage implements Serializable {

    /**
     * 计划订单号(检索)
     */
    private String planNidSrch;

    /**
     * 承接计划编号(检索)
     */
    private String assignPlanNidSrch;

    /**
     * 承接订单号(检索)
     */
    private String assignPlanOrderIdSrch;

    /**
     * 承接人(检索)
     */
    private String userNameSrch;

    /**
     * 出让人(检索)
     */
    private String creditUserNameSrch;

    /**
     * 债转编号(检索)
     */
    private String creditNidSrch;

    /**
     * 原项目编号(检索)
     */
    private String borrowNidSrch;

    /**
     * 承接方式(检索)
     */
    private String assignTypeSrch;

    /**
     * 还款方式(检索)
     */
    private String borrowStyleSrch;

    public String getPlanNidSrch() {
        return planNidSrch;
    }

    public void setPlanNidSrch(String planNidSrch) {
        this.planNidSrch = planNidSrch;
    }

    public String getAssignPlanNidSrch() {
        return assignPlanNidSrch;
    }

    public void setAssignPlanNidSrch(String assignPlanNidSrch) {
        this.assignPlanNidSrch = assignPlanNidSrch;
    }

    public String getAssignPlanOrderIdSrch() {
        return assignPlanOrderIdSrch;
    }

    public void setAssignPlanOrderIdSrch(String assignPlanOrderIdSrch) {
        this.assignPlanOrderIdSrch = assignPlanOrderIdSrch;
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
}
