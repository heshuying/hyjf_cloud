package com.hyjf.am.trade.dao.model.customize;

import java.math.BigDecimal;

/**
 * @Description 优惠券相关
 * @Author sunss
 * @Version v0.1
 * @Date 2018/6/19 17:08
 */
public class CouponCustomize {
    private Integer id;

    private String couponCode;

    private String couponName;

    private BigDecimal couponQuota;

    private Integer couponQuantity;

    private Integer couponProfitTime;

    private Integer expirationType;

    private Integer expirationDate;

    private Integer expirationLength;

    private Integer expirationLengthDay;

    private Integer addFlg;

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

    private Integer addTime;

    private String addUser;

    private Integer updateTime;

    private String updateUser;

    private Integer delFlg;

    // 使用截止时间
    private int endTime;
    // 优惠券状态
    private int usedFlag;
    // 更新时间
    private int userUpdateTime;
    // 发放给用户的优惠券编号
    private String couponUserCode;

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
        this.couponCode = couponCode;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
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

    public Integer getCouponProfitTime() {
        return couponProfitTime;
    }

    public void setCouponProfitTime(Integer couponProfitTime) {
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

    public Integer getExpirationLengthDay() {
        return expirationLengthDay;
    }

    public void setExpirationLengthDay(Integer expirationLengthDay) {
        this.expirationLengthDay = expirationLengthDay;
    }

    public Integer getAddFlg() {
        return addFlg;
    }

    public void setAddFlg(Integer addFlg) {
        this.addFlg = addFlg;
    }

    public String getCouponSystem() {
        return couponSystem;
    }

    public void setCouponSystem(String couponSystem) {
        this.couponSystem = couponSystem;
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
        this.projectType = projectType;
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
        this.content = content;
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
        this.auditContent = auditContent;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
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

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(int usedFlag) {
        this.usedFlag = usedFlag;
    }

    public int getUserUpdateTime() {
        return userUpdateTime;
    }

    public void setUserUpdateTime(int userUpdateTime) {
        this.userUpdateTime = userUpdateTime;
    }

    public String getCouponUserCode() {
        return couponUserCode;
    }

    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }
}
