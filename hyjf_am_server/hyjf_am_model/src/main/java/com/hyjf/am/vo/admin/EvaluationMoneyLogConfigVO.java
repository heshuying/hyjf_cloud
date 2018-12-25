package com.hyjf.am.vo.admin;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EvaluationMoneyLogConfigVO implements Serializable {


    @ApiModelProperty(value = "id")
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

    @ApiModelProperty(value = "添加人")
    private String createUser;

    @ApiModelProperty(value = "添加时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改人")
    private String updateUser;

    private static final long serialVersionUID = 1L;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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