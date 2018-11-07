package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DebtAccedeCommission implements Serializable {
    private Integer id;

    /**
     * 计划编号
     *
     * @mbggenerated
     */
    private String planNid;

    /**
     * 计划期限即计划锁定期限
     *
     * @mbggenerated
     */
    private Integer planLockPeriod;

    /**
     * 充值订单号
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 获得提成的userId
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 提成人用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 是否是51老用户(0:否,1:是)
     *
     * @mbggenerated
     */
    private Integer is51;

    /**
     * 地区ID
     *
     * @mbggenerated
     */
    private Integer regionId;

    /**
     * 地区名
     *
     * @mbggenerated
     */
    private String regionName;

    /**
     * 分公司ID
     *
     * @mbggenerated
     */
    private Integer branchId;

    /**
     * 分公司名
     *
     * @mbggenerated
     */
    private String branchName;

    /**
     * 获得提成的部门id
     *
     * @mbggenerated
     */
    private Integer departmentId;

    /**
     * 部门名
     *
     * @mbggenerated
     */
    private String departmentName;

    /**
     * 计划加入订单号
     *
     * @mbggenerated
     */
    private String accedeOrderId;

    /**
     * 加入用户userId
     *
     * @mbggenerated
     */
    private Integer accedeUserId;

    /**
     * 加入用户名
     *
     * @mbggenerated
     */
    private String accedeUserName;

    /**
     * 加入人的部门id
     *
     * @mbggenerated
     */
    private Integer accedeDepartmentId;

    /**
     * 加入金额
     *
     * @mbggenerated
     */
    private BigDecimal accedeAccount;

    /**
     * 加入时间
     *
     * @mbggenerated
     */
    private Integer accedeTime;

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

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 计算时间
     *
     * @mbggenerated
     */
    private Integer computeTime;

    /**
     * 返现时间
     *
     * @mbggenerated
     */
    private Integer returnTime;

    /**
     * 返现操作用户ID
     *
     * @mbggenerated
     */
    private Integer returnUserId;

    /**
     * 返现操作用户名
     *
     * @mbggenerated
     */
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