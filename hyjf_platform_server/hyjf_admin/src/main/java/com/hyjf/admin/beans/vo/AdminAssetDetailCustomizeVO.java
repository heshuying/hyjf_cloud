/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import java.io.Serializable;

import com.hyjf.am.vo.BaseVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author libin
 * @version AdminAssetDetailCustomizeVO.java, v0.1 2018年7月18日 上午10:28:55
 */
public class AdminAssetDetailCustomizeVO extends BaseVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "资产编号")
	private String assetId;

	@ApiModelProperty(value = "姓名")
	private String truename;

	@ApiModelProperty(value = "性别")
	private String sex;

	@ApiModelProperty(value = "年龄")
	private String age;

	@ApiModelProperty(value = "婚姻状况")
	private String marriage;

	@ApiModelProperty(value = "工作城市")
	private String workCity;

	@ApiModelProperty(value = "岗位职业")
	private String position;

	@ApiModelProperty(value = "身份证号")
	private String idcard;

	@ApiModelProperty(value = "户籍地")
	private String domicile;

	@ApiModelProperty(value = "信用评级")
	private String creditLevel;

	@ApiModelProperty(value = "借款用途")
	private String useage;

	@ApiModelProperty(value = "月薪收入")
	private String monthlyIncome;

	@ApiModelProperty(value = "第一还款来源")
	private String firstPayment;

	@ApiModelProperty(value = "第二还款来源")
	private String secondPayment;

	@ApiModelProperty(value = "费用说明")
	private String costIntrodution;

	@ApiModelProperty(value = "在平台逾期次数")
	private String overdueTimes;

	@ApiModelProperty(value = "在平台逾期金额")
	private String overdueAmount;

	@ApiModelProperty(value = "涉诉情况")
	private String litigation;

	@ApiModelProperty(value = "借款编号")
    private String borrowNid;

	@ApiModelProperty(value = "计划编号")
    private String planNid;

	@ApiModelProperty(value = "状态")
    private Integer status;
    
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getWorkCity() {
		return workCity;
	}
	public void setWorkCity(String workCity) {
		this.workCity = workCity;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getDomicile() {
		return domicile;
	}
	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}
	public String getCreditLevel() {
		return creditLevel;
	}
	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}
	public String getUseage() {
		return useage;
	}
	public void setUseage(String useage) {
		this.useage = useage;
	}
	public String getMonthlyIncome() {
		return monthlyIncome;
	}
	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	public String getFirstPayment() {
		return firstPayment;
	}
	public void setFirstPayment(String firstPayment) {
		this.firstPayment = firstPayment;
	}
	public String getSecondPayment() {
		return secondPayment;
	}
	public void setSecondPayment(String secondPayment) {
		this.secondPayment = secondPayment;
	}
	public String getCostIntrodution() {
		return costIntrodution;
	}
	public void setCostIntrodution(String costIntrodution) {
		this.costIntrodution = costIntrodution;
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
	public String getBorrowNid() {
		return borrowNid;
	}
	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}
	public String getPlanNid() {
		return planNid;
	}
	public void setPlanNid(String planNid) {
		this.planNid = planNid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
