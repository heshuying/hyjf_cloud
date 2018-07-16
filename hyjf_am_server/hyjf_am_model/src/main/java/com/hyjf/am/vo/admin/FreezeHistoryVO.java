/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: FreezeHistoryVO, v0.1 2018/7/11 13:58
 */
public class FreezeHistoryVO extends BaseVO implements Serializable {
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
        this.trxId = trxId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFreezeUser() {
        return freezeUser;
    }

    public void setFreezeUser(String freezeUser) {
        this.freezeUser = freezeUser;
    }

    public Integer getFreezeTime() {
        return freezeTime;
    }

    public void setFreezeTime(Integer freezeTime) {
        this.freezeTime = freezeTime;
    }
}
