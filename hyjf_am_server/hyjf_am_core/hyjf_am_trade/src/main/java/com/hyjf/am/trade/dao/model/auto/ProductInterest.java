package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductInterest implements Serializable {
    /**
     * ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 订单号
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 计息金额
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * 计息天数
     *
     * @mbggenerated
     */
    private Integer interestDays;

    /**
     * 日利率
     *
     * @mbggenerated
     */
    private BigDecimal interestRate;

    /**
     * 总利息
     *
     * @mbggenerated
     */
    private BigDecimal interest;

    /**
     * 时间
     *
     * @mbggenerated
     */
    private Integer interestTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getInterestDays() {
        return interestDays;
    }

    public void setInterestDays(Integer interestDays) {
        this.interestDays = interestDays;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public Integer getInterestTime() {
        return interestTime;
    }

    public void setInterestTime(Integer interestTime) {
        this.interestTime = interestTime;
    }
}