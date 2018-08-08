package com.hyjf.cs.trade.vo;


/**
 * @author xiasq
 * @version AppWithdrawResult, v0.1 2017/12/4 20:01
 */
public class AppWithdrawResultVO {

	public static final String APP_SUCCESS = "0";
	public static final String SUCCESS_MSG = "成功";

    public AppWithdrawResultVO(String status, String statusDesc, String request) {
		super();
		this.status = status;
		this.statusDesc = statusDesc;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	private String status;

	private String statusDesc;

    // 请求的服务 统一返回
	private String request = "";
	// 提现规则url
	private String url = "";
	// 可用金额
	private String total = "";
	// 提现手续费
	private String fee = "";
	// 实际到账
	private String balance = "";
	// 发卡行logo的url
	private String logo = "";
	// 发卡行的名称
	private String bank = "";
	// 卡号
	private String cardNo = "";
	// 按钮上的文字
	private String buttonWord = "";
	// 是否显示开户行号
	private String isDisplay = "0";
	// 开户行号
	private String openCardBankCode = "";
	// 开户行号查询url
	private String openCardBankCodeUrl = "";
	// 银行预留手机号
	private String phoneNumber = "";	
	// 合规改造 需要银行卡统一脱敏显示
    private String cardNo_info = "";
    // 是否需要验证码
	private String needSMSCode;  //0-不显示  1-显示


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getButtonWord() {
		return buttonWord;
	}

	public void setButtonWord(String buttonWord) {
		this.buttonWord = buttonWord;
	}

	public String getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}

	public String getOpenCardBankCodeUrl() {
		return openCardBankCodeUrl;
	}

	public void setOpenCardBankCodeUrl(String openCardBankCodeUrl) {
		this.openCardBankCodeUrl = openCardBankCodeUrl;
	}

	public String getOpenCardBankCode() {
		return openCardBankCode;
	}

	public void setOpenCardBankCode(String openCardBankCode) {
		this.openCardBankCode = openCardBankCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

    public String getCardNo_info() {
        return cardNo_info;
    }

    public void setCardNo_info(String cardNo_info) {
        this.cardNo_info = cardNo_info;
    }

	public String getNeedSMSCode() {
		return needSMSCode;
	}

	public void setNeedSMSCode(String needSMSCode) {
		this.needSMSCode = needSMSCode;
	}
}
