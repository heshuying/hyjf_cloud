/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayPlanVO, v0.1 2018/6/22 15:27
 */
public class BorrowRepayPlanVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -1229013393923143695L;

    private Integer id;

    private Integer status;

    private Integer userId;

    private String userName;

    private String borrowNid;

    private String nid;

    private String repayType;

    private BigDecimal repayFee;

    private String repayActionTime;

    private Integer repayStatus;

    private Integer repayPeriod;

    private Integer repayTime;

    private Integer repayYestime;

    private BigDecimal repayAccountAll;

    private BigDecimal repayAccount;

    private BigDecimal repayInterest;

    private BigDecimal repayCapital;

    private BigDecimal repayAccountYes;

    private Integer lateRepayDays;

    private BigDecimal repayInterestYes;

    private BigDecimal repayCapitalYes;

    private BigDecimal repayCapitalWait;

    private BigDecimal repayInterestWait;

    private Integer advanceStatus;

    private Integer chargeDays;

    private BigDecimal chargeInterest;

    private Integer lateDays;

    private BigDecimal lateInterest;

    private Integer delayDays;

    private BigDecimal delayInterest;

    private String delayRemark;

    private Integer addTime;

    private String addip;

    private Integer repayMoneySource;

    private Integer repayUserId;

    private String repayUsername;

    private Integer repaySmsReminder;

    private Integer autoRepay;
    private Date createTime;

    // 提前还款罚息
    public BigDecimal chargePenaltyInterest;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        this.userName = userName;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public BigDecimal getRepayFee() {
        return repayFee;
    }

    public void setRepayFee(BigDecimal repayFee) {
        this.repayFee = repayFee;
    }

    public String getRepayActionTime() {
        return repayActionTime;
    }

    public void setRepayActionTime(String repayActionTime) {
        this.repayActionTime = repayActionTime;
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
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

    public Integer getRepayYestime() {
        return repayYestime;
    }

    public void setRepayYestime(Integer repayYestime) {
        this.repayYestime = repayYestime;
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

    public Integer getLateRepayDays() {
        return lateRepayDays;
    }

    public void setLateRepayDays(Integer lateRepayDays) {
        this.lateRepayDays = lateRepayDays;
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

    public Integer getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(Integer chargeDays) {
        this.chargeDays = chargeDays;
    }

    public BigDecimal getChargeInterest() {
        return chargeInterest;
    }

    public void setChargeInterest(BigDecimal chargeInterest) {
        this.chargeInterest = chargeInterest;
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

    public String getDelayRemark() {
        return delayRemark;
    }

    public void setDelayRemark(String delayRemark) {
        this.delayRemark = delayRemark;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip;
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
        this.repayUsername = repayUsername;
    }

    public Integer getRepaySmsReminder() {
        return repaySmsReminder;
    }

    public void setRepaySmsReminder(Integer repaySmsReminder) {
        this.repaySmsReminder = repaySmsReminder;
    }

    public Integer getAutoRepay() {
        return autoRepay;
    }

    public void setAutoRepay(Integer autoRepay) {
        this.autoRepay = autoRepay;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getChargePenaltyInterest() {
        return chargePenaltyInterest;
    }

    public void setChargePenaltyInterest(BigDecimal chargePenaltyInterest) {
        this.chargePenaltyInterest = chargePenaltyInterest;
    }
}
