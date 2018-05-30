package com.hyjf.cs.user.vo;

/**
 * 绑卡vo
 * @author hesy
 */
public class BindCardVO {
	private String smsCode;
	private String cardNo;
	private String mobile;
	private String lastSrvAuthCode;
	
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getLastSrvAuthCode() {
		return lastSrvAuthCode;
	}
	public void setLastSrvAuthCode(String lastSrvAuthCode) {
		this.lastSrvAuthCode = lastSrvAuthCode;
	}
	
}

	