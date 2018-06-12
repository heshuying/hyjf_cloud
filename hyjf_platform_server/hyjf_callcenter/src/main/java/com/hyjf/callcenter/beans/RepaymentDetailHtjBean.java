/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.beans;

import java.io.Serializable;

public class RepaymentDetailHtjBean implements Serializable  {
	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 2569541349432465432L;
	//用户名
    private String userName;
    //手机号
    private String mobile;
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
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
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
    
}
