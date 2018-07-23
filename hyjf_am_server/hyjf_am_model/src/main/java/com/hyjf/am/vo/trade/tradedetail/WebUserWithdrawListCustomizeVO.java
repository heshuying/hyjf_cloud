

package com.hyjf.am.vo.trade.tradedetail;

/**
 * @Description
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */

public class WebUserWithdrawListCustomizeVO {

	// 主键
	private String id;
	// 取现时间
	private String time;
	// 取现金额
	private String money;
	// 取现费用
	private String fee;
	// 到账金额
	private String balance;
	// 取现状态
	private String status;
	// 银行存管提现标志位 1为银行存管   0汇付天下
	private String bankFlag;

	/**
	 * 构造方法
	 */

	public WebUserWithdrawListCustomizeVO() {
		super();
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBankFlag() {
		return bankFlag;
	}

	public void setBankFlag(String bankFlag) {
		this.bankFlag = bankFlag;
	}
}
