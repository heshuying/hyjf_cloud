package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductInfo implements Serializable {
    private Integer id;

    /**
     * 转入用户数
     *
     * @mbggenerated
     */
    private Integer inCount;

    /**
     * 转出用户数
     *
     * @mbggenerated
     */
    private Integer outCount;

    /**
     * 转入金额
     *
     * @mbggenerated
     */
    private BigDecimal inAmount;

    /**
     * 转出金额
     *
     * @mbggenerated
     */
    private BigDecimal outAmount;

    /**
     * 转出利息
     *
     * @mbggenerated
     */
    private BigDecimal outInterest;

    /**
     * 资管公司账户余额
     *
     * @mbggenerated
     */
    private BigDecimal loanBalance;

    /**
     * 出借总金额
     *
     * @mbggenerated
     */
    private BigDecimal investAmount;

    /**
     * 日期
     *
     * @mbggenerated
     */
    private String dataDate;

    /**
     * 统计数据月份
     *
     * @mbggenerated
     */
    private String dataMonth;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInCount() {
        return inCount;
    }

    public void setInCount(Integer inCount) {
        this.inCount = inCount;
    }

    public Integer getOutCount() {
        return outCount;
    }

    public void setOutCount(Integer outCount) {
        this.outCount = outCount;
    }

    public BigDecimal getInAmount() {
        return inAmount;
    }

    public void setInAmount(BigDecimal inAmount) {
        this.inAmount = inAmount;
    }

    public BigDecimal getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(BigDecimal outAmount) {
        this.outAmount = outAmount;
    }

    public BigDecimal getOutInterest() {
        return outInterest;
    }

    public void setOutInterest(BigDecimal outInterest) {
        this.outInterest = outInterest;
    }

    public BigDecimal getLoanBalance() {
        return loanBalance;
    }

    public void setLoanBalance(BigDecimal loanBalance) {
        this.loanBalance = loanBalance;
    }

    public BigDecimal getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(BigDecimal investAmount) {
        this.investAmount = investAmount;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate == null ? null : dataDate.trim();
    }

    public String getDataMonth() {
        return dataMonth;
    }

    public void setDataMonth(String dataMonth) {
        this.dataMonth = dataMonth == null ? null : dataMonth.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}