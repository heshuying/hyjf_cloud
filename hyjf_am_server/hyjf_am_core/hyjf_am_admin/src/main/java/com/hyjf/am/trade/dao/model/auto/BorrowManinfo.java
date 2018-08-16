package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BorrowManinfo implements Serializable {
    private Integer id;

    private String name;

    private Integer sex;

    private Integer old;

    private Integer merry;

    private String pro;

    private String city;

    private String industry;

    private String position;

    private Integer credit;

    private String size;

    private BigDecimal business;

    private String wtime;

    private String borrowNid;

    private String borrowPreNid;

    private String cardNo;

    private String domicile;

    private String overdueTimes;

    private String overdueAmount;

    private String litigation;

    private Integer isCard;

    private Integer isIncome;

    private Integer isCredit;

    private Integer isAsset;

    private Integer isVehicle;

    private Integer isDrivingLicense;

    private Integer isVehicleRegistration;

    private Integer isMerry;

    private Integer isWork;

    private Integer isAccountBook;

    private String annualIncome;

    private String overdueReport;

    private String debtSituation;

    private String otherBorrowed;

    private String isFunds;

    private String isManaged;

    private String isAbility;

    private String isOverdue;

    private String isComplaint;

    private String isPunished;

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