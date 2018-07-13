package com.hyjf.admin.beans.request;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author by xiehuili on 2018/7/12.
 */
public class MerchantAccountRequestBean  implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 7768418442884796575L;

    private String ids;


    /** 子账户名称(检索用) */
    private String subAccountNameSear;

    /** 子账户类型(检索用) */
    private String subAccountTypeSear;
    private Integer id;

    private String subAccountName;

    private String subAccountType;

    private String subAccountCode;

    private Integer transferIntoFlg;

    private Integer transferOutFlg;

    private Long balanceLowerLimit;

    private Integer autoTransferOut;

    private Integer autoTransferInto;

    private Integer transferIntoRatio;

    private BigDecimal accountBalance;

    private BigDecimal availableBalance;

    private BigDecimal frost;

    private String purpose;

    private Short sort;

    private Integer createTime;

    private Integer updateTime;

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;


    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubAccountNameSear() {
        return subAccountNameSear;
    }

    public void setSubAccountNameSear(String subAccountNameSear) {
        this.subAccountNameSear = subAccountNameSear;
    }

    public String getSubAccountTypeSear() {
        return subAccountTypeSear;
    }

    public void setSubAccountTypeSear(String subAccountTypeSear) {
        this.subAccountTypeSear = subAccountTypeSear;
    }

    public String getSubAccountName() {
        return subAccountName;
    }

    public void setSubAccountName(String subAccountName) {
        this.subAccountName = subAccountName;
    }

    public String getSubAccountType() {
        return subAccountType;
    }

    public void setSubAccountType(String subAccountType) {
        this.subAccountType = subAccountType;
    }

    public String getSubAccountCode() {
        return subAccountCode;
    }

    public void setSubAccountCode(String subAccountCode) {
        this.subAccountCode = subAccountCode;
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
        this.purpose = purpose;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }
}
