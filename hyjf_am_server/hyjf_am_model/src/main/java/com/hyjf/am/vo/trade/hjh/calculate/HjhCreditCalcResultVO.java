package com.hyjf.am.vo.trade.hjh.calculate;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * 自动承接用计算结果bean
 * @author liubin
 * @version HjhCreditCalcResultVO.java, v0.1 2018年10月25日 下午3:12:49
 */
public class HjhCreditCalcResultVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //分期数据结果
    private Map<Integer, HjhCreditCalcPeriodResultVO> assignResult;
    //承接总额
    private BigDecimal assignAccount;
    //承接本金
    private BigDecimal assignCapital;
    //承接利息
    private BigDecimal assignInterest;
    //承接支付金额
    private BigDecimal assignPay;
    //承接垫付利息
    private BigDecimal assignAdvanceMentInterest;
    //承接延期利息
    private BigDecimal assignRepayDelayInterest;
    //承接逾期利息
    private BigDecimal assignRepayLateInterest;
    //承接服务率
    private BigDecimal serviceApr;
    //承接服务费
    private BigDecimal serviceFee;

    public Map<Integer, HjhCreditCalcPeriodResultVO> getAssignResult() {
        return assignResult;
    }

    public void setAssignResult(Map<Integer, HjhCreditCalcPeriodResultVO> assignResult) {
        this.assignResult = assignResult;
    }

    public BigDecimal getAssignAccount() {
        return assignAccount;
    }

    public void setAssignAccount(BigDecimal assignAccount) {
        this.assignAccount = assignAccount;
    }

    public BigDecimal getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(BigDecimal assignCapital) {
        this.assignCapital = assignCapital;
    }

    public BigDecimal getAssignInterest() {
        return assignInterest;
    }

    public void setAssignInterest(BigDecimal assignInterest) {
        this.assignInterest = assignInterest;
    }

    public BigDecimal getAssignPay() {
        return assignPay;
    }

    public void setAssignPay(BigDecimal assignPay) {
        this.assignPay = assignPay;
    }

    public BigDecimal getAssignAdvanceMentInterest() {
        return assignAdvanceMentInterest;
    }

    public void setAssignAdvanceMentInterest(BigDecimal assignAdvanceMentInterest) {
        this.assignAdvanceMentInterest = assignAdvanceMentInterest;
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

    public BigDecimal getServiceApr() {
        return serviceApr;
    }

    public void setServiceApr(BigDecimal serviceApr) {
        this.serviceApr = serviceApr;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }
}
