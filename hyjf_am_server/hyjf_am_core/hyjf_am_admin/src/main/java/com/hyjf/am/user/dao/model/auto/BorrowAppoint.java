package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BorrowAppoint implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 预约用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 预约订单号
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 出借订单号
     *
     * @mbggenerated
     */
    private String tenderNid;

    /**
     * 项目编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 项目期限
     *
     * @mbggenerated
     */
    private Integer borrowPeriod;

    /**
     * 年华收益
     *
     * @mbggenerated
     */
    private BigDecimal borrowApr;

    /**
     * 借款金额
     *
     * @mbggenerated
     */
    private BigDecimal borrowAccount;

    /**
     * 预约金额
     *
     * @mbggenerated
     */
    private BigDecimal account;

    /**
     * 预约状态 0 预约失败 1预约成功 2取消预约 
     *
     * @mbggenerated
     */
    private Integer appointStatus;

    /**
     * 预约时间
     *
     * @mbggenerated
     */
    private Date appointTime;

    /**
     * 预约备注
     *
     * @mbggenerated
     */
    private String appointRemark;

    /**
     * 撤销时间
     *
     * @mbggenerated
     */
    private Date cancelTime;

    /**
     * 出借状态 0出借中 1投标成功 2出借失败
     *
     * @mbggenerated
     */
    private Integer tenderStatus;

    /**
     * 出借时间
     *
     * @mbggenerated
     */
    private Date tenderTime;

    /**
     * 出借备注
     *
     * @mbggenerated
     */
    private String tenderRemark;

    /**
     * 创建者id
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 更新用户名
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 添加时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getTenderNid() {
        return tenderNid;
    }

    public void setTenderNid(String tenderNid) {
        this.tenderNid = tenderNid == null ? null : tenderNid.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(Integer borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public BigDecimal getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(BigDecimal borrowApr) {
        this.borrowApr = borrowApr;
    }

    public BigDecimal getBorrowAccount() {
        return borrowAccount;
    }

    public void setBorrowAccount(BigDecimal borrowAccount) {
        this.borrowAccount = borrowAccount;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getAppointStatus() {
        return appointStatus;
    }

    public void setAppointStatus(Integer appointStatus) {
        this.appointStatus = appointStatus;
    }

    public Date getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(Date appointTime) {
        this.appointTime = appointTime;
    }

    public String getAppointRemark() {
        return appointRemark;
    }

    public void setAppointRemark(String appointRemark) {
        this.appointRemark = appointRemark == null ? null : appointRemark.trim();
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Integer getTenderStatus() {
        return tenderStatus;
    }

    public void setTenderStatus(Integer tenderStatus) {
        this.tenderStatus = tenderStatus;
    }

    public Date getTenderTime() {
        return tenderTime;
    }

    public void setTenderTime(Date tenderTime) {
        this.tenderTime = tenderTime;
    }

    public String getTenderRemark() {
        return tenderRemark;
    }

    public void setTenderRemark(String tenderRemark) {
        this.tenderRemark = tenderRemark == null ? null : tenderRemark.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}