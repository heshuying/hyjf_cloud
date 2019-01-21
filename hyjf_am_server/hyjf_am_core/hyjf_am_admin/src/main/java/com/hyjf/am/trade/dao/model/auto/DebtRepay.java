package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DebtRepay implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 借款用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 借款用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 项目编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 计划nid
     *
     * @mbggenerated
     */
    private String planNid;

    /**
     * 还款唯一标识
     *
     * @mbggenerated
     */
    private String repayNid;

    /**
     * 还款费用
     *
     * @mbggenerated
     */
    private BigDecimal manageFee;

    /**
     * 清算时收取的服务费
     *
     * @mbggenerated
     */
    private BigDecimal liquidatesServiceFee;

    /**
     * 放款状态 0放款中1已放款
     *
     * @mbggenerated
     */
    private Integer loanStatus;

    /**
     * 还款状态 0未还款 1还款中 2已还款
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
     * 已还款期数
     *
     * @mbggenerated
     */
    private Integer repayPeriod;

    /**
     * 估计还款时间(下期还款时间)
     *
     * @mbggenerated
     */
    private String repayTime;

    /**
     * 实际还款的时间
     *
     * @mbggenerated
     */
    private String repayActionTime;

    /**
     * 应还款总额，加上费用
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountAll;

    /**
     * 应还款本息
     *
     * @mbggenerated
     */
    private BigDecimal repayAccount;

    /**
     * 应还款利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterest;

    /**
     * 应还款本金
     *
     * @mbggenerated
     */
    private BigDecimal repayCapital;

    /**
     * 已还款本息
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountYes;

    /**
     * 已还款利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterestYes;

    /**
     * 已还款本金
     *
     * @mbggenerated
     */
    private BigDecimal repayCapitalYes;

    /**
     * 待还款本息
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountWait;

    /**
     * 待还款本金
     *
     * @mbggenerated
     */
    private BigDecimal repayCapitalWait;

    /**
     * 待还利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterestWait;

    /**
     * 是否提前还款 0正常还款 1提前还款 2延期还款 3逾期还款
     *
     * @mbggenerated
     */
    private Integer advanceStatus;

    /**
     * 提前还款天数
     *
     * @mbggenerated
     */
    private Integer advanceDays;

    /**
     * 提前还款利息
     *
     * @mbggenerated
     */
    private BigDecimal advanceInterest;

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
     * 延期备注
     *
     * @mbggenerated
     */
    private String delayRemark;

    /**
     * create ip
     *
     * @mbggenerated
     */
    private String addIp;

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
     * 还款来源（1、借款人还款，2、机构垫付，3、保证金垫付）
     *
     * @mbggenerated
     */
    private Integer repayMoneySource;

    /**
     * 实际还款人（借款人、担保机构、保证金）的用户ID
     *
     * @mbggenerated
     */
    private Integer repayUserId;

    /**
     * 实际还款人（借款人、担保机构、保证金）的用户名
     *
     * @mbggenerated
     */
    private String repayUsername;

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

    public String getRepayNid() {
        return repayNid;
    }

    public void setRepayNid(String repayNid) {
        this.repayNid = repayNid == null ? null : repayNid.trim();
    }

    public BigDecimal getManageFee() {
        return manageFee;
    }

    public void setManageFee(BigDecimal manageFee) {
        this.manageFee = manageFee;
    }

    public BigDecimal getLiquidatesServiceFee() {
        return liquidatesServiceFee;
    }

    public void setLiquidatesServiceFee(BigDecimal liquidatesServiceFee) {
        this.liquidatesServiceFee = liquidatesServiceFee;
    }

    public Integer getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(Integer loanStatus) {
        this.loanStatus = loanStatus;
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

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime == null ? null : repayTime.trim();
    }

    public String getRepayActionTime() {
        return repayActionTime;
    }

    public void setRepayActionTime(String repayActionTime) {
        this.repayActionTime = repayActionTime == null ? null : repayActionTime.trim();
    }

    public BigDecimal getRepayAccountAll() {
        return repayAccountAll;
    }

    public void setRepayAccountAll(BigDecimal repayAccountAll) {
        this.repayAccountAll = repayAccountAll;
    }

    public BigDecimal getRepayAccount() {
        return repayAccount;
    }

    public void setRepayAccount(BigDecimal repayAccount) {
        this.repayAccount = repayAccount;
    }

    public BigDecimal getRepayInterest() {
        return repayInterest;
    }

    public void setRepayInterest(BigDecimal repayInterest) {
        this.repayInterest = repayInterest;
    }

    public BigDecimal getRepayCapital() {
        return repayCapital;
    }

    public void setRepayCapital(BigDecimal repayCapital) {
        this.repayCapital = repayCapital;
    }

    public BigDecimal getRepayAccountYes() {
        return repayAccountYes;
    }

    public void setRepayAccountYes(BigDecimal repayAccountYes) {
        this.repayAccountYes = repayAccountYes;
    }

    public BigDecimal getRepayInterestYes() {
        return repayInterestYes;
    }

    public void setRepayInterestYes(BigDecimal repayInterestYes) {
        this.repayInterestYes = repayInterestYes;
    }

    public BigDecimal getRepayCapitalYes() {
        return repayCapitalYes;
    }

    public void setRepayCapitalYes(BigDecimal repayCapitalYes) {
        this.repayCapitalYes = repayCapitalYes;
    }

    public BigDecimal getRepayAccountWait() {
        return repayAccountWait;
    }

    public void setRepayAccountWait(BigDecimal repayAccountWait) {
        this.repayAccountWait = repayAccountWait;
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

    public String getDelayRemark() {
        return delayRemark;
    }

    public void setDelayRemark(String delayRemark) {
        this.delayRemark = delayRemark == null ? null : delayRemark.trim();
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
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

    public Integer getRepayMoneySource() {
        return repayMoneySource;
    }

    public void setRepayMoneySource(Integer repayMoneySource) {
        this.repayMoneySource = repayMoneySource;
    }

    public Integer getRepayUserId() {
        return repayUserId;
    }

    public void setRepayUserId(Integer repayUserId) {
        this.repayUserId = repayUserId;
    }

    public String getRepayUsername() {
        return repayUsername;
    }

    public void setRepayUsername(String repayUsername) {
        this.repayUsername = repayUsername == null ? null : repayUsername.trim();
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