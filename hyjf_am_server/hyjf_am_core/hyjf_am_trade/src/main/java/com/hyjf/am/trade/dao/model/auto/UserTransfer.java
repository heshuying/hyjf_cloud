package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserTransfer implements Serializable {
    private Integer id;

    private String orderId;

    private Integer outUserId;

    private String outUserName;

    private Integer inUserId;

    private String inUserName;

    private BigDecimal transferAmount;

    private String transferUrl;

    private Date transferTime;

    private Integer status;

    private Integer transferType;

    private String remark;

    private Integer createUserId;

    private Date createTime;

    private Integer updateUserId;

    private Date updateTime;

    private String createUserName;

    private String reconciliationId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getOutUserId() {
        return outUserId;
    }

    public void setOutUserId(Integer outUserId) {
        this.outUserId = outUserId;
    }

    public String getOutUserName() {
        return outUserName;
    }

    public void setOutUserName(String outUserName) {
        this.outUserName = outUserName == null ? null : outUserName.trim();
    }

    public Integer getInUserId() {
        return inUserId;
    }

    public void setInUserId(Integer inUserId) {
        this.inUserId = inUserId;
    }

    public String getInUserName() {
        return inUserName;
    }

    public void setInUserName(String inUserName) {
        this.inUserName = inUserName == null ? null : inUserName.trim();
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTransferUrl() {
        return transferUrl;
    }

    public void setTransferUrl(String transferUrl) {
        this.transferUrl = transferUrl == null ? null : transferUrl.trim();
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTransferType() {
        return transferType;
    }

    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public String getReconciliationId() {
        return reconciliationId;
    }

    public void setReconciliationId(String reconciliationId) {
        this.reconciliationId = reconciliationId == null ? null : reconciliationId.trim();
    }
}