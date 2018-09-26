/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin.locked;

import com.hyjf.am.vo.BaseVO;

import java.util.Date;

/**
 * @author cui
 * @version LockedUserInfoVO, v0.1 2018/9/21 10:46
 */
public class LockedUserInfoVO extends BaseVO {

    private Integer id;

    private Integer userid;

    private String username;

    private String mobile;

    private Integer front;

    private Integer unlocked;

    private Date lockTime;

    private Date unlockTime;

    private String lockTimeStr;

    private Integer operator;

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
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getLockTimeStr() {
        return lockTimeStr;
    }

    public void setLockTimeStr(String lockTimeStr) {
        this.lockTimeStr = lockTimeStr;
    }
}
