package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WkcdBorrow implements Serializable {
    private Integer id;

    /**
     * 微可唯一标识
     *
     * @mbggenerated
     */
    private String wkcdId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 借款人姓名
     *
     * @mbggenerated
     */
    private String truename;

    /**
     * 手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 借款金额
     *
     * @mbggenerated
     */
    private BigDecimal borrowAmount;

    /**
     * 车牌
     *
     * @mbggenerated
     */
    private String carNo;

    /**
     * 车型
     *
     * @mbggenerated
     */
    private String carType;

    /**
     * 所属门店
     *
     * @mbggenerated
     */
    private String carShop;

    /**
     * 微可还款方式
     *
     * @mbggenerated
     */
    private String wkcdRepayType;

    /**
     * 借款周期（月）
     *
     * @mbggenerated
     */
    private Integer wkcdBorrowPeriod;

    /**
     * 微可审核状态 
     *
     * @mbggenerated
     */
    private String wkcdStatus;

    /**
     * 汇盈审核状态  0未审核/1已审核-通过/2已审核-不通过
     *
     * @mbggenerated
     */
    private Integer htStatus;

    /**
     * 审批意见
     *
     * @mbggenerated
     */
    private String checkDesc;

    /**
     * hyjf审核人id
     *
     * @mbggenerated
     */
    private Integer checkUser;

    /**
     * 汇盈审核时间
     *
     * @mbggenerated
     */
    private Integer checkTime;

    /**
     * 对应项目编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 年化率
     *
     * @mbggenerated
     */
    private BigDecimal apr;

    /**
     * 0：实鑫车贷没有记录还款计划  1：实鑫车贷已经记录还款计划
     *
     * @mbggenerated
     */
    private Integer sync;

    private Integer createUser;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
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