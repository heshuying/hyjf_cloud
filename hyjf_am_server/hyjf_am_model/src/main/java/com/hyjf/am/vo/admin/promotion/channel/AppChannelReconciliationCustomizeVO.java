package com.hyjf.am.vo.admin.promotion.channel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/24 16:27
 * @Description: AppChannelReconciliationCustomizeVO
 */
public class AppChannelReconciliationCustomizeVO implements Serializable {
    /**
     * serialVersionUID:序列化id
     */

    private static final long serialVersionUID = 4264420447504028078L;

    /*
     * 用户名
     */
    private Integer userId;
    /*
     * 用户名
     */
    private String userName;
    /*
     * 渠道ID
     */
    private Integer utmId;
    /*
     * 渠道
     */
    private String utmName;
    /*
     * 投资订单
     */
    private String orderCode;
    /*
     * 借款编号
     */
    private String borrowNid;
    /*
     * 标的期限
     */
    private String borrowPeriod;
    /*
     * 投资金额
     */
    private String investAmount;

    /*
     * 投资时间
     */
    private Integer investTime;

    /*
     * 渠道账号权限
     */
    private String utmIds;

    /**
     * 渠道
     */
    private String[] utmIdsSrch;

    /**
     * 是否是首投
     */
    private Integer isFirst;

    /**
     * 注册时间
     */
    private String regStartTime;

    /**
     * 注册时间
     */
    private String regEndTime;

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    /**
     * 注册时间

     */
    private String regTime;
    /*
     * 页码
     */
    protected int limitStart = -1;
    /*
     * 页码
     */
    protected int limitEnd = -1;
    /*
     * 查询开始时间
     */
    private String timeStartSrch;
    /*
     * 查询结束时间
     */
    private String timeEndSrch;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUtmId() {
        return utmId;
    }

    public void setUtmId(Integer utmId) {
        this.utmId = utmId;
    }

    public String getUtmName() {
        return utmName;
    }

    public void setUtmName(String utmName) {
        this.utmName = utmName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(String investAmount) {
        this.investAmount = investAmount;
    }

    public Integer getInvestTime() {
        return investTime;
    }

    public void setInvestTime(Integer investTime) {
        this.investTime = investTime;
    }

    public String getUtmIds() {
        return utmIds;
    }

    public void setUtmIds(String utmIds) {
        this.utmIds = utmIds;
    }

    public String[] getUtmIdsSrch() {
        return utmIdsSrch;
    }

    public void setUtmIdsSrch(String[] utmIdsSrch) {
        this.utmIdsSrch = utmIdsSrch;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }

    public String getRegStartTime() {
        return regStartTime;
    }

    public void setRegStartTime(String regStartTime) {
        this.regStartTime = regStartTime;
    }

    public String getRegEndTime() {
        return regEndTime;
    }

    public void setRegEndTime(String regEndTime) {
        this.regEndTime = regEndTime;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }
}
