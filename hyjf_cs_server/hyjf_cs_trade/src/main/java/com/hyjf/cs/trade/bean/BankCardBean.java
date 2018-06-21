package com.hyjf.cs.trade.bean;

public class BankCardBean {
	private Integer id;// ID
	private Integer bankId; // 银行Id
	private String bank;// 发卡行的名称
	private String bankCode;// 发卡行的代号
	private String payAllianceCode;// 银联行号
	private String logo;// 发卡行logo的url

	private String cardNo;// 银行卡号

	private String isDefault;// 0普通提现卡1默认提现卡2快捷支付卡

	private String cardNoInfo;// 银行卡号表示用

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCardNoInfo() {
		return cardNoInfo;
	}

	public void setCardNoInfo(String cardNoInfo) {
		this.cardNoInfo = cardNoInfo;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getPayAllianceCode() {
		return payAllianceCode;
	}

	public void setPayAllianceCode(String payAllianceCode) {
		this.payAllianceCode = payAllianceCode;
	}
	
}
