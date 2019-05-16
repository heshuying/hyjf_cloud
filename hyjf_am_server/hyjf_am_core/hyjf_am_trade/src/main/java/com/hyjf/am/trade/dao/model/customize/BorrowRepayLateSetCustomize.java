/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author wangjun
 * @version BorrowRepayLateCustomize, v0.1 2019/3/21 11:11
 */
public class BorrowRepayLateSetCustomize implements Serializable {
    /**
     * 标的编号
     */
    private String borrowNid;

    /**
     * 标的期数
     */
    private Integer recoverPeriod;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public Integer getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(Integer recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public BorrowRepayLateSetCustomize(String borrowNid, Integer recoverPeriod) {
        this.setBorrowNid(borrowNid);
        this.setRecoverPeriod(recoverPeriod);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowRepayLateSetCustomize that = (BorrowRepayLateSetCustomize) o;
        return Objects.equals(borrowNid, that.borrowNid) &&
                Objects.equals(recoverPeriod, that.recoverPeriod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(borrowNid, recoverPeriod);
    }
}
