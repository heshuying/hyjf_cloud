package com.hyjf.am.vo.trade.coupon;

import com.hyjf.am.vo.BaseVO;

/**
 * 我的优惠券列表
 * @author hesy
 * @version MyCouponListCustomizeVO, v0.1 2018/6/22 16:35
 */
public class MyCouponListCustomizeVO extends BaseVO {
    private Integer id;

    private String couponUserCode;

    private String couponName;

    private String couponProfitTime;

    private String couponType;

    private String couponTypeName;

    private String couponQuota;

    private String addTime;

    private String endTime;

    private String endTimeStamp;

    private String couponSystem;

    private String projectType;

    private String tenderQuota;

    private String projectExpirationType;

    private Double tenderQuotaMin;

    private Double tenderQuotaMax;

    private Integer expirationType;

    private Integer tenderQuotaType;

    private Integer projectExpirationLength;

    private Integer projectExpirationLengthMin;

    private Integer projectExpirationLengthMax;

    //是否与本金出借共用，0：共用，1：单独使用
    private Integer addFlag;

    private String tenderQuotaAmount;

    //优惠券使用标识 0：未使用，1：已使用，2：审核不通过，3：待审核，4：已失效
    private Integer usedFlag;

    private String time;
    //0新手   1散标  2计划与前端约定去使用跳转地址标识
    private Integer hrefType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCouponUserCode() {
        return couponUserCode;
    }

    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponProfitTime() {
        return couponProfitTime;
    }

    public void setCouponProfitTime(String couponProfitTime) {
        this.couponProfitTime = couponProfitTime;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponTypeName() {
        return couponTypeName;
    }

    public void setCouponTypeName(String couponTypeName) {
        this.couponTypeName = couponTypeName;
    }

    public String getCouponQuota() {
        return couponQuota;
    }

    public void setCouponQuota(String couponQuota) {
        this.couponQuota = couponQuota;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCouponSystem() {
        return couponSystem;
    }

    public void setCouponSystem(String couponSystem) {
        this.couponSystem = couponSystem;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getTenderQuota() {
        return tenderQuota;
    }

    public void setTenderQuota(String tenderQuota) {
        this.tenderQuota = tenderQuota;
    }

    public String getProjectExpirationType() {
        return projectExpirationType;
    }

    public void setProjectExpirationType(String projectExpirationType) {
        this.projectExpirationType = projectExpirationType;
    }

    public Integer getUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(Integer usedFlag) {
        this.usedFlag = usedFlag;
    }

    public Double getTenderQuotaMin() {
        return tenderQuotaMin;
    }

    public void setTenderQuotaMin(Double tenderQuotaMin) {
        this.tenderQuotaMin = tenderQuotaMin;
    }

    public Double getTenderQuotaMax() {
        return tenderQuotaMax;
    }

    public void setTenderQuotaMax(Double tenderQuotaMax) {
        this.tenderQuotaMax = tenderQuotaMax;
    }

    public Integer getExpirationType() {
        return expirationType;
    }

    public void setExpirationType(Integer expirationType) {
        this.expirationType = expirationType;
    }

    public Integer getProjectExpirationLength() {
        return projectExpirationLength;
    }

    public void setProjectExpirationLength(Integer projectExpirationLength) {
        this.projectExpirationLength = projectExpirationLength;
    }

    public Integer getProjectExpirationLengthMin() {
        return projectExpirationLengthMin;
    }

    public void setProjectExpirationLengthMin(Integer projectExpirationLengthMin) {
        this.projectExpirationLengthMin = projectExpirationLengthMin;
    }

    public Integer getProjectExpirationLengthMax() {
        return projectExpirationLengthMax;
    }

    public void setProjectExpirationLengthMax(Integer projectExpirationLengthMax) {
        this.projectExpirationLengthMax = projectExpirationLengthMax;
    }

    public String getTenderQuotaAmount() {
        return tenderQuotaAmount;
    }

    public void setTenderQuotaAmount(String tenderQuotaAmount) {
        this.tenderQuotaAmount = tenderQuotaAmount;
    }

    public Integer getAddFlag() {
        return addFlag;
    }

    public void setAddFlag(Integer addFlag) {
        this.addFlag = addFlag;
    }

    public Integer getTenderQuotaType() {
        return tenderQuotaType;
    }

    public void setTenderQuotaType(Integer tenderQuotaType) {
        this.tenderQuotaType = tenderQuotaType;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getHrefType() {
        return hrefType;
    }

    public void setHrefType(Integer hrefType) {
        this.hrefType = hrefType;
    }
}
