package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DebtPlan implements Serializable {
    private Integer id;

    private Integer debtPlanType;

    private String debtPlanTypeName;

    private String debtPlanNid;

    private Integer debtPrePlanNid;

    private String debtPlanName;

    private BigDecimal debtPlanMoney;

    private BigDecimal debtPlanMoneyYes;

    private BigDecimal debtPlanMoneyWait;

    private BigDecimal debtPlanAccountScale;

    private Integer debtPlanStatus;

    private BigDecimal roundAmount;

    private Integer accedeTimes;

    private BigDecimal expectApr;

    private BigDecimal actualApr;

    private String investmentScope;

    private Integer debtQuitStyle;

    private Integer debtQuitPeriod;

    private String isAudits;

    private Integer buyBeginTime;

    private Integer buyEndTime;

    private Integer buyPeriodDay;

    private Integer buyPeriodHour;

    private Integer buyPeriod;

    private Integer debtLockPeriod;

    private BigDecimal debtMinInvestment;

    private BigDecimal debtInvestmentIncrement;

    private BigDecimal debtMaxInvestment;

    private BigDecimal debtPlanBalance;

    private BigDecimal debtPlanFrost;

    private Integer fullExpireTime;

    private BigDecimal repayAccountAll;

    private BigDecimal repayAccountInterest;

    private BigDecimal repayAccountCapital;

    private BigDecimal repayAccountYes;

    private BigDecimal repayAccountInterestYes;

    private BigDecimal repayAccountCapitalYes;

    private BigDecimal repayAccountWait;

    private BigDecimal repayAccountInterestWait;

    private BigDecimal repayAccountCapitalWait;

    private BigDecimal serviceFee;

    private Integer minInvestNumber;

    private Integer maxInvestNumber;

    private Integer cycleTimes;

    private Integer unableCycleTimes;

    private BigDecimal investAccountLimit;

    private BigDecimal minSurplusInvestAccount;

    private Integer liquidateShouldTime;

    private Integer liquidateFactTime;

    private Integer repayTime;

    private Integer auditUserId;

    private Integer auditTime;

    private String auditRemark;

    private Integer planLockTime;

    private Integer commissionStatus;

    private BigDecimal commissionTotal;

    private BigDecimal liquidateApr;

    private String couponConfig;

    private BigDecimal liquidateArrivalAmount;

    private Integer repayTimeLast;

    private Integer showStatus;

    private Integer delFlag;

    private Integer createUserId;

    private String createUserName;

    private Integer updateUserId;

    private String updateUserName;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDebtPlanType() {
        return debtPlanType;
    }

    public void setDebtPlanType(Integer debtPlanType) {
        this.debtPlanType = debtPlanType;
    }

    public String getDebtPlanTypeName() {
        return debtPlanTypeName;
    }

    public void setDebtPlanTypeName(String debtPlanTypeName) {
        this.debtPlanTypeName = debtPlanTypeName == null ? null : debtPlanTypeName.trim();
    }

    public String getDebtPlanNid() {
        return debtPlanNid;
    }

    public void setDebtPlanNid(String debtPlanNid) {
        this.debtPlanNid = debtPlanNid == null ? null : debtPlanNid.trim();
    }

    public Integer getDebtPrePlanNid() {
        return debtPrePlanNid;
    }

    public void setDebtPrePlanNid(Integer debtPrePlanNid) {
        this.debtPrePlanNid = debtPrePlanNid;
    }

    public String getDebtPlanName() {
        return debtPlanName;
    }

    public void setDebtPlanName(String debtPlanName) {
        this.debtPlanName = debtPlanName == null ? null : debtPlanName.trim();
    }

    public BigDecimal getDebtPlanMoney() {
        return debtPlanMoney;
    }

    public void setDebtPlanMoney(BigDecimal debtPlanMoney) {
        this.debtPlanMoney = debtPlanMoney;
    }

    public BigDecimal getDebtPlanMoneyYes() {
        return debtPlanMoneyYes;
    }

    public void setDebtPlanMoneyYes(BigDecimal debtPlanMoneyYes) {
        this.debtPlanMoneyYes = debtPlanMoneyYes;
    }

    public BigDecimal getDebtPlanMoneyWait() {
        return debtPlanMoneyWait;
    }

    public void setDebtPlanMoneyWait(BigDecimal debtPlanMoneyWait) {
        this.debtPlanMoneyWait = debtPlanMoneyWait;
    }

    public BigDecimal getDebtPlanAccountScale() {
        return debtPlanAccountScale;
    }

    public void setDebtPlanAccountScale(BigDecimal debtPlanAccountScale) {
        this.debtPlanAccountScale = debtPlanAccountScale;
    }

    public Integer getDebtPlanStatus() {
        return debtPlanStatus;
    }

    public void setDebtPlanStatus(Integer debtPlanStatus) {
        this.debtPlanStatus = debtPlanStatus;
    }

    public BigDecimal getRoundAmount() {
        return roundAmount;
    }

    public void setRoundAmount(BigDecimal roundAmount) {
        this.roundAmount = roundAmount;
    }

    public Integer getAccedeTimes() {
        return accedeTimes;
    }

    public void setAccedeTimes(Integer accedeTimes) {
        this.accedeTimes = accedeTimes;
    }

    public BigDecimal getExpectApr() {
        return expectApr;
    }

    public void setExpectApr(BigDecimal expectApr) {
        this.expectApr = expectApr;
    }

    public BigDecimal getActualApr() {
        return actualApr;
    }

    public void setActualApr(BigDecimal actualApr) {
        this.actualApr = actualApr;
    }

    public String getInvestmentScope() {
        return investmentScope;
    }

    public void setInvestmentScope(String investmentScope) {
        this.investmentScope = investmentScope == null ? null : investmentScope.trim();
    }

    public Integer getDebtQuitStyle() {
        return debtQuitStyle;
    }

    public void setDebtQuitStyle(Integer debtQuitStyle) {
        this.debtQuitStyle = debtQuitStyle;
    }

    public Integer getDebtQuitPeriod() {
        return debtQuitPeriod;
    }

    public void setDebtQuitPeriod(Integer debtQuitPeriod) {
        this.debtQuitPeriod = debtQuitPeriod;
    }

    public String getIsAudits() {
        return isAudits;
    }

    public void setIsAudits(String isAudits) {
        this.isAudits = isAudits == null ? null : isAudits.trim();
    }

    public Integer getBuyBeginTime() {
        return buyBeginTime;
    }

    public void setBuyBeginTime(Integer buyBeginTime) {
        this.buyBeginTime = buyBeginTime;
    }

    public Integer getBuyEndTime() {
        return buyEndTime;
    }

    public void setBuyEndTime(Integer buyEndTime) {
        this.buyEndTime = buyEndTime;
    }

    public Integer getBuyPeriodDay() {
        return buyPeriodDay;
    }

    public void setBuyPeriodDay(Integer buyPeriodDay) {
        this.buyPeriodDay = buyPeriodDay;
    }

    public Integer getBuyPeriodHour() {
        return buyPeriodHour;
    }

    public void setBuyPeriodHour(Integer buyPeriodHour) {
        this.buyPeriodHour = buyPeriodHour;
    }

    public Integer getBuyPeriod() {
        return buyPeriod;
    }

    public void setBuyPeriod(Integer buyPeriod) {
        this.buyPeriod = buyPeriod;
    }

    public Integer getDebtLockPeriod() {
        return debtLockPeriod;
    }

    public void setDebtLockPeriod(Integer debtLockPeriod) {
        this.debtLockPeriod = debtLockPeriod;
    }

    public BigDecimal getDebtMinInvestment() {
        return debtMinInvestment;
    }

    public void setDebtMinInvestment(BigDecimal debtMinInvestment) {
        this.debtMinInvestment = debtMinInvestment;
    }

    public BigDecimal getDebtInvestmentIncrement() {
        return debtInvestmentIncrement;
    }

    public void setDebtInvestmentIncrement(BigDecimal debtInvestmentIncrement) {
        this.debtInvestmentIncrement = debtInvestmentIncrement;
    }

    public BigDecimal getDebtMaxInvestment() {
        return debtMaxInvestment;
    }

    public void setDebtMaxInvestment(BigDecimal debtMaxInvestment) {
        this.debtMaxInvestment = debtMaxInvestment;
    }

    public BigDecimal getDebtPlanBalance() {
        return debtPlanBalance;
    }

    public void setDebtPlanBalance(BigDecimal debtPlanBalance) {
        this.debtPlanBalance = debtPlanBalance;
    }

    public BigDecimal getDebtPlanFrost() {
        return debtPlanFrost;
    }

    public void setDebtPlanFrost(BigDecimal debtPlanFrost) {
        this.debtPlanFrost = debtPlanFrost;
    }

    public Integer getFullExpireTime() {
        return fullExpireTime;
    }

    public void setFullExpireTime(Integer fullExpireTime) {
        this.fullExpireTime = fullExpireTime;
    }

    public BigDecimal getRepayAccountAll() {
        return repayAccountAll;
    }

    public void setRepayAccountAll(BigDecimal repayAccountAll) {
        this.repayAccountAll = repayAccountAll;
    }

    public BigDecimal getRepayAccountInterest() {
        return repayAccountInterest;
    }

    public void setRepayAccountInterest(BigDecimal repayAccountInterest) {
        this.repayAccountInterest = repayAccountInterest;
    }

    public BigDecimal getRepayAccountCapital() {
        return repayAccountCapital;
    }

    public void setRepayAccountCapital(BigDecimal repayAccountCapital) {
        this.repayAccountCapital = repayAccountCapital;
    }

    public BigDecimal getRepayAccountYes() {
        return repayAccountYes;
    }

    public void setRepayAccountYes(BigDecimal repayAccountYes) {
        this.repayAccountYes = repayAccountYes;
    }

    public BigDecimal getRepayAccountInterestYes() {
        return repayAccountInterestYes;
    }

    public void setRepayAccountInterestYes(BigDecimal repayAccountInterestYes) {
        this.repayAccountInterestYes = repayAccountInterestYes;
    }

    public BigDecimal getRepayAccountCapitalYes() {
        return repayAccountCapitalYes;
    }

    public void setRepayAccountCapitalYes(BigDecimal repayAccountCapitalYes) {
        this.repayAccountCapitalYes = repayAccountCapitalYes;
    }

    public BigDecimal getRepayAccountWait() {
        return repayAccountWait;
    }

    public void setRepayAccountWait(BigDecimal repayAccountWait) {
        this.repayAccountWait = repayAccountWait;
    }

    public BigDecimal getRepayAccountInterestWait() {
        return repayAccountInterestWait;
    }

    public void setRepayAccountInterestWait(BigDecimal repayAccountInterestWait) {
        this.repayAccountInterestWait = repayAccountInterestWait;
    }

    public BigDecimal getRepayAccountCapitalWait() {
        return repayAccountCapitalWait;
    }

    public void setRepayAccountCapitalWait(BigDecimal repayAccountCapitalWait) {
        this.repayAccountCapitalWait = repayAccountCapitalWait;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Integer getMinInvestNumber() {
        return minInvestNumber;
    }

    public void setMinInvestNumber(Integer minInvestNumber) {
        this.minInvestNumber = minInvestNumber;
    }

    public Integer getMaxInvestNumber() {
        return maxInvestNumber;
    }

    public void setMaxInvestNumber(Integer maxInvestNumber) {
        this.maxInvestNumber = maxInvestNumber;
    }

    public Integer getCycleTimes() {
        return cycleTimes;
    }

    public void setCycleTimes(Integer cycleTimes) {
        this.cycleTimes = cycleTimes;
    }

    public Integer getUnableCycleTimes() {
        return unableCycleTimes;
    }

    public void setUnableCycleTimes(Integer unableCycleTimes) {
        this.unableCycleTimes = unableCycleTimes;
    }

    public BigDecimal getInvestAccountLimit() {
        return investAccountLimit;
    }

    public void setInvestAccountLimit(BigDecimal investAccountLimit) {
        this.investAccountLimit = investAccountLimit;
    }

    public BigDecimal getMinSurplusInvestAccount() {
        return minSurplusInvestAccount;
    }

    public void setMinSurplusInvestAccount(BigDecimal minSurplusInvestAccount) {
        this.minSurplusInvestAccount = minSurplusInvestAccount;
    }

    public Integer getLiquidateShouldTime() {
        return liquidateShouldTime;
    }

    public void setLiquidateShouldTime(Integer liquidateShouldTime) {
        this.liquidateShouldTime = liquidateShouldTime;
    }

    public Integer getLiquidateFactTime() {
        return liquidateFactTime;
    }

    public void setLiquidateFactTime(Integer liquidateFactTime) {
        this.liquidateFactTime = liquidateFactTime;
    }

    public Integer getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(Integer repayTime) {
        this.repayTime = repayTime;
    }

    public Integer getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Integer auditUserId) {
        this.auditUserId = auditUserId;
    }

    public Integer getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Integer auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark == null ? null : auditRemark.trim();
    }

    public Integer getPlanLockTime() {
        return planLockTime;
    }

    public void setPlanLockTime(Integer planLockTime) {
        this.planLockTime = planLockTime;
    }

    public Integer getCommissionStatus() {
        return commissionStatus;
    }

    public void setCommissionStatus(Integer commissionStatus) {
        this.commissionStatus = commissionStatus;
    }

    public BigDecimal getCommissionTotal() {
        return commissionTotal;
    }

    public void setCommissionTotal(BigDecimal commissionTotal) {
        this.commissionTotal = commissionTotal;
    }

    public BigDecimal getLiquidateApr() {
        return liquidateApr;
    }

    public void setLiquidateApr(BigDecimal liquidateApr) {
        this.liquidateApr = liquidateApr;
    }

    public String getCouponConfig() {
        return couponConfig;
    }

    public void setCouponConfig(String couponConfig) {
        this.couponConfig = couponConfig == null ? null : couponConfig.trim();
    }

    public BigDecimal getLiquidateArrivalAmount() {
        return liquidateArrivalAmount;
    }

    public void setLiquidateArrivalAmount(BigDecimal liquidateArrivalAmount) {
        this.liquidateArrivalAmount = liquidateArrivalAmount;
    }

    public Integer getRepayTimeLast() {
        return repayTimeLast;
    }

    public void setRepayTimeLast(Integer repayTimeLast) {
        this.repayTimeLast = repayTimeLast;
    }

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
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