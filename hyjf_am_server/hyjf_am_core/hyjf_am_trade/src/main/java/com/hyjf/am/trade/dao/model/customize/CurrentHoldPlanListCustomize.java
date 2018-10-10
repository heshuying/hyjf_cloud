package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 *web端当前持有计划列表
 */
public class CurrentHoldPlanListCustomize implements Serializable {

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
	private String repayAccountWait;
	/**计划投资时间*/
	private String createTime;
	/**预计退出时间*/
	private String liquidateShouldTime;
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
    public String getRepayAccountWait() {
        return repayAccountWait;
    }
    public void setRepayAccountWait(String repayAccountWait) {
        this.repayAccountWait = repayAccountWait;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getLiquidateShouldTime() {
        return liquidateShouldTime;
    }
    public void setLiquidateShouldTime(String liquidateShouldTime) {
        this.liquidateShouldTime = liquidateShouldTime;
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