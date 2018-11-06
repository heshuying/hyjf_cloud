package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class HjhUserAuthConfigLog implements Serializable {
    private Integer id;

    private Integer authConfigId;

    private Integer authType;

    private Integer personalMaxAmount;

    private Integer enterpriseMaxAmount;

    private Integer authPeriod;

    private Integer enabledStatus;

    private String remark;

    private String ip;

    private String mac;

    private Integer createUserId;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthConfigId() {
        return authConfigId;
    }

    public void setAuthConfigId(Integer authConfigId) {
        this.authConfigId = authConfigId;
    }

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    public Integer getPersonalMaxAmount() {
        return personalMaxAmount;
    }

    public void setPersonalMaxAmount(Integer personalMaxAmount) {
        this.personalMaxAmount = personalMaxAmount;
    }

    public Integer getEnterpriseMaxAmount() {
        return enterpriseMaxAmount;
    }

    public void setEnterpriseMaxAmount(Integer enterpriseMaxAmount) {
        this.enterpriseMaxAmount = enterpriseMaxAmount;
    }

    public Integer getAuthPeriod() {
        return authPeriod;
    }

    public void setAuthPeriod(Integer authPeriod) {
        this.authPeriod = authPeriod;
    }

    public Integer getEnabledStatus() {
        return enabledStatus;
    }

    public void setEnabledStatus(Integer enabledStatus) {
        this.enabledStatus = enabledStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac == null ? null : mac.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}