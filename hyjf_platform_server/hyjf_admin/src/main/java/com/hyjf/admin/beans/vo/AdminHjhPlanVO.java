/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author libin
 * @version AdminHjhPlanVO.java, v0.1 2018年7月21日 上午9:04:28
 */
public class AdminHjhPlanVO extends BaseVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "主键")
    private Integer id;

	@ApiModelProperty(value = "计划编号")
    private String planNid;

	@ApiModelProperty(value = "计划名称")
    private String planName;

	@ApiModelProperty(value = "锁定期")
    private Integer lockPeriod;

	@ApiModelProperty(value = "天/月")
    private Integer isMonth;

	@ApiModelProperty(value = "天/月")
    private BigDecimal expectApr;

	@ApiModelProperty(value = "最小投资额度")
    private BigDecimal minInvestment;

	@ApiModelProperty(value = "最大投资额度")
    private BigDecimal maxInvestment;

	@ApiModelProperty(value = "投资增量")
    private BigDecimal investmentIncrement;
	
	@ApiModelProperty(value = "剩余可投金额(开放额度)")
    private BigDecimal availableInvestAccount;

	@ApiModelProperty(value = "待还总额")
    private BigDecimal repayWaitAll;

	@ApiModelProperty(value = "计划投资状态")
    private Integer planInvestStatus;

	@ApiModelProperty(value = "计划显示状态")
    private Integer planDisplayStatus;

	@ApiModelProperty(value = "计划添加时间")
    private Integer addTime;

	@ApiModelProperty(value = "还款方式")
    private String borrowStyle;

	@ApiModelProperty(value = "是否可用券")
    private String couponConfig;

	@ApiModelProperty(value = "累积加入总额")
    private BigDecimal joinTotal;

	@ApiModelProperty(value = "待还本金")
    private BigDecimal planWaitCaptical;

	@ApiModelProperty(value = "待还利息")
    private BigDecimal planWaitInterest;

	@ApiModelProperty(value = "已还总额")
    private BigDecimal repayTotal;

	@ApiModelProperty(value = "已还利息")
    private BigDecimal planRepayInterest;

	@ApiModelProperty(value = "已还本金")
    private BigDecimal planRepayCapital;

	@ApiModelProperty(value = "最小自动投资笔数")
    private Integer minInvestCounts;

    private Integer delFlag;

    private Integer createUser;

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;

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
