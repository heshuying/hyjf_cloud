/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: sunpeikai
 * @version: AccountDirectionalTransferVO, v0.1 2018/7/4 16:30
 */
public class AccountDirectionalTransferVO extends BaseVO {
    private Integer id;

    private String orderId;

    private String turnOutUsername;

    private Integer turnOutUserId;

    private String shiftToUsername;

    private Integer shiftToUserId;

    private BigDecimal transferAccountsMoney;

    private Integer transferAccountsState;

    private Date transferAccountsTime;

    private String remark;

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
        this.orderId = orderId;
    }

    public String getTurnOutUsername() {
        return turnOutUsername;
    }

    public void setTurnOutUsername(String turnOutUsername) {
        this.turnOutUsername = turnOutUsername;
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
        this.shiftToUsername = shiftToUsername;
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
        this.remark = remark;
    }
}
