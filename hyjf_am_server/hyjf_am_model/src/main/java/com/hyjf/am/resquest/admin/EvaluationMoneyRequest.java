package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Zha Daojian
 * @date 2018/12/20 17:36
 * @param 
 * @return 
 **/
public class EvaluationMoneyRequest extends BasePage implements Serializable {



    private Integer id;
    @ApiModelProperty(value = "测评到期时间")
    private Integer validityEvaluationDate;

    @ApiModelProperty(value = "成长型单笔投资限额金额")
    private BigDecimal conservativeEvaluationSingleMoney;

    @ApiModelProperty(value = "稳健型单笔投资限额金额")
    private BigDecimal growupEvaluationSingleMoney;

    @ApiModelProperty(value = "进取型单笔投资限额金额")
    private BigDecimal steadyEvaluationSingleMoney;

    @ApiModelProperty(value = "保守型单笔投资限额金额")
    private BigDecimal enterprisingEvaluationSinglMoney;

    @ApiModelProperty(value = "成长型代收本金限额金额")
    private BigDecimal conservativeEvaluationPrincipalMoney;

    @ApiModelProperty(value = "稳健型代收本金限额金额")
    private BigDecimal growupEvaluationPrincipalMoney;

    @ApiModelProperty(value = "进取型代收本金限额金额")
    private BigDecimal steadyEvaluationPrincipalMoney;

    @ApiModelProperty(value = "保守型代收本金限额金额")
    private BigDecimal enterprisingEvaluationPrincipalMoney;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private String updateUser;
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public int limit;

    public int limitStart;

    public int limitEnd;

    public Integer getValidityEvaluationDate() {
        return validityEvaluationDate;
    }

    public void setValidityEvaluationDate(Integer validityEvaluationDate) {
        this.validityEvaluationDate = validityEvaluationDate;
    }

    public BigDecimal getConservativeEvaluationSingleMoney() {
        return conservativeEvaluationSingleMoney;
    }

    public void setConservativeEvaluationSingleMoney(BigDecimal conservativeEvaluationSingleMoney) {
        this.conservativeEvaluationSingleMoney = conservativeEvaluationSingleMoney;
    }

    public BigDecimal getGrowupEvaluationSingleMoney() {
        return growupEvaluationSingleMoney;
    }

    public void setGrowupEvaluationSingleMoney(BigDecimal growupEvaluationSingleMoney) {
        this.growupEvaluationSingleMoney = growupEvaluationSingleMoney;
    }

    public BigDecimal getSteadyEvaluationSingleMoney() {
        return steadyEvaluationSingleMoney;
    }

    public void setSteadyEvaluationSingleMoney(BigDecimal steadyEvaluationSingleMoney) {
        this.steadyEvaluationSingleMoney = steadyEvaluationSingleMoney;
    }

    public BigDecimal getEnterprisingEvaluationSinglMoney() {
        return enterprisingEvaluationSinglMoney;
    }

    public void setEnterprisingEvaluationSinglMoney(BigDecimal enterprisingEvaluationSinglMoney) {
        this.enterprisingEvaluationSinglMoney = enterprisingEvaluationSinglMoney;
    }

    public BigDecimal getConservativeEvaluationPrincipalMoney() {
        return conservativeEvaluationPrincipalMoney;
    }

    public void setConservativeEvaluationPrincipalMoney(BigDecimal conservativeEvaluationPrincipalMoney) {
        this.conservativeEvaluationPrincipalMoney = conservativeEvaluationPrincipalMoney;
    }

    public BigDecimal getGrowupEvaluationPrincipalMoney() {
        return growupEvaluationPrincipalMoney;
    }

    public void setGrowupEvaluationPrincipalMoney(BigDecimal growupEvaluationPrincipalMoney) {
        this.growupEvaluationPrincipalMoney = growupEvaluationPrincipalMoney;
    }

    public BigDecimal getSteadyEvaluationPrincipalMoney() {
        return steadyEvaluationPrincipalMoney;
    }

    public void setSteadyEvaluationPrincipalMoney(BigDecimal steadyEvaluationPrincipalMoney) {
        this.steadyEvaluationPrincipalMoney = steadyEvaluationPrincipalMoney;
    }

    public BigDecimal getEnterprisingEvaluationPrincipalMoney() {
        return enterprisingEvaluationPrincipalMoney;
    }

    public void setEnterprisingEvaluationPrincipalMoney(BigDecimal enterprisingEvaluationPrincipalMoney) {
        this.enterprisingEvaluationPrincipalMoney = enterprisingEvaluationPrincipalMoney;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
