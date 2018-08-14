/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.app;

import com.hyjf.am.vo.BasePage;

/**
 * @author jun
 * @version AppRepayPlanListBeanRequest, v0.1 2018/7/30 8:39
 */
public class AppRepayPlanListBeanRequest extends BasePage {

    private String userId;

    private String borrowNid;

    private String orderId;

    private String creditNid;

    private String assignNid;

    private int limitStart;

    private int limitEnd;

    private String borrowStyle;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid;
    }

    public String getAssignNid() {
        return assignNid;
    }
}
