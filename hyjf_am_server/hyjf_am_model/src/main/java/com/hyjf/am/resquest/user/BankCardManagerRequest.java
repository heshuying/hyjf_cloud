package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BasePage;


/**
 * 银行卡管理請求參數
 * @author nxl
 */
public class BankCardManagerRequest extends BasePage{
	private String userName;
	private String bank;
	private String account;
	private String cardProperty;
	private String cardType;
	private String addTimeStart;
	private String addTimeEnd;
	private String mobile;
	private String realName;

	//分页区分,
	// 导出时设置为0,代表查找全部,
	// 默认为1,代表分页
	private int limitFlg = 1;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCardProperty() {
		return cardProperty;
	}

	public void setCardProperty(String cardProperty) {
		this.cardProperty = cardProperty;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getAddTimeStart() {
		return addTimeStart;
	}

	public void setAddTimeStart(String addTimeStart) {
		this.addTimeStart = addTimeStart;
	}

	public String getAddTimeEnd() {
		return addTimeEnd;
	}

	public void setAddTimeEnd(String addTimeEnd) {
		this.addTimeEnd = addTimeEnd;
	}


	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public int getLimitFlg() {
		return limitFlg;
	}

	public void setLimitFlg(int limitFlg) {
		this.limitFlg = limitFlg;
	}
}
