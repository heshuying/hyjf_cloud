package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class NifaContractTemplate implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 模版编号
     *
     * @mbggenerated
     */
    private String templetNid;

    /**
     * 正常还款定义
     *
     * @mbggenerated
     */
    private String normalDefinition;

    /**
     * 提前还款定义
     *
     * @mbggenerated
     */
    private String prepaymentDefinition;

    /**
     * 借款人承诺与保证
     *
     * @mbggenerated
     */
    private String borrowerPromises;

    /**
     * 出借人承诺与保证
     *
     * @mbggenerated
     */
    private String lenderPromises;

    /**
     * 借款人还款义务
     *
     * @mbggenerated
     */
    private String borrowerObligation;

    /**
     * 保密
     *
     * @mbggenerated
     */
    private String confidentiality;

    /**
     * 违约
     *
     * @mbggenerated
     */
    private String breachContract;

    /**
     * 法律适用
     *
     * @mbggenerated
     */
    private String applicableLaw;

    /**
     * 争议解决
     *
     * @mbggenerated
     */
    private String disputeResolution;

    /**
     * 其他条款
     *
     * @mbggenerated
     */
    private String otherConditions;

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
     * 最后修改时间
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

    public String getTempletNid() {
        return templetNid;
    }

    public void setTempletNid(String templetNid) {
        this.templetNid = templetNid == null ? null : templetNid.trim();
    }

    public String getNormalDefinition() {
        return normalDefinition;
    }

    public void setNormalDefinition(String normalDefinition) {
        this.normalDefinition = normalDefinition == null ? null : normalDefinition.trim();
    }

    public String getPrepaymentDefinition() {
        return prepaymentDefinition;
    }

    public void setPrepaymentDefinition(String prepaymentDefinition) {
        this.prepaymentDefinition = prepaymentDefinition == null ? null : prepaymentDefinition.trim();
    }

    public String getBorrowerPromises() {
        return borrowerPromises;
    }

    public void setBorrowerPromises(String borrowerPromises) {
        this.borrowerPromises = borrowerPromises == null ? null : borrowerPromises.trim();
    }

    public String getLenderPromises() {
        return lenderPromises;
    }

    public void setLenderPromises(String lenderPromises) {
        this.lenderPromises = lenderPromises == null ? null : lenderPromises.trim();
    }

    public String getBorrowerObligation() {
        return borrowerObligation;
    }

    public void setBorrowerObligation(String borrowerObligation) {
        this.borrowerObligation = borrowerObligation == null ? null : borrowerObligation.trim();
    }

    public String getConfidentiality() {
        return confidentiality;
    }

    public void setConfidentiality(String confidentiality) {
        this.confidentiality = confidentiality == null ? null : confidentiality.trim();
    }

    public String getBreachContract() {
        return breachContract;
    }

    public void setBreachContract(String breachContract) {
        this.breachContract = breachContract == null ? null : breachContract.trim();
    }

    public String getApplicableLaw() {
        return applicableLaw;
    }

    public void setApplicableLaw(String applicableLaw) {
        this.applicableLaw = applicableLaw == null ? null : applicableLaw.trim();
    }

    public String getDisputeResolution() {
        return disputeResolution;
    }

    public void setDisputeResolution(String disputeResolution) {
        this.disputeResolution = disputeResolution == null ? null : disputeResolution.trim();
    }

    public String getOtherConditions() {
        return otherConditions;
    }

    public void setOtherConditions(String otherConditions) {
        this.otherConditions = otherConditions == null ? null : otherConditions.trim();
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