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

package com.hyjf.am.trade.dao.model.customize.app;

import com.hyjf.common.util.AsteriskProcessUtil;

import java.io.Serializable;

/**
 * @author 王坤
 */

public class AppProjectPersonDetailCustomize implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1295747731881034543L;
	
	/* 项目描述 borrowContents */
	private String borrowContents;
	/* 年龄 age */
	private String age;
	/* 性别 sex */
	private String sex;
	/* 婚姻状况 maritalStatus */
	private String maritalStatus;
	/* 工作城市 workingCity */
	private String workingCity;
	/* 财务状况 */
	private String accountContents;
	/* 用户姓名 */
	private String trueName;
	/* 岗位职业 */
	private String position;
	/* 身份证号 */
	private String cardNo;
	/*户籍地 */
	private String domicile;
	/* 在平台逾期次数 */
	private String overdueTimes;
	/* 在平台逾期金额 */
	private String overdueAmount;
	/* 涉诉情况 */
	private String litigation;
	/* 个贷审核信息 身份证 0未审核 1已审核  */
	private String isCard;
	/*个贷审核信息 收入状况 0未审核 1已审核 */
	private String isIncome;
	/* 个贷审核信息 信用状况 0未审核 1已审核 */
	private String isCredit;
	/* 个贷审核信息 资产状况 0未审核 1已审核 */
	private String isAsset;
	/* 个贷审核信息 车辆状况 0未审核 1已审核 */
	private String isVehicle;
	/* 个贷审核信息 行驶证 0未审核 1已审核 */
	private String isDrivingLicense;
	/* 个贷审核信息 车辆登记证 0未审核 1已审核 */
	private String isVehicleRegistration;
	/* 个贷审核信息 婚姻状况 0未审核 1已审核 */
	private String isMerry;
	/* 个贷审核信息 工作状况 0未审核 1已审核 */
	private String isWork;
	/* 个贷审核信息 户口本 0未审核 1已审核 */
	private String isAccountBook;
	

	public AppProjectPersonDetailCustomize() {
		super();
	}

	public String getBorrowContents() {
		return borrowContents;
	}

	public void setBorrowContents(String borrowContents) {
		this.borrowContents = borrowContents;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getWorkingCity() {
		return workingCity;
	}

	public void setWorkingCity(String workingCity) {
		this.workingCity = workingCity;
	}

	public String getAccountContents() {
		return accountContents;
	}

	public void setAccountContents(String accountContents) {
		this.accountContents = accountContents;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTrueName() {
		return trueName;
	}
	
	public String getTrueNameAsterisked() {
	    return AsteriskProcessUtil.getAsteriskedValue(trueName, 1, 2);
    }

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getCardNo() {
		return cardNo;
	}
	
	public String getCardNoAsterisked() {
	    return AsteriskProcessUtil.getAsteriskedValue(cardNo, 4, 10);
    }

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getDomicile() {
		return domicile;
	}

	public void setDomicile(String domicile) {
		this.domicile = domicile;
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

	public String getLitigation() {
		return litigation;
	}

	public void setLitigation(String litigation) {
		this.litigation = litigation;
	}

	public String getIsCard() {
		return isCard;
	}

	public void setIsCard(String isCard) {
		this.isCard = isCard;
	}

	public String getIsIncome() {
		return isIncome;
	}

	public void setIsIncome(String isIncome) {
		this.isIncome = isIncome;
	}

	public String getIsCredit() {
		return isCredit;
	}

	public void setIsCredit(String isCredit) {
		this.isCredit = isCredit;
	}

	public String getIsAsset() {
		return isAsset;
	}

	public void setIsAsset(String isAsset) {
		this.isAsset = isAsset;
	}

	public String getIsVehicle() {
		return isVehicle;
	}

	public void setIsVehicle(String isVehicle) {
		this.isVehicle = isVehicle;
	}

	public String getIsDrivingLicense() {
		return isDrivingLicense;
	}

	public void setIsDrivingLicense(String isDrivingLicense) {
		this.isDrivingLicense = isDrivingLicense;
	}

	public String getIsVehicleRegistration() {
		return isVehicleRegistration;
	}

	public void setIsVehicleRegistration(String isVehicleRegistration) {
		this.isVehicleRegistration = isVehicleRegistration;
	}

	public String getIsMerry() {
		return isMerry;
	}

	public void setIsMerry(String isMerry) {
		this.isMerry = isMerry;
	}

	public String getIsWork() {
		return isWork;
	}

	public void setIsWork(String isWork) {
		this.isWork = isWork;
	}

	public String getIsAccountBook() {
		return isAccountBook;
	}

	public void setIsAccountBook(String isAccountBook) {
		this.isAccountBook = isAccountBook;
	}

}
