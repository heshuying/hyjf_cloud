package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;

public class MspQuerydetail implements Serializable {
    private Integer id;

    private String applyId;

    private String querydate;

    private String membertype;

    private String querytype;

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

    public String getQuerydate() {
        return querydate;
    }

    public void setQuerydate(String querydate) {
        this.querydate = querydate == null ? null : querydate.trim();
    }

    public String getMembertype() {
        return membertype;
    }

    public void setMembertype(String membertype) {
        this.membertype = membertype == null ? null : membertype.trim();
    }

    public String getQuerytype() {
        return querytype;
    }

    public void setQuerytype(String querytype) {
        this.querytype = querytype == null ? null : querytype.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}