package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class UserLoginLog implements Serializable {
    /**
     * 用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 登录次数
     *
     * @mbggenerated
     */
    private Integer loginTimes;

    /**
     * 最后登录ip
     *
     * @mbggenerated
     */
    private String loginIp;

    /**
     * 最后登录时间
     *
     * @mbggenerated
     */
    private Date loginTime;

    /**
     * 上一次登录ip
     *
     * @mbggenerated
     */
    private String lastIp;

    /**
     * 上一次登录时间
     *
     * @mbggenerated
     */
    private Date lastTime;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp == null ? null : lastIp.trim();
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
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