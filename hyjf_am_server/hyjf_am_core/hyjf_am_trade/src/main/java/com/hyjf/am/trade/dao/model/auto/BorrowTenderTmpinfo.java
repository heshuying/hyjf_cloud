package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class BorrowTenderTmpinfo implements Serializable {
    private Integer id;

    private String ordid;

    private String tmpArray;

    private Integer addTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrdid() {
        return ordid;
    }

    public void setOrdid(String ordid) {
        this.ordid = ordid == null ? null : ordid.trim();
    }

    public String getTmpArray() {
        return tmpArray;
    }

    public void setTmpArray(String tmpArray) {
        this.tmpArray = tmpArray == null ? null : tmpArray.trim();
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }
}