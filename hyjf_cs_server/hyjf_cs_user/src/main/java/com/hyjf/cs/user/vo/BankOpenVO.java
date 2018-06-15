package com.hyjf.cs.user.vo;

public class BankOpenVO {

	/** 用户真实姓名 */
	private String trueName;
	/** 用户身份证号码 */
	private String idNo;
	/** 用户银行卡号 */
	private String cardNo;
	/** 用户手机号 */
	private String mobile;
	/**用户的验证码*/
	private String smsCode;
	
	private Integer userId;

	private String platform;

	@Override
	public String toString() {
		return "BankOpenVO{" +
				"trueName='" + trueName + '\'' +
				", idNo='" + idNo + '\'' +
				", cardNo='" + cardNo + '\'' +
				", mobile='" + mobile + '\'' +
				", smsCode='" + smsCode + '\'' +
				", userId=" + userId +
				'}';
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
}
