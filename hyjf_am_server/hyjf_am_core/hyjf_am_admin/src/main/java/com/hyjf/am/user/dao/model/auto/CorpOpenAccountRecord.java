package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class CorpOpenAccountRecord implements Serializable {
    private Integer id;

    private Integer userId;

    private String username;

    private String busiCode;

    private String busiName;

    private String guarType;

    private String openBankId;

    private String account;

    private Integer status;

    private Integer isBank;

    private Integer cardType;

    private String taxRegistrationCode;

    private String buseNo;

    private String remark;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode == null ? null : busiCode.trim();
    }

    public String getBusiName() {
        return busiName;
    }

    public void setBusiName(String busiName) {
        this.busiName = busiName == null ? null : busiName.trim();
    }

    public String getGuarType() {
        return guarType;
    }

    public void setGuarType(String guarType) {
        this.guarType = guarType == null ? null : guarType.trim();
    }

    public String getOpenBankId() {
        return openBankId;
    }

    public void setOpenBankId(String openBankId) {
        this.openBankId = openBankId == null ? null : openBankId.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsBank() {
        return isBank;
    }

    public void setIsBank(Integer isBank) {
        this.isBank = isBank;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getTaxRegistrationCode() {
        return taxRegistrationCode;
    }

    public void setTaxRegistrationCode(String taxRegistrationCode) {
        this.taxRegistrationCode = taxRegistrationCode == null ? null : taxRegistrationCode.trim();
    }

    public String getBuseNo() {
        return buseNo;
    }

    public void setBuseNo(String buseNo) {
        this.buseNo = buseNo == null ? null : buseNo.trim();
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