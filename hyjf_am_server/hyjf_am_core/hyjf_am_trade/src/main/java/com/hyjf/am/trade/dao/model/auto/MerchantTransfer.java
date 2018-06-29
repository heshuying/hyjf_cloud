package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MerchantTransfer implements Serializable {
    private Integer id;

    private String orderId;

    private Integer outAccountId;

    private String outAccountCode;

    private String outAccountName;

    private Integer inAccountId;

    private String inAccountCode;

    private String inAccountName;

    private BigDecimal transferAmount;

    private Date transferTime;

    private Integer status;

    private Integer transferType;

    private String remark;

    private String message;

    private Integer createUserId;

    private String createUserName;

    private Integer updateUserId;

    private String updateUserName;

    private Date createTime;

    private Date updateTime;

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

    public Integer getOutAccountId() {
        return outAccountId;
    }

    public void setOutAccountId(Integer outAccountId) {
        this.outAccountId = outAccountId;
    }

    public String getOutAccountCode() {
        return outAccountCode;
    }

    public void setOutAccountCode(String outAccountCode) {
        this.outAccountCode = outAccountCode == null ? null : outAccountCode.trim();
    }

    public String getOutAccountName() {
        return outAccountName;
    }

    public void setOutAccountName(String outAccountName) {
        this.outAccountName = outAccountName == null ? null : outAccountName.trim();
    }

    public Integer getInAccountId() {
        return inAccountId;
    }

    public void setInAccountId(Integer inAccountId) {
        this.inAccountId = inAccountId;
    }

    public String getInAccountCode() {
        return inAccountCode;
    }

    public void setInAccountCode(String inAccountCode) {
        this.inAccountCode = inAccountCode == null ? null : inAccountCode.trim();
    }

    public String getInAccountName() {
        return inAccountName;
    }

    public void setInAccountName(String inAccountName) {
        this.inAccountName = inAccountName == null ? null : inAccountName.trim();
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
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