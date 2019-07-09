package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BankRepayOrgFreezeLog implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 还款人用户userId
     *
     * @mbggenerated
     */
    private Integer repayUserId;

    /**
     * 还款人用户名
     *
     * @mbggenerated
     */
    private String repayUserName;

    /**
     * 借款人userId
     *
     * @mbggenerated
     */
    private Integer borrowUserId;

    /**
     * 借款人用户名
     *
     * @mbggenerated
     */
    private String borrowUserName;

    /**
     * 电子账号
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 订单号:txDate+txTime+seqNo
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 借款编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 计划编号
     *
     * @mbggenerated
     */
    private String planNid;

    /**
     * 资产来源
     *
     * @mbggenerated
     */
    private String instCode;

    /**
     * 借款金额
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * 应还本息
     *
     * @mbggenerated
     */
    private BigDecimal repayAccount;

    /**
     * 还款服务费
     *
     * @mbggenerated
     */
    private BigDecimal repayFee;

    /**
     * 冻结金额
     *
     * @mbggenerated
     */
    private BigDecimal amountFreeze;

    /**
     * 减息金额
     *
     * @mbggenerated
     */
    private BigDecimal lowerInterest;

    /**
     * 违约金
     *
     * @mbggenerated
     */
    private BigDecimal penaltyAmount;

    /**
     * 逾期罚息
     *
     * @mbggenerated
     */
    private BigDecimal defaultInterest;

    /**
     * 借款期限
     *
     * @mbggenerated
     */
    private String borrowPeriod;

    /**
     * 总期数
     *
     * @mbggenerated
     */
    private Integer totalPeriod;

    /**
     * 当前期数
     *
     * @mbggenerated
     */
    private Integer currentPeriod;

    /**
     * 是否全部还款 0否 1是
     *
     * @mbggenerated
     */
    private Integer allRepayFlag;

    /**
     * 是否有效 0有效 1无效记录
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 创建用户userId
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
     * 最后修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 更新用户userId
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
     * 逾期还款期数(单笔或者多笔的最后一期)
     *
     * @mbggenerated
     */
    private Integer latePeriod;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRepayUserId() {
        return repayUserId;
    }

    public void setRepayUserId(Integer repayUserId) {
        this.repayUserId = repayUserId;
    }

    public String getRepayUserName() {
        return repayUserName;
    }

    public void setRepayUserName(String repayUserName) {
        this.repayUserName = repayUserName == null ? null : repayUserName.trim();
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode == null ? null : instCode.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRepayAccount() {
        return repayAccount;
    }

    public void setRepayAccount(BigDecimal repayAccount) {
        this.repayAccount = repayAccount;
    }

    public BigDecimal getRepayFee() {
        return repayFee;
    }

    public void setRepayFee(BigDecimal repayFee) {
        this.repayFee = repayFee;
    }

    public BigDecimal getAmountFreeze() {
        return amountFreeze;
    }

    public void setAmountFreeze(BigDecimal amountFreeze) {
        this.amountFreeze = amountFreeze;
    }

    public BigDecimal getLowerInterest() {
        return lowerInterest;
    }

    public void setLowerInterest(BigDecimal lowerInterest) {
        this.lowerInterest = lowerInterest;
    }

    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(BigDecimal penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public BigDecimal getDefaultInterest() {
        return defaultInterest;
    }

    public void setDefaultInterest(BigDecimal defaultInterest) {
        this.defaultInterest = defaultInterest;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod == null ? null : borrowPeriod.trim();
    }

    public Integer getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(Integer totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public Integer getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(Integer currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public Integer getAllRepayFlag() {
        return allRepayFlag;
    }

    public void setAllRepayFlag(Integer allRepayFlag) {
        this.allRepayFlag = allRepayFlag;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
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

    public Integer getLatePeriod() {
        return latePeriod;
    }

    public void setLatePeriod(Integer latePeriod) {
        this.latePeriod = latePeriod;
    }
}