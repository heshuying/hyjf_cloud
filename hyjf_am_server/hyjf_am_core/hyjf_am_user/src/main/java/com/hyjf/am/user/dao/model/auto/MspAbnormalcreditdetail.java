package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;

public class MspAbnormalcreditdetail implements Serializable {
    private Integer id;

    private String abcdId;

    private String checkoverduedate;

    private String overduedays;

    private String overduereason;

    private String overduestate;

    private String opertime;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAbcdId() {
        return abcdId;
    }

    public void setAbcdId(String abcdId) {
        this.abcdId = abcdId == null ? null : abcdId.trim();
    }

    public String getCheckoverduedate() {
        return checkoverduedate;
    }

    public void setCheckoverduedate(String checkoverduedate) {
        this.checkoverduedate = checkoverduedate == null ? null : checkoverduedate.trim();
    }

    public String getOverduedays() {
        return overduedays;
    }

    public void setOverduedays(String overduedays) {
        this.overduedays = overduedays == null ? null : overduedays.trim();
    }

    public String getOverduereason() {
        return overduereason;
    }

    public void setOverduereason(String overduereason) {
        this.overduereason = overduereason == null ? null : overduereason.trim();
    }

    public String getOverduestate() {
        return overduestate;
    }

    public void setOverduestate(String overduestate) {
        this.overduestate = overduestate == null ? null : overduestate.trim();
    }

    public String getOpertime() {
        return opertime;
    }

    public void setOpertime(String opertime) {
        this.opertime = opertime == null ? null : opertime.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}