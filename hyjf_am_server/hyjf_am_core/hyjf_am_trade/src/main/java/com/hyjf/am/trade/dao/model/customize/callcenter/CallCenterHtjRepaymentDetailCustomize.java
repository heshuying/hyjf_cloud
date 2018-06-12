/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize.callcenter;

import java.io.Serializable;

/**
 * @author wangjun
 * @version CallCenterHtjRepaymentDetailCustomize, v0.1 2018/6/11 17:22
 */

public class CallCenterHtjRepaymentDetailCustomize implements Serializable {

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;
	/**
     * 计划编号
     */
    private String debtPlanNid;
    /**
     * 计划订单号
     */
    private String accedeOrderId;
    /**
     * 加入金额
     */
    private String accedeAccount;
    /**
     * 应还本金
     */
    private String repayCapital;
    /**
     * 应还利息
     */
    private String repayInterest;
    /**
     * 实际回款总额
     */
    private String repayAccountFact;
    /**
     * 实际回款利息
     */
    private String repayInterestFact;
    /**
     * 应清算时间
     */
    private String liquidateShouldTime;
    /**
     * 最晚到账时间
     */
    private String lastRepayTime;
    /**
     * 还款时间
     */
    private String repayTime;
    
    /**
     * 状态
     */
    private String status;
    
    public String getDebtPlanNid() {
        return debtPlanNid;
    }
    public void setDebtPlanNid(String debtPlanNid) {
        this.debtPlanNid = debtPlanNid;
    }
    public String getAccedeOrderId() {
        return accedeOrderId;
    }
    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }
    public String getAccedeAccount() {
        return accedeAccount;
    }
    public void setAccedeAccount(String accedeAccount) {
        this.accedeAccount = accedeAccount;
    }
    public String getRepayCapital() {
        return repayCapital;
    }
    public void setRepayCapital(String repayCapital) {
        this.repayCapital = repayCapital;
    }
    public String getRepayInterest() {
        return repayInterest;
    }
    public void setRepayInterest(String repayInterest) {
        this.repayInterest = repayInterest;
    }
    public String getRepayAccountFact() {
        return repayAccountFact;
    }
    public void setRepayAccountFact(String repayAccountFact) {
        this.repayAccountFact = repayAccountFact;
    }
    public String getRepayInterestFact() {
        return repayInterestFact;
    }
    public void setRepayInterestFact(String repayInterestFact) {
        this.repayInterestFact = repayInterestFact;
    }
    public String getLiquidateShouldTime() {
        return liquidateShouldTime;
    }
    public void setLiquidateShouldTime(String liquidateShouldTime) {
        this.liquidateShouldTime = liquidateShouldTime;
    }
    public String getLastRepayTime() {
        return lastRepayTime;
    }
    public void setLastRepayTime(String lastRepayTime) {
        this.lastRepayTime = lastRepayTime;
    }
    public String getRepayTime() {
        return repayTime;
    }
    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
