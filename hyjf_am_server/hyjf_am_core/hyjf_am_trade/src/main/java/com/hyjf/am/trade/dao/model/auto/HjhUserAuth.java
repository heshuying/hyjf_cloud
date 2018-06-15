package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class HjhUserAuth implements Serializable {
    private Integer id;

    private Integer userId;

    private String userName;

    private Integer autoInvesStatus;

    private Integer autoCreditStatus;

    private Integer autoWithdrawStatus;

    private Integer autoConsumeStatus;

    private Integer autoCreateTime;

    private String autoOrderId;

    private String autoCreditOrderId;

    private Integer autoCreditTime;

    private Integer autoBidTime;

    private Date createTime;

    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;

    private Integer delFlag;

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

    public Integer getAutoInvesStatus() {
        return autoInvesStatus;
    }

    public void setAutoInvesStatus(Integer autoInvesStatus) {
        this.autoInvesStatus = autoInvesStatus;
    }

    public Integer getAutoCreditStatus() {
        return autoCreditStatus;
    }

    public void setAutoCreditStatus(Integer autoCreditStatus) {
        this.autoCreditStatus = autoCreditStatus;
    }

    public Integer getAutoWithdrawStatus() {
        return autoWithdrawStatus;
    }

    public void setAutoWithdrawStatus(Integer autoWithdrawStatus) {
        this.autoWithdrawStatus = autoWithdrawStatus;
    }

    public Integer getAutoConsumeStatus() {
        return autoConsumeStatus;
    }

    public void setAutoConsumeStatus(Integer autoConsumeStatus) {
        this.autoConsumeStatus = autoConsumeStatus;
    }

    public Integer getAutoCreateTime() {
        return autoCreateTime;
    }

    public void setAutoCreateTime(Integer autoCreateTime) {
        this.autoCreateTime = autoCreateTime;
    }

    public String getAutoOrderId() {
        return autoOrderId;
    }

    public void setAutoOrderId(String autoOrderId) {
        this.autoOrderId = autoOrderId == null ? null : autoOrderId.trim();
    }

    public String getAutoCreditOrderId() {
        return autoCreditOrderId;
    }

    public void setAutoCreditOrderId(String autoCreditOrderId) {
        this.autoCreditOrderId = autoCreditOrderId == null ? null : autoCreditOrderId.trim();
    }

    public Integer getAutoCreditTime() {
        return autoCreditTime;
    }

    public void setAutoCreditTime(Integer autoCreditTime) {
        this.autoCreditTime = autoCreditTime;
    }

    public Integer getAutoBidTime() {
        return autoBidTime;
    }

    public void setAutoBidTime(Integer autoBidTime) {
        this.autoBidTime = autoBidTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}