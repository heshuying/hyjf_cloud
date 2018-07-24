package com.hyjf.am.vo.datacollect;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tanyy
 * @version MonthlyOperationReportVO, v0.1 2018/7/24 13:52
 */
public class MonthlyOperationReportVO implements Serializable {
    private String id;

    private String operationReportId;

    private String cnName;

    private String enName;

    private Integer month;

    private BigDecimal lastYearMonthAmount;

    private BigDecimal amountIncrease;

    private BigDecimal lastYearMonthProfit;

    private BigDecimal profitIncrease;

    private BigDecimal monthAvgProfit;

    private Integer monthAppDealNum;

    private BigDecimal monthAppDealProportion;

    private Integer monthWechatDealNum;

    private BigDecimal monthWechatDealProportion;

    private Integer monthPcDealNum;

    private BigDecimal monthPcDealProportion;

    private Integer updateTime;

    private Integer updateUserId;

    private Integer createTime;

    private Integer createUserId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperationReportId() {
        return operationReportId;
    }

    public void setOperationReportId(String operationReportId) {
        this.operationReportId = operationReportId;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName == null ? null : cnName.trim();
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName == null ? null : enName.trim();
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public BigDecimal getLastYearMonthAmount() {
        return lastYearMonthAmount;
    }

    public void setLastYearMonthAmount(BigDecimal lastYearMonthAmount) {
        this.lastYearMonthAmount = lastYearMonthAmount;
    }

    public BigDecimal getAmountIncrease() {
        return amountIncrease;
    }

    public void setAmountIncrease(BigDecimal amountIncrease) {
        this.amountIncrease = amountIncrease;
    }

    public BigDecimal getLastYearMonthProfit() {
        return lastYearMonthProfit;
    }

    public void setLastYearMonthProfit(BigDecimal lastYearMonthProfit) {
        this.lastYearMonthProfit = lastYearMonthProfit;
    }

    public BigDecimal getProfitIncrease() {
        return profitIncrease;
    }

    public void setProfitIncrease(BigDecimal profitIncrease) {
        this.profitIncrease = profitIncrease;
    }

    public BigDecimal getMonthAvgProfit() {
        return monthAvgProfit;
    }

    public void setMonthAvgProfit(BigDecimal monthAvgProfit) {
        this.monthAvgProfit = monthAvgProfit;
    }

    public Integer getMonthAppDealNum() {
        return monthAppDealNum;
    }

    public void setMonthAppDealNum(Integer monthAppDealNum) {
        this.monthAppDealNum = monthAppDealNum;
    }

    public BigDecimal getMonthAppDealProportion() {
        return monthAppDealProportion;
    }

    public void setMonthAppDealProportion(BigDecimal monthAppDealProportion) {
        this.monthAppDealProportion = monthAppDealProportion;
    }

    public Integer getMonthWechatDealNum() {
        return monthWechatDealNum;
    }

    public void setMonthWechatDealNum(Integer monthWechatDealNum) {
        this.monthWechatDealNum = monthWechatDealNum;
    }

    public BigDecimal getMonthWechatDealProportion() {
        return monthWechatDealProportion;
    }

    public void setMonthWechatDealProportion(BigDecimal monthWechatDealProportion) {
        this.monthWechatDealProportion = monthWechatDealProportion;
    }

    public Integer getMonthPcDealNum() {
        return monthPcDealNum;
    }

    public void setMonthPcDealNum(Integer monthPcDealNum) {
        this.monthPcDealNum = monthPcDealNum;
    }

    public BigDecimal getMonthPcDealProportion() {
        return monthPcDealProportion;
    }

    public void setMonthPcDealProportion(BigDecimal monthPcDealProportion) {
        this.monthPcDealProportion = monthPcDealProportion;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
}