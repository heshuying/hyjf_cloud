package com.hyjf.am.vo.datacollect;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tanyy
 * @version QuarterOperationReportVO, v0.1 2018/7/24 13:52
 */
public class QuarterOperationReportVO implements Serializable {
    private String id;

    private String operationReportId;

    private String cnName;

    private String enName;

    private Integer quarterType;

    private BigDecimal firstMonthAmount;

    private BigDecimal secondMonthAmount;

    private BigDecimal thirdMonthAmount;

    private BigDecimal lastYearQuarterAmount;

    private BigDecimal amountIncrease;

    private BigDecimal lastYearQuarterProfit;

    private BigDecimal profitIncrease;

    private BigDecimal quarterAvgProfit;

    private Integer quarterAppDealNum;

    private BigDecimal quarterAppDealProportion;

    private Integer quarterWechatDealNum;

    private BigDecimal quarterWechatDealProportion;

    private Integer quarterPcDealNum;

    private BigDecimal quarterPcDealProportion;

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

    public Integer getQuarterType() {
        return quarterType;
    }

    public void setQuarterType(Integer quarterType) {
        this.quarterType = quarterType;
    }

    public BigDecimal getFirstMonthAmount() {
        return firstMonthAmount;
    }

    public void setFirstMonthAmount(BigDecimal firstMonthAmount) {
        this.firstMonthAmount = firstMonthAmount;
    }

    public BigDecimal getSecondMonthAmount() {
        return secondMonthAmount;
    }

    public void setSecondMonthAmount(BigDecimal secondMonthAmount) {
        this.secondMonthAmount = secondMonthAmount;
    }

    public BigDecimal getThirdMonthAmount() {
        return thirdMonthAmount;
    }

    public void setThirdMonthAmount(BigDecimal thirdMonthAmount) {
        this.thirdMonthAmount = thirdMonthAmount;
    }

    public BigDecimal getLastYearQuarterAmount() {
        return lastYearQuarterAmount;
    }

    public void setLastYearQuarterAmount(BigDecimal lastYearQuarterAmount) {
        this.lastYearQuarterAmount = lastYearQuarterAmount;
    }

    public BigDecimal getAmountIncrease() {
        return amountIncrease;
    }

    public void setAmountIncrease(BigDecimal amountIncrease) {
        this.amountIncrease = amountIncrease;
    }

    public BigDecimal getLastYearQuarterProfit() {
        return lastYearQuarterProfit;
    }

    public void setLastYearQuarterProfit(BigDecimal lastYearQuarterProfit) {
        this.lastYearQuarterProfit = lastYearQuarterProfit;
    }

    public BigDecimal getProfitIncrease() {
        return profitIncrease;
    }

    public void setProfitIncrease(BigDecimal profitIncrease) {
        this.profitIncrease = profitIncrease;
    }

    public BigDecimal getQuarterAvgProfit() {
        return quarterAvgProfit;
    }

    public void setQuarterAvgProfit(BigDecimal quarterAvgProfit) {
        this.quarterAvgProfit = quarterAvgProfit;
    }

    public Integer getQuarterAppDealNum() {
        return quarterAppDealNum;
    }

    public void setQuarterAppDealNum(Integer quarterAppDealNum) {
        this.quarterAppDealNum = quarterAppDealNum;
    }

    public BigDecimal getQuarterAppDealProportion() {
        return quarterAppDealProportion;
    }

    public void setQuarterAppDealProportion(BigDecimal quarterAppDealProportion) {
        this.quarterAppDealProportion = quarterAppDealProportion;
    }

    public Integer getQuarterWechatDealNum() {
        return quarterWechatDealNum;
    }

    public void setQuarterWechatDealNum(Integer quarterWechatDealNum) {
        this.quarterWechatDealNum = quarterWechatDealNum;
    }

    public BigDecimal getQuarterWechatDealProportion() {
        return quarterWechatDealProportion;
    }

    public void setQuarterWechatDealProportion(BigDecimal quarterWechatDealProportion) {
        this.quarterWechatDealProportion = quarterWechatDealProportion;
    }

    public Integer getQuarterPcDealNum() {
        return quarterPcDealNum;
    }

    public void setQuarterPcDealNum(Integer quarterPcDealNum) {
        this.quarterPcDealNum = quarterPcDealNum;
    }

    public BigDecimal getQuarterPcDealProportion() {
        return quarterPcDealProportion;
    }

    public void setQuarterPcDealProportion(BigDecimal quarterPcDealProportion) {
        this.quarterPcDealProportion = quarterPcDealProportion;
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