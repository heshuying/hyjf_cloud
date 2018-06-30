/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;

/**
 * @author jun
 * @version HjhDebtCreditTenderRequest, v0.1 2018/6/29 12:08
 */
public class HjhDebtCreditTenderRequest extends Request {

    private String borrowNid;
    private String creditNid;
    private String investOrderId;
    private String assignOrderId;

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setInvestOrderId(String investOrderId) {
        this.investOrderId = investOrderId;
    }

    public String getInvestOrderId() {
        return investOrderId;
    }

    public void setAssignOrderId(String assignOrderId) {
        this.assignOrderId = assignOrderId;
    }

    public String getAssignOrderId() {
        return assignOrderId;
    }
}
