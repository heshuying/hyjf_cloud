package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 *
 * @author Zha Daojian
 * @date 2018/12/20 17:36
 * @param 
 * @return 
 **/
public class EvaluationCheckRequest extends BasePage implements Serializable {


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

    public int limit;

    public int limitStart;

    public int limitEnd;


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

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimitStart() { return limitStart; }

    public void setLimitStart(int limitStart) { this.limitStart = limitStart; }

    public int getLimitEnd() { return limitEnd; }

    public void setLimitEnd(int limitEnd) { this.limitEnd = limitEnd; }
}
