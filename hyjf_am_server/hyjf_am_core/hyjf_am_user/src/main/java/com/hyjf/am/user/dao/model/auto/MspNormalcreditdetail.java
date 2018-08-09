package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;

public class MspNormalcreditdetail implements Serializable {
    private Integer id;

    private String applyId;

    private String creditstartdate;

    private String creditenddate;

    private String creditaddress;

    private String loantype;

    private String assuretype;

    private String loanmoney;

    private String loanperiods;

    private String remark;

    private String num;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getCreditstartdate() {
        return creditstartdate;
    }

    public void setCreditstartdate(String creditstartdate) {
        this.creditstartdate = creditstartdate == null ? null : creditstartdate.trim();
    }

    public String getCreditenddate() {
        return creditenddate;
    }

    public void setCreditenddate(String creditenddate) {
        this.creditenddate = creditenddate == null ? null : creditenddate.trim();
    }

    public String getCreditaddress() {
        return creditaddress;
    }

    public void setCreditaddress(String creditaddress) {
        this.creditaddress = creditaddress == null ? null : creditaddress.trim();
    }

    public String getLoantype() {
        return loantype;
    }

    public void setLoantype(String loantype) {
        this.loantype = loantype == null ? null : loantype.trim();
    }

    public String getAssuretype() {
        return assuretype;
    }

    public void setAssuretype(String assuretype) {
        this.assuretype = assuretype == null ? null : assuretype.trim();
    }

    public String getLoanmoney() {
        return loanmoney;
    }

    public void setLoanmoney(String loanmoney) {
        this.loanmoney = loanmoney == null ? null : loanmoney.trim();
    }

    public String getLoanperiods() {
        return loanperiods;
    }

    public void setLoanperiods(String loanperiods) {
        this.loanperiods = loanperiods == null ? null : loanperiods.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }
}