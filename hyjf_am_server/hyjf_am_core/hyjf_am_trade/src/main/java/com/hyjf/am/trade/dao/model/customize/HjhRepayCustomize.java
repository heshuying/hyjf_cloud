package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ht_hjh_repay 自定义实体
 * @Author : huanghui
 */
public class HjhRepayCustomize implements Serializable {

    private Integer id;

    private String accedeOrderId;

    private String planNid;

    private Integer lockPeriod;

    private Integer userId;

    private String userName;

    private Integer userAttribute;

    private BigDecimal accedeAccount;

    private BigDecimal repayInterest;

    private BigDecimal repayCapital;

    private Integer repayStatus;

    private BigDecimal repayAlready;

    private BigDecimal repayWait;

    private BigDecimal repayShould;

    private BigDecimal repayActual;

    private Integer orderStatus;

    private String repayActualTime;

    private String repayShouldTime;

    private BigDecimal planRepayCapital;

    private BigDecimal planRepayInterest;

    private BigDecimal repayTotal;

    private BigDecimal planWaitCaptical;

    private BigDecimal planWaitInterest;

    private BigDecimal waitTotal;

    private BigDecimal serviceFee;

    private BigDecimal actualRevenue;

    private BigDecimal actualPayTotal;

    private Integer delFlag;

    private Integer createUser;

    private Integer updateUser;

    private String createTime;

    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public Integer getLockPeriod() {
        return lockPeriod;
    }

    public void setLockPeriod(Integer lockPeriod) {
        this.lockPeriod = lockPeriod;
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

    public Integer getUserAttribute() {
        return userAttribute;
    }

    public void setUserAttribute(Integer userAttribute) {
        this.userAttribute = userAttribute;
    }

    public BigDecimal getAccedeAccount() {
        return accedeAccount;
    }

    public void setAccedeAccount(BigDecimal accedeAccount) {
        this.accedeAccount = accedeAccount;
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

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public BigDecimal getRepayAlready() {
        return repayAlready;
    }

    public void setRepayAlready(BigDecimal repayAlready) {
        this.repayAlready = repayAlready;
    }

    public BigDecimal getRepayWait() {
        return repayWait;
    }

    public void setRepayWait(BigDecimal repayWait) {
        this.repayWait = repayWait;
    }

    public BigDecimal getRepayShould() {
        return repayShould;
    }

    public void setRepayShould(BigDecimal repayShould) {
        this.repayShould = repayShould;
    }

    public BigDecimal getRepayActual() {
        return repayActual;
    }

    public void setRepayActual(BigDecimal repayActual) {
        this.repayActual = repayActual;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRepayActualTime() {
        return repayActualTime;
    }

    public void setRepayActualTime(String repayActualTime) {
        this.repayActualTime = repayActualTime;
    }

    public String getRepayShouldTime() {
        return repayShouldTime;
    }

    public void setRepayShouldTime(String repayShouldTime) {
        this.repayShouldTime = repayShouldTime;
    }

    public BigDecimal getPlanRepayCapital() {
        return planRepayCapital;
    }

    public void setPlanRepayCapital(BigDecimal planRepayCapital) {
        this.planRepayCapital = planRepayCapital;
    }

    public BigDecimal getPlanRepayInterest() {
        return planRepayInterest;
    }

    public void setPlanRepayInterest(BigDecimal planRepayInterest) {
        this.planRepayInterest = planRepayInterest;
    }

    public BigDecimal getRepayTotal() {
        return repayTotal;
    }

    public void setRepayTotal(BigDecimal repayTotal) {
        this.repayTotal = repayTotal;
    }

    public BigDecimal getPlanWaitCaptical() {
        return planWaitCaptical;
    }

    public void setPlanWaitCaptical(BigDecimal planWaitCaptical) {
        this.planWaitCaptical = planWaitCaptical;
    }

    public BigDecimal getPlanWaitInterest() {
        return planWaitInterest;
    }

    public void setPlanWaitInterest(BigDecimal planWaitInterest) {
        this.planWaitInterest = planWaitInterest;
    }

    public BigDecimal getWaitTotal() {
        return waitTotal;
    }

    public void setWaitTotal(BigDecimal waitTotal) {
        this.waitTotal = waitTotal;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getActualRevenue() {
        return actualRevenue;
    }

    public void setActualRevenue(BigDecimal actualRevenue) {
        this.actualRevenue = actualRevenue;
    }

    public BigDecimal getActualPayTotal() {
        return actualPayTotal;
    }

    public void setActualPayTotal(BigDecimal actualPayTotal) {
        this.actualPayTotal = actualPayTotal;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
