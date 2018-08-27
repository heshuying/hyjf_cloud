package com.hyjf.cs.user.bean;

public class PaymentAuthPageResultBean extends BaseMapBean {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -821943657829999082L;
	 // 返回信息
    private String returnMsg;
    
	// 用户是否开户
	private String isOpenAccount;
	// 电子账户号
	private String accountId;
    //银行卡联行号
	private String payAllianceCode;
	 private String callBackAction;
	 private String acqRes;

	public String getIsOpenAccount() {
		return isOpenAccount;
	}

	public void setIsOpenAccount(String isOpenAccount) {
		this.isOpenAccount = isOpenAccount;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getPayAllianceCode() {
		return payAllianceCode;
	}

	public void setPayAllianceCode(String payAllianceCode) {
		this.payAllianceCode = payAllianceCode;
	}

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getCallBackAction() {
        return callBackAction;
    }

    public void setCallBackAction(String callBackAction) {
        this.callBackAction = callBackAction;
    }

    public String getAcqRes() {
        return acqRes;
    }

    public void setAcqRes(String acqRes) {
        this.acqRes = acqRes;
    }
}
