package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class DebtApicron implements Serializable {
    private Integer id;

    /**
     * 唯一标识
     *
     * @mbggenerated
     */
    private String nid;

    /**
     * 计划编号
     *
     * @mbggenerated
     */
    private String planNid;

    /**
     * 借款人用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 借款人用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 项目编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 当前还款期数
     *
     * @mbggenerated
     */
    private Integer periodNow;

    /**
     * 放款状态0未完成1已完成2放款执行中9放款失败
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 0放款1还款2清算
     *
     * @mbggenerated
     */
    private Integer apiType;

    /**
     * 计算状态
     *
     * @mbggenerated
     */
    private Integer webStatus;

    /**
     * 还款状态0未完成1已完成
     *
     * @mbggenerated
     */
    private Integer repayStatus;

    /**
     * 债转还款状态
     *
     * @mbggenerated
     */
    private Integer creditRepayStatus;

    /**
     * 清算状态 0待清算 1已清算 9清算失败
     *
     * @mbggenerated
     */
    private Integer liquidatesStatus;

    /**
     * 创建用户id
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建用户名
     *
     * @mbggenerated
     */
    private String createUserName;

    /**
     * 更新用户名
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 更新用户名
     *
     * @mbggenerated
     */
    private String updateUserName;

    /**
     * 创建时间
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

    /**
     * 错误信息
     *
     * @mbggenerated
     */
    private String data;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid == null ? null : nid.trim();
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
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

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getPeriodNow() {
        return periodNow;
    }

    public void setPeriodNow(Integer periodNow) {
        this.periodNow = periodNow;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getApiType() {
        return apiType;
    }

    public void setApiType(Integer apiType) {
        this.apiType = apiType;
    }

    public Integer getWebStatus() {
        return webStatus;
    }

    public void setWebStatus(Integer webStatus) {
        this.webStatus = webStatus;
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public Integer getCreditRepayStatus() {
        return creditRepayStatus;
    }

    public void setCreditRepayStatus(Integer creditRepayStatus) {
        this.creditRepayStatus = creditRepayStatus;
    }

    public Integer getLiquidatesStatus() {
        return liquidatesStatus;
    }

    public void setLiquidatesStatus(Integer liquidatesStatus) {
        this.liquidatesStatus = liquidatesStatus;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
}