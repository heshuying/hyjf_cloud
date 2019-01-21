package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MerchantTransfer implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 订单编号
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 转出账户id
     *
     * @mbggenerated
     */
    private Integer outAccountId;

    /**
     * 转出账户代码
     *
     * @mbggenerated
     */
    private String outAccountCode;

    /**
     * 转出账户
     *
     * @mbggenerated
     */
    private String outAccountName;

    /**
     * 转入账户id
     *
     * @mbggenerated
     */
    private Integer inAccountId;

    /**
     * 转入账户代码
     *
     * @mbggenerated
     */
    private String inAccountCode;

    /**
     * 转入账户
     *
     * @mbggenerated
     */
    private String inAccountName;

    /**
     * 转账金额
     *
     * @mbggenerated
     */
    private BigDecimal transferAmount;

    /**
     * 转账时间
     *
     * @mbggenerated
     */
    private Date transferTime;

    /**
     * 转让状态 0 待转账 1 转帐中 2 转账成功 3 转账失败
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 转账类型 1自动转账 0手动转账
     *
     * @mbggenerated
     */
    private Integer transferType;

    /**
     * 说明
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 失败原因
     *
     * @mbggenerated
     */
    private String message;

    /**
     * 创建者用户id
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private String createUserName;

    /**
     * 更新者用户id
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 更新者
     *
     * @mbggenerated
     */
    private String updateUserName;

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