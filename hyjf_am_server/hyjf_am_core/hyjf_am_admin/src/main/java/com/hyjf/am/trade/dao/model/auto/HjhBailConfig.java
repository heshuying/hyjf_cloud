package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HjhBailConfig implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 机构编号
     *
     * @mbggenerated
     */
    private String instCode;

    /**
     * 保证金金额
     *
     * @mbggenerated
     */
    private BigDecimal bailTatol;

    /**
     * 保证金比例
     *
     * @mbggenerated
     */
    private Integer bailRate;

    /**
     * 授信周期开始
     *
     * @mbggenerated
     */
    private String timestart;

    /**
     * 授信周期结束
     *
     * @mbggenerated
     */
    private String timeend;

    /**
     * 新增授信额度（元）
     *
     * @mbggenerated
     */
    private BigDecimal newCreditLine;

    /**
     * 在贷授信额度（元）
     *
     * @mbggenerated
     */
    private BigDecimal loanCreditLine;

    /**
     * 日推标额度（元）
     *
     * @mbggenerated
     */
    private BigDecimal dayMarkLine;

    /**
     * 月推标额度（元）
     *
     * @mbggenerated
     */
    private BigDecimal monthMarkLine;

    /**
     * 发标额度上限（元）
     *
     * @mbggenerated
     */
    private BigDecimal pushMarkLine;

    /**
     * 发标已发额度（元）
     *
     * @mbggenerated
     */
    private BigDecimal loanMarkLine;

    /**
     * 发标额度余额（元）
     *
     * @mbggenerated
     */
    private BigDecimal remainMarkLine;

    /**
     * 已还本金
     *
     * @mbggenerated
     */
    private BigDecimal repayedCapital;

    /**
     * 历史标的放款总额
     *
     * @mbggenerated
     */
    private BigDecimal hisLoanTotal;

    /**
     * 周期内发标已发额度
     *
     * @mbggenerated
     */
    private BigDecimal cycLoanTotal;

    /**
     * 在贷余额
     *
     * @mbggenerated
     */
    private BigDecimal loanBalance;

    /**
     * 未用额度是否累计 0：否 1：是
     *
     * @mbggenerated
     */
    private Integer isAccumulate;

    /**
     * 说明
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 最后修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 删除标识
     *
     * @mbggenerated
     */
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
}