/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;

/**
 * @author jun
 * @version HjhDebtCreditRequest, v0.1 2018/6/29 13:23
 */
public class HjhDebtCreditRequest extends Request {

    private String borrowNid;

    private String creditNid;

    private String investOrderId;

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
}
