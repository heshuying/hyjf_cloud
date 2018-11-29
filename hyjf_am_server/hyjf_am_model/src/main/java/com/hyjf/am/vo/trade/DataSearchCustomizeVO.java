package com.hyjf.am.vo.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyjf.common.util.GetDate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lisheng
 * @version DataSearchCustomizeVO, v0.1 2018/8/21 11:08
 */

public class DataSearchCustomizeVO implements Serializable {
    private String username;
    private String truename;
    private String mobile;
    private String type;
    private String plannid;
    private String name="无";
    private String account="0";
    private String  borrow_period="无";
    private String yearAccount="0";
    private String first;
    private String money="0";
    private String  reffername="无";
    private String reffermobile="无";
    private Integer userId;
    private String nid;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date reg_time;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date addtimes;
    private String regTimeT;
    private String addtimesT;

    public String getRegTimeT() {
        if (reg_time != null) {
            regTimeT = GetDate.date2Str(reg_time, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        }
        return regTimeT;
    }

    public void setRegTimeT(String regTimeT) {
        this.regTimeT = regTimeT;
    }

    public String getAddtimesT() {
        if (addtimes != null) {
            addtimesT = GetDate.date2Str(addtimes, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        }
        return addtimesT;
    }

    public void setAddtimesT(String addtimesT) {
        this.addtimesT = addtimesT;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlannid() {
        return plannid;
    }

    public void setPlannid(String plannid) {
        this.plannid = plannid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBorrow_period() {
        return borrow_period;
    }

    public void setBorrow_period(String borrow_period) {
        this.borrow_period = borrow_period;
    }

    public String getYearAccount() {
        return yearAccount;
    }

    public void setYearAccount(String yearAccount) {
        this.yearAccount = yearAccount;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getReffername() {
        return reffername;
    }

    public void setReffername(String reffername) {
        this.reffername = reffername;
    }

    public String getReffermobile() {
        return reffermobile;
    }

    public void setReffermobile(String reffermobile) {
        this.reffermobile = reffermobile;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public Date getReg_time() {
        return reg_time;
    }

    public void setReg_time(Date reg_time) {
        this.reg_time = reg_time;
    }

    public Date getAddtimes() {
        return addtimes;
    }

    public void setAddtimes(Date addtimes) {
        this.addtimes = addtimes;
    }
}
