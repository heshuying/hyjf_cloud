package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DebtCreditTenderLog implements Serializable {
    private Integer id;

    /**
     * 承接用户userId
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 承接用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 出让人userId
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
     * 状态 0初始 1成功 2失败
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 原标标号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 原标的已还款期数
     *
     * @mbggenerated
     */
    private Integer repayPeriod;

    /**
     * 原标出借订单号
     *
     * @mbggenerated
     */
    private String sellOrderId;

    /**
     * 债转标号
     *
     * @mbggenerated
     */
    private String creditNid;

    /**
     * 原始标的出借订单号
     *
     * @mbggenerated
     */
    private String investOrderId;

    /**
     * 清算的计划nid
     *
     * @mbggenerated
     */
    private String liquidatesPlanNid;

    /**
     * 清算的计划订单号
     *
     * @mbggenerated
     */
    private String liquidatesPlanOrderId;

    /**
     * 承接计划nid
     *
     * @mbggenerated
     */
    private String assignPlanNid;

    /**
     * 承接计划加入订单号
     *
     * @mbggenerated
     */
    private String assignPlanOrderId;

    /**
     * 承接订单号
     *
     * @mbggenerated
     */
    private String assignOrderId;

    /**
     * 承接日期
     *
     * @mbggenerated
     */
    private String assignOrderDate;

    /**
     * 承接本金
     *
     * @mbggenerated
     */
    private BigDecimal assignCapital;

    /**
     * 承接总额
     *
     * @mbggenerated
     */
    private BigDecimal assignAccount;

    /**
     * 承接利息
     *
     * @mbggenerated
     */
    private BigDecimal assignInterest;

    /**
     * 承接的延期利息
     *
     * @mbggenerated
     */
    private BigDecimal assignRepayDelayInterest;

    /**
     * 承接的逾期利息
     *
     * @mbggenerated
     */
    private BigDecimal assignRepayLateInterest;

    /**
     * 承接垫付利息
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
     * 债权承接期数
     *
     * @mbggenerated
     */
    private Integer assignRepayPeriod;

    /**
     * 服务费
     *
     * @mbggenerated
     */
    private BigDecimal assignServiceFee;

    /**
     * 承接类型 0 自动承接 1手动承接
     *
     * @mbggenerated
     */
    private Integer assignType;

    /**
     * create ip
     *
     * @mbggenerated
     */
    private String addIp;

    /**
     * 客户端
     *
     * @mbggenerated
     */
    private Integer client;

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

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(Integer repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public String getSellOrderId() {
        return sellOrderId;
    }

    public void setSellOrderId(String sellOrderId) {
        this.sellOrderId = sellOrderId == null ? null : sellOrderId.trim();
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid == null ? null : creditNid.trim();
    }

    public String getInvestOrderId() {
        return investOrderId;
    }

    public void setInvestOrderId(String investOrderId) {
        this.investOrderId = investOrderId == null ? null : investOrderId.trim();
    }

    public String getLiquidatesPlanNid() {
        return liquidatesPlanNid;
    }

    public void setLiquidatesPlanNid(String liquidatesPlanNid) {
        this.liquidatesPlanNid = liquidatesPlanNid == null ? null : liquidatesPlanNid.trim();
    }

    public String getLiquidatesPlanOrderId() {
        return liquidatesPlanOrderId;
    }

    public void setLiquidatesPlanOrderId(String liquidatesPlanOrderId) {
        this.liquidatesPlanOrderId = liquidatesPlanOrderId == null ? null : liquidatesPlanOrderId.trim();
    }

    public String getAssignPlanNid() {
        return assignPlanNid;
    }

    public void setAssignPlanNid(String assignPlanNid) {
        this.assignPlanNid = assignPlanNid == null ? null : assignPlanNid.trim();
    }

    public String getAssignPlanOrderId() {
        return assignPlanOrderId;
    }

    public void setAssignPlanOrderId(String assignPlanOrderId) {
        this.assignPlanOrderId = assignPlanOrderId == null ? null : assignPlanOrderId.trim();
    }

    public String getAssignOrderId() {
        return assignOrderId;
    }

    public void setAssignOrderId(String assignOrderId) {
        this.assignOrderId = assignOrderId == null ? null : assignOrderId.trim();
    }

    public String getAssignOrderDate() {
        return assignOrderDate;
    }

    public void setAssignOrderDate(String assignOrderDate) {
        this.assignOrderDate = assignOrderDate == null ? null : assignOrderDate.trim();
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

    public BigDecimal getAssignRepayDelayInterest() {
        return assignRepayDelayInterest;
    }

    public void setAssignRepayDelayInterest(BigDecimal assignRepayDelayInterest) {
        this.assignRepayDelayInterest = assignRepayDelayInterest;
    }

    public BigDecimal getAssignRepayLateInterest() {
        return assignRepayLateInterest;
    }

    public void setAssignRepayLateInterest(BigDecimal assignRepayLateInterest) {
        this.assignRepayLateInterest = assignRepayLateInterest;
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

    public BigDecimal getAssignServiceFee() {
        return assignServiceFee;
    }

    public void setAssignServiceFee(BigDecimal assignServiceFee) {
        this.assignServiceFee = assignServiceFee;
    }

    public Integer getAssignType() {
        return assignType;
    }

    public void setAssignType(Integer assignType) {
        this.assignType = assignType;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
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