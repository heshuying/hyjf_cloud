package com.hyjf.am.trade.dao.model.customize.callcenter;

import java.io.Serializable;
import java.math.BigDecimal;


public class CallCenterWithdrawCustomize implements Serializable {

	/** 提现来源 */
	private Integer bankFlag;

	/** 银行流水号 */
	private Integer seqNo;

	/** 用户名 */
	private String username;

	/** 手机号 */
	private String mobile;

	/** 订单号，提现银行，提交时间，提现平台，处理状态 */
	private String ordid;

	/** 提现金额 */
	private BigDecimal total;

	/** 手续费 */
	private String fee;

	/** 手续费 */
	private BigDecimal credited;

	/** 提现银行 */
	private String bank;

	/** 账户 */
	private String account;

	/** 提交时间 */
	private String addtimeStr;

	/** 提现平台 */
	private String clientStr;

	/** 处理状态 */
	private Integer status;

	/** 失败原因 */
	private String reason;

	public Integer getBankFlag() {
		return bankFlag;
	}

	public void setBankFlag(Integer bankFlag) {
		this.bankFlag = bankFlag;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrdid() {
		return ordid;
	}

	public void setOrdid(String ordid) {
		this.ordid = ordid;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public BigDecimal getCredited() {
		return credited;
	}

	public void setCredited(BigDecimal credited) {
		this.credited = credited;
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

	public String getAddtimeStr() {
		return addtimeStr;
	}

	public void setAddtimeStr(String addtimeStr) {
		this.addtimeStr = addtimeStr;
	}

	public String getClientStr() {
		return clientStr;
	}

	public void setClientStr(String clientStr) {
		this.clientStr = clientStr;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}