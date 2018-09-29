package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

public class AssetDetailCustomizeVO extends BaseVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 资产编号
	 */
	private String assetId;
	/**
	 * 姓名
	 */
	private String truename;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 年龄
	 */
	private String age;
	/**
	 * 婚姻状况
	 */
	private String marriage;
	/**
	 * 工作城市
	 */
	private String workCity;
	/**
	 * 岗位职业
	 */
	private String position;
	/**
	 * 身份证号
	 */
	private String idcard;
	/**
	 * 户籍地
	 */
	private String domicile;
	/**
	 * 信用评级
	 */
	private String creditLevel;
	/**
	 * 借款用途
	 */
	private String useage;
	/**
	 * 月薪收入
	 */
	private String monthlyIncome;
	/**
	 * 第一还款来源
	 */
	private String firstPayment;
	/**
	 * 第二还款来源
	 */
	private String secondPayment;
	/**
	 * 费用说明
	 */
	private String costIntrodution;
	/**
	 * 在平台逾期次数
	 */
	private String overdueTimes;
	/**
	 * 在平台逾期金额
	 */
	private String overdueAmount;
	/**
	 * 涉诉情况
	 */
	private String litigation;


    private String borrowNid;

    private String planNid;

    private Integer status;
    
	/**
	 * assetId
	 * @return the assetId
	 */
	public String getAssetId() {
		return assetId;
	}
	/**
	 * @param assetId the assetId to set
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	/**
	 * truename
	 * @return the truename
	 */
	public String getTruename() {
		return truename;
	}
	/**
	 * @param truename the truename to set
	 */
	public void setTruename(String truename) {
		this.truename = truename;
	}
	/**
	 * sex
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * age
	 * @return the age
	 */
	public String getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}
	/**
	 * marriage
	 * @return the marriage
	 */
	public String getMarriage() {
		return marriage;
	}
	/**
	 * @param marriage the marriage to set
	 */
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	/**
	 * workCity
	 * @return the workCity
	 */
	public String getWorkCity() {
		return workCity;
	}
	/**
	 * @param workCity the workCity to set
	 */
	public void setWorkCity(String workCity) {
		this.workCity = workCity;
	}
	/**
	 * position
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * idcard
	 * @return the idcard
	 */
	public String getIdcard() {
		return idcard;
	}
	/**
	 * @param idcard the idcard to set
	 */
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	/**
	 * domicile
	 * @return the domicile
	 */
	public String getDomicile() {
		return domicile;
	}
	/**
	 * @param domicile the domicile to set
	 */
	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}
	/**
	 * creditLevel
	 * @return the creditLevel
	 */
	public String getCreditLevel() {
		return creditLevel;
	}
	/**
	 * @param creditLevel the creditLevel to set
	 */
	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}
	/**
	 * useage
	 * @return the useage
	 */
	public String getUseage() {
		return useage;
	}
	/**
	 * @param useage the useage to set
	 */
	public void setUseage(String useage) {
		this.useage = useage;
	}
	/**
	 * monthlyIncome
	 * @return the monthlyIncome
	 */
	public String getMonthlyIncome() {
		return monthlyIncome;
	}
	/**
	 * @param monthlyIncome the monthlyIncome to set
	 */
	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	/**
	 * firstPayment
	 * @return the firstPayment
	 */
	public String getFirstPayment() {
		return firstPayment;
	}
	/**
	 * @param firstPayment the firstPayment to set
	 */
	public void setFirstPayment(String firstPayment) {
		this.firstPayment = firstPayment;
	}
	/**
	 * secondPayment
	 * @return the secondPayment
	 */
	public String getSecondPayment() {
		return secondPayment;
	}
	/**
	 * @param secondPayment the secondPayment to set
	 */
	public void setSecondPayment(String secondPayment) {
		this.secondPayment = secondPayment;
	}
	/**
	 * costIntrodution
	 * @return the costIntrodution
	 */
	public String getCostIntrodution() {
		return costIntrodution;
	}
	/**
	 * @param costIntrodution the costIntrodution to set
	 */
	public void setCostIntrodution(String costIntrodution) {
		this.costIntrodution = costIntrodution;
	}
	/**
	 * overdueTimes
	 * @return the overdueTimes
	 */
	public String getOverdueTimes() {
		return overdueTimes;
	}
	/**
	 * @param overdueTimes the overdueTimes to set
	 */
	public void setOverdueTimes(String overdueTimes) {
		this.overdueTimes = overdueTimes;
	}
	/**
	 * overdueAmount
	 * @return the overdueAmount
	 */
	public String getOverdueAmount() {
		return overdueAmount;
	}
	/**
	 * @param overdueAmount the overdueAmount to set
	 */
	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}
	/**
	 * litigation
	 * @return the litigation
	 */
	public String getLitigation() {
		return litigation;
	}
	/**
	 * @param litigation the litigation to set
	 */
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
