package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

public class CreditListVO extends BaseVO  implements Serializable {

    private String projectName;

    private String bidName;

    private String creditNid;

    private String bidApr;

    private String creditTerm;

    private String creditDiscount;

    private String creditCapital;

    private String creditInProgress;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBidName() {
        return bidName;
    }

    public void setBidName(String bidName) {
        this.bidName = bidName;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getBidApr() {
        return bidApr;
    }

    public void setBidApr(String bidApr) {
        this.bidApr = bidApr;
    }

    public String getCreditTerm() {
        return creditTerm;
    }

    public void setCreditTerm(String creditTerm) {
        this.creditTerm = creditTerm;
    }

    public String getCreditDiscount() {
        return creditDiscount;
    }

    public void setCreditDiscount(String creditDiscount) {
        this.creditDiscount = creditDiscount;
    }

    public String getCreditCapital() {
        return creditCapital;
    }

    public void setCreditCapital(String creditCapital) {
        this.creditCapital = creditCapital;
    }

    public String getCreditInProgress() {
        return creditInProgress;
    }

    public void setCreditInProgress(String creditInProgress) {
        this.creditInProgress = creditInProgress;
    }
}
