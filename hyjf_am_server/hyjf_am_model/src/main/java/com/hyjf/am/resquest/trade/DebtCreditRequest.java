package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;

import java.util.List;

public class DebtCreditRequest extends Request {

    private String borrowNid;

    private List<Integer> creditStatus;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public List<Integer> getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(List<Integer> creditStatus) {
        this.creditStatus = creditStatus;
    }
}
