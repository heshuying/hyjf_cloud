package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 产品中心 --> 汇计划 --> 资金计划
 * @Author : huanghui
 */
public class HjhPlanCapitalRequestBean extends BasePage {

    @ApiModelProperty(value = "计划编号")
    private String planNidSrch;

    @ApiModelProperty(value = "计划名称")
    private String planNameSrch;

    @ApiModelProperty(value = "锁定期")
    private String lockPeriodSrch;

    @ApiModelProperty(value = "日期开始")
    private String dateFromSrch;

    @ApiModelProperty(value = "日期结束")
    private String dateToSrch;

    @ApiModelProperty(value = "复投总额")
    private BigDecimal sumReinvestAccount;

    @ApiModelProperty(value = "债转总额")
    private BigDecimal sumCreditAccount;

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

    public BigDecimal getSumReinvestAccount() {
        return sumReinvestAccount;
    }

    public void setSumReinvestAccount(BigDecimal sumReinvestAccount) {
        this.sumReinvestAccount = sumReinvestAccount;
    }

    public BigDecimal getSumCreditAccount() {
        return sumCreditAccount;
    }

    public void setSumCreditAccount(BigDecimal sumCreditAccount) {
        this.sumCreditAccount = sumCreditAccount;
    }
}
