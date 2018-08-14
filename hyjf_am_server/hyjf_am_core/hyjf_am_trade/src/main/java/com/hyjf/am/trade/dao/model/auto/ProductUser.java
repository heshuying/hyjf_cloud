package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductUser implements Serializable {
    private Integer id;

    private Integer userId;

    private String username;

    private BigDecimal total;

    private Integer validDays;

    private BigDecimal redeemed;

    private BigDecimal restAmount;

    private BigDecimal interest;

    private BigDecimal restAmountCheck;

    private BigDecimal redeemedCheck;

    private Integer status;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public BigDecimal getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(BigDecimal redeemed) {
        this.redeemed = redeemed;
    }

    public BigDecimal getRestAmount() {
        return restAmount;
    }

    public void setRestAmount(BigDecimal restAmount) {
        this.restAmount = restAmount;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getRestAmountCheck() {
        return restAmountCheck;
    }

    public void setRestAmountCheck(BigDecimal restAmountCheck) {
        this.restAmountCheck = restAmountCheck;
    }

    public BigDecimal getRedeemedCheck() {
        return redeemedCheck;
    }

    public void setRedeemedCheck(BigDecimal redeemedCheck) {
        this.redeemedCheck = redeemedCheck;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}