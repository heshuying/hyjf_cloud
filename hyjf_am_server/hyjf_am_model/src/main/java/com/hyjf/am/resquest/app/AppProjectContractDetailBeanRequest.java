/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.app;

import com.hyjf.am.vo.BasePage;

/**
 * @author jun
 * @version AppProjectContractDetailBeanRequest, v0.1 2018/7/30 11:45
 */
public class AppProjectContractDetailBeanRequest extends BasePage {

    private String borrowNid;

    private String orderId;

    private String userId;


    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
