/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.common.bean;

import java.math.BigDecimal;

/**
 * @author liubin
 * @version RedisBorrow, v0.1 2018/7/4 9:25
 */
public class RedisBorrow {
    private String borrowNid;
    private BigDecimal borrowAccountWait;

    public String getBorrowNid() {
        return borrowNid;
    }
    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
    public BigDecimal getBorrowAccountWait() {
        return borrowAccountWait;
    }
    public void setBorrowAccountWait(BigDecimal borrowAccountWait) {
        this.borrowAccountWait = borrowAccountWait;
    }
}
