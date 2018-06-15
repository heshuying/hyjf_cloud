package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BorrowBail implements Serializable {
    private Integer id;

    private String borrowNid;

    private Integer borrowUid;

    private Integer operaterUid;

    private BigDecimal bailNum;

    private Date updateTime;

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

    public BigDecimal getBailNum() {
        return bailNum;
    }

    public void setBailNum(BigDecimal bailNum) {
        this.bailNum = bailNum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}