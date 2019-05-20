/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.coupon;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author jun
 * @version CouponUserForAppCustomizeVO, v0.1 2018/7/3 17:25
 */
public class CouponUserForAppCustomizeVO extends BaseVO implements Serializable {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = -6645581267786667497L;

    //优惠券id
    private Integer id;

    //优惠券类型
    private String couponType;

    //优惠券类型原始字段值
    private String couponTypeOrigin;

    //优惠券名字
    private String couponName;

    //优惠券额度
    private String couponQuota;

    //优惠券额度原始字段值
    private String couponQuotaOrigin;

    //有效期截止日
    private String endTime;

    //出借额度条件
    private String tenderQuota;

    //优惠券详情url
    private String detailUrl;

    //优惠券状态
    private String couponStatus;

    //优惠券的使用项目类别
    private String projectType;

    //优惠券的使用平台0:全部，1：PC，2：微官网，3：Android，4：IOS
    private String operationPlatform;

    //出借金额
    private String tenderQuotaOrigin;

    //项目期限时长 以月为单位
    private String projectExpirationLength;

    //出借金额最小额度
    private String tenderQuotaMin;

    //出借金额最大额度
    private String tenderQuotaMax;

    //出借金额
    private String tenderQuotaValue;

    //出借金额
    private String investQuota;

    //出借金额最大额度
    private String time;

    //出借期限
    private String investTime;

    //项目期限类别0:不限，1;等于，2：期限范围，3：大于等于，4：小于等于
    private Integer projectExpirationType;

    //项目期限最短时长
    private Integer projectExpirationLengthMax;

    //项目期限最长时长
    private Integer projectExpirationLengthMin;

    //出借金额类别
    private Integer tenderQuotaType;

    //到期时间
    private String endTimeStamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponQuota() {
        return couponQuota;
    }

    public void setCouponQuota(String couponQuota) {
        this.couponQuota = couponQuota;
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

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getCouponTypeOrigin() {
        return couponTypeOrigin;
    }

    public void setCouponTypeOrigin(String couponTypeOrigin) {
        this.couponTypeOrigin = couponTypeOrigin;
    }

    public String getCouponQuotaOrigin() {
        return couponQuotaOrigin;
    }

    public void setCouponQuotaOrigin(String couponQuotaOrigin) {
        this.couponQuotaOrigin = couponQuotaOrigin;
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

    public String getTenderQuotaOrigin() {
        return tenderQuotaOrigin;
    }

    public void setTenderQuotaOrigin(String tenderQuotaOrigin) {
        this.tenderQuotaOrigin = tenderQuotaOrigin;
    }

    public String getProjectExpirationLength() {
        return projectExpirationLength;
    }

    public void setProjectExpirationLength(String projectExpirationLength) {
        this.projectExpirationLength = projectExpirationLength;
    }

    public String getTenderQuotaMin() {
        return tenderQuotaMin;
    }

    public void setTenderQuotaMin(String tenderQuotaMin) {
        this.tenderQuotaMin = tenderQuotaMin;
    }

    public String getTenderQuotaMax() {
        return tenderQuotaMax;
    }

    public String getInvestQuota() {
        return investQuota;
    }

    public void setInvestQuota(String investQuota) {
        this.investQuota = investQuota;
    }

    public void setTenderQuotaMax(String tenderQuotaMax) {
        this.tenderQuotaMax = tenderQuotaMax;
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

    public Integer getProjectExpirationType() {
        return projectExpirationType;
    }

    public void setProjectExpirationType(Integer projectExpirationType) {
        this.projectExpirationType = projectExpirationType;
    }

    public Integer getProjectExpirationLengthMax() {
        return projectExpirationLengthMax;
    }

    public void setProjectExpirationLengthMax(Integer projectExpirationLengthMax) {
        this.projectExpirationLengthMax = projectExpirationLengthMax;
    }

    public Integer getProjectExpirationLengthMin() {
        return projectExpirationLengthMin;
    }

    public void setProjectExpirationLengthMin(Integer projectExpirationLengthMin) {
        this.projectExpirationLengthMin = projectExpirationLengthMin;
    }

    public Integer getTenderQuotaType() {
        return tenderQuotaType;
    }

    public void setTenderQuotaType(Integer tenderQuotaType) {
        this.tenderQuotaType = tenderQuotaType;
    }

    public String getTenderQuotaValue() {
        return tenderQuotaValue;
    }

    public void setTenderQuotaValue(String tenderQuotaValue) {
        this.tenderQuotaValue = tenderQuotaValue;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }
}
