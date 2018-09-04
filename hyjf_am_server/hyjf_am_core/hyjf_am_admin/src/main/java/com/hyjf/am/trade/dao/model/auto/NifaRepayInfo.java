package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class NifaRepayInfo implements Serializable {
    private Integer id;

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
        this.paymentDate = paymentDate == null ? null : paymentDate.trim();
    }

    public String getPaymentPrincipal() {
        return paymentPrincipal;
    }

    public void setPaymentPrincipal(String paymentPrincipal) {
        this.paymentPrincipal = paymentPrincipal == null ? null : paymentPrincipal.trim();
    }

    public String getPaymentInterest() {
        return paymentInterest;
    }

    public void setPaymentInterest(String paymentInterest) {
        this.paymentInterest = paymentInterest == null ? null : paymentInterest.trim();
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
        this.paymentPrincipalRest = paymentPrincipalRest == null ? null : paymentPrincipalRest.trim();
    }

    public String getPaymentInterestRest() {
        return paymentInterestRest;
    }

    public void setPaymentInterestRest(String paymentInterestRest) {
        this.paymentInterestRest = paymentInterestRest == null ? null : paymentInterestRest.trim();
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