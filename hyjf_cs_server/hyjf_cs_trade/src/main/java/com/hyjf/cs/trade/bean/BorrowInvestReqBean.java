package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author zhangyk
 * @date 2018/8/9 17:34
 */
public class BorrowInvestReqBean extends BasePage implements Serializable {

    private String borrowNid;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
