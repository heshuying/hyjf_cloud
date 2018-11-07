package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class BankMerchantAccountInfo implements Serializable {
    private Integer id;

    /**
     * 平台子账户电子账户
     *
     * @mbggenerated
     */
    private String accountCode;

    /**
     * 银行开户名称
     *
     * @mbggenerated
     */
    private String accountName;

    /**
     * 银行开户手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 证件号
     *
     * @mbggenerated
     */
    private String idNo;

    /**
     * 证件类型
     *
     * @mbggenerated
     */
    private String idType;

    /**
     * 绑定银行卡号
     *
     * @mbggenerated
     */
    private String bankCard;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode == null ? null : accountCode.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType == null ? null : idType.trim();
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard == null ? null : bankCard.trim();
    }
}