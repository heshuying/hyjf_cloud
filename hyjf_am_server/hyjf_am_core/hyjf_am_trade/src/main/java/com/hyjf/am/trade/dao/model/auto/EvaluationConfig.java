package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EvaluationConfig implements Serializable {
    private Integer id;

    /**
     * 散标债转出借者测评类型校验（1开启，0关闭）
     *
     * @mbggenerated
     */
    private Boolean debtEvaluationTypeCheck;

    /**
     * 智投出借者测评类型校验（1开启，0关闭）
     *
     * @mbggenerated
     */
    private Boolean intellectualEveluationTypeCheck;

    /**
     * 散标债转单笔投资金额校验（1开启，0关闭）
     *
     * @mbggenerated
     */
    private Boolean deptEvaluationMoneyCheck;

    /**
     * 智投单笔投资金额校验（1开启，0关闭）
     *
     * @mbggenerated
     */
    private Boolean intellectualEvaluationMoneyCheck;

    /**
     * 散标债转待收本金校验（1开启，0关闭）
     *
     * @mbggenerated
     */
    private Boolean deptCollectionEvaluationCheck;

    /**
     * 智投待收本金校验（1开启，0关闭）
     *
     * @mbggenerated
     */
    private Boolean intellectualCollectionEvaluationCheck;

    /**
     * 投资时校验二期（1开启，0关闭）
     *
     * @mbggenerated
     */
    private Boolean investmentEvaluationCheck;

    /**
     * 测评有效期
     *
     * @mbggenerated
     */
    private Integer validityEvaluationDate;

    /**
     * 保守型单笔投资限额金额
     *
     * @mbggenerated
     */
    private BigDecimal conservativeEvaluationSingleMoney;

    /**
     * 成长型单笔投资限额金额
     *
     * @mbggenerated
     */
    private BigDecimal growupEvaluationSingleMoney;

    /**
     * 稳健型单笔投资限额金额
     *
     * @mbggenerated
     */
    private BigDecimal steadyEvaluationSingleMoney;

    /**
     * 进取型单笔投资限额金额
     *
     * @mbggenerated
     */
    private BigDecimal enterprisingEvaluationSinglMoney;

    /**
     * 保守型待收本金限额金额
     *
     * @mbggenerated
     */
    private BigDecimal conservativeEvaluationPrincipalMoney;

    /**
     * 成长型待收本金限额金额
     *
     * @mbggenerated
     */
    private BigDecimal growupEvaluationPrincipalMoney;

    /**
     * 稳健型待收本金限额金额
     *
     * @mbggenerated
     */
    private BigDecimal steadyEvaluationPrincipalMoney;

    /**
     * 进取型待收本金限额金额
     *
     * @mbggenerated
     */
    private BigDecimal enterprisingEvaluationPrincipalMoney;

    /**
     * BBB信用等级对应的建议出借者类型
     *
     * @mbggenerated
     */
    private String bbbEvaluationProposal;

    /**
     * A信用等级对应的建议出借者类型
     *
     * @mbggenerated
     */
    private String aEvaluationProposal;

    /**
     * AA-信用等级对应的建议出借者类型
     *
     * @mbggenerated
     */
    private String aa0EvaluationProposal;

    /**
     * AA信用等级对应的建议出借者类型
     *
     * @mbggenerated
     */
    private String aa1EvaluationProposal;

    /**
     * AA+信用等级对应的建议出借者类型
     *
     * @mbggenerated
     */
    private String aa2EvaluationProposal;

    /**
     * AAA信用等级对应的建议出借者类型
     *
     * @mbggenerated
     */
    private String aaaEvaluationProposal;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private String createUser;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private String updateUser;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDebtEvaluationTypeCheck() {
        return debtEvaluationTypeCheck;
    }

    public void setDebtEvaluationTypeCheck(Boolean debtEvaluationTypeCheck) {
        this.debtEvaluationTypeCheck = debtEvaluationTypeCheck;
    }

    public Boolean getIntellectualEveluationTypeCheck() {
        return intellectualEveluationTypeCheck;
    }

    public void setIntellectualEveluationTypeCheck(Boolean intellectualEveluationTypeCheck) {
        this.intellectualEveluationTypeCheck = intellectualEveluationTypeCheck;
    }

    public Boolean getDeptEvaluationMoneyCheck() {
        return deptEvaluationMoneyCheck;
    }

    public void setDeptEvaluationMoneyCheck(Boolean deptEvaluationMoneyCheck) {
        this.deptEvaluationMoneyCheck = deptEvaluationMoneyCheck;
    }

    public Boolean getIntellectualEvaluationMoneyCheck() {
        return intellectualEvaluationMoneyCheck;
    }

    public void setIntellectualEvaluationMoneyCheck(Boolean intellectualEvaluationMoneyCheck) {
        this.intellectualEvaluationMoneyCheck = intellectualEvaluationMoneyCheck;
    }

    public Boolean getDeptCollectionEvaluationCheck() {
        return deptCollectionEvaluationCheck;
    }

    public void setDeptCollectionEvaluationCheck(Boolean deptCollectionEvaluationCheck) {
        this.deptCollectionEvaluationCheck = deptCollectionEvaluationCheck;
    }

    public Boolean getIntellectualCollectionEvaluationCheck() {
        return intellectualCollectionEvaluationCheck;
    }

    public void setIntellectualCollectionEvaluationCheck(Boolean intellectualCollectionEvaluationCheck) {
        this.intellectualCollectionEvaluationCheck = intellectualCollectionEvaluationCheck;
    }

    public Boolean getInvestmentEvaluationCheck() {
        return investmentEvaluationCheck;
    }

    public void setInvestmentEvaluationCheck(Boolean investmentEvaluationCheck) {
        this.investmentEvaluationCheck = investmentEvaluationCheck;
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

    public String getBbbEvaluationProposal() {
        return bbbEvaluationProposal;
    }

    public void setBbbEvaluationProposal(String bbbEvaluationProposal) {
        this.bbbEvaluationProposal = bbbEvaluationProposal == null ? null : bbbEvaluationProposal.trim();
    }

    public String getaEvaluationProposal() {
        return aEvaluationProposal;
    }

    public void setaEvaluationProposal(String aEvaluationProposal) {
        this.aEvaluationProposal = aEvaluationProposal == null ? null : aEvaluationProposal.trim();
    }

    public String getAa0EvaluationProposal() {
        return aa0EvaluationProposal;
    }

    public void setAa0EvaluationProposal(String aa0EvaluationProposal) {
        this.aa0EvaluationProposal = aa0EvaluationProposal == null ? null : aa0EvaluationProposal.trim();
    }

    public String getAa1EvaluationProposal() {
        return aa1EvaluationProposal;
    }

    public void setAa1EvaluationProposal(String aa1EvaluationProposal) {
        this.aa1EvaluationProposal = aa1EvaluationProposal == null ? null : aa1EvaluationProposal.trim();
    }

    public String getAa2EvaluationProposal() {
        return aa2EvaluationProposal;
    }

    public void setAa2EvaluationProposal(String aa2EvaluationProposal) {
        this.aa2EvaluationProposal = aa2EvaluationProposal == null ? null : aa2EvaluationProposal.trim();
    }

    public String getAaaEvaluationProposal() {
        return aaaEvaluationProposal;
    }

    public void setAaaEvaluationProposal(String aaaEvaluationProposal) {
        this.aaaEvaluationProposal = aaaEvaluationProposal == null ? null : aaaEvaluationProposal.trim();
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