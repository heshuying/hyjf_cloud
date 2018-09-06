/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * @author PC-LIUSHOUYI
 * @version NifaReceivedPaymentsCustomize, v0.1 2018/9/4 19:07
 */
public class NifaReceivedPaymentsCustomize implements Serializable {

    private String platformNo;

    private String projectNo;

    private String contractNo;

    private Integer returnNum;

    private String returnDate;

    private String returnPrincipal;

    private String returnInterest;

    private Integer returnSource;

    private Integer returnSituation;

    private String returnPrincipalRest;

    private String returnInterestRest;

    public static final long serialVersionUID = 1L;

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

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(Integer returnNum) {
        this.returnNum = returnNum;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnPrincipal() {
        return returnPrincipal;
    }

    public void setReturnPrincipal(String returnPrincipal) {
        this.returnPrincipal = returnPrincipal;
    }

    public String getReturnInterest() {
        return returnInterest;
    }

    public void setReturnInterest(String returnInterest) {
        this.returnInterest = returnInterest;
    }

    public Integer getReturnSource() {
        return returnSource;
    }

    public void setReturnSource(Integer returnSource) {
        this.returnSource = returnSource;
    }

    public Integer getReturnSituation() {
        return returnSituation;
    }

    public void setReturnSituation(Integer returnSituation) {
        this.returnSituation = returnSituation;
    }

    public String getReturnPrincipalRest() {
        return returnPrincipalRest;
    }

    public void setReturnPrincipalRest(String returnPrincipalRest) {
        this.returnPrincipalRest = returnPrincipalRest;
    }

    public String getReturnInterestRest() {
        return returnInterestRest;
    }

    public void setReturnInterestRest(String returnInterestRest) {
        this.returnInterestRest = returnInterestRest;
    }
}
