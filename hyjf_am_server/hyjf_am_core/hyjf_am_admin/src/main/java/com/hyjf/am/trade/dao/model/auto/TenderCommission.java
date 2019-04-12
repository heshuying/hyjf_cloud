package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TenderCommission implements Serializable {
    private Integer id;

    private String borrowNid;

    /**
     * 投资类别 1：直投类，2：汇计划
     *
     * @mbggenerated
     */
    private Integer tenderType;

    /**
     * 充值订单号
     *
     * @mbggenerated
     */
    private String ordid;

    private Integer tenderId;

    /**
     * 获得提成的uid
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 获得提成的部门id
     *
     * @mbggenerated
     */
    private Integer departmentId;

    /**
     * 投资人的uid
     *
     * @mbggenerated
     */
    private Integer tenderUserId;

    /**
     * 投资人的部门id
     *
     * @mbggenerated
     */
    private Integer tenderDepartmentId;

    /**
     * 投资金额
     *
     * @mbggenerated
     */
    private BigDecimal accountTender;

    /**
     * 投资时间
     *
     * @mbggenerated
     */
    private Integer tenderTime;

    /**
     * 提成发放时间
     *
     * @mbggenerated
     */
    private Integer sendTime;

    /**
     * 提成
     *
     * @mbggenerated
     */
    private BigDecimal commission;

    /**
     * 0:未发放;1:已发放;100:删除
     *
     * @mbggenerated
     */
    private Integer status;

    private String remark;

    /**
     * 计算时间
     *
     * @mbggenerated
     */
    private Integer computeTime;

    /**
     * 地区ID
     *
     * @mbggenerated
     */
    private Integer regionId;

    /**
     * 分公司ID
     *
     * @mbggenerated
     */
    private Integer branchId;

    /**
     * 地区名
     *
     * @mbggenerated
     */
    private String regionName;

    /**
     * 分公司名
     *
     * @mbggenerated
     */
    private String branchName;

    /**
     * 部门名
     *
     * @mbggenerated
     */
    private String departmentName;

    /**
     * 电子账号
     *
     * @mbggenerated
     */
    private String accountId;

    /**
     * 返现时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private String logOrderId;

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

    public Integer getTenderType() {
        return tenderType;
    }

    public void setTenderType(Integer tenderType) {
        this.tenderType = tenderType;
    }

    public String getOrdid() {
        return ordid;
    }

    public void setOrdid(String ordid) {
        this.ordid = ordid == null ? null : ordid.trim();
    }

    public Integer getTenderId() {
        return tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getTenderUserId() {
        return tenderUserId;
    }

    public void setTenderUserId(Integer tenderUserId) {
        this.tenderUserId = tenderUserId;
    }

    public Integer getTenderDepartmentId() {
        return tenderDepartmentId;
    }

    public void setTenderDepartmentId(Integer tenderDepartmentId) {
        this.tenderDepartmentId = tenderDepartmentId;
    }

    public BigDecimal getAccountTender() {
        return accountTender;
    }

    public void setAccountTender(BigDecimal accountTender) {
        this.accountTender = accountTender;
    }

    public Integer getTenderTime() {
        return tenderTime;
    }

    public void setTenderTime(Integer tenderTime) {
        this.tenderTime = tenderTime;
    }

    public Integer getSendTime() {
        return sendTime;
    }

    public void setSendTime(Integer sendTime) {
        this.sendTime = sendTime;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getComputeTime() {
        return computeTime;
    }

    public void setComputeTime(Integer computeTime) {
        this.computeTime = computeTime;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLogOrderId() {
        return logOrderId;
    }

    public void setLogOrderId(String logOrderId) {
        this.logOrderId = logOrderId;
    }
}