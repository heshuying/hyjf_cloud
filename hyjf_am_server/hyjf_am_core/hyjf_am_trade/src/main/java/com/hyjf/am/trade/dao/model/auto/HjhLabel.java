package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HjhLabel implements Serializable {
    private Integer id;

    /**
     * 标签名称,最长不超过10个字符
     *
     * @mbggenerated
     */
    private String labelName;

    /**
     * 期限开始时间
     *
     * @mbggenerated
     */
    private Integer labelTermStart;

    /**
     * 期限结束时间
     *
     * @mbggenerated
     */
    private Integer labelTermEnd;

    /**
     * 期限类型--日、月
     *
     * @mbggenerated
     */
    private String labelTermType;

    /**
     * 标的实际利率开始范围
     *
     * @mbggenerated
     */
    private BigDecimal labelAprStart;

    /**
     * 标的实际利率的结束范围
     *
     * @mbggenerated
     */
    private BigDecimal labelAprEnd;

    /**
     * 还款方式
     *
     * @mbggenerated
     */
    private String borrowStyle;

    /**
     * 还款方式名称
     *
     * @mbggenerated
     */
    private String borrowStyleName;

    /**
     * 标的实际支付金额
     *
     * @mbggenerated
     */
    private BigDecimal labelPaymentAccountStart;

    /**
     * 标的实际支付金额
     *
     * @mbggenerated
     */
    private BigDecimal labelPaymentAccountEnd;

    /**
     * 资产来源,机构编号
     *
     * @mbggenerated
     */
    private String instCode;

    /**
     * 资产来源,机构名称
     *
     * @mbggenerated
     */
    private String instName;

    /**
     * 机构产品类型
     *
     * @mbggenerated
     */
    private Integer assetType;

    /**
     * 机构产品类型名称
     *
     * @mbggenerated
     */
    private String assetTypeName;

    /**
     * 项目类型 0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标
     *
     * @mbggenerated
     */
    private Integer projectType;

    /**
     * 项目类型名称
     *
     * @mbggenerated
     */
    private String projectTypeName;

    /**
     * 标的是否发生债转 0:否 1：是
     *
     * @mbggenerated
     */
    private Integer isCredit;

    /**
     * 标的是否逾期  0:否 1：是
     *
     * @mbggenerated
     */
    private Integer isLate;

    /**
     * 债转次数不超过
     *
     * @mbggenerated
     */
    private Integer creditSumMax;

    /**
     * 推送时间开始范围
     *
     * @mbggenerated
     */
    private Date pushTimeStart;

    /**
     * 推送时间结束范围
     *
     * @mbggenerated
     */
    private Date pushTimeEnd;

    /**
     * 剩余天数开始范围
     *
     * @mbggenerated
     */
    private Integer remainingDaysStart;

    /**
     * 剩余天数结束范围
     *
     * @mbggenerated
     */
    private Integer remainingDaysEnd;

    /**
     * 启用状态 0：停用 1：启用
     *
     * @mbggenerated
     */
    private Integer labelState;

    /**
     * 创建用户
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 更新用户
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 删除标识
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
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

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName == null ? null : labelName.trim();
    }

    public Integer getLabelTermStart() {
        return labelTermStart;
    }

    public void setLabelTermStart(Integer labelTermStart) {
        this.labelTermStart = labelTermStart;
    }

    public Integer getLabelTermEnd() {
        return labelTermEnd;
    }

    public void setLabelTermEnd(Integer labelTermEnd) {
        this.labelTermEnd = labelTermEnd;
    }

    public String getLabelTermType() {
        return labelTermType;
    }

    public void setLabelTermType(String labelTermType) {
        this.labelTermType = labelTermType == null ? null : labelTermType.trim();
    }

    public BigDecimal getLabelAprStart() {
        return labelAprStart;
    }

    public void setLabelAprStart(BigDecimal labelAprStart) {
        this.labelAprStart = labelAprStart;
    }

    public BigDecimal getLabelAprEnd() {
        return labelAprEnd;
    }

    public void setLabelAprEnd(BigDecimal labelAprEnd) {
        this.labelAprEnd = labelAprEnd;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle == null ? null : borrowStyle.trim();
    }

    public String getBorrowStyleName() {
        return borrowStyleName;
    }

    public void setBorrowStyleName(String borrowStyleName) {
        this.borrowStyleName = borrowStyleName == null ? null : borrowStyleName.trim();
    }

    public BigDecimal getLabelPaymentAccountStart() {
        return labelPaymentAccountStart;
    }

    public void setLabelPaymentAccountStart(BigDecimal labelPaymentAccountStart) {
        this.labelPaymentAccountStart = labelPaymentAccountStart;
    }

    public BigDecimal getLabelPaymentAccountEnd() {
        return labelPaymentAccountEnd;
    }

    public void setLabelPaymentAccountEnd(BigDecimal labelPaymentAccountEnd) {
        this.labelPaymentAccountEnd = labelPaymentAccountEnd;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode == null ? null : instCode.trim();
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName == null ? null : instName.trim();
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public String getAssetTypeName() {
        return assetTypeName;
    }

    public void setAssetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName == null ? null : assetTypeName.trim();
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName == null ? null : projectTypeName.trim();
    }

    public Integer getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(Integer isCredit) {
        this.isCredit = isCredit;
    }

    public Integer getIsLate() {
        return isLate;
    }

    public void setIsLate(Integer isLate) {
        this.isLate = isLate;
    }

    public Integer getCreditSumMax() {
        return creditSumMax;
    }

    public void setCreditSumMax(Integer creditSumMax) {
        this.creditSumMax = creditSumMax;
    }

    public Date getPushTimeStart() {
        return pushTimeStart;
    }

    public void setPushTimeStart(Date pushTimeStart) {
        this.pushTimeStart = pushTimeStart;
    }

    public Date getPushTimeEnd() {
        return pushTimeEnd;
    }

    public void setPushTimeEnd(Date pushTimeEnd) {
        this.pushTimeEnd = pushTimeEnd;
    }

    public Integer getRemainingDaysStart() {
        return remainingDaysStart;
    }

    public void setRemainingDaysStart(Integer remainingDaysStart) {
        this.remainingDaysStart = remainingDaysStart;
    }

    public Integer getRemainingDaysEnd() {
        return remainingDaysEnd;
    }

    public void setRemainingDaysEnd(Integer remainingDaysEnd) {
        this.remainingDaysEnd = remainingDaysEnd;
    }

    public Integer getLabelState() {
        return labelState;
    }

    public void setLabelState(Integer labelState) {
        this.labelState = labelState;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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