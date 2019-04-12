/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.admin.TenderCommissionVO;

import java.io.Serializable;

/**
 * @author 李彬
 * 汇计划发提成bean组合体 组装 TenderCommission BankCallBean ChinapnrBean
 * @version CommissionComboRequest.java, v0.1 2018年8月9日 下午3:18:40
 */
public class CommissionComboRequest extends BasePage implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * TenderCommissionVO
	 */
	private TenderCommissionVO tenderCommission;
	/*涉及到的 BankCallBean 相关字段 start*/
	/** 交易金额 */
	public String txAmount;
	/** 交易流水号 */
	public String seqNo;
	/** 交易日期 */
	public String txDate;
	/** 交易时间 */
	public String txTime;
	/** 操作用户ip */
	public String logIp;
	/** 电子账号 */
	public String accountId;
	/*涉及到的 BankCallBean 相关字段 end*/
	/*涉及到的 ChinapnrBean 相关字段 start*/
    /** 交易金额 */
    public String TransAmt;
    /** IP地址(日志用) */
    private String chinapnrlogIp;
    /** bankopenaccount 的  account*/
    private String account;
    
	private String userName;
	
	private String regionName;

    private String branchName;

    private String departmentName;
    
    private String trueName;
    
    private String sex;
    
    private String mobile;
    
    private Integer attribute;

    private String logOrderId;
   
	public TenderCommissionVO getTenderCommission() {
		return tenderCommission;
	}
	public void setTenderCommission(TenderCommissionVO tenderCommission) {
		this.tenderCommission = tenderCommission;
	}
	public String getTxAmount() {
		return txAmount;
	}
	public void setTxAmount(String txAmount) {
		this.txAmount = txAmount;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getTxDate() {
		return txDate;
	}
	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}
	public String getTxTime() {
		return txTime;
	}
	public void setTxTime(String txTime) {
		this.txTime = txTime;
	}
	public String getLogIp() {
		return logIp;
	}
	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getTransAmt() {
		return TransAmt;
	}
	public void setTransAmt(String transAmt) {
		TransAmt = transAmt;
	}
	public String getChinapnrlogIp() {
		return chinapnrlogIp;
	}
	public void setChinapnrlogIp(String chinapnrlogIp) {
		this.chinapnrlogIp = chinapnrlogIp;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getAttribute() {
		return attribute;
	}
	public void setAttribute(Integer attribute) {
		this.attribute = attribute;
	}

	public String getLogOrderId() {
		return logOrderId;
	}

	public void setLogOrderId(String logOrderId) {
		this.logOrderId = logOrderId;
	}
}
