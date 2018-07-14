/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author wangjun
 * @version WebProjectRepayListCustomizeVO, v0.1 2018/7/12 11:15
 */
public class WebProjectRepayListCustomizeVO extends BaseVO implements Serializable {
    public String id;

    public String projectPeriod;

    public String projectTotal;

    public String projectCapital;

    public String projectInterest;

    public String projectFee;

    public String repayTime;

    public String repayDay;

    public String repayTotal;

    public String status;

    public String chargeInterest;

    public String lateInterest;

    public String dalayInterest;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectPeriod() {
        return projectPeriod;
    }

    public void setProjectPeriod(String projectPeriod) {
        this.projectPeriod = projectPeriod;
    }

    public String getProjectTotal() {
        return projectTotal;
    }

    public void setProjectTotal(String projectTotal) {
        this.projectTotal = projectTotal;
    }

    public String getProjectCapital() {
        return projectCapital;
    }

    public void setProjectCapital(String projectCapital) {
        this.projectCapital = projectCapital;
    }

    public String getProjectInterest() {
        return projectInterest;
    }

    public void setProjectInterest(String projectInterest) {
        this.projectInterest = projectInterest;
    }

    public String getProjectFee() {
        return projectFee;
    }

    public void setProjectFee(String projectFee) {
        this.projectFee = projectFee;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    public String getRepayDay() {
        return repayDay;
    }

    public void setRepayDay(String repayDay) {
        this.repayDay = repayDay;
    }

    public String getRepayTotal() {
        return repayTotal;
    }

    public void setRepayTotal(String repayTotal) {
        this.repayTotal = repayTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChargeInterest() {
        return chargeInterest;
    }

    public void setChargeInterest(String chargeInterest) {
        this.chargeInterest = chargeInterest;
    }

    public String getLateInterest() {
        return lateInterest;
    }

    public void setLateInterest(String lateInterest) {
        this.lateInterest = lateInterest;
    }

    public String getDalayInterest() {
        return dalayInterest;
    }

    public void setDalayInterest(String dalayInterest) {
        this.dalayInterest = dalayInterest;
    }
}
