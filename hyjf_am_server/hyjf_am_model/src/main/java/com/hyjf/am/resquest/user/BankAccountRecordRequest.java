package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BasePage;

/**
 * @author nxl
 * @version RegisterUserRequest, v0.1 2018/4/11 12:49
 */
public class BankAccountRecordRequest extends BasePage {

	private String customerAccount;
	private String mobile;
	private String openAccountPlat;
	private String userName;
	private String idCard;
	private String realName;
	private String openTimeStart;
	private String openTimeEnd;
	//默认为true ,获取全部数据，为false时，获取部分数据
	private boolean limitFlg = false;
	public String getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOpenAccountPlat() {
		return openAccountPlat;
	}

	public void setOpenAccountPlat(String openAccountPlat) {
		this.openAccountPlat = openAccountPlat;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getOpenTimeStart() {
		return openTimeStart;
	}

	public void setOpenTimeStart(String openTimeStart) {
		this.openTimeStart = openTimeStart;
	}

	public String getOpenTimeEnd() {
		return openTimeEnd;
	}

	public void setOpenTimeEnd(String openTimeEnd) {
		this.openTimeEnd = openTimeEnd;
	}

	public boolean isLimitFlg() {
		return limitFlg;
	}

	public void setLimitFlg(boolean limitFlg) {
		this.limitFlg = limitFlg;
	}
}
