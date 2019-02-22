package com.hyjf.am.vo.trade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author by nixiaol on 2018/7/24
 */
public class JxBankConfigVO implements Serializable {
    private Integer id;

    private Integer bankId;

    private String bankName;

    private String payAllianceCode;

    private String bankCode;

    private String bankIcon;

    private String bankLogo;

    private Integer quickPayment;

    private BigDecimal singleQuota;

    private BigDecimal singleCardQuota;

    private BigDecimal feeWithdraw;

    private Short sortId;

    private String remark;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private Integer delFlag;

    private BigDecimal monthCardQuota;

    //导出专用
    private String monthCardQuotaStr;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getPayAllianceCode() {
        return payAllianceCode;
    }

    public void setPayAllianceCode(String payAllianceCode) {
        this.payAllianceCode = payAllianceCode == null ? null : payAllianceCode.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getBankIcon() {
        return bankIcon;
    }

    public void setBankIcon(String bankIcon) {
        this.bankIcon = bankIcon == null ? null : bankIcon.trim();
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo == null ? null : bankLogo.trim();
    }

    public Integer getQuickPayment() {
        return quickPayment;
    }

    public void setQuickPayment(Integer quickPayment) {
        this.quickPayment = quickPayment;
    }

    public BigDecimal getSingleQuota() {
        return singleQuota;
    }

    public void setSingleQuota(BigDecimal singleQuota) {
        this.singleQuota = singleQuota;
    }

    public BigDecimal getSingleCardQuota() {
        return singleCardQuota;
    }

    public void setSingleCardQuota(BigDecimal singleCardQuota) {
        this.singleCardQuota = singleCardQuota;
    }

    public BigDecimal getFeeWithdraw() {
        return feeWithdraw;
    }

    public void setFeeWithdraw(BigDecimal feeWithdraw) {
        this.feeWithdraw = feeWithdraw;
    }

    public Short getSortId() {
        return sortId;
    }

    public void setSortId(Short sortId) {
        this.sortId = sortId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public BigDecimal getMonthCardQuota() {
        return monthCardQuota;
    }

    public void setMonthCardQuota(BigDecimal monthCardQuota) {
        this.monthCardQuota = monthCardQuota;
    }

    public String getMonthCardQuotaStr() {
        return monthCardQuotaStr;
    }

    public void setMonthCardQuotaStr(String monthCardQuotaStr) {
        this.monthCardQuotaStr = monthCardQuotaStr;
    }

    @Override
    public String toString() {
        return "JxBankConfigVO{" +
                "id=" + id +
                ", bankId=" + bankId +
                ", bankName='" + bankName + '\'' +
                ", payAllianceCode='" + payAllianceCode + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", bankIcon='" + bankIcon + '\'' +
                ", bankLogo='" + bankLogo + '\'' +
                ", quickPayment=" + quickPayment +
                ", singleQuota=" + singleQuota +
                ", singleCardQuota=" + singleCardQuota +
                ", feeWithdraw=" + feeWithdraw +
                ", sortId=" + sortId +
                ", remark='" + remark + '\'' +
                ", createUserId=" + createUserId +
                ", updateUserId=" + updateUserId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                ", monthCardQuota=" + monthCardQuota +
                '}';
    }
}