package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WkcdBorrow implements Serializable {
    private Integer id;

    private String wkcdId;

    private Integer userId;

    private String userName;

    private String truename;

    private String mobile;

    private BigDecimal borrowAmount;

    private String carNo;

    private String carType;

    private String carShop;

    private String wkcdRepayType;

    private Integer wkcdBorrowPeriod;

    private String wkcdStatus;

    private Integer htStatus;

    private String checkDesc;

    private Integer checkUser;

    private Integer checkTime;

    private String borrowNid;

    private BigDecimal apr;

    private Integer sync;

    private Integer createUser;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWkcdId() {
        return wkcdId;
    }

    public void setWkcdId(String wkcdId) {
        this.wkcdId = wkcdId == null ? null : wkcdId.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename == null ? null : truename.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public BigDecimal getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(BigDecimal borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo == null ? null : carNo.trim();
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType == null ? null : carType.trim();
    }

    public String getCarShop() {
        return carShop;
    }

    public void setCarShop(String carShop) {
        this.carShop = carShop == null ? null : carShop.trim();
    }

    public String getWkcdRepayType() {
        return wkcdRepayType;
    }

    public void setWkcdRepayType(String wkcdRepayType) {
        this.wkcdRepayType = wkcdRepayType == null ? null : wkcdRepayType.trim();
    }

    public Integer getWkcdBorrowPeriod() {
        return wkcdBorrowPeriod;
    }

    public void setWkcdBorrowPeriod(Integer wkcdBorrowPeriod) {
        this.wkcdBorrowPeriod = wkcdBorrowPeriod;
    }

    public String getWkcdStatus() {
        return wkcdStatus;
    }

    public void setWkcdStatus(String wkcdStatus) {
        this.wkcdStatus = wkcdStatus == null ? null : wkcdStatus.trim();
    }

    public Integer getHtStatus() {
        return htStatus;
    }

    public void setHtStatus(Integer htStatus) {
        this.htStatus = htStatus;
    }

    public String getCheckDesc() {
        return checkDesc;
    }

    public void setCheckDesc(String checkDesc) {
        this.checkDesc = checkDesc == null ? null : checkDesc.trim();
    }

    public Integer getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(Integer checkUser) {
        this.checkUser = checkUser;
    }

    public Integer getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Integer checkTime) {
        this.checkTime = checkTime;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public Integer getSync() {
        return sync;
    }

    public void setSync(Integer sync) {
        this.sync = sync;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}