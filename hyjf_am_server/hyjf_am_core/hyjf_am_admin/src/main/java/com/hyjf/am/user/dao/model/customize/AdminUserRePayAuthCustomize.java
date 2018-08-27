package com.hyjf.am.user.dao.model.customize;


public class AdminUserRePayAuthCustomize {
	private int userid;
	/** 用户名 */
	private String userName;
	// 电子账号
	private String bankid;
	/** 收款状态 */
	private String authType;
//	/** 操作平台 */
//	private String operateEsb;	
	//授权结束时间
	private String signEndDate;
	//授权时间
	private String signDate;
	
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBankid() {
		return bankid;
	}
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public String getSignEndDate() {
		return signEndDate;
	}
	public void setSignEndDate(String signEndDate) {
		this.signEndDate = signEndDate;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
//	public String getOperateEsb() {
//		return operateEsb;
//	}
//	public void setOperateEsb(String operateEsb) {
//		this.operateEsb = operateEsb;
//	}
	
	
}
