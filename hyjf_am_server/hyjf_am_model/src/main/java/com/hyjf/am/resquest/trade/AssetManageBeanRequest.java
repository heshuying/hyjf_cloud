package com.hyjf.am.resquest.trade;


import com.hyjf.am.vo.BasePage;

public class AssetManageBeanRequest extends BasePage {
    // 用户id
    public String userId;
    // 投资开始值
    public String startDate;
    // 投资结束值
    public String endDate;
    //排序字段标识
    private String orderByFlag;
    //排序类型
    private String sortBy;

    private Integer limitStart;

    private Integer limitEnd;

    private String host;
    private String sign;
    private String countStatus;

    private String borrowNid;
    private String tenderNid;
    private Integer nowTime;

    private String type;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOrderByFlag() {
        return orderByFlag;
    }

    public void setOrderByFlag(String orderByFlag) {
        this.orderByFlag = orderByFlag;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCountStatus() {
        return countStatus;
    }

    public void setCountStatus(String countStatus) {
        this.countStatus = countStatus;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getTenderNid() {
        return tenderNid;
    }

    public void setTenderNid(String tenderNid) {
        this.tenderNid = tenderNid;
    }

    public Integer getNowTime() {
        return nowTime;
    }

    public void setNowTime(Integer nowTime) {
        this.nowTime = nowTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
