package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HjhRepay implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 汇计划加入订单号
     *
     * @mbggenerated
     */
    private String accedeOrderId;

    /**
     * 计划nid
     *
     * @mbggenerated
     */
    private String planNid;

    /**
     * 锁定期(天)
     *
     * @mbggenerated
     */
    private Integer lockPeriod;

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
     * 用户属性
     *
     * @mbggenerated
     */
    private Integer userAttribute;

    /**
     * 加入金额
     *
     * @mbggenerated
     */
    private BigDecimal accedeAccount;

    /**
     * 应还利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterest;

    /**
     * 应还本金
     *
     * @mbggenerated
     */
    private BigDecimal repayCapital;

    /**
     * 回款状态 0 未回款，1 部分回款 2 已回款
     *
     * @mbggenerated
     */
    private Integer repayStatus;

    /**
     * 已回款
     *
     * @mbggenerated
     */
    private BigDecimal repayAlready;

    /**
     * 待回款
     *
     * @mbggenerated
     */
    private BigDecimal repayWait;

    /**
     * 应还金额
     *
     * @mbggenerated
     */
    private BigDecimal repayShould;

    /**
     * 实还金额
     *
     * @mbggenerated
     */
    private BigDecimal repayActual;

    /**
     * 订单状态 0自动投标中 1锁定中 2退出中 3已退出
     *
     * @mbggenerated
     */
    private Integer orderStatus;

    /**
     * 计划实际还款时间
     *
     * @mbggenerated
     */
    private Integer repayActualTime;

    /**
     * 计划应还时间
     *
     * @mbggenerated
     */
    private Integer repayShouldTime;

    /**
     * 已还本金
     *
     * @mbggenerated
     */
    private BigDecimal planRepayCapital;

    /**
     * 已还利息
     *
     * @mbggenerated
     */
    private BigDecimal planRepayInterest;

    /**
     * 已还总额
     *
     * @mbggenerated
     */
    private BigDecimal repayTotal;

    /**
     * 待还本金
     *
     * @mbggenerated
     */
    private BigDecimal planWaitCaptical;

    /**
     * 待还利息
     *
     * @mbggenerated
     */
    private BigDecimal planWaitInterest;

    /**
     * 待还总额
     *
     * @mbggenerated
     */
    private BigDecimal waitTotal;

    /**
     * 服务费
     *
     * @mbggenerated
     */
    private BigDecimal serviceFee;

    /**
     * 实际收益(元)
     *
     * @mbggenerated
     */
    private BigDecimal actualRevenue;

    /**
     * 实际回款总额(元)
     *
     * @mbggenerated
     */
    private BigDecimal actualPayTotal;

    /**
     * 删除标识
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建人id
     *
     * @mbggenerated
     */
    private Integer createUser;

    /**
     * 更新人id
     *
     * @mbggenerated
     */
    private Integer updateUser;

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

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId == null ? null : accedeOrderId.trim();
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
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
        this.userName = userName == null ? null : userName.trim();
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

    public Integer getRepayActualTime() {
        return repayActualTime;
    }

    public void setRepayActualTime(Integer repayActualTime) {
        this.repayActualTime = repayActualTime;
    }

    public Integer getRepayShouldTime() {
        return repayShouldTime;
    }

    public void setRepayShouldTime(Integer repayShouldTime) {
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