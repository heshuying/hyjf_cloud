/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.ic;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Description 网站运营数据
 * @Author sunss
 * @Date 2018/7/7 15:34
 */
@Document(collection = "t_calculate_invest_interest")
public class CalculateInvestInterest implements Serializable {
    private String id;

    private BigDecimal tenderSum;

    private BigDecimal interestSum;

    private BigDecimal sevenDayTenderSum;

    private BigDecimal sevenDayInterestSum;

    private Integer borrowOneThree;

    private Integer borrowThreeSix;

    private Integer borrowSixTwelve;

    private Integer borrowTwelveUp;

    private Integer investOneDown;

    private Integer investOneFive;

    private Integer investFiveTen;

    private BigDecimal investTenFifth;

    private Integer investFifthUp;

    private BigDecimal bailMoney;

    private Integer updateTime;

    private Integer createTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public BigDecimal getInvestTenFifth() {
        return investTenFifth;
    }

    public void setInvestTenFifth(BigDecimal investTenFifth) {
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

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
