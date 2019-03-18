package com.hyjf.am.vo.trade;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * AEMS系统:查询逾期数据返回
 * @author xiehuili on 2019/3/12.
 */
public class AemsOverdueVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 项目编号
     */
    private String borrowNid;
    /**
     * 姓名
     */
    private String truename;
    /**
     * 身份证号码
     */
    private String idcard;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 当前还款期数
     */
    private int currPeriods;
    /**
     * 本期还款状态
     */
    private String repayStatus;
    /**
     * 当前应还时间
     */
    private String repayTime;
    /**
     * 本期计划还款金额
     */
    private BigDecimal planRepayAmount;
    /**
     * 本次还款金额
     */
    private BigDecimal repayAmount;
    /**
     * 当前逾期天数
     */
    private String overdueDays;
    /**
     * 本期还款状态确  认时间
     */
    private String confirmStatusTime;
    /**
     * 还款方式
     */
    private String borrowStyle;

    /**
     * 本期剩余应还金额
     * @return
     */
    private BigDecimal targetAmount;

    /**
     * 本笔贷款状态
     * @return
     */
    private int status;

    /**
     * 延期天数
     * @return
     */
    private String delayDays;

    /**
     * 服务费
     * @return
     */
    private String repayFee;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getCurrPeriods() {
        return currPeriods;
    }

    public void setCurrPeriods(int currPeriods) {
        this.currPeriods = currPeriods;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    public BigDecimal getPlanRepayAmount() {
        return planRepayAmount;
    }

    public void setPlanRepayAmount(BigDecimal planRepayAmount) {
        this.planRepayAmount = planRepayAmount;
    }

    public BigDecimal getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(BigDecimal repayAmount) {
        this.repayAmount = repayAmount;
    }

    public String getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(String overdueDays) {
        this.overdueDays = overdueDays;
    }

    public String getConfirmStatusTime() {
        return confirmStatusTime;
    }

    public void setConfirmStatusTime(String confirmStatusTime) {
        this.confirmStatusTime = confirmStatusTime;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(String delayDays) {
        this.delayDays = delayDays;
    }

    public String getRepayFee() {
        return repayFee;
    }

    public void setRepayFee(String repayFee) {
        this.repayFee = repayFee;
    }
}
