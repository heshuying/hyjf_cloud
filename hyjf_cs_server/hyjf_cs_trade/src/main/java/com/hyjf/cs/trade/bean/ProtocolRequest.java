package com.hyjf.cs.trade.bean;

import java.io.Serializable;

public class ProtocolRequest implements Serializable {

    private String borrowNid;

    private String nid;

    private String projectType;

    private String userId;

    private String flag;

    private String creditUserId;

    public String getCreditUserId() {
        return creditUserId;
    }

    public void setCreditUserId(String creditUserId) {
        this.creditUserId = creditUserId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }
}
