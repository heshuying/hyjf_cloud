package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;

import java.math.BigDecimal;

/**
 * 还款冻结日志表
 * @author hesy
 * @version BankRepayFreezeLogRequest, v0.1 2018/7/9 15:58
 */
public class BankRepayFreezeLogRequest extends Request {
    private Integer userId;

    private String account;

    private String orderId;

    private BigDecimal amount;

    private String userName;

    private String borrowNid;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

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
}
