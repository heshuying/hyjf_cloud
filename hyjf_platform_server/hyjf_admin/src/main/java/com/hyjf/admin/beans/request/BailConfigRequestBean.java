/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigRequestBean, v0.1 2018/9/27 15:32
 */
public class BailConfigRequestBean {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "机构编号")
    private String instCode;

    @ApiModelProperty(value = "保证金金额")
    private BigDecimal bailTatol;

    @ApiModelProperty(value = "保证金比例")
    private Integer bailRate;

    @ApiModelProperty(value = "授信周期开始")
    private String timestart;

    @ApiModelProperty(value = "授信周期结束")
    private String timeend;

    @ApiModelProperty(value = "新增授信额度（元）")
    private BigDecimal newCreditLine;

    @ApiModelProperty(value = "在贷授信额度（元）")
    private BigDecimal loanCreditLine;

    @ApiModelProperty(value = "日推标额度（元）")
    private BigDecimal dayMarkLine;

    @ApiModelProperty(value = "月推标额度（元）")
    private BigDecimal monthMarkLine;

    @ApiModelProperty(value = "发标额度上限（元）")
    private BigDecimal pushMarkLine;

    @ApiModelProperty(value = "发标已发额度（元）")
    private BigDecimal loanMarkLine;

    @ApiModelProperty(value = "发标额度余额（元）")
    private BigDecimal remainMarkLine;

    @ApiModelProperty(value = "已还本金")
    private BigDecimal repayedCapital;

    @ApiModelProperty(value = "历史标的放款总额")
    private BigDecimal hisLoanTotal;

    @ApiModelProperty(value = "周期内发标已发额度")
    private BigDecimal cycLoanTotal;

    @ApiModelProperty(value = "在贷余额")
    private BigDecimal loanBalance;

    @ApiModelProperty(value = "未用额度是否累计")
    private Integer isAccumulate;

    @ApiModelProperty(value = "说明")
    private String remark;

    /**
     * 等额本息 NCL:新增授信  LCL:在贷授信 RCT:回滚方式
     */
    @ApiModelProperty(value = "等额本息-新增授信")
    private Integer monthNCL;
    @ApiModelProperty(value = "等额本息-在贷授信")
    private Integer monthLCL;
    @ApiModelProperty(value = "等额本息-回滚方式")
    private Integer monthRCT;
    @ApiModelProperty(value = "等额本息-删除标识")
    private Integer monthDEL;
    /**
     * 按月计息，到期还本还息
     */
    @ApiModelProperty(value = "按月计息，到期还本还息-新增授信")
    private Integer endNCL;
    @ApiModelProperty(value = "按月计息，到期还本还息-在贷授信")
    private Integer endLCL;
    @ApiModelProperty(value = "按月计息，到期还本还息-回滚方式")
    private Integer endRCT;
    @ApiModelProperty(value = "按月计息，到期还本还息-删除标识")
    private Integer endDEL;
    /**
     * 先息后本
     */
    @ApiModelProperty(value = "先息后本-新增授信")
    private Integer endmonthNCL;
    @ApiModelProperty(value = "先息后本-在贷授信")
    private Integer endmonthLCL;
    @ApiModelProperty(value = "先息后本-回滚方式")
    private Integer endmonthRCT;
    @ApiModelProperty(value = "先息后本-删除标识")
    private Integer endmonthDEL;
    /**
     * 按天计息，到期还本息
     */
    @ApiModelProperty(value = "按天计息，到期还本息-新增授信")
    private Integer enddayNCL;
    @ApiModelProperty(value = "按天计息，到期还本息-在贷授信")
    private Integer enddayLCL;
    @ApiModelProperty(value = "按天计息，到期还本息-回滚方式")
    private Integer enddayRCT;
    @ApiModelProperty(value = "按天计息，到期还本息-删除标识")
    private Integer enddayDEL;
    /**
     * 等额本金
     */
    @ApiModelProperty(value = "等额本金-新增授信")
    private Integer principalNCL;
    @ApiModelProperty(value = "等额本金-在贷授信")
    private Integer principalLCL;
    @ApiModelProperty(value = "等额本金-回滚方式")
    private Integer principalRCT;
    @ApiModelProperty(value = "等额本金-删除标识")
    private Integer principalDEL;

    /**
     * 按季还款
     */
    @ApiModelProperty(value = "按季还款-新增授信")
    private Integer seasonNCL;
    @ApiModelProperty(value = "按季还款-在贷授信")
    private Integer seasonLCL;
    @ApiModelProperty(value = "按季还款-回滚方式")
    private Integer seasonRCT;
    @ApiModelProperty(value = "按季还款-删除标识")
    private Integer seasonDEL;

    /**
     * 按月付息到期还本
     */
    @ApiModelProperty(value = "按月付息到期还本-新增授信")
    private Integer endmonthsNCL;
    @ApiModelProperty(value = "按月付息到期还本-在贷授信")
    private Integer endmonthsLCL;
    @ApiModelProperty(value = "按月付息到期还本-回滚方式")
    private Integer endmonthsRCT;
    @ApiModelProperty(value = "按月付息到期还本-删除标识")
    private Integer endmonthsDEL;
    /**
     * 分页变量
     */
    private int limitStart = -1;
    private int limitEnd = -1;

    private int initQuery;

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public int getInitQuery() {
        return initQuery;
    }

    public void setInitQuery(int initQuery) {
        this.initQuery = initQuery;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public BigDecimal getBailTatol() {
        return bailTatol;
    }

    public void setBailTatol(BigDecimal bailTatol) {
        this.bailTatol = bailTatol;
    }

    public Integer getBailRate() {
        return bailRate;
    }

    public void setBailRate(Integer bailRate) {
        this.bailRate = bailRate;
    }

    public String getTimestart() {
        return timestart;
    }

    public void setTimestart(String timestart) {
        this.timestart = timestart;
    }

    public String getTimeend() {
        return timeend;
    }

    public void setTimeend(String timeend) {
        this.timeend = timeend;
    }

    public BigDecimal getNewCreditLine() {
        return newCreditLine;
    }

    public void setNewCreditLine(BigDecimal newCreditLine) {
        this.newCreditLine = newCreditLine;
    }

    public BigDecimal getLoanCreditLine() {
        return loanCreditLine;
    }

    public void setLoanCreditLine(BigDecimal loanCreditLine) {
        this.loanCreditLine = loanCreditLine;
    }

    public BigDecimal getDayMarkLine() {
        return dayMarkLine;
    }

    public void setDayMarkLine(BigDecimal dayMarkLine) {
        this.dayMarkLine = dayMarkLine;
    }

    public BigDecimal getMonthMarkLine() {
        return monthMarkLine;
    }

    public void setMonthMarkLine(BigDecimal monthMarkLine) {
        this.monthMarkLine = monthMarkLine;
    }

    public BigDecimal getPushMarkLine() {
        return pushMarkLine;
    }

    public void setPushMarkLine(BigDecimal pushMarkLine) {
        this.pushMarkLine = pushMarkLine;
    }

    public BigDecimal getLoanMarkLine() {
        return loanMarkLine;
    }

    public void setLoanMarkLine(BigDecimal loanMarkLine) {
        this.loanMarkLine = loanMarkLine;
    }

    public BigDecimal getRemainMarkLine() {
        return remainMarkLine;
    }

    public void setRemainMarkLine(BigDecimal remainMarkLine) {
        this.remainMarkLine = remainMarkLine;
    }

    public BigDecimal getRepayedCapital() {
        return repayedCapital;
    }

    public void setRepayedCapital(BigDecimal repayedCapital) {
        this.repayedCapital = repayedCapital;
    }

    public BigDecimal getHisLoanTotal() {
        return hisLoanTotal;
    }

    public void setHisLoanTotal(BigDecimal hisLoanTotal) {
        this.hisLoanTotal = hisLoanTotal;
    }

    public BigDecimal getCycLoanTotal() {
        return cycLoanTotal;
    }

    public void setCycLoanTotal(BigDecimal cycLoanTotal) {
        this.cycLoanTotal = cycLoanTotal;
    }

    public BigDecimal getLoanBalance() {
        return loanBalance;
    }

    public void setLoanBalance(BigDecimal loanBalance) {
        this.loanBalance = loanBalance;
    }

    public Integer getIsAccumulate() {
        return isAccumulate;
    }

    public void setIsAccumulate(Integer isAccumulate) {
        this.isAccumulate = isAccumulate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getMonthNCL() {
        return monthNCL;
    }

    public void setMonthNCL(Integer monthNCL) {
        this.monthNCL = monthNCL;
    }

    public Integer getMonthLCL() {
        return monthLCL;
    }

    public void setMonthLCL(Integer monthLCL) {
        this.monthLCL = monthLCL;
    }

    public Integer getMonthRCT() {
        return monthRCT;
    }

    public void setMonthRCT(Integer monthRCT) {
        this.monthRCT = monthRCT;
    }

    public Integer getMonthDEL() {
        return monthDEL;
    }

    public void setMonthDEL(Integer monthDEL) {
        this.monthDEL = monthDEL;
    }

    public Integer getEndNCL() {
        return endNCL;
    }

    public void setEndNCL(Integer endNCL) {
        this.endNCL = endNCL;
    }

    public Integer getEndLCL() {
        return endLCL;
    }

    public void setEndLCL(Integer endLCL) {
        this.endLCL = endLCL;
    }

    public Integer getEndRCT() {
        return endRCT;
    }

    public void setEndRCT(Integer endRCT) {
        this.endRCT = endRCT;
    }

    public Integer getEndDEL() {
        return endDEL;
    }

    public void setEndDEL(Integer endDEL) {
        this.endDEL = endDEL;
    }

    public Integer getEndmonthNCL() {
        return endmonthNCL;
    }

    public void setEndmonthNCL(Integer endmonthNCL) {
        this.endmonthNCL = endmonthNCL;
    }

    public Integer getEndmonthLCL() {
        return endmonthLCL;
    }

    public void setEndmonthLCL(Integer endmonthLCL) {
        this.endmonthLCL = endmonthLCL;
    }

    public Integer getEndmonthRCT() {
        return endmonthRCT;
    }

    public void setEndmonthRCT(Integer endmonthRCT) {
        this.endmonthRCT = endmonthRCT;
    }

    public Integer getEndmonthDEL() {
        return endmonthDEL;
    }

    public void setEndmonthDEL(Integer endmonthDEL) {
        this.endmonthDEL = endmonthDEL;
    }

    public Integer getEnddayNCL() {
        return enddayNCL;
    }

    public void setEnddayNCL(Integer enddayNCL) {
        this.enddayNCL = enddayNCL;
    }

    public Integer getEnddayLCL() {
        return enddayLCL;
    }

    public void setEnddayLCL(Integer enddayLCL) {
        this.enddayLCL = enddayLCL;
    }

    public Integer getEnddayRCT() {
        return enddayRCT;
    }

    public void setEnddayRCT(Integer enddayRCT) {
        this.enddayRCT = enddayRCT;
    }

    public Integer getEnddayDEL() {
        return enddayDEL;
    }

    public void setEnddayDEL(Integer enddayDEL) {
        this.enddayDEL = enddayDEL;
    }

    public Integer getPrincipalNCL() {
        return principalNCL;
    }

    public void setPrincipalNCL(Integer principalNCL) {
        this.principalNCL = principalNCL;
    }

    public Integer getPrincipalLCL() {
        return principalLCL;
    }

    public void setPrincipalLCL(Integer principalLCL) {
        this.principalLCL = principalLCL;
    }

    public Integer getPrincipalRCT() {
        return principalRCT;
    }

    public void setPrincipalRCT(Integer principalRCT) {
        this.principalRCT = principalRCT;
    }

    public Integer getPrincipalDEL() {
        return principalDEL;
    }

    public void setPrincipalDEL(Integer principalDEL) {
        this.principalDEL = principalDEL;
    }

    public Integer getSeasonNCL() {
        return seasonNCL;
    }

    public void setSeasonNCL(Integer seasonNCL) {
        this.seasonNCL = seasonNCL;
    }

    public Integer getSeasonLCL() {
        return seasonLCL;
    }

    public void setSeasonLCL(Integer seasonLCL) {
        this.seasonLCL = seasonLCL;
    }

    public Integer getSeasonRCT() {
        return seasonRCT;
    }

    public void setSeasonRCT(Integer seasonRCT) {
        this.seasonRCT = seasonRCT;
    }

    public Integer getSeasonDEL() {
        return seasonDEL;
    }

    public void setSeasonDEL(Integer seasonDEL) {
        this.seasonDEL = seasonDEL;
    }

    public Integer getEndmonthsNCL() {
        return endmonthsNCL;
    }

    public void setEndmonthsNCL(Integer endmonthsNCL) {
        this.endmonthsNCL = endmonthsNCL;
    }

    public Integer getEndmonthsLCL() {
        return endmonthsLCL;
    }

    public void setEndmonthsLCL(Integer endmonthsLCL) {
        this.endmonthsLCL = endmonthsLCL;
    }

    public Integer getEndmonthsRCT() {
        return endmonthsRCT;
    }

    public void setEndmonthsRCT(Integer endmonthsRCT) {
        this.endmonthsRCT = endmonthsRCT;
    }

    public Integer getEndmonthsDEL() {
        return endmonthsDEL;
    }

    public void setEndmonthsDEL(Integer endmonthsDEL) {
        this.endmonthsDEL = endmonthsDEL;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
