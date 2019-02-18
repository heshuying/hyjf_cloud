package com.hyjf.am.vo.trade.coupon;


public class AppCouponCustomizeVO {
	/** 计划信息 */
	// 计划nid
	private String planNid;
	// 计划名称
	private String planName;
	// 计划出借订单号
	private String accedeOrderId;
	// 计划年化收益率
	private String planApr;
	// 计划锁定期
	private String planPeriod;
	// 计划出借用户id
	private String userId;
	// 计划出借金额
	private String accedeAccount;
	// 计划出借时间
	private String addTime;
	// 计划待收本金
	private String waitCaptical;
	// 计划待收收益
	private String waitInterest;
	// 计划已回款总额
	private String receivedTotal;
	// 还款方式 已翻译
	private String repayMethod;
	// 还款方式 代号
	private String repayStyle;
	// 订单状态
	private String orderStatus;
	// 优惠券还款状态
	private String recoverStatus;
	// add 添加计划开始计息时间字段 nxl 20180420 start
    // 计划开始计息时间
    private String countInterestTime;
	// add 添加计划开始计息时间字段 nxl 20180420 end
	/** 优惠券信息  */
	// 优惠券类型
	private String couponType;
	// 优惠券面额
	private String couponAmount;
	// 待收总额
	private String recoverAccountWait;
	// 待收利息
	private String recoverAccountInterestWait;
	// 待收本金
	private String recoverAccountCapitalWait;
	// 本金出借订单号
	private String realTenderId;

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(String couponAmount) {
		this.couponAmount = couponAmount;
	}

	public String getRecoverAccountWait() {
		return recoverAccountWait;
	}

	public void setRecoverAccountWait(String recoverAccountWait) {
		this.recoverAccountWait = recoverAccountWait;
	}

	public String getRecoverAccountInterestWait() {
		return recoverAccountInterestWait;
	}

	public void setRecoverAccountInterestWait(String recoverAccountInterestWait) {
		this.recoverAccountInterestWait = recoverAccountInterestWait;
	}

	public String getRecoverAccountCapitalWait() {
		return recoverAccountCapitalWait;
	}

	public void setRecoverAccountCapitalWait(String recoverAccountCapitalWait) {
		this.recoverAccountCapitalWait = recoverAccountCapitalWait;
	}

	public String getRealTenderId() {
		return realTenderId;
	}

	public void setRealTenderId(String realTenderId) {
		this.realTenderId = realTenderId;
	}

	public String getPlanNid() {
		return planNid;
	}

	public void setPlanNid(String planNid) {
		this.planNid = planNid;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getAccedeOrderId() {
		return accedeOrderId;
	}

	public void setAccedeOrderId(String accedeOrderId) {
		this.accedeOrderId = accedeOrderId;
	}

	public String getPlanApr() {
		return planApr;
	}

	public void setPlanApr(String planApr) {
		this.planApr = planApr;
	}

	public String getPlanPeriod() {
		return planPeriod;
	}

	public void setPlanPeriod(String planPeriod) {
		this.planPeriod = planPeriod;
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

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getWaitCaptical() {
		return waitCaptical;
	}

	public void setWaitCaptical(String waitCaptical) {
		this.waitCaptical = waitCaptical;
	}

	public String getWaitInterest() {
		return waitInterest;
	}

	public void setWaitInterest(String waitInterest) {
		this.waitInterest = waitInterest;
	}

	public String getReceivedTotal() {
		return receivedTotal;
	}

	public void setReceivedTotal(String receivedTotal) {
		this.receivedTotal = receivedTotal;
	}

	public String getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(String repayMethod) {
		this.repayMethod = repayMethod;
	}

	public String getRepayStyle() {
		return repayStyle;
	}

	public void setRepayStyle(String repayStyle) {
		this.repayStyle = repayStyle;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCountInterestTime() {
		return countInterestTime;
	}

	public void setCountInterestTime(String countInterestTime) {
		this.countInterestTime = countInterestTime;
	}

	public String getRecoverStatus() {
		return recoverStatus;
	}

	public void setRecoverStatus(String recoverStatus) {
		this.recoverStatus = recoverStatus;
	}
}
