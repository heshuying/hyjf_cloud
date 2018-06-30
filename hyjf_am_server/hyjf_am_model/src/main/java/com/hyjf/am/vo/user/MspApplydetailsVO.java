package com.hyjf.am.vo.user;

import java.io.Serializable;

public class MspApplydetailsVO implements Serializable {
    private Integer id;

    private String applyId;

    private String applydate;

    private String membertype;

    private String creditaddress;

    private String loantype;

    private String loanmoney;

    private String applyresult;

    private String remark;

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

    public String getApplydate() {
        return applydate;
    }

    public void setApplydate(String applydate) {
        this.applydate = applydate == null ? null : applydate.trim();
    }

    public String getMembertype() {
        return membertype;
    }

    public void setMembertype(String membertype) {
        this.membertype = membertype == null ? null : membertype.trim();
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

    public String getLoanmoney() {
        return loanmoney;
    }

    public void setLoanmoney(String loanmoney) {
        this.loanmoney = loanmoney == null ? null : loanmoney.trim();
    }

    public String getApplyresult() {
        return applyresult;
    }

    public void setApplyresult(String applyresult) {
        this.applyresult = applyresult == null ? null : applyresult.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}