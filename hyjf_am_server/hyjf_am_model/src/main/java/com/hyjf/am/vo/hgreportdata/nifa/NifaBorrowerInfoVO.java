/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.hgreportdata.nifa;

import com.hyjf.am.vo.hgreportdata.BaseHgDataReportVO;

import java.io.Serializable;

/**
 * @author PC-LIUSHOUYI
 * @version NifaBorrowerInfo, v0.1 2018/11/27 15:14
 */
public class NifaBorrowerInfoVO extends BaseHgDataReportVO implements Serializable {
    /**
     * 项目唯一编号
     */
    private String projectNo;
    /**
     * 借款人类型
     */
    private String borrowerType;
    /**
     * 借款人ID
     */
    private String userId;
    /**
     * 证件类型
     */
    private String cardType;
    /**
     * 证件号码
     */
    private String cardNo;
    /**
     * 借款人性别
     */
    private String sex;
    /**
     * 借款人年平均收入
     */
    private String annualIncome;
    /**
     * 借款人主要收入来源
     */
    private String mainIncome;
    /**
     * 职业类型
     */
    private String position;
    /**
     * 所属地区
     */
    private String area;
    /**
     * 实缴资本
     */
    private String contributedCapital;
    /**
     * 注册资本
     */
    private String regCaptial;
    /**
     * 所属行业
     */
    private String industry;
    /**
     * 机构成立时间
     */
    private String comRegTime;
    /**
     * 收款账户开户行银行名称
     */
    private String bank;
    /**
     * 收款账户开户行所在地区
     */
    private String bankOpenArea;
    /**
     * 借款人信用评级
     */
    private String borrowLevel;
    /**
     * 借款人累计借款次数
     */
    private String borrowCounts;

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getBorrowerType() {
        return borrowerType;
    }

    public void setBorrowerType(String borrowerType) {
        this.borrowerType = borrowerType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getMainIncome() {
        return mainIncome;
    }

    public void setMainIncome(String mainIncome) {
        this.mainIncome = mainIncome;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getContributedCapital() {
        return contributedCapital;
    }

    public void setContributedCapital(String contributedCapital) {
        this.contributedCapital = contributedCapital;
    }

    public String getRegCaptial() {
        return regCaptial;
    }

    public void setRegCaptial(String regCaptial) {
        this.regCaptial = regCaptial;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getComRegTime() {
        return comRegTime;
    }

    public void setComRegTime(String comRegTime) {
        this.comRegTime = comRegTime;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankOpenArea() {
        return bankOpenArea;
    }

    public void setBankOpenArea(String bankOpenArea) {
        this.bankOpenArea = bankOpenArea;
    }

    public String getBorrowLevel() {
        return borrowLevel;
    }

    public void setBorrowLevel(String borrowLevel) {
        this.borrowLevel = borrowLevel;
    }

    public String getBorrowCounts() {
        return borrowCounts;
    }

    public void setBorrowCounts(String borrowCounts) {
        this.borrowCounts = borrowCounts;
    }
}
