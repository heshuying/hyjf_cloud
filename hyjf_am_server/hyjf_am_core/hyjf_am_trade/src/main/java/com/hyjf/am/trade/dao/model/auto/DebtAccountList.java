package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DebtAccountList implements Serializable {
    private Integer id;

    /**
     * 交易凭证号
     *
     * @mbggenerated
     */
    private String nid;

    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 推荐人用户userId
     *
     * @mbggenerated
     */
    private Integer refererUserId;

    /**
     * 推荐人用户名
     *
     * @mbggenerated
     */
    private String refererUserName;

    /**
     * 计划nid
     *
     * @mbggenerated
     */
    private String planNid;

    /**
     * 计划订单号
     *
     * @mbggenerated
     */
    private String planOrderId;

    /**
     * 计划订单余额
     *
     * @mbggenerated
     */
    private BigDecimal planOrderBalance;

    /**
     * 计划订单冻结金额
     *
     * @mbggenerated
     */
    private BigDecimal planOrderFrost;

    /**
     * 计划账户余额
     *
     * @mbggenerated
     */
    private BigDecimal planBalance;

    /**
     * 计划账户冻结金额
     *
     * @mbggenerated
     */
    private BigDecimal planFrost;

    /**
     * 操作金额
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * 收支类型1收入2支出3冻结4解冻
     *
     * @mbggenerated
     */
    private Integer type;

    /**
     * 交易类型
     *
     * @mbggenerated
     */
    private String trade;

    /**
     * 操作识别码 balance余额操作 frost冻结操作 await待收操作
     *
     * @mbggenerated
     */
    private String tradeCode;

    /**
     * 资金总额(可用+冻结+待收)
     *
     * @mbggenerated
     */
    private BigDecimal total;

    /**
     * 可用金额
     *
     * @mbggenerated
     */
    private BigDecimal balance;

    /**
     * 冻结金额
     *
     * @mbggenerated
     */
    private BigDecimal frost;

    /**
     * 待收总金额
     *
     * @mbggenerated
     */
    private BigDecimal accountWait;

    /**
     * 待收本金
     *
     * @mbggenerated
     */
    private BigDecimal capitalWait;

    /**
     * 待还收益
     *
     * @mbggenerated
     */
    private BigDecimal interestWait;

    /**
     * 待还金额
     *
     * @mbggenerated
     */
    private BigDecimal repayWait;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 操作IP
     *
     * @mbggenerated
     */
    private String ip;

    /**
     * 网站收支计算标识
     *
     * @mbggenerated
     */
    private Integer web;

    /**
     * 创建用户id
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建用户名
     *
     * @mbggenerated
     */
    private String createUserName;

    /**
     * 更新用户名
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 更新用户名
     *
     * @mbggenerated
     */
    private String updateUserName;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

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