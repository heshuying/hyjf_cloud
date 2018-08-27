package com.hyjf.cs.trade.bean;

import java.io.Serializable;

public class WebPlanBorrowBean implements Serializable {

    private String borrowNid;

    private String name;

    private String borrowPeriodStr;

    private String borrowUserName;

    private String financePurpose;


    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBorrowPeriodStr() {
        return borrowPeriodStr;
    }

    public void setBorrowPeriodStr(String borrowPeriodStr) {
        this.borrowPeriodStr = borrowPeriodStr;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public String getFinancePurpose() {
        return financePurpose;
    }

    public void setFinancePurpose(String financePurpose) {
        this.financePurpose = financePurpose;
    }
}
