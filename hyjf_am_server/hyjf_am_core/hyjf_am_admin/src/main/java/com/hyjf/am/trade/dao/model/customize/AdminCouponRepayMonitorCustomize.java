package com.hyjf.am.trade.dao.model.customize;

import java.math.BigDecimal;

/**
 * 
 * 加息券还款监测实体类
 * @author hsy
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年7月12日
 * @see下午4:41:41
 */
public class AdminCouponRepayMonitorCustomize {
    //主键
	private Integer id;
	//日期
	private String day;
	//星期
	private String week;
	//日累计待还收益
	private BigDecimal interestWaitTotal;
	//日累计已还收益
	private BigDecimal interestYesTotal;
	//累计待还收益
	private BigDecimal interestWaitTotalAll;
	//累计一环收益
	private BigDecimal interestYesTotalAll;
	//差额
	private BigDecimal repayGap;
	//更新时间
	private String updateTime;
	
	
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
        this.day = day;
    }
    public String getWeek() {
        return week;
    }
    public void setWeek(String week) {
        this.week = week;
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
    public BigDecimal getRepayGap() {
        return repayGap;
    }
    public void setRepayGap(BigDecimal repayGap) {
        this.repayGap = repayGap;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public BigDecimal getInterestWaitTotalAll() {
        return interestWaitTotalAll;
    }
    public void setInterestWaitTotalAll(BigDecimal interestWaitTotalAll) {
        this.interestWaitTotalAll = interestWaitTotalAll;
    }
    public BigDecimal getInterestYesTotalAll() {
        return interestYesTotalAll;
    }
    public void setInterestYesTotalAll(BigDecimal interestYesTotalAll) {
        this.interestYesTotalAll = interestYesTotalAll;
    }
	
	
}

	