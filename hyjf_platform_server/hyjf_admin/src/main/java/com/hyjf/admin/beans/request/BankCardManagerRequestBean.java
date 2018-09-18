package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 银行卡管理請求參數
 * @author nxl
 */
public class BankCardManagerRequestBean  extends BaseRequest implements Serializable {
	@ApiModelProperty(value = "用户名")
	private String userName;
	@ApiModelProperty(value = "所属银行")
	private String bank;
	@ApiModelProperty(value = "银行帐号")
	private String account;
	@ApiModelProperty(value = "银行卡属性")
	private String cardProperty;
	@ApiModelProperty(value = "是否默认 0:是,1:否")
	private String cardType;
	@ApiModelProperty(value = "添加时间/绑卡时间开始")
	private String addTimeStart;
	@ApiModelProperty(value = "添加时间/绑卡时间结束")
	private String addTimeEnd;
	@ApiModelProperty(value = "手机号")
	private String mobile;
	@ApiModelProperty(value = "姓名")
	private String realName;

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
}
