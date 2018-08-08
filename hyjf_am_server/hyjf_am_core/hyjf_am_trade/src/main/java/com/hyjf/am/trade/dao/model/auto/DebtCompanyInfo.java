package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class DebtCompanyInfo implements Serializable {
    private Integer id;

    private String borrowNid;

    private Integer borrowPreNid;

    private String username;

    private String province;

    private String city;

    private String area;

    private Integer regCaptial;

    private String industry;

    private String litigation;

    private String creReport;

    private Integer credit;

    private Integer staff;

    private String comRegTime;

    private String otherInfo;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getBorrowPreNid() {
        return borrowPreNid;
    }

    public void setBorrowPreNid(Integer borrowPreNid) {
        this.borrowPreNid = borrowPreNid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public Integer getRegCaptial() {
        return regCaptial;
    }

    public void setRegCaptial(Integer regCaptial) {
        this.regCaptial = regCaptial;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public String getLitigation() {
        return litigation;
    }

    public void setLitigation(String litigation) {
        this.litigation = litigation == null ? null : litigation.trim();
    }

    public String getCreReport() {
        return creReport;
    }

    public void setCreReport(String creReport) {
        this.creReport = creReport == null ? null : creReport.trim();
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getStaff() {
        return staff;
    }

    public void setStaff(Integer staff) {
        this.staff = staff;
    }

    public String getComRegTime() {
        return comRegTime;
    }

    public void setComRegTime(String comRegTime) {
        this.comRegTime = comRegTime == null ? null : comRegTime.trim();
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo == null ? null : otherInfo.trim();
    }
}