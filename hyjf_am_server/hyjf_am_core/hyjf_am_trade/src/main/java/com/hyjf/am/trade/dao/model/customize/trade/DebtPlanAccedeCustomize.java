/**
 * Description:计划加入明细
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.am.trade.dao.model.customize.trade;

import java.io.Serializable;

/**
 * @author 王坤
 */

public class DebtPlanAccedeCustomize implements Serializable {
	
	/**序列化id */
	private static final long serialVersionUID = 6315391112691033945L;
	//用户id
	public String userId;
	//vip等级
    private String vipId;
    //vip等级
    private String vipLevel;
	//用户名
	public String userName;
	//加入金额
	public String accedeAccount;
	//加入时间
	public String accedeTime;
	//加入平台
	public Integer client;

	public String clientName;

	/** 构造方法*/
	public DebtPlanAccedeCustomize() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
	}

	public String getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(String vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccedeAccount() {
		return accedeAccount;
	}

	public void setAccedeAccount(String accedeAccount) {
		this.accedeAccount = accedeAccount;
	}

	public String getAccedeTime() {
		return accedeTime;
	}

	public void setAccedeTime(String accedeTime) {
		this.accedeTime = accedeTime;
	}

	public Integer getClient() {
		return client;
	}

	public void setClient(Integer client) {
		this.client = client;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

}

	