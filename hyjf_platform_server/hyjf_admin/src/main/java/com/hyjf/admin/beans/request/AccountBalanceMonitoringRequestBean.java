package com.hyjf.admin.beans.request;

import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author by xiehuili on 2018/7/13.
 */
public class AccountBalanceMonitoringRequestBean {
    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = -1967959761793508714L;

    @ApiModelProperty(value = "ids")
    private String ids;

    @ApiModelProperty(value = "是否更新 ")
    private boolean isUpdateFlg;

    @ApiModelProperty(value = "子账户名称(检索用) ")
    private String subAccountNameSear;

    @ApiModelProperty(value = "子账户类型(检索用)")
    private String subAccountTypeSear;

    @ApiModelProperty(value = "列表数据JSON")
    private String balanceDataJson;

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

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public boolean isUpdateFlg() {
        return isUpdateFlg;
    }

    public void setUpdateFlg(boolean updateFlg) {
        isUpdateFlg = updateFlg;
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

    public String getBalanceDataJson() {
        return balanceDataJson;
    }

    public void setBalanceDataJson(String balanceDataJson) {
        this.balanceDataJson = balanceDataJson;
    }

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

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;

    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private Paginator paginator;

    public int getPaginatorPage() {
        return paginatorPage == 0 ? 1 : paginatorPage;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }



}
