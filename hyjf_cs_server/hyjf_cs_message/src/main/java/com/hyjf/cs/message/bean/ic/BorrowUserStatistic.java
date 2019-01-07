/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.ic;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author fuqiang
 * @version BorrowUserStatistic, v0.1 2018/7/18 13:51
 */
@Document(collection = "ht_borrow_user_statistic")
public class BorrowUserStatistic implements Serializable {
    private String id;

    private Integer borrowUserCountTotal;

    private Integer borrowUserCountCurrent;

    private BigDecimal borrowUserMoneyTopTen;

    private BigDecimal borrowUserMoneyTopOne;

    private BigDecimal borrowuserMoneyTotal;

    private Integer tenderUserCountCurrent;

    private Integer addTime;

    private Integer updateTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getBorrowUserCountTotal() {
        return borrowUserCountTotal;
    }

    public void setBorrowUserCountTotal(Integer borrowUserCountTotal) {
        this.borrowUserCountTotal = borrowUserCountTotal;
    }

    public Integer getBorrowUserCountCurrent() {
        return borrowUserCountCurrent;
    }

    public void setBorrowUserCountCurrent(Integer borrowUserCountCurrent) {
        this.borrowUserCountCurrent = borrowUserCountCurrent;
    }

    public BigDecimal getBorrowUserMoneyTopTen() {
        return borrowUserMoneyTopTen;
    }

    public void setBorrowUserMoneyTopTen(BigDecimal borrowUserMoneyTopTen) {
        this.borrowUserMoneyTopTen = borrowUserMoneyTopTen;
    }

    public BigDecimal getBorrowUserMoneyTopOne() {
        return borrowUserMoneyTopOne;
    }

    public void setBorrowUserMoneyTopOne(BigDecimal borrowUserMoneyTopOne) {
        this.borrowUserMoneyTopOne = borrowUserMoneyTopOne;
    }

    public BigDecimal getBorrowuserMoneyTotal() {
        return borrowuserMoneyTotal;
    }

    public void setBorrowuserMoneyTotal(BigDecimal borrowuserMoneyTotal) {
        this.borrowuserMoneyTotal = borrowuserMoneyTotal;
    }

    public Integer getTenderUserCountCurrent() {
        return tenderUserCountCurrent;
    }

    public void setTenderUserCountCurrent(Integer tenderUserCountCurrent) {
        this.tenderUserCountCurrent = tenderUserCountCurrent;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }
}
