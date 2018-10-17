package com.hyjf.am.vo.coupon;

import java.io.Serializable;

public class CouponBeanVo implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3803722754627032581L;
    //用户优惠券id
    private String userCouponId;
    //优惠券类型
    private String couponType;
    //项目类型
    private String projectType;
    //操作平台
    private String operationPlatform;
    //优惠券额度
    private String couponQuota;
    //优惠券额度
    private String couponQuotaStr;
    //投资金额
    private String investQuota;
    //优惠券有效期
    private String time;
    //投资期限
    private String investTime;
    //备注
    private String remarks;

    //汇计划使用平台
    private String couponSystem;

    //汇计划优惠券使用项目期限
    private String projectExpiration;

    //汇计划优惠券名称
    private String couponName;

    //汇计划优惠券原始类型
    private Integer couponTypeStr;

    //汇计划优惠券使用期限
    private String endTime;

    //汇计划投资金额
    private String tenderQuota;

    //汇计划优惠券有效期
    private String couponAddTime;
    //汇计划优惠券有效期
    private String couponEndTime;
    //汇计划优惠券code
    private String couponUserCode;

    public String getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(String userCouponId) {
        this.userCouponId = userCouponId;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getOperationPlatform() {
        return operationPlatform;
    }

    public void setOperationPlatform(String operationPlatform) {
        this.operationPlatform = operationPlatform;
    }

    public String getCouponQuota() {
        return couponQuota;
    }

    public void setCouponQuota(String couponQuota) {
        this.couponQuota = couponQuota;
    }

    public String getInvestQuota() {
        return investQuota;
    }

    public void setInvestQuota(String investQuota) {
        this.investQuota = investQuota;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCouponSystem() {
        return couponSystem;
    }

    public void setCouponSystem(String couponSystem) {
        this.couponSystem = couponSystem;
    }

    public String getProjectExpiration() {
        return projectExpiration;
    }

    public void setProjectExpiration(String projectExpiration) {
        this.projectExpiration = projectExpiration;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getCouponTypeStr() {
        return couponTypeStr;
    }

    public void setCouponTypeStr(Integer couponTypeStr) {
        this.couponTypeStr = couponTypeStr;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTenderQuota() {
        return tenderQuota;
    }

    public void setTenderQuota(String tenderQuota) {
        this.tenderQuota = tenderQuota;
    }

    public String getCouponAddTime() {
        return couponAddTime;
    }

    public void setCouponAddTime(String couponAddTime) {
        this.couponAddTime = couponAddTime;
    }

    public String getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(String couponEndTime) {
        this.couponEndTime = couponEndTime;
    }

    public String getCouponUserCode() {
        return couponUserCode;
    }

    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }

    public String getCouponQuotaStr() {
        return couponQuotaStr;
    }

    public void setCouponQuotaStr(String couponQuotaStr) {
        this.couponQuotaStr = couponQuotaStr;
    }
}
