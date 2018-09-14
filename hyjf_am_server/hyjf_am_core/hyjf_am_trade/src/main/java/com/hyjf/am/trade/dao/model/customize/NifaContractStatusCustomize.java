/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * @author PC-LIUSHOUYI
 * @version NifaContractStatusCustomize, v0.1 2018/9/4 19:07
 */
public class NifaContractStatusCustomize implements Serializable {

    private String platformNo;

    private String projectNo;

    private String contractNo;

    private String contractStatus;

    private String changeDate;

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

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }
}
