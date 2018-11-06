package com.hyjf.am.vo.trade.hjh.calculate;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

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

    @Override
    public String toString() {
        return "HjhCreditCalcResultVO{" +
                "assignResult=" + assignResult +
                ", assignAccount=" + assignAccount +
                ", assignCapital=" + assignCapital +
                ", assignInterest=" + assignInterest +
                ", assignPay=" + assignPay +
                ", assignAdvanceMentInterest=" + assignAdvanceMentInterest +
                ", assignRepayDelayInterest=" + assignRepayDelayInterest +
                ", assignRepayLateInterest=" + assignRepayLateInterest +
                ", serviceApr=" + serviceApr +
                ", serviceFee=" + serviceFee +
                '}';
    }

    public String toLog() {
        String result;
        result = " 承接总额:" + assignAccount +
                "\n,承接本金:" + assignCapital +
                "\n,承接利息:" + assignInterest +
                "\n,承接支付金额:" + assignPay +
                "\n,承接垫付利息:" + assignAdvanceMentInterest +
                "\n,承接延期利息:" + assignRepayDelayInterest +
                "\n,承接逾期利息:" + assignRepayLateInterest +
                "\n,承接服务率:" + serviceApr +
                "\n,承接服务费:" + serviceFee +
                "\n,分期数据结果:" + assignResult;
        System.out.println(result);
        return result;
    }

//    public static void main(String[] args) {
//        HjhCreditCalcResultVO vo = new HjhCreditCalcResultVO();
//        Map<Integer, HjhCreditCalcPeriodResultVO> assignResult;
//
//
////        HjhCreditCalcPeriodResultVO p1 = new HjhCreditCalcPeriodResultVO();
////        p1.setAssignPeriodCapital(new BigDecimal("1.1"));
////        p1.setAssignPeriodInterest(new BigDecimal("1.2"));
////        assignResult.put(1,p1);
////        assignResult.put(2,p1);
//        vo.setAssignAccount(new BigDecimal("1.11"));
//        vo.setAssignCapital(new BigDecimal(2));
//        vo.setAssignInterest(new BigDecimal(3));
//        vo.setAssignPay(new BigDecimal(4));
//        vo.setAssignAdvanceMentInterest(new BigDecimal(5));
//        vo.setAssignRepayDelayInterest(new BigDecimal(6));
//        vo.setAssignRepayLateInterest(new BigDecimal(7));
//        vo.setServiceApr(new BigDecimal(8));
//        vo.setServiceFee(new BigDecimal(9));
////        vo.setAssignResult(assignResult);
//
//        vo.toLog();
//    }
}
