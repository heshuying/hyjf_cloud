package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductRedeemDay implements Serializable {
    private Integer id;

    private Integer userId;

    private BigDecimal amount;

    private BigDecimal interest;

    private Integer investTime;

    private BigDecimal interestAll;

    private Integer flag;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public Integer getInvestTime() {
        return investTime;
    }

    public void setInvestTime(Integer investTime) {
        this.investTime = investTime;
    }

    public BigDecimal getInterestAll() {
        return interestAll;
    }

    public void setInterestAll(BigDecimal interestAll) {
        this.interestAll = interestAll;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}