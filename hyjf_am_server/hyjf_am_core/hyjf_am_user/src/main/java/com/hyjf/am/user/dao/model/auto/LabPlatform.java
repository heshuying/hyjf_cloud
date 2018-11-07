package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class LabPlatform implements Serializable {
    /**
     * 平台ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 平台id
     *
     * @mbggenerated
     */
    private Integer pid;

    /**
     * 关联用户
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 平台用户名
     *
     * @mbggenerated
     */
    private String username;

    /**
     * 平台密码
     *
     * @mbggenerated
     */
    private String password;

    /**
     * 固定IP地址
     *
     * @mbggenerated
     */
    private String ip;

    /**
     * 平台名称
     *
     * @mbggenerated
     */
    private String pName;

    /**
     * 接口开放状态：0为不开放，1为开放
     *
     * @mbggenerated
     */
    private Integer status;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName == null ? null : pName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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