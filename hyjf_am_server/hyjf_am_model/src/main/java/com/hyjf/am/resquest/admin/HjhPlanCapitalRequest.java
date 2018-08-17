package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 汇计划--计划资金
 * @Author : huanghui
 */
public class HjhPlanCapitalRequest extends BasePage implements Serializable {

    /**
     * 检索条件 计划编号
     */
    private String planNidSrch;
    /**
     * 检索条件 计划名称
     */
    private String planNameSrch;
    /**
     * 检索条件锁定期
     */
    private String lockPeriodSrch;
    /**
     * 检索条件 日期开始
     */
    private String dateFromSrch;
    /**
     * 检索条件 日期结束
     */
    private String dateToSrch;

    /** 总计：复投总额 */
    private BigDecimal sumReinvestAccount;

    /** 总计：债转总额 */
    private BigDecimal sumCreditAccount;

    /**
     *  日期KEY1
     */
    private String dateKey;

    private Integer limitStart = -1;

    private Integer limitEnd = -1;

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

    public String getDateKey() {
        return dateKey;
    }

    public void setDateKey(String dateKey) {
        this.dateKey = dateKey;
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
