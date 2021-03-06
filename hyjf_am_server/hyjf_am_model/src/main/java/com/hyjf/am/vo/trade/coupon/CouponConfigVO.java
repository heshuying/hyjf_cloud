/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.coupon;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.common.util.GetDate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yaoy
 * @version CouponConfigVO, v0.1 2018/6/22 10:31
 */
public class CouponConfigVO extends BaseVO implements Serializable {
    private Integer id;

    private String couponCode;

    private String couponName;

    private BigDecimal couponQuota;

    private Integer couponQuantity;

    private Short couponProfitTime;

    private Integer expirationType;

    private Integer expirationDate;

    private Integer expirationLength;

    private Short expirationLengthDay;

    private Integer addFlag;

    private String couponSystem;

    private Integer couponType;

    private String projectType;

    private Integer projectExpirationType;

    private Integer projectExpirationLength;

    private Integer projectExpirationLengthMin;

    private Integer projectExpirationLengthMax;

    private Integer tenderQuotaType;

    private Integer tenderQuota;

    private Integer tenderQuotaMin;

    private Integer tenderQuotaMax;

    private String content;

    private Integer status;

    private String auditContent;

    private String auditUser;

    private Integer auditTime;

    private Integer repayTimeConfig;

    private Integer delFlag;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private String expirationdate;

    private String audittime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode == null ? null : couponCode.trim();
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName == null ? null : couponName.trim();
    }

    public BigDecimal getCouponQuota() {
        return couponQuota;
    }

    public void setCouponQuota(BigDecimal couponQuota) {
        this.couponQuota = couponQuota;
    }

    public Integer getCouponQuantity() {
        return couponQuantity;
    }

    public void setCouponQuantity(Integer couponQuantity) {
        this.couponQuantity = couponQuantity;
    }

    public Short getCouponProfitTime() {
        return couponProfitTime;
    }

    public void setCouponProfitTime(Short couponProfitTime) {
        this.couponProfitTime = couponProfitTime;
    }

    public Integer getExpirationType() {
        return expirationType;
    }

    public void setExpirationType(Integer expirationType) {
        this.expirationType = expirationType;
    }

    public Integer getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Integer expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getExpirationLength() {
        return expirationLength;
    }

    public void setExpirationLength(Integer expirationLength) {
        this.expirationLength = expirationLength;
    }

    public Short getExpirationLengthDay() {
        return expirationLengthDay;
    }

    public void setExpirationLengthDay(Short expirationLengthDay) {
        this.expirationLengthDay = expirationLengthDay;
    }

    public Integer getAddFlag() {
        return addFlag;
    }

    public void setAddFlag(Integer addFlg) {
        this.addFlag = addFlg;
    }

    public String getCouponSystem() {
        return couponSystem;
    }

    public void setCouponSystem(String couponSystem) {
        this.couponSystem = couponSystem == null ? null : couponSystem.trim();
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
    }

    public Integer getProjectExpirationType() {
        return projectExpirationType;
    }

    public void setProjectExpirationType(Integer projectExpirationType) {
        this.projectExpirationType = projectExpirationType;
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

    public Integer getTenderQuotaType() {
        return tenderQuotaType;
    }

    public void setTenderQuotaType(Integer tenderQuotaType) {
        this.tenderQuotaType = tenderQuotaType;
    }

    public Integer getTenderQuota() {
        return tenderQuota;
    }

    public void setTenderQuota(Integer tenderQuota) {
        this.tenderQuota = tenderQuota;
    }

    public Integer getTenderQuotaMin() {
        return tenderQuotaMin;
    }

    public void setTenderQuotaMin(Integer tenderQuotaMin) {
        this.tenderQuotaMin = tenderQuotaMin;
    }

    public Integer getTenderQuotaMax() {
        return tenderQuotaMax;
    }

    public void setTenderQuotaMax(Integer tenderQuotaMax) {
        this.tenderQuotaMax = tenderQuotaMax;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuditContent() {
        return auditContent;
    }

    public void setAuditContent(String auditContent) {
        this.auditContent = auditContent == null ? null : auditContent.trim();
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser == null ? null : auditUser.trim();
    }

    public Integer getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Integer auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getRepayTimeConfig() {
        return repayTimeConfig;
    }

    public void setRepayTimeConfig(Integer repayTimeConfig) {
        this.repayTimeConfig = repayTimeConfig;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlg) {
        this.delFlag = delFlg;
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

    public String getExpirationdate() {
        if (expirationDate != null) {
            return GetDate.timestamptoStrYYYYMMDD(expirationDate);
        }
        return null;
    }

    public void setExpirationdate(String expirationdate) {
        this.expirationdate = expirationdate;
    }

    public String getAudittime() {
        if (auditTime != null) {
            return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(auditTime);
        }
        return null;
    }

    public void setAudittime(String audittime) {
        this.audittime = audittime;
    }
}
