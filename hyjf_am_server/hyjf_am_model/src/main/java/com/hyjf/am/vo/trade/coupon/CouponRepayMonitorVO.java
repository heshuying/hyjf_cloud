/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.coupon;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yaoy
 * @version CouponRepayMonitorVO, v0.1 2018/6/20 16:49
 */
public class CouponRepayMonitorVO extends BaseVO implements Serializable {
    private Integer id;

    private String day;

    private String week;

    private BigDecimal interestWaitTotal;

    private BigDecimal interestYesTotal;

    private Integer addTime;

    private Integer addUser;

    private Integer updateTime;

    private Integer updateUser;

    private Integer delFlg;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week == null ? null : week.trim();
    }

    public BigDecimal getInterestWaitTotal() {
        return interestWaitTotal;
    }

    public void setInterestWaitTotal(BigDecimal interestWaitTotal) {
        this.interestWaitTotal = interestWaitTotal;
    }

    public BigDecimal getInterestYesTotal() {
        return interestYesTotal;
    }

    public void setInterestYesTotal(BigDecimal interestYesTotal) {
        this.interestYesTotal = interestYesTotal;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Integer getAddUser() {
        return addUser;
    }

    public void setAddUser(Integer addUser) {
        this.addUser = addUser;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }
}
