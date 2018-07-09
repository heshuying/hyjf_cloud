/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.account;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: sunpeikai
 * @version: BankMerchantAccountVO, v0.1 2018/7/9 18:07
 */
public class BankMerchantAccountVO extends BaseVO implements Serializable {
    private Integer id;

    private String accountName;

    private String accountType;

    private String accountCode;

    private BigDecimal accountBalance;

    private BigDecimal availableBalance;

    private BigDecimal frost;

    private Date createTime;

    private Date updateTime;

    private Integer isSetPassword;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
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

    public Integer getIsSetPassword() {
        return isSetPassword;
    }

    public void setIsSetPassword(Integer isSetPassword) {
        this.isSetPassword = isSetPassword;
    }
}
