/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * @author PC-LIUSHOUYI
 * @version NifaContractTemplateCustomize, v0.1 2018/9/4 19:06
 */
public class NifaContractTemplateCustomize implements Serializable {

    private String templetNid;

    private String normalDefinition;

    private String prepaymentDefinition;

    private String borrowerPromises;

    private String lenderPromises;

    private String borrowerObligation;

    private String confidentiality;

    private String breachContract;

    private String applicableLaw;

    private String disputeResolution;

    private String otherConditions;

    private static final long serialVersionUID = 1L;

    public String getTempletNid() {
        return templetNid;
    }

    public void setTempletNid(String templetNid) {
        this.templetNid = templetNid;
    }

    public String getNormalDefinition() {
        return normalDefinition;
    }

    public void setNormalDefinition(String normalDefinition) {
        this.normalDefinition = normalDefinition;
    }

    public String getPrepaymentDefinition() {
        return prepaymentDefinition;
    }

    public void setPrepaymentDefinition(String prepaymentDefinition) {
        this.prepaymentDefinition = prepaymentDefinition;
    }

    public String getBorrowerPromises() {
        return borrowerPromises;
    }

    public void setBorrowerPromises(String borrowerPromises) {
        this.borrowerPromises = borrowerPromises;
    }

    public String getLenderPromises() {
        return lenderPromises;
    }

    public void setLenderPromises(String lenderPromises) {
        this.lenderPromises = lenderPromises;
    }

    public String getBorrowerObligation() {
        return borrowerObligation;
    }

    public void setBorrowerObligation(String borrowerObligation) {
        this.borrowerObligation = borrowerObligation;
    }

    public String getConfidentiality() {
        return confidentiality;
    }

    public void setConfidentiality(String confidentiality) {
        this.confidentiality = confidentiality;
    }

    public String getBreachContract() {
        return breachContract;
    }

    public void setBreachContract(String breachContract) {
        this.breachContract = breachContract;
    }

    public String getApplicableLaw() {
        return applicableLaw;
    }

    public void setApplicableLaw(String applicableLaw) {
        this.applicableLaw = applicableLaw;
    }

    public String getDisputeResolution() {
        return disputeResolution;
    }

    public void setDisputeResolution(String disputeResolution) {
        this.disputeResolution = disputeResolution;
    }

    public String getOtherConditions() {
        return otherConditions;
    }

    public void setOtherConditions(String otherConditions) {
        this.otherConditions = otherConditions;
    }
}
