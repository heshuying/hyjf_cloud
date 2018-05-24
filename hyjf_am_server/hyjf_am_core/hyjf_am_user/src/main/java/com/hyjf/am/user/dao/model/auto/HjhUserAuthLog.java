package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class HjhUserAuthLog implements Serializable {
    private Integer id;

    private Integer userId;

    private String userName;

    private String orderId;

    private Integer orderStatus;

    private Integer authType;

    private Integer operateEsb;

    private Date authCreateTime;

    private Integer delFlg;

    private Integer createUser;

    private Integer updateUser;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    public Integer getOperateEsb() {
        return operateEsb;
    }

    public void setOperateEsb(Integer operateEsb) {
        this.operateEsb = operateEsb;
    }

    public Date getAuthCreateTime() {
        return authCreateTime;
    }

    public void setAuthCreateTime(Date authCreateTime) {
        this.authCreateTime = authCreateTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
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