package com.hyjf.am.vo.admin;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class EvaluationCheckLogConfigVO implements Serializable {


    @ApiModelProperty(value = "散标债转出借者测评类型校验")
    private Integer debtEvaluationTypeCheck;

    @ApiModelProperty(value = "智投出借者测评类型校验")
    private Integer intellectualEveluationTypeCheck;

    @ApiModelProperty(value = "散标债转单笔出借金额校验")
    private Integer deptEvaluationMoneyCheck;

    @ApiModelProperty(value = "智投单笔出借金额校验")
    private Integer intellectualEvaluationMoneyCheck;

    @ApiModelProperty(value = "散标债转待收本金校验")
    private Integer deptCollectionEvaluationCheck;

    @ApiModelProperty(value = "智投待收本金校验")
    private Integer intellectualCollectionEvaluationCheck;

    @ApiModelProperty(value = "投标时校验二期")
    private Integer investmentEvaluationCheck;

    @ApiModelProperty(value = "添加人")
    private String createUser;

    @ApiModelProperty(value = "添加时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改人")
    private String updateUser;

    private static final long serialVersionUID = 1L;


    public Integer getDebtEvaluationTypeCheck() {
        return debtEvaluationTypeCheck;
    }

    public void setDebtEvaluationTypeCheck(Integer debtEvaluationTypeCheck) {
        this.debtEvaluationTypeCheck = debtEvaluationTypeCheck;
    }

    public Integer getIntellectualEveluationTypeCheck() {
        return intellectualEveluationTypeCheck;
    }

    public void setIntellectualEveluationTypeCheck(Integer intellectualEveluationTypeCheck) {
        this.intellectualEveluationTypeCheck = intellectualEveluationTypeCheck;
    }

    public Integer getDeptEvaluationMoneyCheck() {
        return deptEvaluationMoneyCheck;
    }

    public void setDeptEvaluationMoneyCheck(Integer deptEvaluationMoneyCheck) {
        this.deptEvaluationMoneyCheck = deptEvaluationMoneyCheck;
    }

    public Integer getIntellectualEvaluationMoneyCheck() {
        return intellectualEvaluationMoneyCheck;
    }

    public void setIntellectualEvaluationMoneyCheck(Integer intellectualEvaluationMoneyCheck) {
        this.intellectualEvaluationMoneyCheck = intellectualEvaluationMoneyCheck;
    }

    public Integer getDeptCollectionEvaluationCheck() {
        return deptCollectionEvaluationCheck;
    }

    public void setDeptCollectionEvaluationCheck(Integer deptCollectionEvaluationCheck) {
        this.deptCollectionEvaluationCheck = deptCollectionEvaluationCheck;
    }

    public Integer getIntellectualCollectionEvaluationCheck() {
        return intellectualCollectionEvaluationCheck;
    }

    public void setIntellectualCollectionEvaluationCheck(Integer intellectualCollectionEvaluationCheck) {
        this.intellectualCollectionEvaluationCheck = intellectualCollectionEvaluationCheck;
    }

    public Integer getInvestmentEvaluationCheck() {
        return investmentEvaluationCheck;
    }

    public void setInvestmentEvaluationCheck(Integer investmentEvaluationCheck) {
        this.investmentEvaluationCheck = investmentEvaluationCheck;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
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

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}