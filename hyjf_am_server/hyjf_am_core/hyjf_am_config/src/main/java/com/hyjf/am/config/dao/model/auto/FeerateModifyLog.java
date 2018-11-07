package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FeerateModifyLog implements Serializable {
    /**
     * 变更日志id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 机构编号-- 资产来源：10000001 关联 hyjf_hjh_asset_type 查询
     *
     * @mbggenerated
     */
    private String instCode;

    /**
     * 产品类型：1 关联 hyjf_hjh_asset_type 查询
     *
     * @mbggenerated
     */
    private Integer assetType;

    /**
     * 借款期限 60 或 3 
     *
     * @mbggenerated
     */
    private Integer borrowPeriod;

    /**
     * 还款方式 - endmonth
     *
     * @mbggenerated
     */
    private String borrowStyle;

    /**
     * 自动发标利率
     *
     * @mbggenerated
     */
    private BigDecimal borrowApr;

    /**
     * 服务费
     *
     * @mbggenerated
     */
    private String serviceFee;

    /**
     * 管理费
     *
     * @mbggenerated
     */
    private String manageFee;

    /**
     * 收益差率
     *
     * @mbggenerated
     */
    private String revenueDiffRate;

    /**
     * 逾期利率(汇计划用)
     *
     * @mbggenerated
     */
    private String lateInterestRate;

    /**
     * 逾期免息天数(汇计划用)
     *
     * @mbggenerated
     */
    private Integer lateFreeDays;

    /**
     * 状态：0 启用，1 禁用
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 修改类型 0:全部 1：增加 2:修改 3:删除
     *
     * @mbggenerated
     */
    private Integer modifyType;

    /**
     * 删除标识
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

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode == null ? null : instCode.trim();
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public Integer getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(Integer borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle == null ? null : borrowStyle.trim();
    }

    public BigDecimal getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(BigDecimal borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee == null ? null : serviceFee.trim();
    }

    public String getManageFee() {
        return manageFee;
    }

    public void setManageFee(String manageFee) {
        this.manageFee = manageFee == null ? null : manageFee.trim();
    }

    public String getRevenueDiffRate() {
        return revenueDiffRate;
    }

    public void setRevenueDiffRate(String revenueDiffRate) {
        this.revenueDiffRate = revenueDiffRate == null ? null : revenueDiffRate.trim();
    }

    public String getLateInterestRate() {
        return lateInterestRate;
    }

    public void setLateInterestRate(String lateInterestRate) {
        this.lateInterestRate = lateInterestRate == null ? null : lateInterestRate.trim();
    }

    public Integer getLateFreeDays() {
        return lateFreeDays;
    }

    public void setLateFreeDays(Integer lateFreeDays) {
        this.lateFreeDays = lateFreeDays;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getModifyType() {
        return modifyType;
    }

    public void setModifyType(Integer modifyType) {
        this.modifyType = modifyType;
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