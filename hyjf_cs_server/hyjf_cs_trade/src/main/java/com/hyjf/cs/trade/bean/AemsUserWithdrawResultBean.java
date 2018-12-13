package com.hyjf.cs.trade.bean;

/**
 * Aems接口:用户提现结果Bean
 * @author Zha Daojian
 * @date 2018/12/12 11:02
 * @param
 * @return
 **/
public class AemsUserWithdrawResultBean extends BaseMapBean {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -9106212219698443171L;
	// 交易金额
	private String amt;
	// 到账金额
	private String arrivalAmount;
	// 提现手续费
	private String fee;
	/**
	 * 返回调用方的url
	 */
	private String callBackAction;

	public String getCallBackAction() {
		return callBackAction;
	}

	public void setCallBackAction(String callBackAction) {
		this.callBackAction = callBackAction;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getArrivalAmount() {
		return arrivalAmount;
	}

	public void setArrivalAmount(String arrivalAmount) {
		this.arrivalAmount = arrivalAmount;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

}
