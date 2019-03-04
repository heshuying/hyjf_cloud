package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author hesy
 * @version BankRepayFreezeOrgCustomizeVO, v0.1 2018/10/19 11:35
 */
public class BankRepayFreezeOrgCustomizeVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String borrowNid;
    /**
     * 借款期限
     */
    private String borrowPeriod;
    /**
     * 总期数
     */
    private String totalPeriod;
    /**
     * 当前期数
     */
    private String currentPeriod;
 /**
     * 还款人用户名
     */
    private String repayUserName;
    /**
     * 借款人用户名
     */
    private String borrowUserName;
    private String instCode;
    /**
     * 机构名称
     */
    private String instName;
    private String planNid;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 标的金额
     */
    private String amount;
    /**
     * 冻结金额
     */
    private String amountFreeze;
    /**
     * 应还本息
     */
    private String repayAccount;
    /**
     * 还款服务费
     */
    private String repayFee;
    /**
     * 减息金额
     */
    private String lowerInterest;
    /**
     * 违约金
     */
    private String penaltyAmount;
    /**
     * 逾期罚息
     */
    private String defaultInterest;
    /**
     * 添加时间
     */
    private String createTimeView;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(String totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public String getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(String currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public String getRepayUserName() {
        return repayUserName;
    }

    public void setRepayUserName(String repayUserName) {
        this.repayUserName = repayUserName;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmountFreeze() {
        return amountFreeze;
    }

    public void setAmountFreeze(String amountFreeze) {
        this.amountFreeze = amountFreeze;
    }

    public String getRepayAccount() {
        return repayAccount;
    }

    public void setRepayAccount(String repayAccount) {
        this.repayAccount = repayAccount;
    }

    public String getRepayFee() {
        return repayFee;
    }

    public void setRepayFee(String repayFee) {
        this.repayFee = repayFee;
    }

    public String getLowerInterest() {
        return lowerInterest;
    }

    public void setLowerInterest(String lowerInterest) {
        this.lowerInterest = lowerInterest;
    }

    public String getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(String penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public String getDefaultInterest() {
        return defaultInterest;
    }

    public void setDefaultInterest(String defaultInterest) {
        this.defaultInterest = defaultInterest;
    }

    public String getCreateTimeView() {
        return createTimeView;
    }

    public void setCreateTimeView(String createTimeView) {
        this.createTimeView = createTimeView;
    }
}
