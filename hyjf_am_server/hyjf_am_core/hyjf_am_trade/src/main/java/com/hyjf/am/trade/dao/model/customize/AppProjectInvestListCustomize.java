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

package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * @author 王坤
 */

public class AppProjectInvestListCustomize  implements Serializable {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -4720030760960740262L;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 *投资金额
	 */
	private String account;
	/**
	 *投资时间
	 */
	private String investTime;
	/**
	 * 平台
	 */
	private Integer client;

	private String clientName;

	public Integer getClient() {
		return client;
	}

	public void setClient(Integer client) {
		this.client = client;
	}



	/**
	 * 构造方法
	 */
	public AppProjectInvestListCustomize() {
		super();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getInvestTime() {
		return investTime;
	}

	public void setInvestTime(String investTime) {
		this.investTime = investTime;
	}


	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
}

	