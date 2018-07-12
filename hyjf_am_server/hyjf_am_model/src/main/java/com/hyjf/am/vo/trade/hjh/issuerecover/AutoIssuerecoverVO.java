package com.hyjf.am.vo.trade.hjh.issuerecover;

import java.io.Serializable;

public class AutoIssuerecoverVO implements Serializable {
    private static final long serialVersionUID = -4063164049843296435L;

    /** 用户加入计划ID*/
    private Integer planId;
    /** 标的编号*/
    private String borrowNid;

    /** 资产编号 */
    private String assetId;

    /** 债转编号*/
    private String creditNid;

    /** 机构编号*/
    private String instCode;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }
}
