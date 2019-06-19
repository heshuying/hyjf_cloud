package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 产品中心 --> 汇计划 --> 资金计划
 * @Author : wenxin
 */
public class HjhPlanCapitalActualRequestBean extends BasePage {

    @ApiModelProperty(value = "日期开始")
    private String dateFromSrch;

    @ApiModelProperty(value = "日期结束")
    private String dateToSrch;

    @ApiModelProperty(value = "智投编号")
    private String planNidSrch;

    @ApiModelProperty(value = "智投名称")
    private String planNameSrch;

    @ApiModelProperty(value = "锁定期")
    private String lockPeriodSrch;

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
}
