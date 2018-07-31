package com.hyjf.cs.user.bean;

//import com.hyjf.app.BaseBean;

public class BankCardBean {
    private String bank;//发卡行的名称
    private String bankCode;//发卡行的代号

    private String logo;//发卡行logo的url

    private String cardNo;//银行卡号
    
    private String isDefault;//0普通提现卡1默认提现卡2快捷支付卡

    
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


   

}
