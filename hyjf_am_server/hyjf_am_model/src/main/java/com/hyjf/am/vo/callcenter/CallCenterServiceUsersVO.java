package com.hyjf.am.vo.callcenter;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

public class CallCenterServiceUsersVO extends BaseVO implements Serializable {
    private Integer id;

    private String username;

    private Integer assigned;

    private Date insdate;

    private Date upddate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getAssigned() {
        return assigned;
    }

    public void setAssigned(Integer assigned) {
        this.assigned = assigned;
    }

    public Date getInsdate() {
        return insdate;
    }

    public void setInsdate(Date insdate) {
        this.insdate = insdate;
    }

    public Date getUpddate() {
        return upddate;
    }

    public void setUpddate(Date upddate) {
        this.upddate = upddate;
    }
}