package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MerchantAccount implements Serializable {
    private Integer id;

    /**
     * 子账户名称
     *
     * @mbggenerated
     */
    private String subAccountName;

    /**
     * 子账户类型
     *
     * @mbggenerated
     */
    private String subAccountType;

    /**
     * 子账户代号
     *
     * @mbggenerated
     */
    private String subAccountCode;

    /**
     * 子账户转入:0 不支持 1 支持
     *
     * @mbggenerated
     */
    private Integer transferIntoFlg;

    /**
     * 子账户转出:0 不支持 1 支持
     *
     * @mbggenerated
     */
    private Integer transferOutFlg;

    /**
     * 余额下限(元)
     *
     * @mbggenerated
     */
    private Long balanceLowerLimit;

    /**
     * 自动转出:0 否 1 是
     *
     * @mbggenerated
     */
    private Integer autoTransferOut;

    /**
     * 自动转出:0 否 1 是
     *
     * @mbggenerated
     */
    private Integer autoTransferInto;

    /**
     * 转入比例
     *
     * @mbggenerated
     */
    private Integer transferIntoRatio;

    /**
     * 账户余额
     *
     * @mbggenerated
     */
    private BigDecimal accountBalance;

    /**
     * 可用余额
     *
     * @mbggenerated
     */
    private BigDecimal availableBalance;

    /**
     * 冻结金额
     *
     * @mbggenerated
     */
    private BigDecimal frost;

    /**
     * 用途
     *
     * @mbggenerated
     */
    private String purpose;

    /**
     * 排序
     *
     * @mbggenerated
     */
    private Short sort;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 最后修改时间
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

    public String getSubAccountName() {
        return subAccountName;
    }

    public void setSubAccountName(String subAccountName) {
        this.subAccountName = subAccountName == null ? null : subAccountName.trim();
    }

    public String getSubAccountType() {
        return subAccountType;
    }

    public void setSubAccountType(String subAccountType) {
        this.subAccountType = subAccountType == null ? null : subAccountType.trim();
    }

    public String getSubAccountCode() {
        return subAccountCode;
    }

    public void setSubAccountCode(String subAccountCode) {
        this.subAccountCode = subAccountCode == null ? null : subAccountCode.trim();
    }

    public Integer getTransferIntoFlg() {
        return transferIntoFlg;
    }

    public void setTransferIntoFlg(Integer transferIntoFlg) {
        this.transferIntoFlg = transferIntoFlg;
    }

    public Integer getTransferOutFlg() {
        return transferOutFlg;
    }

    public void setTransferOutFlg(Integer transferOutFlg) {
        this.transferOutFlg = transferOutFlg;
    }

    public Long getBalanceLowerLimit() {
        return balanceLowerLimit;
    }

    public void setBalanceLowerLimit(Long balanceLowerLimit) {
        this.balanceLowerLimit = balanceLowerLimit;
    }

    public Integer getAutoTransferOut() {
        return autoTransferOut;
    }

    public void setAutoTransferOut(Integer autoTransferOut) {
        this.autoTransferOut = autoTransferOut;
    }

    public Integer getAutoTransferInto() {
        return autoTransferInto;
    }

    public void setAutoTransferInto(Integer autoTransferInto) {
        this.autoTransferInto = autoTransferInto;
    }

    public Integer getTransferIntoRatio() {
        return transferIntoRatio;
    }

    public void setTransferIntoRatio(Integer transferIntoRatio) {
        this.transferIntoRatio = transferIntoRatio;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getFrost() {
        return frost;
    }

    public void setFrost(BigDecimal frost) {
        this.frost = frost;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
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