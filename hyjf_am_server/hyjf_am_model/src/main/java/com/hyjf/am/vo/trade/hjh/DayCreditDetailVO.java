package com.hyjf.am.vo.trade.hjh;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

public class DayCreditDetailVO extends BaseVO implements Serializable {

    /**
     * 序号
     */
    private String id;

    /**
     * 出让人计划编号
     */
    private String planNid;

    /**
     * 清算后计划编号
     */
    private String planNidNew;

    /**
     * 出让人计划订单号
     */
    private String planOrderId;

    /**
     * 出让人用户名
     */
    private String userName;

    /**
     * 债转编号
     */
    private String creditNid;

    /**
     * 项目编号-原项目编号
     */
    private String borrowNid;

    /**
     * 项目收益率-原项目收益率
     */
    private String borrowApr;

    /**
     * 还款方式
     */
    private String repayStyleName;

    /**
     * 债权本金
     */
    private BigDecimal creditCapital;

    /**
     * 债权价值
     */
    private BigDecimal liquidationFairValue;

    /**
     * 预计实际收益率
     */
    private String actualApr;

    /**
     * 已转让本金
     */
    private BigDecimal assignCapital;

    /**
     * 已转让垫付利息-垫付利息
     */
    private BigDecimal assignAdvanceInterest;


    /**
     * 承接总额
     */
    private String assignAccount;

    /**
     * 待承接总金额-在出借金(ADD)
     */

    private String creditAccountWait;

    /**
     * 出让人实际到账金额
     */
    private String accountReceive;

    /**
     * 实际清算时间
     */
    private String liquidatesTime;

    /**
     * 转让状态
     */
    private String creditStatusName;

    /**
     * 还款状态
     */
    private String repayStatusName;
    /**
     * 项目总期数
     */
    private String borrowPeriod;

    /**
     * 承接原项目所在期数
     */
    private String assignPeriod;

    /**
     * 清算时所在期数
     */
    private String liquidatesPeriod;

    /**
     * 还款时间-当期应还款时间
     */
    private String repayNextTime;

    /**
     * 债转结束时间（全部承接或者提前还款导致）
     */
    private String endTime;

    private String liquidatesPeriodView;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getPlanNidNew() {
        return planNidNew;
    }

    public void setPlanNidNew(String planNidNew) {
        this.planNidNew = planNidNew;
    }

    public String getPlanOrderId() {
        return planOrderId;
    }

    public void setPlanOrderId(String planOrderId) {
        this.planOrderId = planOrderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getRepayStyleName() {
        return repayStyleName;
    }

    public void setRepayStyleName(String repayStyleName) {
        this.repayStyleName = repayStyleName;
    }

    public BigDecimal getCreditCapital() {
        return creditCapital;
    }

    public void setCreditCapital(BigDecimal creditCapital) {
        this.creditCapital = creditCapital;
    }

    public BigDecimal getLiquidationFairValue() {
        return liquidationFairValue;
    }

    public void setLiquidationFairValue(BigDecimal liquidationFairValue) {
        this.liquidationFairValue = liquidationFairValue;
    }

    public String getActualApr() {
        return actualApr;
    }

    public void setActualApr(String actualApr) {
        this.actualApr = actualApr;
    }

    public BigDecimal getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(BigDecimal assignCapital) {
        this.assignCapital = assignCapital;
    }

    public BigDecimal getAssignAdvanceInterest() {
        return assignAdvanceInterest;
    }

    public void setAssignAdvanceInterest(BigDecimal assignAdvanceInterest) {
        this.assignAdvanceInterest = assignAdvanceInterest;
    }

    public String getAssignAccount() {
        return assignAccount;
    }

    public void setAssignAccount(String assignAccount) {
        this.assignAccount = assignAccount;
    }

    public String getCreditAccountWait() {
        return creditAccountWait;
    }

    public void setCreditAccountWait(String creditAccountWait) {
        this.creditAccountWait = creditAccountWait;
    }

    public String getAccountReceive() {
        return accountReceive;
    }

    public void setAccountReceive(String accountReceive) {
        this.accountReceive = accountReceive;
    }

    public String getLiquidatesTime() {
        return liquidatesTime;
    }

    public void setLiquidatesTime(String liquidatesTime) {
        this.liquidatesTime = liquidatesTime;
    }

    public String getCreditStatusName() {
        return creditStatusName;
    }

    public void setCreditStatusName(String creditStatusName) {
        this.creditStatusName = creditStatusName;
    }

    public String getRepayStatusName() {
        return repayStatusName;
    }

    public void setRepayStatusName(String repayStatusName) {
        this.repayStatusName = repayStatusName;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getAssignPeriod() {
        return assignPeriod;
    }

    public void setAssignPeriod(String assignPeriod) {
        this.assignPeriod = assignPeriod;
    }

    public String getLiquidatesPeriod() {
        return liquidatesPeriod;
    }

    public void setLiquidatesPeriod(String liquidatesPeriod) {
        this.liquidatesPeriod = liquidatesPeriod;
    }

    public String getRepayNextTime() {
        return repayNextTime;
    }

    public void setRepayNextTime(String repayNextTime) {
        this.repayNextTime = repayNextTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLiquidatesPeriodView() {
        return this.getLiquidatesPeriod() + "/" + this.getBorrowPeriod();
    }

    public void setLiquidatesPeriodView(String liquidatesPeriodView) {
        this.liquidatesPeriodView = liquidatesPeriodView;
    }
}
