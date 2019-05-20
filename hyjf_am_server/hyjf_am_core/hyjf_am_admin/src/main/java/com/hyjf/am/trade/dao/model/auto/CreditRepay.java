package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CreditRepay implements Serializable {
    private Integer id;

    /**
     * 承接人用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 承接人用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 出让人id
     *
     * @mbggenerated
     */
    private Integer creditUserId;

    /**
     * 出让人用户名
     *
     * @mbggenerated
     */
    private String creditUserName;

    /**
     * 状态
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 原标标号
     *
     * @mbggenerated
     */
    private String bidNid;

    /**
     * 债转标号
     *
     * @mbggenerated
     */
    private String creditNid;

    /**
     * 债转投标单号
     *
     * @mbggenerated
     */
    private String creditTenderNid;

    /**
     * 认购单号
     *
     * @mbggenerated
     */
    private String assignNid;

    /**
     * 投资人投标成功的授权号
     *
     * @mbggenerated
     */
    private String authCode;

    /**
     * 应还本金
     *
     * @mbggenerated
     */
    private BigDecimal assignCapital;

    /**
     * 应还总额
     *
     * @mbggenerated
     */
    private BigDecimal assignAccount;

    /**
     * 应还利息
     *
     * @mbggenerated
     */
    private BigDecimal assignInterest;

    /**
     * 垫付利息
     *
     * @mbggenerated
     */
    private BigDecimal assignInterestAdvance;

    /**
     * 购买价格
     *
     * @mbggenerated
     */
    private BigDecimal assignPrice;

    /**
     * 支付金额
     *
     * @mbggenerated
     */
    private BigDecimal assignPay;

    /**
     * 已还总额
     *
     * @mbggenerated
     */
    private BigDecimal assignRepayAccount;

    /**
     * 已还本金
     *
     * @mbggenerated
     */
    private BigDecimal assignRepayCapital;

    /**
     * 已还利息
     *
     * @mbggenerated
     */
    private BigDecimal assignRepayInterest;

    /**
     * 最后还款日
     *
     * @mbggenerated
     */
    private Integer assignRepayEndTime;

    /**
     * 上次还款时间
     *
     * @mbggenerated
     */
    private Integer assignRepayLastTime;

    /**
     * 下次还款时间
     *
     * @mbggenerated
     */
    private Integer assignRepayNextTime;

    /**
     * 最终实际还款时间
     *
     * @mbggenerated
     */
    private Integer assignRepayYesTime;

    /**
     * 还款期数
     *
     * @mbggenerated
     */
    private Integer assignRepayPeriod;

    /**
     * 认购日期
     *
     * @mbggenerated
     */
    private Integer assignCreateDate;

    /**
     * 客户端
     *
     * @mbggenerated
     */
    private Integer client;

    /**
     * 原标还款期数
     *
     * @mbggenerated
     */
    private Integer recoverPeriod;

    /**
     * 管理费
     *
     * @mbggenerated
     */
    private BigDecimal manageFee;

    /**
     * 债转还款订单号
     *
     * @mbggenerated
     */
    private String creditRepayOrderId;

    /**
     * 债转还款订单日期
     *
     * @mbggenerated
     */
    private String creditRepayOrderDate;

    /**
     * 债转提前还款状态 0正常还款 1提前还款 2延期还款 3逾期还款
     *
     * @mbggenerated
     */
    private Integer advanceStatus;

    /**
     * 提前天数
     *
     * @mbggenerated
     */
    private Integer chargeDays;

    /**
     * 提前还款利息（负数）(已加罚息)
     *
     * @mbggenerated
     */
    private BigDecimal chargeInterest;

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
     * 债权是否结束状态(0:否,1:是)
     *
     * @mbggenerated
     */
    private Integer debtStatus;

    /**
     * create ip
     *
     * @mbggenerated
     */
    private String addIp;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 提前还款罚息
     *
     * @mbggenerated
     */
    private BigDecimal chargePenaltyInterest;

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

    public Integer getCreditUserId() {
        return creditUserId;
    }

    public void setCreditUserId(Integer creditUserId) {
        this.creditUserId = creditUserId;
    }

    public String getCreditUserName() {
        return creditUserName;
    }

    public void setCreditUserName(String creditUserName) {
        this.creditUserName = creditUserName == null ? null : creditUserName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBidNid() {
        return bidNid;
    }

    public void setBidNid(String bidNid) {
        this.bidNid = bidNid == null ? null : bidNid.trim();
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid == null ? null : creditNid.trim();
    }

    public String getCreditTenderNid() {
        return creditTenderNid;
    }

    public void setCreditTenderNid(String creditTenderNid) {
        this.creditTenderNid = creditTenderNid == null ? null : creditTenderNid.trim();
    }

    public String getAssignNid() {
        return assignNid;
    }

    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid == null ? null : assignNid.trim();
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode == null ? null : authCode.trim();
    }

    public BigDecimal getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(BigDecimal assignCapital) {
        this.assignCapital = assignCapital;
    }

    public BigDecimal getAssignAccount() {
        return assignAccount;
    }

    public void setAssignAccount(BigDecimal assignAccount) {
        this.assignAccount = assignAccount;
    }

    public BigDecimal getAssignInterest() {
        return assignInterest;
    }

    public void setAssignInterest(BigDecimal assignInterest) {
        this.assignInterest = assignInterest;
    }

    public BigDecimal getAssignInterestAdvance() {
        return assignInterestAdvance;
    }

    public void setAssignInterestAdvance(BigDecimal assignInterestAdvance) {
        this.assignInterestAdvance = assignInterestAdvance;
    }

    public BigDecimal getAssignPrice() {
        return assignPrice;
    }

    public void setAssignPrice(BigDecimal assignPrice) {
        this.assignPrice = assignPrice;
    }

    public BigDecimal getAssignPay() {
        return assignPay;
    }

    public void setAssignPay(BigDecimal assignPay) {
        this.assignPay = assignPay;
    }

    public BigDecimal getAssignRepayAccount() {
        return assignRepayAccount;
    }

    public void setAssignRepayAccount(BigDecimal assignRepayAccount) {
        this.assignRepayAccount = assignRepayAccount;
    }

    public BigDecimal getAssignRepayCapital() {
        return assignRepayCapital;
    }

    public void setAssignRepayCapital(BigDecimal assignRepayCapital) {
        this.assignRepayCapital = assignRepayCapital;
    }

    public BigDecimal getAssignRepayInterest() {
        return assignRepayInterest;
    }

    public void setAssignRepayInterest(BigDecimal assignRepayInterest) {
        this.assignRepayInterest = assignRepayInterest;
    }

    public Integer getAssignRepayEndTime() {
        return assignRepayEndTime;
    }

    public void setAssignRepayEndTime(Integer assignRepayEndTime) {
        this.assignRepayEndTime = assignRepayEndTime;
    }

    public Integer getAssignRepayLastTime() {
        return assignRepayLastTime;
    }

    public void setAssignRepayLastTime(Integer assignRepayLastTime) {
        this.assignRepayLastTime = assignRepayLastTime;
    }

    public Integer getAssignRepayNextTime() {
        return assignRepayNextTime;
    }

    public void setAssignRepayNextTime(Integer assignRepayNextTime) {
        this.assignRepayNextTime = assignRepayNextTime;
    }

    public Integer getAssignRepayYesTime() {
        return assignRepayYesTime;
    }

    public void setAssignRepayYesTime(Integer assignRepayYesTime) {
        this.assignRepayYesTime = assignRepayYesTime;
    }

    public Integer getAssignRepayPeriod() {
        return assignRepayPeriod;
    }

    public void setAssignRepayPeriod(Integer assignRepayPeriod) {
        this.assignRepayPeriod = assignRepayPeriod;
    }

    public Integer getAssignCreateDate() {
        return assignCreateDate;
    }

    public void setAssignCreateDate(Integer assignCreateDate) {
        this.assignCreateDate = assignCreateDate;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(Integer recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public BigDecimal getManageFee() {
        return manageFee;
    }

    public void setManageFee(BigDecimal manageFee) {
        this.manageFee = manageFee;
    }

    public String getCreditRepayOrderId() {
        return creditRepayOrderId;
    }

    public void setCreditRepayOrderId(String creditRepayOrderId) {
        this.creditRepayOrderId = creditRepayOrderId == null ? null : creditRepayOrderId.trim();
    }

    public String getCreditRepayOrderDate() {
        return creditRepayOrderDate;
    }

    public void setCreditRepayOrderDate(String creditRepayOrderDate) {
        this.creditRepayOrderDate = creditRepayOrderDate == null ? null : creditRepayOrderDate.trim();
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

    public Integer getDebtStatus() {
        return debtStatus;
    }

    public void setDebtStatus(Integer debtStatus) {
        this.debtStatus = debtStatus;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
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