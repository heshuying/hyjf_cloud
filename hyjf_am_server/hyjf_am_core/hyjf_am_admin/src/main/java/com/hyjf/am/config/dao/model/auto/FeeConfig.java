package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class FeeConfig implements Serializable {
    /**
     * 主键ID
     *
     * @mbggenerated
     */
    private Integer id;

    private String bankCode;

    /**
     * 银行名称
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 个人网银充值
     *
     * @mbggenerated
     */
    private String personalCredit;

    /**
     * 企业网银充值
     *
     * @mbggenerated
     */
    private String enterpriseCredit;

    /**
     * 快捷支付充值
     *
     * @mbggenerated
     */
    private String quickPayment;

    /**
     * 即时提现
     *
     * @mbggenerated
     */
    private String directTakeout;

    /**
     * 即时提现、千分比
     *
     * @mbggenerated
     */
    private String directTakeoutPercent;

    /**
     * 快速提现
     *
     * @mbggenerated
     */
    private String quickTakeout;

    /**
     * 快速提现、千分比
     *
     * @mbggenerated
     */
    private String quickTakeoutPercent;

    /**
     * 普通提现
     *
     * @mbggenerated
     */
    private String normalTakeout;

    /**
     * 说明
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPersonalCredit() {
        return personalCredit;
    }

    public void setPersonalCredit(String personalCredit) {
        this.personalCredit = personalCredit == null ? null : personalCredit.trim();
    }

    public String getEnterpriseCredit() {
        return enterpriseCredit;
    }

    public void setEnterpriseCredit(String enterpriseCredit) {
        this.enterpriseCredit = enterpriseCredit == null ? null : enterpriseCredit.trim();
    }

    public String getQuickPayment() {
        return quickPayment;
    }

    public void setQuickPayment(String quickPayment) {
        this.quickPayment = quickPayment == null ? null : quickPayment.trim();
    }

    public String getDirectTakeout() {
        return directTakeout;
    }

    public void setDirectTakeout(String directTakeout) {
        this.directTakeout = directTakeout == null ? null : directTakeout.trim();
    }

    public String getDirectTakeoutPercent() {
        return directTakeoutPercent;
    }

    public void setDirectTakeoutPercent(String directTakeoutPercent) {
        this.directTakeoutPercent = directTakeoutPercent == null ? null : directTakeoutPercent.trim();
    }

    public String getQuickTakeout() {
        return quickTakeout;
    }

    public void setQuickTakeout(String quickTakeout) {
        this.quickTakeout = quickTakeout == null ? null : quickTakeout.trim();
    }

    public String getQuickTakeoutPercent() {
        return quickTakeoutPercent;
    }

    public void setQuickTakeoutPercent(String quickTakeoutPercent) {
        this.quickTakeoutPercent = quickTakeoutPercent == null ? null : quickTakeoutPercent.trim();
    }

    public String getNormalTakeout() {
        return normalTakeout;
    }

    public void setNormalTakeout(String normalTakeout) {
        this.normalTakeout = normalTakeout == null ? null : normalTakeout.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
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
}