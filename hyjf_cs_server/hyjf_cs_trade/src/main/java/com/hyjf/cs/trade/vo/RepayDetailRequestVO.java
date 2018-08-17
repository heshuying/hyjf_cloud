package com.hyjf.cs.trade.vo;

/**
 * @author hesy
 * @version RepayDetailRequestVO, v0.1 2018/8/4 14:09
 */
public class RepayDetailRequestVO {
    String borrowNid;

    String isAllRepay;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getIsAllRepay() {
        return isAllRepay;
    }

    public void setIsAllRepay(String isAllRepay) {
        this.isAllRepay = isAllRepay;
    }
}
