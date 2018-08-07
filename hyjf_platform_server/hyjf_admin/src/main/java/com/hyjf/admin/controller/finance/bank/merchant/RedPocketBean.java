/**
 * Description: 交易明细查询返回列表类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Michael
 * @version: 1.0
 * Created at: 2015年12月2日 下午4:31:47
 * Modification History:
 * Modified by : 
 */

package com.hyjf.admin.controller.finance.bank.merchant;

import java.io.Serializable;

/**
 * @author Michael
 */

public class RedPocketBean implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6367215623402957824L;

	/**
	 * 电子账号
	 */
	private String userAccount;

	/**
	 * 红包金额
	 */
	private String amount;

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}