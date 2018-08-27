/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: jijun
 * @date 20180625
 * Created at: 2015年11月20日 下午5:24:10
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

import com.hyjf.am.trade.dao.model.auto.CouponConfig;

/**
 * @author Administrator
 */

public class CouponConfigCustomizeV2 extends CouponConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4588370000503814380L;

	// 使用截止时间
	private int endTime;
	// 优惠券状态
	private int usedFlag;
	// 更新时间
	private int userUpdateTime;
	// 发放给用户的优惠券编号
	private String couponUserCode;
	
	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public int getUsedFlag() {
		return usedFlag;
	}

	public void setUsedFlag(int usedFlag) {
		this.usedFlag = usedFlag;
	}

	public int getUserUpdateTime() {
		return userUpdateTime;
	}

	public void setUserUpdateTime(int userUpdateTime) {
		this.userUpdateTime = userUpdateTime;
	}

	public String getCouponUserCode() {
		return couponUserCode;
	}

	public void setCouponUserCode(String couponUserCode) {
		this.couponUserCode = couponUserCode;
	}
	
	
}
