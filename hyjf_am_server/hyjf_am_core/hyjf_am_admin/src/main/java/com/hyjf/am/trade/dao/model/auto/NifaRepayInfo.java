package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class NifaRepayInfo implements Serializable {
    private Integer id;

    /**
     * 统一社会信用代码
     *
     * @mbggenerated
     */
    private String platformNo;

    /**
     * 项目编号
     *
     * @mbggenerated
     */
    private String projectNo;

    /**
     * 还款期数
     *
     * @mbggenerated
     */
    private Integer paymentNum;

    /**
     * 还款日期
     *
     * @mbggenerated
     */
    private String paymentDate;

    /**
     * 还款本金
     *
     * @mbggenerated
     */
    private String paymentPrincipal;

    /**
     * 还款利息
     *
     * @mbggenerated
     */
    private String paymentInterest;

    /**
     * 还款来源
     *
     * @mbggenerated
     */
    private Integer paymentSource;

    /**
     * 还款情况
     *
     * @mbggenerated
     */
    private Integer paymentSituation;

    /**
     * 剩余待还本金
     *
     * @mbggenerated
     */
    private String paymentPrincipalRest;

    /**
     * 剩余待还利息
     *
     * @mbggenerated
     */
    private String paymentInterestRest;

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