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

package com.hyjf.am.trade.dao.model.customize.app;

import java.io.Serializable;

/**
 * @author 王坤
 */

public class AppProjectConsumeCustomize implements Serializable {

	/** 序列化id*/
	private static final long serialVersionUID = -7448207599617767446L;
	// 姓名 name
	private String name;
	// 身份证 idCard
	private String idCard;
	// 融资金额 account
	private String account;

	/**
	 * 构造方法
	 */
	public AppProjectConsumeCustomize() {
		super();
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

}
