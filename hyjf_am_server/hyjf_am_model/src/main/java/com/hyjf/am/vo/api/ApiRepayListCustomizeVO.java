package com.hyjf.am.vo.api;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @version ApiRepayListCustomizeVO, v0.1 2018/9/1 15:32
 * @Author: Zha Daojian
 */
public class ApiRepayListCustomizeVO extends BaseVO {

    @ApiModelProperty(value = "电子账号")
    private String accountId;

    @ApiModelProperty(value = "交易流水号")
    private String nid;

    @ApiModelProperty(value = "标的编号")
    private String borrowNid;

    @ApiModelProperty(value = "应回本金")
    private String recoverCapital;

    @ApiModelProperty(value = "应回利息")
    private String recoverInterest;

    @ApiModelProperty(value = "回款时间")
    private String recoverTime;

    @ApiModelProperty(value = "回款收益")
    private String recoverTotal;

    @ApiModelProperty(value = "项目状态")
    private String status;

    @ApiModelProperty(value = "项目类型")
    private String projectType;

    @ApiModelProperty(value = "应收加息收益")
    private String repayInterest;

    @ApiModelProperty(value = "回款加息收益=加息还款明细中实还加息收益")
    private String repayInterestYes;

    @ApiModelProperty(value = "加息回款时间")
    private String repayActionTime;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getRecoverCapital() {
        return recoverCapital;
    }

    public void setRecoverCapital(String recoverCapital) {
        this.recoverCapital = recoverCapital;
    }

    public String getRecoverInterest() {
        return recoverInterest;
    }

    public void setRecoverInterest(String recoverInterest) {
        this.recoverInterest = recoverInterest;
    }

    public String getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(String recoverTime) {
        this.recoverTime = recoverTime;
    }

    public String getRecoverTotal() {
        return recoverTotal;
    }

    public void setRecoverTotal(String recoverTotal) {
        this.recoverTotal = recoverTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getRepayInterest() {
        return repayInterest;
    }

    public void setRepayInterest(String repayInterest) {
        this.repayInterest = repayInterest;
    }

    public String getRepayInterestYes() {
        return repayInterestYes;
    }

    public void setRepayInterestYes(String repayInterestYes) {
        this.repayInterestYes = repayInterestYes;
    }

    public String getRepayActionTime() {
        return repayActionTime;
    }

    public void setRepayActionTime(String repayActionTime) {
        this.repayActionTime = repayActionTime;
    }
}
