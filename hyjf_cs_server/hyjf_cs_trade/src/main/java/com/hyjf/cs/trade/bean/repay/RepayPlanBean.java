/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2016
 * Company: HYJF Corporation
 * @author: b
 * @version: 1.0
 * Created at: 2016年1月4日 下午4:56:02
 * Modification History:
 * Modified by : 
 */

package com.hyjf.cs.trade.bean.repay;

/**
 */
public class RepayPlanBean {
	
	private String repayTime;
	private String repayTotal;
	private String repayType;

	/**
	 * @param repayTime
	 * @param repayTotal
	 * @param repayType
	 */
		
	public RepayPlanBean(String repayTime, String repayTotal, String repayType) {
		super();
		this.repayTime = repayTime;
		this.repayTotal = repayTotal;
		this.repayType = repayType;
	}
	
	/**
	 * repayTime
	 * 
	 * @return the repayTime
	 */

	public String getRepayTime() {
		return repayTime;
	}


	/**
	 * @param repayTime
	 *            the repayTime to set
	 */

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	/**
	 * repayTotal
	 * 
	 * @return the repayTotal
	 */

	public String getRepayTotal() {
		return repayTotal;
	}

	/**
	 * @param repayTotal
	 *            the repayTotal to set
	 */

	public void setRepayTotal(String repayTotal) {
		this.repayTotal = repayTotal;
	}

	/**
	 * repayType
	 * 
	 * @return the repayType
	 */

	public String getRepayType() {
		return repayType;
	}

	/**
	 * @param repayType
	 *            the repayType to set
	 */

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

}
