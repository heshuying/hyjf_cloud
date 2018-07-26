package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

public class CreditTenderListCustomizeVO extends BaseVO {
    /* 主键 */
    private String id;
    /* 用户名称 */
    private String userName;
    /* 用户是否vip 0不是 1是*/
    private String isVip;
    /* 投资本金 */
    private String assignCapital;
    /* 投资时间 */
    private String assignTime;
    //承接平台
    private String client;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIsVip() {
		return isVip;
	}
	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}
	public String getAssignCapital() {
		return assignCapital;
	}
	public void setAssignCapital(String assignCapital) {
		this.assignCapital = assignCapital;
	}
	public String getAssignTime() {
		return assignTime;
	}
	public void setAssignTime(String assignTime) {
		this.assignTime = assignTime;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
    
    
}
