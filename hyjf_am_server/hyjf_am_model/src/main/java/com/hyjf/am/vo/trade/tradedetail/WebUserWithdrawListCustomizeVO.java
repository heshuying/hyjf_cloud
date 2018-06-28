

package com.hyjf.am.vo.trade.tradedetail;

/**
 * @Description
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */

public class WebUserWithdrawListCustomizeVO {

	// 主键
	public String id;
	// 取现时间
	public String time;
	// 取现金额
	public String money;
	// 取现费用
	public String fee;
	// 到账金额
	public String balance;
	// 取现状态
	public String status;
	// 银行存管提现标志位 1为银行存管   0汇付天下
	public String bankFlag;

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
