/**
 * Description:用户开户列表前端显示查询所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

import com.hyjf.common.util.AsteriskProcessUtil;

/**
 * @author 王坤
 */

public class AppProjectCompanyDetailCustomize implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1896382103843595521L;
	
	/* 项目描述 borrowContents */
	private String borrowContents;
	/* 项目名称 borrowName  */
	private String borrowName;
	/* 所在地区 borrowAddress */
	private String borrowAddress;
	/* 所属行业 borrowIndustry */
	private String borrowIndustry;
	/* 注册资本 borrowCapital */
	private String regCaptial;
	/* 注册资本格式化*/
	private String regCaptialFormat;
	/* 注册时间 registTime */
	private String registTime;
	/* 财务状况 */
	private String accountContents;
	/* 融资主体 */
	private String companyName;
	/* 涉诉 */
	private String litigation;
	/* 统一社会信用代码 */
	private String socialCreditCode;
	/* 注册号  */
	private String registCode;
	/* 主营业务  */
	private String mainBusiness;
	/* 法人   */
	private String legalPerson;
	/* 在平台逾期次数   */
	private String overdueTimes;
	/* 在平台逾期金额    */
	private String overdueAmount;
	/* 企贷审核信息 企业证件 0未审核 1已审核    */
	private String isCertificate;
	/* 企贷审核信息 经营状况 0未审核 1已审核    */
	private String isOperation;
	/* 企贷审核信息 财务状况 0未审核 1已审核     */
	private String isFinance;	
	/* 企贷审核信息 企业信用 0未审核 1已审核     */
	private String isEnterpriseCreidt;
	/* 企贷审核信息 法人信息 0未审核 1已审核     */
	private String isLegalPerson;
	/* 企贷审核信息 资产状况 0未审核 1已审核    */
	private String isAsset;
	/* 企贷审核信息 购销合同 0未审核 1已审核    */
	private String isPurchaseContract;
	/* 企贷审核信息 供销合同 0未审核 1已审核    */
	private String isSupplyContract;
	
	
	public String getBorrowContents() {
		return borrowContents;
	}
	public void setBorrowContents(String borrowContents) {
		this.borrowContents = borrowContents;
	}
	public String getBorrowName() {
		return borrowName;
	}
	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}
	public String getBorrowAddress() {
		return borrowAddress;
	}
	public void setBorrowAddress(String borrowAddress) {
		this.borrowAddress = borrowAddress;
	}
	public String getBorrowIndustry() {
		return borrowIndustry;
	}
	public void setBorrowIndustry(String borrowIndustry) {
		this.borrowIndustry = borrowIndustry;
	}
	public String getRegCaptial() {
		return regCaptial;
	}
	public void setRegCaptial(String regCaptial) {
		this.regCaptial = regCaptial;
	}
	public String getRegistTime() {
		return registTime;
	}
	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}
	public String getAccountContents() {
		return accountContents;
	}
	public void setAccountContents(String accountContents) {
		this.accountContents = accountContents;
	}
	public String getCompanyName() {
		return AsteriskProcessUtil.getAsteriskedValue(companyName, companyName.length()-2);
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getLitigation() {
		return litigation;
	}
	public void setLitigation(String litigation) {
		this.litigation = litigation;
	}
	public String getSocialCreditCode() {
	    return socialCreditCode;
	}
	public String getSocialCreditCodeAsterisked() {
        return AsteriskProcessUtil.getAsteriskedValue(socialCreditCode, 4, 10);
    }
	public void setSocialCreditCode(String socialCreditCode) {
		this.socialCreditCode = socialCreditCode;
	}
	public String getRegistCode() {
	    return registCode;
	}
	public String getRegistCodeAsterisked() {
        return AsteriskProcessUtil.getAsteriskedValue(registCode, 4, 10);
    }
	public void setRegistCode(String registCode) {
		this.registCode = registCode;
	}
	public String getMainBusiness() {
		return mainBusiness;
	}
	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}
	public String getLegalPerson() {
		return legalPerson;
	}
	public String getLegalPersonAsterisked() {
	    return AsteriskProcessUtil.getAsteriskedValue(legalPerson, 1, 2);
    }
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	public String getOverdueTimes() {
		return overdueTimes;
	}
	public void setOverdueTimes(String overdueTimes) {
		this.overdueTimes = overdueTimes;
	}
	public String getOverdueAmount() {
		return overdueAmount;
	}
	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}
	public String getIsCertificate() {
		return isCertificate;
	}
	public void setIsCertificate(String isCertificate) {
		this.isCertificate = isCertificate;
	}
	public String getIsOperation() {
		return isOperation;
	}
	public void setIsOperation(String isOperation) {
		this.isOperation = isOperation;
	}
	public String getIsFinance() {
		return isFinance;
	}
	public void setIsFinance(String isFinance) {
		this.isFinance = isFinance;
	}
	public String getIsEnterpriseCreidt() {
		return isEnterpriseCreidt;
	}
	public void setIsEnterpriseCreidt(String isEnterpriseCreidt) {
		this.isEnterpriseCreidt = isEnterpriseCreidt;
	}
	public String getIsLegalPerson() {
		return isLegalPerson;
	}
	public void setIsLegalPerson(String isLegalPerson) {
		this.isLegalPerson = isLegalPerson;
	}
	public String getIsAsset() {
		return isAsset;
	}
	public void setIsAsset(String isAsset) {
		this.isAsset = isAsset;
	}
	public String getIsPurchaseContract() {
		return isPurchaseContract;
	}
	public void setIsPurchaseContract(String isPurchaseContract) {
		this.isPurchaseContract = isPurchaseContract;
	}
	public String getIsSupplyContract() {
		return isSupplyContract;
	}
	public void setIsSupplyContract(String isSupplyContract) {
		this.isSupplyContract = isSupplyContract;
	}
    public String getRegCaptialFormat() {
        return regCaptialFormat;
    }
    public void setRegCaptialFormat(String regCaptialFormat) {
        this.regCaptialFormat = regCaptialFormat;
    }	

}
