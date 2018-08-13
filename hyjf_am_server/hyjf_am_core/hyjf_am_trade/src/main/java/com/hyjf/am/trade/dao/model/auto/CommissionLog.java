package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CommissionLog implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer spreadsUserId;

    private String nid;

    private String type;

    private String spreadsType;

    private String accountType;

    private String scales;

    private String borrowNid;

    private Integer tenderId;

    private Integer repayId;

    private BigDecimal accountAll;

    private BigDecimal accountCapital;

    private BigDecimal accountInterest;

    private BigDecimal account;

    private String remark;

    private String createIp;

    private Integer payStatus;

    private Integer isValid;

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

    public Integer getSpreadsUserId() {
        return spreadsUserId;
    }

    public void setSpreadsUserId(Integer spreadsUserId) {
        this.spreadsUserId = spreadsUserId;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid == null ? null : nid.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSpreadsType() {
        return spreadsType;
    }

    public void setSpreadsType(String spreadsType) {
        this.spreadsType = spreadsType == null ? null : spreadsType.trim();
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    public String getScales() {
        return scales;
    }

    public void setScales(String scales) {
        this.scales = scales == null ? null : scales.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getTenderId() {
        return tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }

    public Integer getRepayId() {
        return repayId;
    }

    public void setRepayId(Integer repayId) {
        this.repayId = repayId;
    }

    public BigDecimal getAccountAll() {
        return accountAll;
    }

    public void setAccountAll(BigDecimal accountAll) {
        this.accountAll = accountAll;
    }

    public BigDecimal getAccountCapital() {
        return accountCapital;
    }

    public void setAccountCapital(BigDecimal accountCapital) {
        this.accountCapital = accountCapital;
    }

    public BigDecimal getAccountInterest() {
        return accountInterest;
    }

    public void setAccountInterest(BigDecimal accountInterest) {
        this.accountInterest = accountInterest;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp == null ? null : createIp.trim();
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
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