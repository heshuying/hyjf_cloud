package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class LockedUserInfo implements Serializable {
    private Integer id;

    /**
     * 锁定用户ID
     *
     * @mbggenerated
     */
    private Integer userid;

    /**
     * 锁定用户用户名
     *
     * @mbggenerated
     */
    private String username;

    /**
     * 用户手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 是否是前端锁定用户1：前端 0：后台
     *
     * @mbggenerated
     */
    private Integer front;

    /**
     * 是否被解锁 1：已解锁 0：锁定
     *
     * @mbggenerated
     */
    private Integer unlocked;

    /**
     * 被锁定时间（最后一次登录失败时间）
     *
     * @mbggenerated
     */
    private Date lockTime;

    /**
     * 解锁时间(自动解锁是redis过期时间)
     *
     * @mbggenerated
     */
    private Date unlockTime;

    /**
     * 解锁用户ID
     *
     * @mbggenerated
     */
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