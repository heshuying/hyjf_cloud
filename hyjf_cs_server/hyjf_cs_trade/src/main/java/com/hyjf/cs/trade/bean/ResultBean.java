/**
 * Description: 交易明细查询返回列表类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Michael
 * @version: 1.0
 * Created at: 2015年12月2日 下午4:31:47
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.cs.trade.bean;

import java.math.BigDecimal;

/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */

public class ResultBean {
	
	private String accDate;//	入账日期
	private String inpDate;//	交易日期
	private String relDate;//	自然日期
	private String inpTime;//	交易时间
	private int traceNo;//流水号
	private String accountId;//	电子账号
	private String tranType;//	交易类型
	private String orFlag; //冲正撤销标志 O-原始交易 R-已经冲正或者撤销
	private BigDecimal txAmount; //交易金额
	private String txFlag; //交易金额符号   +/-
	private String describe;//	交易描述
	private String currency;//货币代码 156-人民币
	private BigDecimal currBal;//交易后余额
	private String forAccountId;//	对手电子账号

	public String getAccDate() {
		return accDate;
	}
	public void setAccDate(String accDate) {
		this.accDate = accDate;
	}
	public String getInpDate() {
		return inpDate;
	}
	public void setInpDate(String inpDate) {
		this.inpDate = inpDate;
	}
	public String getRelDate() {
		return relDate;
	}
	public void setRelDate(String relDate) {
		this.relDate = relDate;
	}
	public String getInpTime() {
		return inpTime;
	}
	public void setInpTime(String inpTime) {
		this.inpTime = inpTime;
	}
	public int getTraceNo() {
		return traceNo;
	}
	public void setTraceNo(int traceNo) {
		this.traceNo = traceNo;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	public String getOrFlag() {
		return orFlag;
	}
	public void setOrFlag(String orFlag) {
		this.orFlag = orFlag;
	}
	public BigDecimal getTxAmount() {
		return txAmount;
	}
	public void setTxAmount(BigDecimal txAmount) {
		this.txAmount = txAmount;
	}
	public String getTxFlag() {
		return txFlag;
	}
	public void setTxFlag(String txFlag) {
		this.txFlag = txFlag;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getCurrBal() {
		return currBal;
	}
	public void setCurrBal(BigDecimal currBal) {
		this.currBal = currBal;
	}
	public String getForAccountId() {
		return forAccountId;
	}
	public void setForAccountId(String forAccountId) {
		this.forAccountId = forAccountId;
	}
	
}