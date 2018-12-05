package com.hyjf.am.vo.trade.hjh;

import com.hyjf.common.util.GetDate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HjhBailConfigVO implements Serializable {
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

    public static void main(String[] args) {
        System.out.println(GetDate.getDate("yyyyMMdd"));
    }
}