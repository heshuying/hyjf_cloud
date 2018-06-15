package com.hyjf.callcenter.beans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wangjun
 * @version SrchWithdrawalInfoBean, v0.1 2018/6/15 14:35
 */
public class SrchWithdrawalInfoBean implements Serializable {

	/**
	 * 请求时间戳(暂时)
	 */
	private String timestamp;
	/**
	 * md5校验码(暂时)
	 */
	private String sign;
	//以下为需求定义字段
	/** 1.用户名 */
	private String username;
	/** 2.手机号 */
	private String mobile;
	/** 3.资金托管平台(提现来源) */
	private Integer bankFlag;
	/** 4.流水号 */
	private String bankSeqNo;
	/** 5.订单号 */
	private String ordid;
	/** 6.提现金额 */
	private BigDecimal total;
	/** 7.手续费 */
	private String fee;
	/** 8.到账金额 */
	private BigDecimal credited;
	/** 9.实际出账金额 */
	private BigDecimal actTotal;//同于 提现金额
	/** 10.提现银行 */
	private String bank;
	/** 11.提现平台 */
	private String clientStr;
	/** 12.处理状态 */
	private Integer status;
	/** 13.失败原因 */
	private String reason;
	/** 14.提交时间 */
	private String addtimeStr;
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
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
	public Integer getBankFlag() {
		return bankFlag;
	}
	public void setBankFlag(Integer bankFlag) {
		this.bankFlag = bankFlag;
	}
	public String getBankSeqNo() {
		return bankSeqNo;
	}
	public void setBankSeqNo(String bankSeqNo) {
		this.bankSeqNo = bankSeqNo;
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
	public BigDecimal getActTotal() {
		return actTotal;
	}
	public void setActTotal(BigDecimal actTotal) {
		this.actTotal = actTotal;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
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
	public String getAddtimeStr() {
		return addtimeStr;
	}
	public void setAddtimeStr(String addtimeStr) {
		this.addtimeStr = addtimeStr;
	}
}
