/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import java.math.BigDecimal;

/**
 * @author wangjun
 * @version BorrowBailInfoResponseBean, v0.1 2018/7/3 18:50
 */
public class BorrowBailInfoResponseBean {
    private String userName;

    private String borrowNid;

    private String name;

    private BigDecimal account;

    private BigDecimal borrowApr;

    private Integer borrowPeriod;

    private String borrowStyle;

    private double accountBail;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public BigDecimal getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(BigDecimal borrowApr) {
        this.borrowApr = borrowApr;
    }

    public Integer getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(Integer borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public double getAccountBail() {
        return accountBail;
    }

    public void setAccountBail(double accountBail) {
        this.accountBail = accountBail;
    }
}
