package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

public class BanksConfigVO extends BaseVO implements Serializable {
    private Integer id;

    private Integer bankId;

    private String bankName;

    private String name;

    private String payAllianceCode;

    private String bankCode;

    private String bankIcon;

    private String bankLogo;

    private Integer quickPayment;

    private BigDecimal singleQuota;

    private BigDecimal singleCardQuota;

    private BigDecimal feeWithdraw;

    private Integer sortId;

    private String remark;

    private Integer createUser;

    private String createTime;

    private Integer updateUser;

    private String updateTime;

    private Integer delFlg;

    private BigDecimal monthCardQuota;

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

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    public BigDecimal getMonthCardQuota() {
        return monthCardQuota;
    }

    public void setMonthCardQuota(BigDecimal monthCardQuota) {
        this.monthCardQuota = monthCardQuota;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}