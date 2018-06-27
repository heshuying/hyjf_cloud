package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DebtUserInfo implements Serializable {
    private Integer id;

    private String name;

    private Integer sex;

    private Integer old;

    private Integer merry;

    private String pro;

    private String city;

    private String industry;

    private Integer credit;

    private String size;

    private BigDecimal business;

    private String wtime;

    private String borrowNid;

    private Integer borrowPreNid;

    private String position;

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

    public Integer getBorrowPreNid() {
        return borrowPreNid;
    }

    public void setBorrowPreNid(Integer borrowPreNid) {
        this.borrowPreNid = borrowPreNid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }
}