package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DebtBail implements Serializable {
    private Integer id;

    private String borrowNid;

    private Integer borrowUid;

    private Integer operaterUid;

    private Integer updatetime;

    private BigDecimal bailNum;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getBorrowUid() {
        return borrowUid;
    }

    public void setBorrowUid(Integer borrowUid) {
        this.borrowUid = borrowUid;
    }

    public Integer getOperaterUid() {
        return operaterUid;
    }

    public void setOperaterUid(Integer operaterUid) {
        this.operaterUid = operaterUid;
    }

    public Integer getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Integer updatetime) {
        this.updatetime = updatetime;
    }

    public BigDecimal getBailNum() {
        return bailNum;
    }

    public void setBailNum(BigDecimal bailNum) {
        this.bailNum = bailNum;
    }
}