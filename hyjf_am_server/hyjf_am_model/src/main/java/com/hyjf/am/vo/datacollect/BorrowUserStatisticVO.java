/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.datacollect;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author fuqiang
 * @version BorrowUserStatisticVO, v0.1 2018/7/18 13:52
 */
public class BorrowUserStatisticVO extends BaseVO implements Serializable {
    private Integer id;

    private Integer borrowUserCountTotal;

    private Integer borrowUserCountCurrent;

    private BigDecimal borrowUserMoneyTopTen;

    private BigDecimal borrowUserMoneyTopOne;

    private BigDecimal borrowUserMoneyTotal;

    private Integer tenderUserCountCurrent;

    private Integer addTime;

    private Integer updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public BigDecimal getBorrowUserMoneyTotal() {
        return borrowUserMoneyTotal;
    }

    public void setBorrowUserMoneyTotal(BigDecimal borrowUserMoneyTotal) {
        this.borrowUserMoneyTotal = borrowUserMoneyTotal;
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
