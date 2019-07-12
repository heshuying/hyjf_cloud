package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class RUser implements Serializable {
    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String username;

    /**
     * 手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 银行预留手机号
     *
     * @mbggenerated
     */
    private String bankMobile;

    /**
     * 用户角色1投资人2借款人3担保机构
     *
     * @mbggenerated
     */
    private Integer roleId;

    /**
     * 真实姓名
     *
     * @mbggenerated
     */
    private String truename;

    /**
     * 用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工
     *
     * @mbggenerated
     */
    private Integer attribute;

    private Integer spreadsUserId;

    /**
     * 用户类型 0普通用户 1企业用户
     *
     * @mbggenerated
     */
    private Integer userType;

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

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile == null ? null : bankMobile.trim();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename == null ? null : truename.trim();
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public Integer getSpreadsUserId() {
        return spreadsUserId;
    }

    public void setSpreadsUserId(Integer spreadsUserId) {
        this.spreadsUserId = spreadsUserId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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