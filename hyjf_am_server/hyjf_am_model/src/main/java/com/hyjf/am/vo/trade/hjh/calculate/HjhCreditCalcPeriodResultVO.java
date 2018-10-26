package com.hyjf.am.vo.trade.hjh.calculate;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 自动承接用分期计算结果bean
 * @author liubin
 * @version HjhCreditCalcPeriodResultVO.java, v0.1 2018年10月25日 下午3:12:49
 */
public class HjhCreditCalcPeriodResultVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    // 分期本金
    private BigDecimal assignPeriodCapital;
    // 分期利息
    private BigDecimal assignPeriodInterest;
    // 分期垫付利息
    private BigDecimal assignPeriodAdvanceMentInterest;
    // 分期承接人应收取延期利息
    private BigDecimal assignPeriodRepayDelayInterest;
    // 分期承接人应收取延期利息
    private BigDecimal assignPeriodRepayLateInterest;

    public BigDecimal getAssignPeriodCapital() {
        return assignPeriodCapital;
    }

    public void setAssignPeriodCapital(BigDecimal assignPeriodCapital) {
        this.assignPeriodCapital = assignPeriodCapital;
    }

    public BigDecimal getAssignPeriodInterest() {
        return assignPeriodInterest;
    }

    public void setAssignPeriodInterest(BigDecimal assignPeriodInterest) {
        this.assignPeriodInterest = assignPeriodInterest;
    }

    public BigDecimal getAssignPeriodAdvanceMentInterest() {
        return assignPeriodAdvanceMentInterest;
    }

    public void setAssignPeriodAdvanceMentInterest(BigDecimal assignPeriodAdvanceMentInterest) {
        this.assignPeriodAdvanceMentInterest = assignPeriodAdvanceMentInterest;
    }

    public BigDecimal getAssignPeriodRepayDelayInterest() {
        return assignPeriodRepayDelayInterest;
    }

    public void setAssignPeriodRepayDelayInterest(BigDecimal assignPeriodRepayDelayInterest) {
        this.assignPeriodRepayDelayInterest = assignPeriodRepayDelayInterest;
    }

    public BigDecimal getAssignPeriodRepayLateInterest() {
        return assignPeriodRepayLateInterest;
    }

    public void setAssignPeriodRepayLateInterest(BigDecimal assignPeriodRepayLateInterest) {
        this.assignPeriodRepayLateInterest = assignPeriodRepayLateInterest;
    }
}
