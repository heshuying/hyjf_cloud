/**
 * Description:用户开户列表前端显示查询所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.vo.app;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author 王坤
 */

public class AppRepayPlanListCustomizeVO extends BaseVO implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -2869301136107487471L;
	// 类型：本息还是利息
	private String repayName;
	// 金额
	private String account;
	// 时间
	private String repayTime;
	// 还款状态
	private String repayStatus;
	// 优惠券收益领取状态
	private String couponStatus = "";

	/**
	 * 构造方法
	 */
	public AppRepayPlanListCustomizeVO() {
		super();
	}

	public String getRepayName() {
		return repayName;
	}

	public void setRepayName(String repayName) {
		this.repayName = repayName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public String getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}

	public String getCouponStatus() {
		return couponStatus;
	}

	public void setCouponStatus(String couponStatus) {
		this.couponStatus = couponStatus;
	}

}
