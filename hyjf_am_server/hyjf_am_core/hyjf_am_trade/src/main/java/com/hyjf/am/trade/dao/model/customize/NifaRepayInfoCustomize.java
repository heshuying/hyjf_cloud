/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * @author PC-LIUSHOUYI
 * @version NifaRepayInfoCustomize, v0.1 2018/9/4 19:07
 */
public class NifaRepayInfoCustomize implements Serializable {

    private String platformNo;

    private String projectNo;

    private Integer paymentNum;

    private String paymentDate;

    private String paymentPrincipal;

    private String paymentInterest;

    private Integer paymentSource;

    private Integer paymentSituation;

    private String paymentPrincipalRest;

    private String paymentInterestRest;

    private static final long serialVersionUID = 1L;

    public String getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(String platformNo) {
        this.platformNo = platformNo;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public Integer getPaymentNum() {
        return paymentNum;
    }

    public void setPaymentNum(Integer paymentNum) {
        this.paymentNum = paymentNum;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentPrincipal() {
        return paymentPrincipal;
    }

    public void setPaymentPrincipal(String paymentPrincipal) {
        this.paymentPrincipal = paymentPrincipal;
    }

    public String getPaymentInterest() {
        return paymentInterest;
    }

    public void setPaymentInterest(String paymentInterest) {
        this.paymentInterest = paymentInterest;
    }

    public Integer getPaymentSource() {
        return paymentSource;
    }

    public void setPaymentSource(Integer paymentSource) {
        this.paymentSource = paymentSource;
    }

    public Integer getPaymentSituation() {
        return paymentSituation;
    }

    public void setPaymentSituation(Integer paymentSituation) {
        this.paymentSituation = paymentSituation;
    }

    public String getPaymentPrincipalRest() {
        return paymentPrincipalRest;
    }

    public void setPaymentPrincipalRest(String paymentPrincipalRest) {
        this.paymentPrincipalRest = paymentPrincipalRest;
    }

    public String getPaymentInterestRest() {
        return paymentInterestRest;
    }

    public void setPaymentInterestRest(String paymentInterestRest) {
        this.paymentInterestRest = paymentInterestRest;
    }
}
