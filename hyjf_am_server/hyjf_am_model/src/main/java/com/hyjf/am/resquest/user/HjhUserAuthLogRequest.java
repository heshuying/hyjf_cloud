package com.hyjf.am.resquest.user;

import java.io.Serializable;
import java.util.Date;
/**
 * @author nxl
 * @version HjhUserAuthLogRequest, v0.1 2018/1/21 22:38
 */
public class HjhUserAuthLogRequest implements Serializable {
    private Integer id;

    private Integer userId;

    private String userName;

    private String orderId;

    private Integer orderStatus;

    private Integer authType;

    private Integer operateEsb;

    private Integer authCreateTime;

    private Integer delFlag;

    private Integer createUserId;

    private Integer updateUserId;

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

    public Integer getAuthCreateTime() {
        return authCreateTime;
    }

    public void setAuthCreateTime(Integer authCreateTime) {
        this.authCreateTime = authCreateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
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