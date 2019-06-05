/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实际资金计划
 * @author PC-LIUSHOUYI
 * @version HjhPlanCapitalActualVO, v0.1 2019/4/2 14:42
 */
public class HjhPlanCapitalActualVO extends BaseVO implements Serializable {

    private String id;

    /**
     * 日期
     */
    private Date date;

    @ApiModelProperty(value = "给前端的格式化后的日期")
    private String strDate;

    @ApiModelProperty(value = "智投编号")
    private String planNid;

    @ApiModelProperty(value = "智投名称")
    private String planName;

    @ApiModelProperty(value = "天/月标")
    private Integer isMonth;

    /**
     * 锁定期
     */
    private Integer lockPeriod;

    @ApiModelProperty(value = "拼接单位后的锁定期")
    private String lockPeriodView;

    @ApiModelProperty(value = "当日新增债转额")
    private BigDecimal addCreditAccount;

    @ApiModelProperty(value = "当日发起债转额:当日已承接+当日未承接")
    private BigDecimal createCreditAccount;

    @ApiModelProperty(value = "当日已承接债转额")
    private BigDecimal usedCreditAccount;

    @ApiModelProperty(value = "当日未承接额")
    private BigDecimal leaveCreditAccount;

    @ApiModelProperty(value = "当日新增复投额:当日可复投额-昨日未复投额")
    private BigDecimal addReinvestAccount;

    @ApiModelProperty(value = "当日可复投额:当日已复投额+当日未复投额")
    private BigDecimal sumReinvestAccount;

    @ApiModelProperty(value = "当日已复投额")
    private BigDecimal usedReinvestAccount;

    @ApiModelProperty(value = "当日未复投额")
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

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
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

    public String getLockPeriodView() {
        return lockPeriodView;
    }

    public void setLockPeriodView(String lockPeriodView) {
        this.lockPeriodView = lockPeriodView;
    }

    public Integer getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(Integer isMonth) {
        this.isMonth = isMonth;
    }
}
