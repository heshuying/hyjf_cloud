package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DebtAccountList implements Serializable {
    private Integer id;

    private String nid;

    private Integer userId;

    private String userName;

    private Integer refererUserId;

    private String refererUserName;

    private String planNid;

    private String planOrderId;

    private BigDecimal planOrderBalance;

    private BigDecimal planOrderFrost;

    private BigDecimal planBalance;

    private BigDecimal planFrost;

    private BigDecimal amount;

    private Integer type;

    private String trade;

    private String tradeCode;

    private BigDecimal total;

    private BigDecimal balance;

    private BigDecimal frost;

    private BigDecimal accountWait;

    private BigDecimal capitalWait;

    private BigDecimal interestWait;

    private BigDecimal repayWait;

    private String remark;

    private String ip;

    private Integer web;

    private Integer createTime;

    private Integer createUserId;

    private String createUserName;

    private Integer updateTime;

    private Integer updateUserId;

    private String updateUserName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid == null ? null : nid.trim();
    }

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
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getRefererUserId() {
        return refererUserId;
    }

    public void setRefererUserId(Integer refererUserId) {
        this.refererUserId = refererUserId;
    }

    public String getRefererUserName() {
        return refererUserName;
    }

    public void setRefererUserName(String refererUserName) {
        this.refererUserName = refererUserName == null ? null : refererUserName.trim();
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
    }

    public String getPlanOrderId() {
        return planOrderId;
    }

    public void setPlanOrderId(String planOrderId) {
        this.planOrderId = planOrderId == null ? null : planOrderId.trim();
    }

    public BigDecimal getPlanOrderBalance() {
        return planOrderBalance;
    }

    public void setPlanOrderBalance(BigDecimal planOrderBalance) {
        this.planOrderBalance = planOrderBalance;
    }

    public BigDecimal getPlanOrderFrost() {
        return planOrderFrost;
    }

    public void setPlanOrderFrost(BigDecimal planOrderFrost) {
        this.planOrderFrost = planOrderFrost;
    }

    public BigDecimal getPlanBalance() {
        return planBalance;
    }

    public void setPlanBalance(BigDecimal planBalance) {
        this.planBalance = planBalance;
    }

    public BigDecimal getPlanFrost() {
        return planFrost;
    }

    public void setPlanFrost(BigDecimal planFrost) {
        this.planFrost = planFrost;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade == null ? null : trade.trim();
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode == null ? null : tradeCode.trim();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFrost() {
        return frost;
    }

    public void setFrost(BigDecimal frost) {
        this.frost = frost;
    }

    public BigDecimal getAccountWait() {
        return accountWait;
    }

    public void setAccountWait(BigDecimal accountWait) {
        this.accountWait = accountWait;
    }

    public BigDecimal getCapitalWait() {
        return capitalWait;
    }

    public void setCapitalWait(BigDecimal capitalWait) {
        this.capitalWait = capitalWait;
    }

    public BigDecimal getInterestWait() {
        return interestWait;
    }

    public void setInterestWait(BigDecimal interestWait) {
        this.interestWait = interestWait;
    }

    public BigDecimal getRepayWait() {
        return repayWait;
    }

    public void setRepayWait(BigDecimal repayWait) {
        this.repayWait = repayWait;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getWeb() {
        return web;
    }

    public void setWeb(Integer web) {
        this.web = web;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
    }
}