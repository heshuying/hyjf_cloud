package com.hyjf.wbs.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HjhPlan implements Serializable {
    /**
     * 计划id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 计划编号
     *
     * @mbggenerated
     */
    private String planNid;

    /**
     * 计划名称
     *
     * @mbggenerated
     */
    private String planName;

    /**
     * 锁定期(天)
     *
     * @mbggenerated
     */
    private Integer lockPeriod;

    /**
     * 默认0 天标，1 月标
     *
     * @mbggenerated
     */
    private Integer isMonth;

    /**
     * 预期年化利率
     *
     * @mbggenerated
     */
    private BigDecimal expectApr;

    /**
     * 最低加入金额
     *
     * @mbggenerated
     */
    private BigDecimal minInvestment;

    /**
     * 最高加入金额
     *
     * @mbggenerated
     */
    private BigDecimal maxInvestment;

    /**
     * 投资增量
     *
     * @mbggenerated
     */
    private BigDecimal investmentIncrement;

    /**
     * 计划可投金额，之前计划有总金额，现在只有剩余能投金额
     *
     * @mbggenerated
     */
    private BigDecimal availableInvestAccount;

    /**
     * 待还总额
     *
     * @mbggenerated
     */
    private BigDecimal repayWaitAll;

    /**
     * 投资状态 0 全部；1 启用；2 关闭
     *
     * @mbggenerated
     */
    private Integer planInvestStatus;

    /**
     * 显示状态字段 1显示 2 隐藏
     *
     * @mbggenerated
     */
    private Integer planDisplayStatus;

    /**
     * 添加时间
     *
     * @mbggenerated
     */
    private Integer addTime;

    /**
     * 还款方式
     *
     * @mbggenerated
     */
    private String borrowStyle;

    /**
     * 是否可用券：0 不可用 1 体验金 2 加息券 3 代金券
     *
     * @mbggenerated
     */
    private String couponConfig;

    /**
     * 累积加入总额
     *
     * @mbggenerated
     */
    private BigDecimal joinTotal;

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
     * 已还总额
     *
     * @mbggenerated
     */
    private BigDecimal repayTotal;

    /**
     * 已还利息
     *
     * @mbggenerated
     */
    private BigDecimal planRepayInterest;

    /**
     * 已还本金
     *
     * @mbggenerated
     */
    private BigDecimal planRepayCapital;

    /**
     * 最小自动投资笔数
     *
     * @mbggenerated
     */
    private Integer minInvestCounts;

    /**
     * 服务投资等级
     *
     * @mbggenerated
     */
    private String investLevel;

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

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    public Integer getLockPeriod() {
        return lockPeriod;
    }

    public void setLockPeriod(Integer lockPeriod) {
        this.lockPeriod = lockPeriod;
    }

    public Integer getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(Integer isMonth) {
        this.isMonth = isMonth;
    }

    public BigDecimal getExpectApr() {
        return expectApr;
    }

    public void setExpectApr(BigDecimal expectApr) {
        this.expectApr = expectApr;
    }

    public BigDecimal getMinInvestment() {
        return minInvestment;
    }

    public void setMinInvestment(BigDecimal minInvestment) {
        this.minInvestment = minInvestment;
    }

    public BigDecimal getMaxInvestment() {
        return maxInvestment;
    }

    public void setMaxInvestment(BigDecimal maxInvestment) {
        this.maxInvestment = maxInvestment;
    }

    public BigDecimal getInvestmentIncrement() {
        return investmentIncrement;
    }

    public void setInvestmentIncrement(BigDecimal investmentIncrement) {
        this.investmentIncrement = investmentIncrement;
    }

    public BigDecimal getAvailableInvestAccount() {
        return availableInvestAccount;
    }

    public void setAvailableInvestAccount(BigDecimal availableInvestAccount) {
        this.availableInvestAccount = availableInvestAccount;
    }

    public BigDecimal getRepayWaitAll() {
        return repayWaitAll;
    }

    public void setRepayWaitAll(BigDecimal repayWaitAll) {
        this.repayWaitAll = repayWaitAll;
    }

    public Integer getPlanInvestStatus() {
        return planInvestStatus;
    }

    public void setPlanInvestStatus(Integer planInvestStatus) {
        this.planInvestStatus = planInvestStatus;
    }

    public Integer getPlanDisplayStatus() {
        return planDisplayStatus;
    }

    public void setPlanDisplayStatus(Integer planDisplayStatus) {
        this.planDisplayStatus = planDisplayStatus;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle == null ? null : borrowStyle.trim();
    }

    public String getCouponConfig() {
        return couponConfig;
    }

    public void setCouponConfig(String couponConfig) {
        this.couponConfig = couponConfig == null ? null : couponConfig.trim();
    }

    public BigDecimal getJoinTotal() {
        return joinTotal;
    }

    public void setJoinTotal(BigDecimal joinTotal) {
        this.joinTotal = joinTotal;
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

    public BigDecimal getRepayTotal() {
        return repayTotal;
    }

    public void setRepayTotal(BigDecimal repayTotal) {
        this.repayTotal = repayTotal;
    }

    public BigDecimal getPlanRepayInterest() {
        return planRepayInterest;
    }

    public void setPlanRepayInterest(BigDecimal planRepayInterest) {
        this.planRepayInterest = planRepayInterest;
    }

    public BigDecimal getPlanRepayCapital() {
        return planRepayCapital;
    }

    public void setPlanRepayCapital(BigDecimal planRepayCapital) {
        this.planRepayCapital = planRepayCapital;
    }

    public Integer getMinInvestCounts() {
        return minInvestCounts;
    }

    public void setMinInvestCounts(Integer minInvestCounts) {
        this.minInvestCounts = minInvestCounts;
    }

    public String getInvestLevel() {
        return investLevel;
    }

    public void setInvestLevel(String investLevel) {
        this.investLevel = investLevel == null ? null : investLevel.trim();
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