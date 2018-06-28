package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DebtAccedeCommission implements Serializable {
    private Integer id;

    private String planNid;

    private Integer planLockPeriod;

    private String orderId;

    private Integer userId;

    private String userName;

    private Integer is51;

    private Integer regionId;

    private String regionName;

    private Integer branchId;

    private String branchName;

    private Integer departmentId;

    private String departmentName;

    private String accedeOrderId;

    private Integer accedeUserId;

    private String accedeUserName;

    private Integer accedeDepartmentId;

    private BigDecimal accedeAccount;

    private Integer accedeTime;

    private BigDecimal commission;

    private Byte status;

    private String remark;

    private Integer computeTime;

    private Integer returnTime;

    private Integer returnUserId;

    private String returnUserName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
    }

    public Integer getPlanLockPeriod() {
        return planLockPeriod;
    }

    public void setPlanLockPeriod(Integer planLockPeriod) {
        this.planLockPeriod = planLockPeriod;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
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

    public Integer getIs51() {
        return is51;
    }

    public void setIs51(Integer is51) {
        this.is51 = is51;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId == null ? null : accedeOrderId.trim();
    }

    public Integer getAccedeUserId() {
        return accedeUserId;
    }

    public void setAccedeUserId(Integer accedeUserId) {
        this.accedeUserId = accedeUserId;
    }

    public String getAccedeUserName() {
        return accedeUserName;
    }

    public void setAccedeUserName(String accedeUserName) {
        this.accedeUserName = accedeUserName == null ? null : accedeUserName.trim();
    }

    public Integer getAccedeDepartmentId() {
        return accedeDepartmentId;
    }

    public void setAccedeDepartmentId(Integer accedeDepartmentId) {
        this.accedeDepartmentId = accedeDepartmentId;
    }

    public BigDecimal getAccedeAccount() {
        return accedeAccount;
    }

    public void setAccedeAccount(BigDecimal accedeAccount) {
        this.accedeAccount = accedeAccount;
    }

    public Integer getAccedeTime() {
        return accedeTime;
    }

    public void setAccedeTime(Integer accedeTime) {
        this.accedeTime = accedeTime;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
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

    public Integer getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Integer returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getReturnUserId() {
        return returnUserId;
    }

    public void setReturnUserId(Integer returnUserId) {
        this.returnUserId = returnUserId;
    }

    public String getReturnUserName() {
        return returnUserName;
    }

    public void setReturnUserName(String returnUserName) {
        this.returnUserName = returnUserName == null ? null : returnUserName.trim();
    }
}