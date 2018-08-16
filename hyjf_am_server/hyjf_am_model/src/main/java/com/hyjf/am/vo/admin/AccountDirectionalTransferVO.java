/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: sunpeikai
 * @version: AccountDirectionalTransferVO, v0.1 2018/7/4 16:30
 */
@ApiModel(value = "定向转账返回值参数")
public class AccountDirectionalTransferVO extends BaseVO {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "订单号")
    private String orderId;
    @ApiModelProperty(value = "转出账户用户名")
    private String turnOutUsername;
    @ApiModelProperty(value = "转出账户用户编号")
    private Integer turnOutUserId;
    @ApiModelProperty(value = "转入账户用户名")
    private String shiftToUsername;
    @ApiModelProperty(value = "转入账户用户编号")
    private Integer shiftToUserId;
    @ApiModelProperty(value = "转账金额")
    private BigDecimal transferAccountsMoney;
    @ApiModelProperty(value = "状态状态（0转账中 1 成功 2 失败）")
    private Integer transferAccountsState;
    @ApiModelProperty(value = "转账时间")
    private Date transferAccountsTime;
    @ApiModelProperty(value = "备注说明")
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
