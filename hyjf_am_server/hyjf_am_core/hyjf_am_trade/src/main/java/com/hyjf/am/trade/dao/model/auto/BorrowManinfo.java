package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BorrowManinfo implements Serializable {
    private Integer id;

    private String name;

    /**
     * 1男2女
     *
     * @mbggenerated
     */
    private Integer sex;

    /**
     * 年龄
     *
     * @mbggenerated
     */
    private Integer old;

    /**
     * 1已婚2未婚
     *
     * @mbggenerated
     */
    private Integer merry;

    private String pro;

    private String city;

    private String industry;

    /**
     * 岗位职业
     *
     * @mbggenerated
     */
    private String position;

    /**
     * 个人授信额度
     *
     * @mbggenerated
     */
    private Integer credit;

    /**
     * 公司规模（人数）
     *
     * @mbggenerated
     */
    private String size;

    /**
     * 月营业额
     *
     * @mbggenerated
     */
    private BigDecimal business;

    /**
     * 在现单位工作的时间
     *
     * @mbggenerated
     */
    private String wtime;

    private String borrowNid;

    private String borrowPreNid;

    /**
     * 身份证号
     *
     * @mbggenerated
     */
    private String cardNo;

    /**
     * 户籍地
     *
     * @mbggenerated
     */
    private String domicile;

    /**
     * 在平台逾期次数
     *
     * @mbggenerated
     */
    private String overdueTimes;

    /**
     * 在平台逾期金额
     *
     * @mbggenerated
     */
    private String overdueAmount;

    /**
     * 涉诉情况
     *
     * @mbggenerated
     */
    private String litigation;

    /**
     * 个贷审核信息 身份证 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isCard;

    /**
     * 个贷审核信息 收入状况 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isIncome;

    /**
     * 个贷审核信息 信用状况 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isCredit;

    /**
     * 个贷审核信息 资产状况 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isAsset;

    /**
     * 个贷审核信息 车辆状况 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isVehicle;

    /**
     * 个贷审核信息 行驶证 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isDrivingLicense;

    /**
     * 个贷审核信息 车辆登记证 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isVehicleRegistration;

    /**
     * 个贷审核信息 婚姻状况 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isMerry;

    /**
     * 个贷审核信息 工作状况 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isWork;

    /**
     * 个贷审核信息 户口本 0未审核 1已审核
     *
     * @mbggenerated
     */
    private Integer isAccountBook;

    /**
     * 个人年收入:10万以内；10万以上
     *
     * @mbggenerated
     */
    private String annualIncome;

    /**
     * 征信报告逾期情况:暂未提供；无；已处理
     *
     * @mbggenerated
     */
    private String overdueReport;

    /**
     * 重大负债状况:无
     *
     * @mbggenerated
     */
    private String debtSituation;

    /**
     * 其他平台借款情况:无
     *
     * @mbggenerated
     */
    private String otherBorrowed;

    /**
     * 借款资金运用情况：不正常,正常
     *
     * @mbggenerated
     */
    private String isFunds;

    /**
     * 借款人经营状况及财务状况：不正常,正常
     *
     * @mbggenerated
     */
    private String isManaged;

    /**
     * 借款人还款能力变化情况：不正常,正常
     *
     * @mbggenerated
     */
    private String isAbility;

    /**
     * 借款人逾期情况：暂无,有
     *
     * @mbggenerated
     */
    private String isOverdue;

    /**
     * 借款人涉诉情况：暂无,有
     *
     * @mbggenerated
     */
    private String isComplaint;

    /**
     * 借款人受行政处罚情况：暂无,有
     *
     * @mbggenerated
     */
    private String isPunished;

    /**
     * 借款人地址
     *
     * @mbggenerated
     */
    private String address;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getOld() {
        return old;
    }

    public void setOld(Integer old) {
        this.old = old;
    }

    public Integer getMerry() {
        return merry;
    }

    public void setMerry(Integer merry) {
        this.merry = merry;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro == null ? null : pro.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public BigDecimal getBusiness() {
        return business;
    }

    public void setBusiness(BigDecimal business) {
        this.business = business;
    }

    public String getWtime() {
        return wtime;
    }

    public void setWtime(String wtime) {
        this.wtime = wtime == null ? null : wtime.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public String getBorrowPreNid() {
        return borrowPreNid;
    }

    public void setBorrowPreNid(String borrowPreNid) {
        this.borrowPreNid = borrowPreNid == null ? null : borrowPreNid.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile == null ? null : domicile.trim();
    }

    public String getOverdueTimes() {
        return overdueTimes;
    }

    public void setOverdueTimes(String overdueTimes) {
        this.overdueTimes = overdueTimes == null ? null : overdueTimes.trim();
    }

    public String getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(String overdueAmount) {
        this.overdueAmount = overdueAmount == null ? null : overdueAmount.trim();
    }

    public String getLitigation() {
        return litigation;
    }

    public void setLitigation(String litigation) {
        this.litigation = litigation == null ? null : litigation.trim();
    }

    public Integer getIsCard() {
        return isCard;
    }

    public void setIsCard(Integer isCard) {
        this.isCard = isCard;
    }

    public Integer getIsIncome() {
        return isIncome;
    }

    public void setIsIncome(Integer isIncome) {
        this.isIncome = isIncome;
    }

    public Integer getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(Integer isCredit) {
        this.isCredit = isCredit;
    }

    public Integer getIsAsset() {
        return isAsset;
    }

    public void setIsAsset(Integer isAsset) {
        this.isAsset = isAsset;
    }

    public Integer getIsVehicle() {
        return isVehicle;
    }

    public void setIsVehicle(Integer isVehicle) {
        this.isVehicle = isVehicle;
    }

    public Integer getIsDrivingLicense() {
        return isDrivingLicense;
    }

    public void setIsDrivingLicense(Integer isDrivingLicense) {
        this.isDrivingLicense = isDrivingLicense;
    }

    public Integer getIsVehicleRegistration() {
        return isVehicleRegistration;
    }

    public void setIsVehicleRegistration(Integer isVehicleRegistration) {
        this.isVehicleRegistration = isVehicleRegistration;
    }

    public Integer getIsMerry() {
        return isMerry;
    }

    public void setIsMerry(Integer isMerry) {
        this.isMerry = isMerry;
    }

    public Integer getIsWork() {
        return isWork;
    }

    public void setIsWork(Integer isWork) {
        this.isWork = isWork;
    }

    public Integer getIsAccountBook() {
        return isAccountBook;
    }

    public void setIsAccountBook(Integer isAccountBook) {
        this.isAccountBook = isAccountBook;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome == null ? null : annualIncome.trim();
    }

    public String getOverdueReport() {
        return overdueReport;
    }

    public void setOverdueReport(String overdueReport) {
        this.overdueReport = overdueReport == null ? null : overdueReport.trim();
    }

    public String getDebtSituation() {
        return debtSituation;
    }

    public void setDebtSituation(String debtSituation) {
        this.debtSituation = debtSituation == null ? null : debtSituation.trim();
    }

    public String getOtherBorrowed() {
        return otherBorrowed;
    }

    public void setOtherBorrowed(String otherBorrowed) {
        this.otherBorrowed = otherBorrowed == null ? null : otherBorrowed.trim();
    }

    public String getIsFunds() {
        return isFunds;
    }

    public void setIsFunds(String isFunds) {
        this.isFunds = isFunds == null ? null : isFunds.trim();
    }

    public String getIsManaged() {
        return isManaged;
    }

    public void setIsManaged(String isManaged) {
        this.isManaged = isManaged == null ? null : isManaged.trim();
    }

    public String getIsAbility() {
        return isAbility;
    }

    public void setIsAbility(String isAbility) {
        this.isAbility = isAbility == null ? null : isAbility.trim();
    }

    public String getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(String isOverdue) {
        this.isOverdue = isOverdue == null ? null : isOverdue.trim();
    }

    public String getIsComplaint() {
        return isComplaint;
    }

    public void setIsComplaint(String isComplaint) {
        this.isComplaint = isComplaint == null ? null : isComplaint.trim();
    }

    public String getIsPunished() {
        return isPunished;
    }

    public void setIsPunished(String isPunished) {
        this.isPunished = isPunished == null ? null : isPunished.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}