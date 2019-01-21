package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DebtPlan implements Serializable {
    /**
     * 计划id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 计划类型，关联计划类型表hyjf_debt_plan_config
     *
     * @mbggenerated
     */
    private Integer debtPlanType;

    /**
     * 计划类型名称
     *
     * @mbggenerated
     */
    private String debtPlanTypeName;

    /**
     * 计划编号
     *
     * @mbggenerated
     */
    private String debtPlanNid;

    /**
     * 预发布计划编号
     *
     * @mbggenerated
     */
    private Integer debtPrePlanNid;

    /**
     * 计划名称
     *
     * @mbggenerated
     */
    private String debtPlanName;

    /**
     * 计划金额
     *
     * @mbggenerated
     */
    private BigDecimal debtPlanMoney;

    /**
     * 计划加入金额
     *
     * @mbggenerated
     */
    private BigDecimal debtPlanMoneyYes;

    /**
     * 计划余额
     *
     * @mbggenerated
     */
    private BigDecimal debtPlanMoneyWait;

    /**
     * 计划完成率
     *
     * @mbggenerated
     */
    private BigDecimal debtPlanAccountScale;

    /**
     * 计划状态 0 发起中；1 待审核；2审核不通过；3待开放；4募集中；5锁定中；6清算中；7清算完成，8未还款，9还款中，10还款完成
     *
     * @mbggenerated
     */
    private Integer debtPlanStatus;

    /**
     * 取整金额
     *
     * @mbggenerated
     */
    private BigDecimal roundAmount;

    /**
     * 计划加入次数
     *
     * @mbggenerated
     */
    private Integer accedeTimes;

    /**
     * 预期年化利率
     *
     * @mbggenerated
     */
    private BigDecimal expectApr;

    /**
     * 实际年化利率
     *
     * @mbggenerated
     */
    private BigDecimal actualApr;

    /**
     * 投资范围
     *
     * @mbggenerated
     */
    private String investmentScope;

    /**
     * 退出方式:0:到期退出
     *
     * @mbggenerated
     */
    private Integer debtQuitStyle;

    /**
     * 退出天数
     *
     * @mbggenerated
     */
    private Integer debtQuitPeriod;

    /**
     * 是否立即审核:yes:立即提交审核,no:暂不提交审核
     *
     * @mbggenerated
     */
    private String isAudits;

    /**
     * 申购开始时间
     *
     * @mbggenerated
     */
    private Integer buyBeginTime;

    /**
     * 申购结束时间
     *
     * @mbggenerated
     */
    private Integer buyEndTime;

    /**
     * 申购期限（天）
     *
     * @mbggenerated
     */
    private Integer buyPeriodDay;

    /**
     * 申购期限（小时）
     *
     * @mbggenerated
     */
    private Integer buyPeriodHour;

    /**
     * 申购总期限（小时）
     *
     * @mbggenerated
     */
    private Integer buyPeriod;

    /**
     * 锁定期(月)
     *
     * @mbggenerated
     */
    private Integer debtLockPeriod;

    /**
     * 最低投资金额
     *
     * @mbggenerated
     */
    private BigDecimal debtMinInvestment;

    /**
     * 投资增量
     *
     * @mbggenerated
     */
    private BigDecimal debtInvestmentIncrement;

    /**
     * 最高投资金额
     *
     * @mbggenerated
     */
    private BigDecimal debtMaxInvestment;

    /**
     * 计划可用余额
     *
     * @mbggenerated
     */
    private BigDecimal debtPlanBalance;

    /**
     * 计划投资汇资产冻结金额
     *
     * @mbggenerated
     */
    private BigDecimal debtPlanFrost;

    /**
     * 满标/到期时间
     *
     * @mbggenerated
     */
    private Integer fullExpireTime;

    /**
     * 应还款总额
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountAll;

    /**
     * 应还利息
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountInterest;

    /**
     * 应还本金
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountCapital;

    /**
     * 实还总额
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountYes;

    /**
     * 实还利息
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountInterestYes;

    /**
     * 实还本金
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountCapitalYes;

    /**
     * 未还款总额
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountWait;

    /**
     * 未还款利息
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountInterestWait;

    /**
     * 未还款本金
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountCapitalWait;

    /**
     * 服务费
     *
     * @mbggenerated
     */
    private BigDecimal serviceFee;

    /**
     * 最小投资笔数
     *
     * @mbggenerated
     */
    private Integer minInvestNumber;

    /**
     * 最大投资笔数
     *
     * @mbggenerated
     */
    private Integer maxInvestNumber;

    /**
     * 投资循环次数
     *
     * @mbggenerated
     */
    private Integer cycleTimes;

    /**
     * 无视规则投资次数
     *
     * @mbggenerated
     */
    private Integer unableCycleTimes;

    /**
     * 投资金额界定
     *
     * @mbggenerated
     */
    private BigDecimal investAccountLimit;

    /**
     * 最低剩余投资金额
     *
     * @mbggenerated
     */
    private BigDecimal minSurplusInvestAccount;

    /**
     * 应清算时间
     *
     * @mbggenerated
     */
    private Integer liquidateShouldTime;

    /**
     * 实际清算时间
     *
     * @mbggenerated
     */
    private Integer liquidateFactTime;

    /**
     * 还款时间
     *
     * @mbggenerated
     */
    private Integer repayTime;

    /**
     * 审核人
     *
     * @mbggenerated
     */
    private Integer auditUserId;

    /**
     * 审核时间
     *
     * @mbggenerated
     */
    private Integer auditTime;

    /**
     * 审核备注
     *
     * @mbggenerated
     */
    private String auditRemark;

    /**
     * 计划进入锁定期时间
     *
     * @mbggenerated
     */
    private Integer planLockTime;

    /**
     * 提成状态:0:计算提成,1:提成明细
     *
     * @mbggenerated
     */
    private Integer commissionStatus;

    /**
     * 提成总额
     *
     * @mbggenerated
     */
    private BigDecimal commissionTotal;

    /**
     * 清算进度百分比
     *
     * @mbggenerated
     */
    private BigDecimal liquidateApr;

    /**
     * 是否可用券：0 不可用 1 体验金 2 加息券 3 代金券
     *
     * @mbggenerated
     */
    private String couponConfig;

    /**
     * 清算已经到账金额
     *
     * @mbggenerated
     */
    private BigDecimal liquidateArrivalAmount;

    /**
     * 最迟还款日
     *
     * @mbggenerated
     */
    private Integer repayTimeLast;

    /**
     * web是否表示(汇添金测试用:0:表示,1:不表示)
     *
     * @mbggenerated
     */
    private Integer showStatus;

    /**
     * 删除标识 0：未删除，1：已删除
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 发起人id
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建人用户名
     *
     * @mbggenerated
     */
    private String createUserName;

    /**
     * 更新人id
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 修改人用户名
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