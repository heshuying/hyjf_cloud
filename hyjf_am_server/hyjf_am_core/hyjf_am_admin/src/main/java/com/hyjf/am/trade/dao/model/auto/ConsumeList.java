package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConsumeList implements Serializable {
    private Integer id;

    private String contractNo;

    private String personName;

    private String ident;

    private String loanDate;

    private BigDecimal creditAmount;

    private Integer initPay;

    private String instalmentNum;

    private String mobile;

    private String identExp;

    private String identAuth;

    private String company;

    private String bankName;

    private String accountNo;

    private BigDecimal income;

    private String address;

    private String insertTime;

    private Integer status;

    private String sex;

    private Integer release;

    private String insertDay;

    private String consumeId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName == null ? null : personName.trim();
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident == null ? null : ident.trim();
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate == null ? null : loanDate.trim();
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Integer getInitPay() {
        return initPay;
    }

    public void setInitPay(Integer initPay) {
        this.initPay = initPay;
    }

    public String getInstalmentNum() {
        return instalmentNum;
    }

    public void setInstalmentNum(String instalmentNum) {
        this.instalmentNum = instalmentNum == null ? null : instalmentNum.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getIdentExp() {
        return identExp;
    }

    public void setIdentExp(String identExp) {
        this.identExp = identExp == null ? null : identExp.trim();
    }

    public String getIdentAuth() {
        return identAuth;
    }

    public void setIdentAuth(String identAuth) {
        this.identAuth = identAuth == null ? null : identAuth.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime == null ? null : insertTime.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getRelease() {
        return release;
    }

    public void setRelease(Integer release) {
        this.release = release;
    }

    public String getInsertDay() {
        return insertDay;
    }

    public void setInsertDay(String insertDay) {
        this.insertDay = insertDay == null ? null : insertDay.trim();
    }

    public String getConsumeId() {
        return consumeId;
    }

    public void setConsumeId(String consumeId) {
        this.consumeId = consumeId == null ? null : consumeId.trim();
    }
}