package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class LockedUserInfo implements Serializable {
    private Integer id;

    private Integer userid;

    private String username;

    private String mobile;

    private Integer front;

    private Integer unlocked;

    private Date lockTime;

    private Date unlockTime;

    private Integer operator;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getFront() {
        return front;
    }

    public void setFront(Integer front) {
        this.front = front;
    }

    public Integer getUnlocked() {
        return unlocked;
    }

    public void setUnlocked(Integer unlocked) {
        this.unlocked = unlocked;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public Date getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(Date unlockTime) {
        this.unlockTime = unlockTime;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }
}