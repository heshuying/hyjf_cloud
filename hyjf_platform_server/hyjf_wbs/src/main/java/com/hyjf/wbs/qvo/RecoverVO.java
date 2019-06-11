/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.qvo;

import java.util.Date;

/**
 * 回款信息接口返回具体参数
 * @author kou
 * @version RecoverVO, v0.1 2019/4/17 10:13
 */
public class RecoverVO {

    //资产端客户id
    private  String assetCustomerId;
    //回款订单编号
    private  String refundOrderNumber;
    //订单编号
    private  String orderNo;
    //产品编号
    private String productNo;
    //财富端ID
    private Integer  entId;
    //资产端id
    private Integer  assetId;
    //预计退出日期--应还
    private String  expectedExitTime;
    //实际退出日期--实还
    private String  actualExitTime;
    //参考回报--
    private  Double expectedRepay;
    //实际回报--
    private  Double actualRepay;
    //预计退出本金--应还本金
    private  Double expectedExitCapital;
    //实际退出本金--一已还本金
    private  Double actualExitCapital;
    //预计退出总额--应还总额
    private  Double  expectedExitAmount;
    //实际退出总额--一已还总额
    private  Double  actualExitAmount;
    //期次
    private  Integer  period;
    //状态
    private  Integer  status;

    public String getAssetCustomerId() {
        return assetCustomerId;
    }

    public void setAssetCustomerId(String assetCustomerId) {
        this.assetCustomerId = assetCustomerId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Integer getEntId() {
        return entId;
    }

    public void setEntId(Integer entId) {
        this.entId = entId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getExpectedExitTime() {
        return expectedExitTime;
    }

    public void setExpectedExitTime(String expectedExitTime) {
        this.expectedExitTime = expectedExitTime;
    }

    public String getActualExitTime() {
        return actualExitTime;
    }

    public void setActualExitTime(String actualExitTime) {
        this.actualExitTime = actualExitTime;
    }

    public String getRefundOrderNumber() {
        return refundOrderNumber;
    }

    public void setRefundOrderNumber(String refundOrderNumber) {
        this.refundOrderNumber = refundOrderNumber;
    }

    public Double getExpectedRepay() {
        return expectedRepay;
    }

    public void setExpectedRepay(Double expectedRepay) {
        this.expectedRepay = expectedRepay;
    }

    public Double getActualRepay() {
        return actualRepay;
    }

    public void setActualRepay(Double actualRepay) {
        this.actualRepay = actualRepay;
    }

    public Double getExpectedExitCapital() {
        return expectedExitCapital;
    }

    public void setExpectedExitCapital(Double expectedExitCapital) {
        this.expectedExitCapital = expectedExitCapital;
    }

    public Double getActualExitCapital() {
        return actualExitCapital;
    }

    public void setActualExitCapital(Double actualExitCapital) {
        this.actualExitCapital = actualExitCapital;
    }

    public Double getExpectedExitAmount() {
        return expectedExitAmount;
    }

    public void setExpectedExitAmount(Double expectedExitAmount) {
        this.expectedExitAmount = expectedExitAmount;
    }

    public Double getActualExitAmount() {
        return actualExitAmount;
    }

    public void setActualExitAmount(Double actualExitAmount) {
        this.actualExitAmount = actualExitAmount;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
