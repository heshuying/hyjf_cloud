package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountDirectionalTransfer implements Serializable {
    /**
     * 主键id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 订单号
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 转出账户用户名
     *
     * @mbggenerated
     */
    private String turnOutUsername;

    /**
     * 转出账户用户编号
     *
     * @mbggenerated
     */
    private Integer turnOutUserId;

    /**
     * 转入账户用户名
     *
     * @mbggenerated
     */
    private String shiftToUsername;

    /**
     * 转入账户用户编号
     *
     * @mbggenerated
     */
    private Integer shiftToUserId;

    /**
     * 转账金额
     *
     * @mbggenerated
     */
    private BigDecimal transferAccountsMoney;

    /**
     * 状态状态（0转账中 1 成功 2 失败）
     *
     * @mbggenerated
     */
    private Integer transferAccountsState;

    /**
     * 转账时间
     *
     * @mbggenerated
     */
    private Date transferAccountsTime;

    /**
     * 备注说明
     *
     * @mbggenerated
     */
    private String remark;

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

    public String getTurnOutUsername() {
        return turnOutUsername;
    }

    public void setTurnOutUsername(String turnOutUsername) {
        this.turnOutUsername = turnOutUsername == null ? null : turnOutUsername.trim();
    }

    public Integer getTurnOutUserId() {
        return turnOutUserId;
    }

    public void setTurnOutUserId(Integer turnOutUserId) {
        this.turnOutUserId = turnOutUserId;
    }

    public String getShiftToUsername() {
        return shiftToUsername;
    }

    public void setShiftToUsername(String shiftToUsername) {
        this.shiftToUsername = shiftToUsername == null ? null : shiftToUsername.trim();
    }

    public Integer getShiftToUserId() {
        return shiftToUserId;
    }

    public void setShiftToUserId(Integer shiftToUserId) {
        this.shiftToUserId = shiftToUserId;
    }

    public BigDecimal getTransferAccountsMoney() {
        return transferAccountsMoney;
    }

    public void setTransferAccountsMoney(BigDecimal transferAccountsMoney) {
        this.transferAccountsMoney = transferAccountsMoney;
    }

    public Integer getTransferAccountsState() {
        return transferAccountsState;
    }

    public void setTransferAccountsState(Integer transferAccountsState) {
        this.transferAccountsState = transferAccountsState;
    }

    public Date getTransferAccountsTime() {
        return transferAccountsTime;
    }

    public void setTransferAccountsTime(Date transferAccountsTime) {
        this.transferAccountsTime = transferAccountsTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}