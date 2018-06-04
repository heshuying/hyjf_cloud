package com.hyjf.am.borrow.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class BorrowConsume implements Serializable {
    private Integer id;

    private String consumeClass;

    private String name;

    private String projectType;

    private String userName;

    private String companyOrPersonal;

    private String username;

    private String province;

    private String city;

    private String area;

    private String comRegTime;

    private Integer regCaptial;

    private Date updatetime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConsumeClass() {
        return consumeClass;
    }

    public void setConsumeClass(String consumeClass) {
        this.consumeClass = consumeClass == null ? null : consumeClass.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getCompanyOrPersonal() {
        return companyOrPersonal;
    }

    public void setCompanyOrPersonal(String companyOrPersonal) {
        this.companyOrPersonal = companyOrPersonal == null ? null : companyOrPersonal.trim();
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

    public String getComRegTime() {
        return comRegTime;
    }

    public void setComRegTime(String comRegTime) {
        this.comRegTime = comRegTime == null ? null : comRegTime.trim();
    }

    public Integer getRegCaptial() {
        return regCaptial;
    }

    public void setRegCaptial(Integer regCaptial) {
        this.regCaptial = regCaptial;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}