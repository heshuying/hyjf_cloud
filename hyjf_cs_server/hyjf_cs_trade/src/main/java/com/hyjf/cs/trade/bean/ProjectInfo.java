package com.hyjf.cs.trade.bean;

import java.io.Serializable;

/**
 * 计划基本信息
 */
public class ProjectInfo implements Serializable {


    // 项目类型 :HJH
    private String type;
    // 项目进行状态 如 1：复审中
    private String status;
    // 历史年回报率: 6.5
    private String planApr;
    // 计划期限
    private String planPeriod;
    // 计划期限单位  天 月
    private String planPeriodUnit;
    // 计划加入人次
    private String planPersonTime;
    // 开放额度
    private String account;
    private String planProgressStatus;
    // 计划名称
    private String planName;
    // 计息时间
    private String onAccrual;
    // 还款方式: 按天计息，到期还本还息
    private String repayStyle;

    // 标的类型
    private String investLevel;
    // 最小加入金额
    private String minInvestment;

    public String getInvestLevel() {
        return investLevel;
    }

    public void setInvestLevel(String investLevel) {
        this.investLevel = investLevel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlanApr() {
        return planApr;
    }

    public void setPlanApr(String planApr) {
        this.planApr = planApr;
    }

    public String getPlanPeriod() {
        return planPeriod;
    }

    public void setPlanPeriod(String planPeriod) {
        this.planPeriod = planPeriod;
    }

    public String getPlanPeriodUnit() {
        return planPeriodUnit;
    }

    public void setPlanPeriodUnit(String planPeriodUnit) {
        this.planPeriodUnit = planPeriodUnit;
    }

    public String getPlanPersonTime() {
        return planPersonTime;
    }

    public void setPlanPersonTime(String planPersonTime) {
        this.planPersonTime = planPersonTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPlanProgressStatus() {
        return planProgressStatus;
    }

    public void setPlanProgressStatus(String planProgressStatus) {
        this.planProgressStatus = planProgressStatus;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getOnAccrual() {
        return onAccrual;
    }

    public void setOnAccrual(String onAccrual) {
        this.onAccrual = onAccrual;
    }

    public String getRepayStyle() {
        return repayStyle;
    }

    public void setRepayStyle(String repayStyle) {
        this.repayStyle = repayStyle;
    }

    public String getMinInvestment() {
        return minInvestment;
    }

    public void setMinInvestment(String minInvestment) {
        this.minInvestment = minInvestment;
    }
}
