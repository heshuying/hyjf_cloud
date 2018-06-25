package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class FreezeHistory implements Serializable {
    private Integer id;

    private String trxId;

    private String notes;

    private String freezeUser;

    private Integer freezeTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId == null ? null : trxId.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getFreezeUser() {
        return freezeUser;
    }

    public void setFreezeUser(String freezeUser) {
        this.freezeUser = freezeUser == null ? null : freezeUser.trim();
    }

    public Integer getFreezeTime() {
        return freezeTime;
    }

    public void setFreezeTime(Integer freezeTime) {
        this.freezeTime = freezeTime;
    }
}