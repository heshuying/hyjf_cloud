package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HjhDebtDetail implements Serializable {
    /**
     * 主键id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 投资用户编号
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 投资者用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 借款用户编号
     *
     * @mbggenerated
     */
    private Integer borrowUserId;

    /**
     * 借款者用户名
     *
     * @mbggenerated
     */
    private String borrowUserName;

    /**
     * 项目标的号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 原标标题
     *
     * @mbggenerated
     */
    private String borrowName;

    /**
     * 原标年化利率
     *
     * @mbggenerated
     */
    private BigDecimal borrowApr;

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
     * 原始标的投资订单号
     *
     * @mbggenerated
     */
    private String investOrderId;

    /**
     * 订单号(原始投资:投资订单号,承接债权:承接订单号)
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 上次债转编号
     *
     * @mbggenerated
     */
    private String creditNid;

    /**
     * 订单日期
     *
     * @mbggenerated
     */
    private String orderDate;

    /**
     * 订单类型 0 汇添金专属项目投资 1 债权承接
     *
     * @mbggenerated
     */
    private Integer orderType;

    /**
     * 是否原始债权 0非原始 1原始
     *
     * @mbggenerated
     */
    private Integer sourceType;

    /**
     * 投资金额或者债权承接金额
     *
     * @mbggenerated
     */
    private BigDecimal account;

    private Integer loanTime;

    /**
     * 放款本金（应还本金）
     *
     * @mbggenerated
     */
    private BigDecimal loanCapital;

    /**
     * 放款利息（应还利息）
     *
     * @mbggenerated
     */
    private BigDecimal loanInterest;

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
     * 实际还款时间
     *
     * @mbggenerated
     */
    private Integer repayActionTime;

    /**
     * 已还本金
     *
     * @mbggenerated
     */
    private BigDecimal repayCapitalYes;

    /**
     * 已还利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterestYes;

    /**
     * 未收本金
     *
     * @mbggenerated
     */
    private BigDecimal repayCapitalWait;

    /**
     * 未收利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterestWait;

    /**
     * 还款状态 0未还款 1已还款
     *
     * @mbggenerated
     */
    private Integer repayStatus;

    /**
     * 账户管理费
     *
     * @mbggenerated
     */
    private BigDecimal manageFee;

    /**
     * 债权收取的服务费
     *
     * @mbggenerated
     */
    private BigDecimal serviceFee;

    /**
     * 是否提前还款 0正常还款 1提前还款 2延期还款 3逾期还款
     *
     * @mbggenerated
     */
    private Integer advanceStatus;

    /**
     * 罚息天数
     *
     * @mbggenerated
     */
    private Integer advanceDays;

    /**
     * 罚息总额
     *
     * @mbggenerated
     */
    private BigDecimal advanceInterest;

    /**
     * 逾期天数
     *
     * @mbggenerated
     */
    private Integer lateDays;

    /**
     * 逾期利息
     *
     * @mbggenerated
     */
    private BigDecimal lateInterest;

    /**
     * 承接垫付的逾期利息
     *
     * @mbggenerated
     */
    private BigDecimal lateInterestAssigned;

    /**
     * 延期天数
     *
     * @mbggenerated
     */
    private Integer delayDays;

    /**
     * 延期利息
     *
     * @mbggenerated
     */
    private BigDecimal delayInterest;

    /**
     * 承接垫付的延期利息
     *
     * @mbggenerated
     */
    private BigDecimal delayInterestAssigned;

    /**
     * 还款订单号
     *
     * @mbggenerated
     */
    private String repayOrderId;

    private String repayOrderDate;

    /**
     * 到期公允价值
     *
     * @mbggenerated
     */
    private BigDecimal expireFairValue;

    /**
     * 债权上次被清算时间
     *
     * @mbggenerated
     */
    private Integer lastLiquidationTime;

    /**
     * 债权是否有效（0失效 1有效）
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 客户端0PC，1微信2安卓APP，3IOS APP，4其他
     *
     * @mbggenerated
     */
    private Integer client;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 债权出让次数
     *
     * @mbggenerated
     */
    private Integer creditTimes;

    /**
     * 清算标识 0未清算 1清算
     *
     * @mbggenerated
     */
    private Integer delFlag;

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

    public Integer getBorrowUserId() {
        return borrowUserId;
    }

    public void setBorrowUserId(Integer borrowUserId) {
        this.borrowUserId = borrowUserId;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName == null ? null : borrowUserName.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName == null ? null : borrowName.trim();
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
        this.borrowStyle = borrowStyle == null ? null : borrowStyle.trim();
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

    public String getInvestOrderId() {
        return investOrderId;
    }

    public void setInvestOrderId(String investOrderId) {
        this.investOrderId = investOrderId == null ? null : investOrderId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid == null ? null : creditNid.trim();
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate == null ? null : orderDate.trim();
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Integer loanTime) {
        this.loanTime = loanTime;
    }

    public BigDecimal getLoanCapital() {
        return loanCapital;
    }

    public void setLoanCapital(BigDecimal loanCapital) {
        this.loanCapital = loanCapital;
    }

    public BigDecimal getLoanInterest() {
        return loanInterest;
    }

    public void setLoanInterest(BigDecimal loanInterest) {
        this.loanInterest = loanInterest;
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

    public BigDecimal getRepayCapitalYes() {
        return repayCapitalYes;
    }

    public void setRepayCapitalYes(BigDecimal repayCapitalYes) {
        this.repayCapitalYes = repayCapitalYes;
    }

    public BigDecimal getRepayInterestYes() {
        return repayInterestYes;
    }

    public void setRepayInterestYes(BigDecimal repayInterestYes) {
        this.repayInterestYes = repayInterestYes;
    }

    public BigDecimal getRepayCapitalWait() {
        return repayCapitalWait;
    }

    public void setRepayCapitalWait(BigDecimal repayCapitalWait) {
        this.repayCapitalWait = repayCapitalWait;
    }

    public BigDecimal getRepayInterestWait() {
        return repayInterestWait;
    }

    public void setRepayInterestWait(BigDecimal repayInterestWait) {
        this.repayInterestWait = repayInterestWait;
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public BigDecimal getManageFee() {
        return manageFee;
    }

    public void setManageFee(BigDecimal manageFee) {
        this.manageFee = manageFee;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Integer getAdvanceStatus() {
        return advanceStatus;
    }

    public void setAdvanceStatus(Integer advanceStatus) {
        this.advanceStatus = advanceStatus;
    }

    public Integer getAdvanceDays() {
        return advanceDays;
    }

    public void setAdvanceDays(Integer advanceDays) {
        this.advanceDays = advanceDays;
    }

    public BigDecimal getAdvanceInterest() {
        return advanceInterest;
    }

    public void setAdvanceInterest(BigDecimal advanceInterest) {
        this.advanceInterest = advanceInterest;
    }

    public Integer getLateDays() {
        return lateDays;
    }

    public void setLateDays(Integer lateDays) {
        this.lateDays = lateDays;
    }

    public BigDecimal getLateInterest() {
        return lateInterest;
    }

    public void setLateInterest(BigDecimal lateInterest) {
        this.lateInterest = lateInterest;
    }

    public BigDecimal getLateInterestAssigned() {
        return lateInterestAssigned;
    }

    public void setLateInterestAssigned(BigDecimal lateInterestAssigned) {
        this.lateInterestAssigned = lateInterestAssigned;
    }

    public Integer getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(Integer delayDays) {
        this.delayDays = delayDays;
    }

    public BigDecimal getDelayInterest() {
        return delayInterest;
    }

    public void setDelayInterest(BigDecimal delayInterest) {
        this.delayInterest = delayInterest;
    }

    public BigDecimal getDelayInterestAssigned() {
        return delayInterestAssigned;
    }

    public void setDelayInterestAssigned(BigDecimal delayInterestAssigned) {
        this.delayInterestAssigned = delayInterestAssigned;
    }

    public String getRepayOrderId() {
        return repayOrderId;
    }

    public void setRepayOrderId(String repayOrderId) {
        this.repayOrderId = repayOrderId == null ? null : repayOrderId.trim();
    }

    public String getRepayOrderDate() {
        return repayOrderDate;
    }

    public void setRepayOrderDate(String repayOrderDate) {
        this.repayOrderDate = repayOrderDate == null ? null : repayOrderDate.trim();
    }

    public BigDecimal getExpireFairValue() {
        return expireFairValue;
    }

    public void setExpireFairValue(BigDecimal expireFairValue) {
        this.expireFairValue = expireFairValue;
    }

    public Integer getLastLiquidationTime() {
        return lastLiquidationTime;
    }

    public void setLastLiquidationTime(Integer lastLiquidationTime) {
        this.lastLiquidationTime = lastLiquidationTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCreditTimes() {
        return creditTimes;
    }

    public void setCreditTimes(Integer creditTimes) {
        this.creditTimes = creditTimes;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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