package com.hyjf.am.trade.dao.model.customize.trade;

import java.io.Serializable;

/**
 *web端用户已回款计划列表
 */
public class RepayMentPlanListCustomize implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 358190081082338992L;
	/**计划编号*/
    private String debtPlanNid;
    /**计划订单号*/
    private String accedeOrderId;
    /**预计年化收益率*/
    private String expectApr;
    /**计划期限*/
    private String debtLockPeriod;
    /**投资用户编号*/
    private String userId;
    /**计划投资金额*/
    private String accedeAccount;
    /**计划待收总额*/
    private String repayAccountYes;
    /**计划待收总额*/
    private String repayInterestYes;
    /**预计退出时间*/
    private String liquidateFactTime;
    /**当前持有类型*/
    private String type;
    /**说明*/
    private String data;
    /**优惠券类型*/
    private String couponType;
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
    public String getExpectApr() {
        return expectApr;
    }
    public void setExpectApr(String expectApr) {
        this.expectApr = expectApr;
    }
    public String getDebtLockPeriod() {
        return debtLockPeriod;
    }
    public void setDebtLockPeriod(String debtLockPeriod) {
        this.debtLockPeriod = debtLockPeriod;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getAccedeAccount() {
        return accedeAccount;
    }
    public void setAccedeAccount(String accedeAccount) {
        this.accedeAccount = accedeAccount;
    }
    public String getRepayAccountYes() {
        return repayAccountYes;
    }
    public void setRepayAccountYes(String repayAccountYes) {
        this.repayAccountYes = repayAccountYes;
    }
    public String getRepayInterestYes() {
        return repayInterestYes;
    }
    public void setRepayInterestYes(String repayInterestYes) {
        this.repayInterestYes = repayInterestYes;
    }
    public String getLiquidateFactTime() {
        return liquidateFactTime;
    }
    public void setLiquidateFactTime(String liquidateFactTime) {
        this.liquidateFactTime = liquidateFactTime;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getCouponType() {
        return couponType;
    }
    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }
    
    
	
}