package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CalculateInvestInterest implements Serializable {
    private Integer id;

    private BigDecimal tenderSum;

    private BigDecimal interestSum;

    private BigDecimal sevenDayTenderSum;

    private BigDecimal sevenDayInterestSum;

    private Integer borrowZeroOne;

    private Integer borrowOneThree;

    private Integer borrowThreeSix;

    private Integer borrowSixTwelve;

    private Integer borrowTwelveUp;

    private Integer investOneDown;

    private Integer investOneFive;

    private Integer investFiveTen;

    private Integer investTenFifth;

    private Integer investFifthUp;

    private BigDecimal bailMoney;

    private Date updateTime;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTenderSum() {
        return tenderSum;
    }

    public void setTenderSum(BigDecimal tenderSum) {
        this.tenderSum = tenderSum;
    }

    public BigDecimal getInterestSum() {
        return interestSum;
    }

    public void setInterestSum(BigDecimal interestSum) {
        this.interestSum = interestSum;
    }

    public BigDecimal getSevenDayTenderSum() {
        return sevenDayTenderSum;
    }

    public void setSevenDayTenderSum(BigDecimal sevenDayTenderSum) {
        this.sevenDayTenderSum = sevenDayTenderSum;
    }

    public BigDecimal getSevenDayInterestSum() {
        return sevenDayInterestSum;
    }

    public void setSevenDayInterestSum(BigDecimal sevenDayInterestSum) {
        this.sevenDayInterestSum = sevenDayInterestSum;
    }

    public Integer getBorrowZeroOne() {
        return borrowZeroOne;
    }

    public void setBorrowZeroOne(Integer borrowZeroOne) {
        this.borrowZeroOne = borrowZeroOne;
    }

    public Integer getBorrowOneThree() {
        return borrowOneThree;
    }

    public void setBorrowOneThree(Integer borrowOneThree) {
        this.borrowOneThree = borrowOneThree;
    }

    public Integer getBorrowThreeSix() {
        return borrowThreeSix;
    }

    public void setBorrowThreeSix(Integer borrowThreeSix) {
        this.borrowThreeSix = borrowThreeSix;
    }

    public Integer getBorrowSixTwelve() {
        return borrowSixTwelve;
    }

    public void setBorrowSixTwelve(Integer borrowSixTwelve) {
        this.borrowSixTwelve = borrowSixTwelve;
    }

    public Integer getBorrowTwelveUp() {
        return borrowTwelveUp;
    }

    public void setBorrowTwelveUp(Integer borrowTwelveUp) {
        this.borrowTwelveUp = borrowTwelveUp;
    }

    public Integer getInvestOneDown() {
        return investOneDown;
    }

    public void setInvestOneDown(Integer investOneDown) {
        this.investOneDown = investOneDown;
    }

    public Integer getInvestOneFive() {
        return investOneFive;
    }

    public void setInvestOneFive(Integer investOneFive) {
        this.investOneFive = investOneFive;
    }

    public Integer getInvestFiveTen() {
        return investFiveTen;
    }

    public void setInvestFiveTen(Integer investFiveTen) {
        this.investFiveTen = investFiveTen;
    }

    public Integer getInvestTenFifth() {
        return investTenFifth;
    }

    public void setInvestTenFifth(Integer investTenFifth) {
        this.investTenFifth = investTenFifth;
    }

    public Integer getInvestFifthUp() {
        return investFifthUp;
    }

    public void setInvestFifthUp(Integer investFifthUp) {
        this.investFifthUp = investFifthUp;
    }

    public BigDecimal getBailMoney() {
        return bailMoney;
    }

    public void setBailMoney(BigDecimal bailMoney) {
        this.bailMoney = bailMoney;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}