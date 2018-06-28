package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CouponRepayMonitor implements Serializable {
    private Integer id;

    private String day;

    private String week;

    private BigDecimal interestWaitTotal;

    private BigDecimal interestYesTotal;

    private Boolean delFlag;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

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

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}