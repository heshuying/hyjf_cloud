package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IncreaseInterestRepay implements Serializable {
    private Integer id;

    /**
     * 用户ID
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
     * 投资id
     *
     * @mbggenerated
     */
    private Integer investId;

    /**
     * 投资订单号
     *
     * @mbggenerated
     */
    private String investOrderId;

    /**
     * 投资金额
     *
     * @mbggenerated
     */
    private BigDecimal investAccount;

    /**
     * 借款编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 借款利率
     *
     * @mbggenerated
     */
    private BigDecimal borrowApr;

    /**
     * 产品加息收益率（风险缓释金）
     *
     * @mbggenerated
     */
    private BigDecimal borrowExtraYield;

    /**
     * 借款期限
     *
     * @mbggenerated
     */
    private Integer borrowPeriod;

    /**
     * 借款类型
     *
     * @mbggenerated
     */
    private String borrowStyle;

    /**
     * 借款类型名称
     *
     * @mbggenerated
     */
    private String borrowStyleName;

    /**
     * 借款金额
     *
     * @mbggenerated
     */
    private BigDecimal borrowAccount;

    /**
     * 转账订单号
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 转账订单日期
     *
     * @mbggenerated
     */
    private String orderDate;

    /**
     * 还款状态 0未转账  1已转账 2转账中
     *
     * @mbggenerated
     */
    private Integer repayStatus;

    /**
     * 剩余期数
     *
     * @mbggenerated
     */
    private Integer remainPeriod;

    /**
     * 已还款期数
     *
     * @mbggenerated
     */
    private Integer alreadyRepayPeriod;

    /**
     * 还款期数
     *
     * @mbggenerated
     */
    private Integer repayPeriod;

    /**
     * 估计还款时间
     *
     * @mbggenerated
     */
    private Integer repayTime;

    /**
     * 已经还款时间
     *
     * @mbggenerated
     */
    private Integer repayActionTime;

    /**
     * 应还款利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterest;

    /**
     * 已还款利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterestYes;

    /**
     * 待还利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterestWait;

    /**
     * create ip
     *
     * @mbggenerated
     */
    private String addIp;

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
     * 实际放款时间
     *
     * @mbggenerated
     */
    private Integer loanActionTime;

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

    public Integer getInvestId() {
        return investId;
    }

    public void setInvestId(Integer investId) {
        this.investId = investId;
    }

    public String getInvestOrderId() {
        return investOrderId;
    }

    public void setInvestOrderId(String investOrderId) {
        this.investOrderId = investOrderId == null ? null : investOrderId.trim();
    }

    public BigDecimal getInvestAccount() {
        return investAccount;
    }

    public void setInvestAccount(BigDecimal investAccount) {
        this.investAccount = investAccount;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public BigDecimal getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(BigDecimal borrowApr) {
        this.borrowApr = borrowApr;
    }

    public BigDecimal getBorrowExtraYield() {
        return borrowExtraYield;
    }

    public void setBorrowExtraYield(BigDecimal borrowExtraYield) {
        this.borrowExtraYield = borrowExtraYield;
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
        this.borrowStyle = borrowStyle == null ? null : borrowStyle.trim();
    }

    public String getBorrowStyleName() {
        return borrowStyleName;
    }

    public void setBorrowStyleName(String borrowStyleName) {
        this.borrowStyleName = borrowStyleName == null ? null : borrowStyleName.trim();
    }

    public BigDecimal getBorrowAccount() {
        return borrowAccount;
    }

    public void setBorrowAccount(BigDecimal borrowAccount) {
        this.borrowAccount = borrowAccount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate == null ? null : orderDate.trim();
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public Integer getRemainPeriod() {
        return remainPeriod;
    }

    public void setRemainPeriod(Integer remainPeriod) {
        this.remainPeriod = remainPeriod;
    }

    public Integer getAlreadyRepayPeriod() {
        return alreadyRepayPeriod;
    }

    public void setAlreadyRepayPeriod(Integer alreadyRepayPeriod) {
        this.alreadyRepayPeriod = alreadyRepayPeriod;
    }

    public Integer getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(Integer repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public Integer getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(Integer repayTime) {
        this.repayTime = repayTime;
    }

    public Integer getRepayActionTime() {
        return repayActionTime;
    }

    public void setRepayActionTime(Integer repayActionTime) {
        this.repayActionTime = repayActionTime;
    }

    public BigDecimal getRepayInterest() {
        return repayInterest;
    }

    public void setRepayInterest(BigDecimal repayInterest) {
        this.repayInterest = repayInterest;
    }

    public BigDecimal getRepayInterestYes() {
        return repayInterestYes;
    }

    public void setRepayInterestYes(BigDecimal repayInterestYes) {
        this.repayInterestYes = repayInterestYes;
    }

    public BigDecimal getRepayInterestWait() {
        return repayInterestWait;
    }

    public void setRepayInterestWait(BigDecimal repayInterestWait) {
        this.repayInterestWait = repayInterestWait;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
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

    public Integer getLoanActionTime() {
        return loanActionTime;
    }

    public void setLoanActionTime(Integer loanActionTime) {
        this.loanActionTime = loanActionTime;
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