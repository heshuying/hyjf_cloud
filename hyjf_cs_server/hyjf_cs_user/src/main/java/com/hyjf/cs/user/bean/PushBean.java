package com.hyjf.cs.user.bean;

import java.io.Serializable;

public class PushBean implements Serializable {

	private static final long serialVersionUID = -9133593440657771846L;

	// 下面是必须参数
    private String assetId;

//    private String instCode;
//
//    private Integer assetType;

    private String truename;

    private String idcard;

    private Long account;

    private Integer borrowPeriod;

    private String borrowStyle;
    
    // 返回参数
    private String retCode;
    
    private String retMsg;
    
    
    // 可选参数
    private Integer sex;

    private Integer age;

    private Integer marriage;
    
    private Integer isMonth = 0;// 不传默认天标

    // 受托支付标志
    private Integer entrustedFlg;
    private String entrustedAccountId;

    private String workCity;

    private String position;

    private String domicile;

    private String creditLevel;

    private String useage;

    private String monthlyIncome;

    private String firstPayment;

    private String secondPayment;

    private String costIntrodution;

    private String assetInfo;
    
    /** * 信批需求新增字段(选传)目前只支持个人 start */
    /**
     * (个人)年收入:10万以内；10万以上
     */
    private String annualIncome;
    /**
     * (个人)征信报告逾期情况:暂未提供；无；已处理
     */
    private String overdueReport;
    /**
     * (个人)重大负债状况:无;有
     */
    private String debtSituation;
    /**
     * (个人)其他平台借款情况:无;有
     */
    private String otherBorrowed;
    /**
     * (个人)借款资金运用情况：不正常,正常
     */
    private String isFunds;
    /**
     * (个人)借款方经营状况及财务状况：不正常,正常
     */
    private String isManaged;
    /**
     * (个人)借款方还款能力变化情况：不正常,正常
     */
    private String isAbility;
    /**
     * (个人)借款人逾期情况：暂无,有
     */
    private String isOverdue;
    /**
     * (个人)借款人涉诉情况：暂无,有
     */
    private String isComplaint;
    /**
     * (个人)借款人受行政处罚情况：暂无,有
     */
    private String isPunished;
    /** * 信批需求新增字段(选传)目前只支持个人 end */

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

//	public String getInstCode() {
//		return instCode;
//	}
//
//	public void setInstCode(String instCode) {
//		this.instCode = instCode;
//	}
//
//	public Integer getAssetType() {
//		return assetType;
//	}
//
//	public void setAssetType(Integer assetType) {
//		this.assetType = assetType;
//	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Long getAccount() {
		return account;
	}

	public void setAccount(Long account) {
		this.account = account;
	}

	public Integer getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(Integer borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getMarriage() {
		return marriage;
	}

	public void setMarriage(Integer marriage) {
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

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getAssetInfo() {
		return assetInfo;
	}

	public void setAssetInfo(String assetInfo) {
		this.assetInfo = assetInfo;
	}

	public Integer getEntrustedFlg() {
		return entrustedFlg;
	}

	public void setEntrustedFlg(Integer entrustedFlg) {
		this.entrustedFlg = entrustedFlg;
	}

	public String getEntrustedAccountId() {
		return entrustedAccountId;
	}

	public void setEntrustedAccountId(String entrustedAccountId) {
		this.entrustedAccountId = entrustedAccountId;
	}

	public Integer getIsMonth() {
		return isMonth;
	}

	public void setIsMonth(Integer isMonth) {
		this.isMonth = isMonth;
	}

	public String getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getOverdueReport() {
		return overdueReport;
	}

	public void setOverdueReport(String overdueReport) {
		this.overdueReport = overdueReport;
	}

	public String getDebtSituation() {
		return debtSituation;
	}

	public void setDebtSituation(String debtSituation) {
		this.debtSituation = debtSituation;
	}

	public String getOtherBorrowed() {
		return otherBorrowed;
	}

	public void setOtherBorrowed(String otherBorrowed) {
		this.otherBorrowed = otherBorrowed;
	}

	public String getIsFunds() {
		return isFunds;
	}

	public void setIsFunds(String isFunds) {
		this.isFunds = isFunds;
	}

	public String getIsManaged() {
		return isManaged;
	}

	public void setIsManaged(String isManaged) {
		this.isManaged = isManaged;
	}

	public String getIsAbility() {
		return isAbility;
	}

	public void setIsAbility(String isAbility) {
		this.isAbility = isAbility;
	}

	public String getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}

	public String getIsComplaint() {
		return isComplaint;
	}

	public void setIsComplaint(String isComplaint) {
		this.isComplaint = isComplaint;
	}

	public String getIsPunished() {
		return isPunished;
	}

	public void setIsPunished(String isPunished) {
		this.isPunished = isPunished;
	}
}
