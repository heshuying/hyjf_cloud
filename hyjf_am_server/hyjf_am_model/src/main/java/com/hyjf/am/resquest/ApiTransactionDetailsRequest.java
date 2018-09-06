package com.hyjf.am.resquest;

import java.io.Serializable;

/**
 * @Author : huanghui
 */
public class ApiTransactionDetailsRequest implements Serializable {

    public String startDate;

    public String endDate;

    public String phone;

    private String accountId;

    private String nid;

    private String username;

    private String isBank;

    private String tradeStatus;

    private String typeSearch;

    private String tradeTypeSearch;

    private Long timestamp;

    private String instCode;

    private Integer userId;

    private Integer limitStart;

    private Integer limitEnd;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIsBank() {
        return isBank;
    }

    public void setIsBank(String isBank) {
        this.isBank = isBank;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTypeSearch() {
        return typeSearch;
    }

    public void setTypeSearch(String typeSearch) {
        this.typeSearch = typeSearch;
    }

    public String getTradeTypeSearch() {
        return tradeTypeSearch;
    }

    public void setTradeTypeSearch(String tradeTypeSearch) {
        this.tradeTypeSearch = tradeTypeSearch;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
}
