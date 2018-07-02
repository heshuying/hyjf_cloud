/**
 * Description:项目详情查询所用vo
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */
package com.hyjf.cs.trade.bean;

import java.io.Serializable;

public class AppBorrowRepayPlanBean implements Serializable {

	/** 序列化id */
	private static final long serialVersionUID = -2913028255458205989L;

	/**
	 * 还款时间
	 * string
	 * example: 2018-10-10
	 */
	private String time;
	
	/**
	 * 还款期数
	 * number 
	 * example: 24
	 */
	private String number;
	
	/**
	 * 还款金额
	 * number 
	 * example: 24
	 */
	private String account;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}


}
