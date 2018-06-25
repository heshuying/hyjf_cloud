package com.hyjf.am.resquest.user;

/**
 * @author nxl
 * @version RegisterUserRequest, v0.1 2018/4/11 12:49
 */
public class BankAccountRecordRequest {

	private String customerAccount;
	private String mobile;
	private String openAccountPlat;
	private String userName;
	private String idCard;
	private String realName;
	private String openTimeStart;
	private String openTimeEnd;
	public int limit;
	private int paginatorPage = 1;
	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}
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

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
