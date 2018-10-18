package com.hyjf.am.vo.admin;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class HjhUserAuthConfigCustomizeVO implements Serializable {

    private Integer id;
    private Integer authType;
    private Integer personalMaxAmount;
    private Integer enterpriseMaxAmount;
    private Integer authPeriod;
    private Integer enabledStatus;
    private String remark;

    private Integer createUser;

    private Integer createTime;

    private Integer updateUser;

    private String updateUserStr;

    private Integer updateTime;

    private String updateTimeStr;

    private String ip;

    private static final long serialVersionUID = 1L;

    public String getUpdateUserStr() {
        return updateUserStr;
    }

    public void setUpdateUserStr(String updateUserStr) {
        this.updateUserStr = updateUserStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}