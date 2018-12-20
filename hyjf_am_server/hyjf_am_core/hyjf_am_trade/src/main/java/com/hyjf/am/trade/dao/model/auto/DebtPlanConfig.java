package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DebtPlanConfig implements Serializable {
    private Integer id;

    /**
     * 计划类型 与hyjf_debt_plan表debt_plan_type外键
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
     * 计划前缀
     *
     * @mbggenerated
     */
    private String debtPlanPrefix;

    /**
     * 锁定期 月
     *
     * @mbggenerated
     */
    private Integer debtLockPeriod;

    /**
     * 最低出借金额
     *
     * @mbggenerated
     */
    private BigDecimal debtMinInvestment;

    /**
     * 出借增量
     *
     * @mbggenerated
     */
    private BigDecimal debtInvestmentIncrement;

    /**
     * 最高出借金额
     *
     * @mbggenerated
     */
    private BigDecimal debtMaxInvestment;

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
     * 最小出借笔数
     *
     * @mbggenerated
     */
    private Integer minInvestNumber;

    /**
     * 最大出借笔数
     *
     * @mbggenerated
     */
    private Integer maxInvestNumber;

    /**
     * 出借循环次数
     *
     * @mbggenerated
     */
    private Integer cycleTimes;

    /**
     * 无视规则出借次数
     *
     * @mbggenerated
     */
    private Integer unableCycleTimes;

    /**
     * 汇添金专属资产最后一笔界定金额
     *
     * @mbggenerated
     */
    private BigDecimal investAccountLimit;

    /**
     * 最低剩余出借金额
     *
     * @mbggenerated
     */
    private BigDecimal minSurplusInvestAccount;

    /**
     * 取整金额
     *
     * @mbggenerated
     */
    private BigDecimal roundAmount;

    /**
     * 是否可用券：0 不可用 1 体验金 2 加息券 3 代金券
     *
     * @mbggenerated
     */
    private String couponConfig;

    /**
     * 状态 0关闭  1启用
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 删除状态 1:删除  0:未删除
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建人
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
     * 修改人
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

    /**
     * 常见问题
     *
     * @mbggenerated
     */
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

    public String getCouponConfig() {
        return couponConfig;
    }

    public void setCouponConfig(String couponConfig) {
        this.couponConfig = couponConfig == null ? null : couponConfig.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}