

package com.hyjf.am.vo.trade.tradedetail;

/**
 * @Description
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */

public class WebUserRechargeListCustomizeVO {

	// 主键
	private String id;
	// 交易日期
	private String time;
	// 费用
	private String fee;
	// 交易金额
	private String money;
	// 到账金额
	private String balance;
	// 状态 
	private String status;
	
	//汇付天下 或者 江西银行
	private String isBank;

	/**
	 * 构造方法
	 */

	public WebUserRechargeListCustomizeVO() {
		super();
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
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

	public String getIsBank() {
		return isBank;
	}

	public void setIsBank(String isBank) {
		this.isBank = isBank;
	}
	
}
