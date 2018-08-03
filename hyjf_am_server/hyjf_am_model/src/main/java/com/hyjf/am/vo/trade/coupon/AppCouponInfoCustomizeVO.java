/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月20日 下午5:24:10
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.vo.trade.coupon;

import java.io.Serializable;

public class AppCouponInfoCustomizeVO implements Serializable {
	private static final long serialVersionUID = 1L;

	// 优惠券类型
	private String couponType;
	// 优惠券面值
	private String couponQuota;
	// 优惠券名称
	private String couponName;
	// 优惠券编号
	private String couponCode;

	// 真实资金投资订单号
	private String realOrderId;

	// 待收本金
	private String recoverAccountCapitalWait;
	// 待收利息
	private String recoverAccountInterestWait;
	// 待收总额
	private String recoverAaccountWait;

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getCouponQuota() {
		return couponQuota;
	}

	public void setCouponQuota(String couponQuota) {
		this.couponQuota = couponQuota;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getRealOrderId() {
		return realOrderId;
	}

	public void setRealOrderId(String realOrderId) {
		this.realOrderId = realOrderId;
	}

	public String getRecoverAccountCapitalWait() {
		return recoverAccountCapitalWait;
	}

	public void setRecoverAccountCapitalWait(String recoverAccountCapitalWait) {
		this.recoverAccountCapitalWait = recoverAccountCapitalWait;
	}

	public String getRecoverAccountInterestWait() {
		return recoverAccountInterestWait;
	}

	public void setRecoverAccountInterestWait(String recoverAccountInterestWait) {
		this.recoverAccountInterestWait = recoverAccountInterestWait;
	}

	public String getRecoverAaccountWait() {
		return recoverAaccountWait;
	}

	public void setRecoverAaccountWait(String recoverAaccountWait) {
		this.recoverAaccountWait = recoverAaccountWait;
	}
}
