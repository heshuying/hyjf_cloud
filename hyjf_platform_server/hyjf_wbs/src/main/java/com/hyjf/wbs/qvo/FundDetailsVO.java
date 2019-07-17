/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.qvo;

import java.math.BigDecimal;

/**
 * 资金明细接口返回数据结构
 * @author cui
 * @version FundDetailsVO, v0.1 2019/6/28 16:01
 */
public class FundDetailsVO {
    //资产端客户id
    private String assetCustomerId;
    //资产端id
    private Integer assetId;
    //业务类型
    private Integer  businessType;
    //财富端id
    private Integer entId;
    //明细编号
    private String detailNo;
    //资金托管平台
    private String investPlatform;
    //类型
    private String investType;
    //银行
    private String bank;
    //操作金额
    private BigDecimal operateAmount;
    //手续费
    private BigDecimal serviceCharge;
    //到账金额
    private BigDecimal arriveAmount;
    //状态
    private Integer status;
    //提现方式
    private String cashWay;
    //操作平台
    private Integer operatePlatform;
    //操作时间
    private String operateTime;
    //起始时间
    private String startTime;
    //截止时间
    private String endTime;

    public String getAssetCustomerId() {
        return assetCustomerId;
    }

    public void setAssetCustomerId(String assetCustomerId) {
        this.assetCustomerId = assetCustomerId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getEntId() {
        return entId;
    }

    public void setEntId(Integer entId) {
        this.entId = entId;
    }

    public String getDetailNo() {
        return detailNo;
    }

    public void setDetailNo(String detailNo) {
        this.detailNo = detailNo;
    }

    public String getInvestPlatform() {
        return investPlatform;
    }

    public void setInvestPlatform(String investPlatform) {
        this.investPlatform = investPlatform;
    }

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public BigDecimal getOperateAmount() {
        return operateAmount;
    }

    public void setOperateAmount(BigDecimal operateAmount) {
        this.operateAmount = operateAmount;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getArriveAmount() {
        return arriveAmount;
    }

    public void setArriveAmount(BigDecimal arriveAmount) {
        this.arriveAmount = arriveAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCashWay() {
        return cashWay;
    }

    public void setCashWay(String cashWay) {
        this.cashWay = cashWay;
    }

    public Integer getOperatePlatform() {
        return operatePlatform;
    }

    public void setOperatePlatform(Integer operatePlatform) {
        this.operatePlatform = operatePlatform;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
