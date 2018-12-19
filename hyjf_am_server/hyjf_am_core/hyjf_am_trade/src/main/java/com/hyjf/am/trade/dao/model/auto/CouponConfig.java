package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CouponConfig implements Serializable {
    /**
     * 主键id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 优惠券编号，显示用
     *
     * @mbggenerated
     */
    private String couponCode;

    /**
     * 优惠券名称
     *
     * @mbggenerated
     */
    private String couponName;

    /**
     * 优惠券额度
     *
     * @mbggenerated
     */
    private BigDecimal couponQuota;

    /**
     * 发行数量
     *
     * @mbggenerated
     */
    private Integer couponQuantity;

    /**
     * 收益期限，以天为单位
     *
     * @mbggenerated
     */
    private Short couponProfitTime;

    /**
     * 优惠券有效期类别
     *
     * @mbggenerated
     */
    private Integer expirationType;

    /**
     * 优惠券有效期截止日
     *
     * @mbggenerated
     */
    private Integer expirationDate;

    /**
     * 优惠券有效期时长
     *
     * @mbggenerated
     */
    private Integer expirationLength;

    /**
     * 固定期限时长（日），以日为单位的期限
     *
     * @mbggenerated
     */
    private Short expirationLengthDay;

    /**
     * 是否与本金出借共用，0：共用，1：单独使用
     *
     * @mbggenerated
     */
    private Integer addFlag;

    /**
     * 优惠券的使用平台0:全部，1：PC，2：微官网，3：Android，4：IOS
     *
     * @mbggenerated
     */
    private String couponSystem;

    /**
     * 优惠券的类别 1：体验金，2：加息券
     *
     * @mbggenerated
     */
    private Integer couponType;

    /**
     * 优惠券的使用项目类别
     *
     * @mbggenerated
     */
    private String projectType;

    /**
     * 项目期限类别0:不限，1;等于，2：期限范围，3：大于等于，4：小于等于
     *
     * @mbggenerated
     */
    private Integer projectExpirationType;

    /**
     * 项目期限时长  以月为单位
     *
     * @mbggenerated
     */
    private Integer projectExpirationLength;

    /**
     * 项目期限最短时长
     *
     * @mbggenerated
     */
    private Integer projectExpirationLengthMin;

    /**
     * 项目期限最长时长
     *
     * @mbggenerated
     */
    private Integer projectExpirationLengthMax;

    /**
     * 出借金额类别
     *
     * @mbggenerated
     */
    private Integer tenderQuotaType;

    /**
     * 出借金额
     *
     * @mbggenerated
     */
    private Integer tenderQuota;

    /**
     * 出借金额最小额度
     *
     * @mbggenerated
     */
    private Integer tenderQuotaMin;

    /**
     * 出借金额最大额度
     *
     * @mbggenerated
     */
    private Integer tenderQuotaMax;

    /**
     * 优惠券描述
     *
     * @mbggenerated
     */
    private String content;

    /**
     * 1：待审核，2：已发行，3：审核不通过
     *
     * @mbggenerated
     */
    private Integer status;

    private String auditContent;

    private String auditUser;

    private Integer auditTime;

    /**
     * 收益还款时间配置： 1 项目到期还款  2 收益期限到期还款
     *
     * @mbggenerated
     */
    private Integer repayTimeConfig;

    /**
     * 删除标识 0：未删除，1：已删除
     *
     * @mbggenerated
     */
    private Integer delFlag;

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
     * 修改时间
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

    public void setAddFlag(Integer addFlag) {
        this.addFlag = addFlag;
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

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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