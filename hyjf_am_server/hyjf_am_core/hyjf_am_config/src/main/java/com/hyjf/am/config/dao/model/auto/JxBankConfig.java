package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class JxBankConfig implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 银行ID
     *
     * @mbggenerated
     */
    private Integer bankId;

    /**
     * 银行名称
     *
     * @mbggenerated
     */
    private String bankName;

    /**
     * 银行总行的行联号
     *
     * @mbggenerated
     */
    private String payAllianceCode;

    /**
     * 银行代码
     *
     * @mbggenerated
     */
    private String bankCode;

    /**
     * 银行icon
     *
     * @mbggenerated
     */
    private String bankIcon;

    /**
     * 银行logo
     *
     * @mbggenerated
     */
    private String bankLogo;

    /**
     * 支持快捷支付：0 不支持  1 支持
     *
     * @mbggenerated
     */
    private Integer quickPayment;

    /**
     * 单笔限额
     *
     * @mbggenerated
     */
    private BigDecimal singleQuota;

    /**
     * 单卡单日限额
     *
     * @mbggenerated
     */
    private BigDecimal singleCardQuota;

    /**
     * 提现手续费
     *
     * @mbggenerated
     */
    private BigDecimal feeWithdraw;

    /**
     * 排序id
     *
     * @mbggenerated
     */
    private Short sortId;

    /**
     * 备注说明
     *
     * @mbggenerated
     */
    private String remark;

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

    /**
     * 删除标识
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 单月限额
     *
     * @mbggenerated
     */
    private BigDecimal monthCardQuota;

    /**
     * 银行android应用市场链接
     *
     * @mbggenerated
     */
    private String androidMarket;

    /**
     * 银行ios应用市场链接
     *
     * @mbggenerated
     */
    private String iosMarket;

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

    public String getAndroidMarket() {
        return androidMarket;
    }

    public void setAndroidMarket(String androidMarket) {
        this.androidMarket = androidMarket == null ? null : androidMarket.trim();
    }

    public String getIosMarket() {
        return iosMarket;
    }

    public void setIosMarket(String iosMarket) {
        this.iosMarket = iosMarket == null ? null : iosMarket.trim();
    }
}