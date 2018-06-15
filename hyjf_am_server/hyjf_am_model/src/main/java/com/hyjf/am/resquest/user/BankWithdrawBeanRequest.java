package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.trade.AccountVO;
import com.hyjf.am.vo.trade.AccountWithdrawVO;

import java.math.BigDecimal;

/**
 * @author pangchengchao
 * @version BankWithdrawBeanRequest, v0.1 2018/6/13 15:00
 *//*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
public class BankWithdrawBeanRequest {

    private AccountWithdrawVO accountWithdrawVO;
    private AccountVO accountVO;
    private String ip;
    private Integer userId;
    private BigDecimal transAmt;
    private String fee;
    private BigDecimal feeAmt;
    private BigDecimal total;
    private String ordId;
    private int nowTime;
    private String accountId;


    public AccountWithdrawVO getAccountWithdrawVO() {
        return accountWithdrawVO;
    }

    public void setAccountWithdrawVO(AccountWithdrawVO accountWithdrawVO) {
        this.accountWithdrawVO = accountWithdrawVO;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public AccountVO getAccountVO() {
        return accountVO;
    }

    public void setAccountVO(AccountVO accountVO) {
        this.accountVO = accountVO;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public BigDecimal getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId;
    }

    public int getNowTime() {
        return nowTime;
    }

    public void setNowTime(int nowTime) {
        this.nowTime = nowTime;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
