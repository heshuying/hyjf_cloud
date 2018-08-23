package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class CardBin implements Serializable {
    private Integer id;

    private String issuingBankName;

    private String cardName;

    private Integer cardLength;

    private String cardNumFormat;

    private Integer binLength;

    private String binValue;

    private String cardType;

    private String bankId;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIssuingBankName() {
        return issuingBankName;
    }

    public void setIssuingBankName(String issuingBankName) {
        this.issuingBankName = issuingBankName == null ? null : issuingBankName.trim();
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName == null ? null : cardName.trim();
    }

    public Integer getCardLength() {
        return cardLength;
    }

    public void setCardLength(Integer cardLength) {
        this.cardLength = cardLength;
    }

    public String getCardNumFormat() {
        return cardNumFormat;
    }

    public void setCardNumFormat(String cardNumFormat) {
        this.cardNumFormat = cardNumFormat == null ? null : cardNumFormat.trim();
    }

    public Integer getBinLength() {
        return binLength;
    }

    public void setBinLength(Integer binLength) {
        this.binLength = binLength;
    }

    public String getBinValue() {
        return binValue;
    }

    public void setBinValue(String binValue) {
        this.binValue = binValue == null ? null : binValue.trim();
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
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