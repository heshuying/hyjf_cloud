package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class NifaReceivedPayments implements Serializable {
    private Integer id;

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

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(String platformNo) {
        this.platformNo = platformNo == null ? null : platformNo.trim();
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo == null ? null : projectNo.trim();
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
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
        this.returnDate = returnDate == null ? null : returnDate.trim();
    }

    public String getReturnPrincipal() {
        return returnPrincipal;
    }

    public void setReturnPrincipal(String returnPrincipal) {
        this.returnPrincipal = returnPrincipal == null ? null : returnPrincipal.trim();
    }

    public String getReturnInterest() {
        return returnInterest;
    }

    public void setReturnInterest(String returnInterest) {
        this.returnInterest = returnInterest == null ? null : returnInterest.trim();
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
        this.returnPrincipalRest = returnPrincipalRest == null ? null : returnPrincipalRest.trim();
    }

    public String getReturnInterestRest() {
        return returnInterestRest;
    }

    public void setReturnInterestRest(String returnInterestRest) {
        this.returnInterestRest = returnInterestRest == null ? null : returnInterestRest.trim();
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