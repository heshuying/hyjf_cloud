package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductInterest implements Serializable {
    private Integer id;

    private Integer userId;

    private String orderId;

    private BigDecimal amount;

    private Integer interestDays;

    private BigDecimal interestRate;

    private BigDecimal interest;

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