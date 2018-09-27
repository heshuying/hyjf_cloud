/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigAddRequest, v0.1 2018/9/27 15:44
 */
public class BailConfigAddRequest {

    private Integer id;

    private String instCode;

    private BigDecimal bailTatol;

    private Integer bailRate;

    private String timestart;

    private String timeend;

    private BigDecimal newCreditLine;

    private BigDecimal loanCreditLine;

    private BigDecimal dayMarkLine;

    private BigDecimal monthMarkLine;

    private BigDecimal pushMarkLine;

    private BigDecimal loanMarkLine;

    private BigDecimal remainMarkLine;

    private BigDecimal repayedCapital;

    private BigDecimal hisLoanTotal;

    private BigDecimal cycLoanTotal;

    private BigDecimal loanBalance;

    private Integer isAccumulate;

    private String remark;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private Integer delFlg;

    /**
     * 等额本息 NCL:新增授信  LCL:在贷授信 RCT:回滚方式
     */
    private Integer monthNCL;
    private Integer monthLCL;
    private Integer monthRCT;
    private Integer monthDEL;
    /**
     * 按月计息，到期还本还息
     */
    private Integer endNCL;
    private Integer endLCL;
    private Integer endRCT;
    private Integer endDEL;
    /**
     * 先息后本
     */
    private Integer endmonthNCL;
    private Integer endmonthLCL;
    private Integer endmonthRCT;
    private Integer endmonthDEL;
    /**
     * 按天计息，到期还本息
     */
    private Integer enddayNCL;
    private Integer enddayLCL;
    private Integer enddayRCT;
    private Integer enddayDEL;
    /**
     * 等额本金
     */
    private Integer principalNCL;
    private Integer principalLCL;
    private Integer principalRCT;
    private Integer principalDEL;

    /**
     * 按季还款
     */
    private Integer seasonNCL;
    private Integer seasonLCL;
    private Integer seasonRCT;
    private Integer seasonDEL;

    /**
     * 按月付息到期还本
     */
    private Integer endmonthsNCL;
    private Integer endmonthsLCL;
    private Integer endmonthsRCT;
    private Integer endmonthsDEL;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode == null ? null : instCode.trim();
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
        this.timestart = timestart == null ? null : timestart.trim();
    }

    public String getTimeend() {
        return timeend;
    }

    public void setTimeend(String timeend) {
        this.timeend = timeend == null ? null : timeend.trim();
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
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
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

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
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
}
