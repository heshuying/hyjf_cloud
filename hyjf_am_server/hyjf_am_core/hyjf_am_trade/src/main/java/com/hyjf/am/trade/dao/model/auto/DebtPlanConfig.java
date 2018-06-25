package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DebtPlanConfig implements Serializable {
    private Integer id;

    private Integer debtPlanType;

    private String debtPlanTypeName;

    private String debtPlanPrefix;

    private Integer debtLockPeriod;

    private BigDecimal debtMinInvestment;

    private BigDecimal debtInvestmentIncrement;

    private BigDecimal debtMaxInvestment;

    private Integer debtQuitStyle;

    private Integer debtQuitPeriod;

    private Integer minInvestNumber;

    private Integer maxInvestNumber;

    private Integer cycleTimes;

    private Integer unableCycleTimes;

    private BigDecimal investAccountLimit;

    private BigDecimal minSurplusInvestAccount;

    private BigDecimal roundAmount;

    private Integer status;

    private Integer delFlag;

    private Integer createTime;

    private Integer createUserId;

    private String createUserName;

    private Integer updateTime;

    private Integer updateUserId;

    private String updateUserName;

    private String couponConfig;

    private String remark;

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

    public String getDebtPlanPrefix() {
        return debtPlanPrefix;
    }

    public void setDebtPlanPrefix(String debtPlanPrefix) {
        this.debtPlanPrefix = debtPlanPrefix == null ? null : debtPlanPrefix.trim();
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

    public BigDecimal getRoundAmount() {
        return roundAmount;
    }

    public void setRoundAmount(BigDecimal roundAmount) {
        this.roundAmount = roundAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

    public String getCouponConfig() {
        return couponConfig;
    }

    public void setCouponConfig(String couponConfig) {
        this.couponConfig = couponConfig == null ? null : couponConfig.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}