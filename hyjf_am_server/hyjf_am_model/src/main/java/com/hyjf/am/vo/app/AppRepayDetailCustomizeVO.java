/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.app;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jun
 * @version AppRepayDetailCustomizeVO, v0.1 2018/7/30 18:54
 */
public class AppRepayDetailCustomizeVO extends BaseVO implements Serializable {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = -4588946681367930323L;

    /**
     * 借款标号
     */
    private String borrowNid;

    /**
     * 借款标题
     */
    private String borrowName;

    /**
     * 预期年化收益率
     */
    private String borrowApr;

    /**
     * 项目期限(计算用)
     */
    private String borrowPeriod;

    /**
     * 期限单位
     */
    private String periodUnit;

    /**
     * 优惠券编号
     */
    private String couponUserCode;

    /**
     * 优惠券面额
     */
    private String couponQuota;

    /**
     * 项目期限
     */
    private String period;

    /**
     * 投资金额
     */
    private String account;

    /**
     * 投资金额(计算用)
     */
    private BigDecimal accountNum;

    /**
     * 借款方式
     */
    private String borrowStyle;

    /**
     * 还款方式
     */
    private String repayStyle;

    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 还款时间
     */
    private String repayTime;

    /**
     * 还款状态
     */
    private String recoverStatus;

    /**
     * 还款金额
     */
    private String recoverCapitalYes;

    /**
     * 债转状态
     */
    private String transStatus;

    /**
     * 债转金额
     */
    private String transAccount;

    /**
     * 到期预估收益（元）
     */
    private String interest;

    /**
     * 到期预估本息（元）
     */
    private String accountAll;
    /**
     * 优惠券类别 1：体验金，2：加息券，3：代金券
     */
    private String couponType;
    /**
     * 项目类别
     */
    private Integer projectType;
    /**
     * 融通宝加息收益率
     */
    private BigDecimal borrowExtraYield;
    /**
     * 融通宝名称
     */
    private String borrowAssetNumber;

    /**
     * 收益期限
     */
    private String couponProfitTime;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRepayStyle() {
        return repayStyle;
    }

    public void setRepayStyle(String repayStyle) {
        this.repayStyle = repayStyle;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    public String getRecoverStatus() {
        return recoverStatus;
    }

    public void setRecoverStatus(String recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public String getTransAccount() {
        return transAccount;
    }

    public void setTransAccount(String transAccount) {
        this.transAccount = transAccount;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getAccountAll() {
        return accountAll;
    }

    public void setAccountAll(String accountAll) {
        this.accountAll = accountAll;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getRecoverCapitalYes() {
        return recoverCapitalYes;
    }

    public void setRecoverCapitalYes(String recoverCapitalYes) {
        this.recoverCapitalYes = recoverCapitalYes;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(BigDecimal accountNum) {
        this.accountNum = accountNum;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public String getCouponUserCode() {
        return couponUserCode;
    }

    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }

    public String getCouponQuota() {
        return couponQuota;
    }

    public void setCouponQuota(String couponQuota) {
        this.couponQuota = couponQuota;
    }

    public String getPeriodUnit() {
        return periodUnit;
    }

    public void setPeriodUnit(String periodUnit) {
        this.periodUnit = periodUnit;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public BigDecimal getBorrowExtraYield() {
        return borrowExtraYield;
    }

    public void setBorrowExtraYield(BigDecimal borrowExtraYield) {
        this.borrowExtraYield = borrowExtraYield;
    }

    public String getCouponProfitTime() {
        return couponProfitTime;
    }

    public void setCouponProfitTime(String couponProfitTime) {
        this.couponProfitTime = couponProfitTime;
    }

    public String getBorrowAssetNumber() {
        return borrowAssetNumber;
    }

    public void setBorrowAssetNumber(String borrowAssetNumber) {
        this.borrowAssetNumber = borrowAssetNumber;
    }
}
