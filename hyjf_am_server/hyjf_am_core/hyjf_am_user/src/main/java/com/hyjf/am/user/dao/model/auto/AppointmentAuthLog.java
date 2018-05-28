package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class AppointmentAuthLog implements Serializable {
    private Integer id;

    private Integer userId;

    private Boolean authType;

    private Boolean authStatus;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getAuthType() {
        return authType;
    }

    public void setAuthType(Boolean authType) {
        this.authType = authType;
    }

    public Boolean getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Boolean authStatus) {
        this.authStatus = authStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}