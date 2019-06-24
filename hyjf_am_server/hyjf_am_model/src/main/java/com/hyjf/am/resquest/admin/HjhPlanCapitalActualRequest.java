/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实际资金计划
 *
 * @author PC-LIUSHOUYI
 * @version HjhPlanCapitalActualRequest, v0.1 2019/4/2 14:37
 */
public class HjhPlanCapitalActualRequest extends BasePage implements Serializable {

    /**
     * 时间查询条件开始
     */
    private String dateFromSrch;

    /**
     * 时间查询条件结束
     */
    private String dateToSrch;

    /**
     * 智投编号查询条件
     */
    private String planNidSrch;

    /**
     * 智投名称查询条件
     */
    private String planNameSrch;

    /**
     * 锁定期查询条件
     */
    private String lockPeriodSrch;

    private String id;

    /**
     * 日期
     */
    private Date date;

    /**
     * 智投编号
     */
    private String planNid;

    /**
     * 智投名称
     */
    private String planName;

    /**
     * 锁定期
     */
    private Integer lockPeriod;

    /**
     * 当日新增债转额
     */
    private BigDecimal addCreditAccount;

    /**
     * 当日发起债转额:当日已承接+当日未承接
     */
    private BigDecimal createCreditAccount;

    /**
     * 当日已承接债转额
     */
    private BigDecimal usedCreditAccount;

    /**
     * 当日未承接额
     */
    private BigDecimal leaveCreditAccount;

    /**
     * 当日新增复投额:当日可复投额-昨日未复投额
     */
    private BigDecimal addReinvestAccount;

    /**
     * 当日可复投额:当日已复投额+当日未复投额
     */
    private BigDecimal sumReinvestAccount;

    /**
     * 当日已复投额
     */
    private BigDecimal usedReinvestAccount;

    /**
     * 当日未复投额
     */
    private BigDecimal leaveReinvestAccount;

    /**
     * 创建人id
     */
    private Integer createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人id
     */
    private Integer updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标识
     */
    private Integer delFlg;

    private Integer limitStart = -1;

    private Integer limitEnd = -1;

    public String getDateFromSrch() {
        return dateFromSrch;
    }

    public void setDateFromSrch(String dateFromSrch) {
        this.dateFromSrch = dateFromSrch;
    }

    public String getDateToSrch() {
        return dateToSrch;
    }

    public void setDateToSrch(String dateToSrch) {
        this.dateToSrch = dateToSrch;
    }

    public String getPlanNidSrch() {
        return planNidSrch;
    }

    public void setPlanNidSrch(String planNidSrch) {
        this.planNidSrch = planNidSrch;
    }

    public String getPlanNameSrch() {
        return planNameSrch;
    }

    public void setPlanNameSrch(String planNameSrch) {
        this.planNameSrch = planNameSrch;
    }

    public String getLockPeriodSrch() {
        return lockPeriodSrch;
    }

    public void setLockPeriodSrch(String lockPeriodSrch) {
        this.lockPeriodSrch = lockPeriodSrch;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getLockPeriod() {
        return lockPeriod;
    }

    public void setLockPeriod(Integer lockPeriod) {
        this.lockPeriod = lockPeriod;
    }

    public BigDecimal getAddCreditAccount() {
        return addCreditAccount;
    }

    public void setAddCreditAccount(BigDecimal addCreditAccount) {
        this.addCreditAccount = addCreditAccount;
    }

    public BigDecimal getCreateCreditAccount() {
        return createCreditAccount;
    }

    public void setCreateCreditAccount(BigDecimal createCreditAccount) {
        this.createCreditAccount = createCreditAccount;
    }

    public BigDecimal getUsedCreditAccount() {
        return usedCreditAccount;
    }

    public void setUsedCreditAccount(BigDecimal usedCreditAccount) {
        this.usedCreditAccount = usedCreditAccount;
    }

    public BigDecimal getLeaveCreditAccount() {
        return leaveCreditAccount;
    }

    public void setLeaveCreditAccount(BigDecimal leaveCreditAccount) {
        this.leaveCreditAccount = leaveCreditAccount;
    }

    public BigDecimal getAddReinvestAccount() {
        return addReinvestAccount;
    }

    public void setAddReinvestAccount(BigDecimal addReinvestAccount) {
        this.addReinvestAccount = addReinvestAccount;
    }

    public BigDecimal getSumReinvestAccount() {
        return sumReinvestAccount;
    }

    public void setSumReinvestAccount(BigDecimal sumReinvestAccount) {
        this.sumReinvestAccount = sumReinvestAccount;
    }

    public BigDecimal getUsedReinvestAccount() {
        return usedReinvestAccount;
    }

    public void setUsedReinvestAccount(BigDecimal usedReinvestAccount) {
        this.usedReinvestAccount = usedReinvestAccount;
    }

    public BigDecimal getLeaveReinvestAccount() {
        return leaveReinvestAccount;
    }

    public void setLeaveReinvestAccount(BigDecimal leaveReinvestAccount) {
        this.leaveReinvestAccount = leaveReinvestAccount;
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

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }
}
