package com.hyjf.wbs.dto.trade.constomerinfo;

import com.hyjf.wbs.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: wxd
 * @Date: 2019-04-11 11:16
 * @Description:
 */

public class AccountDto extends BaseDto{
    @ApiModelProperty(value = "客户id")
    private Integer userId;

    @ApiModelProperty(value = "客户名")
    private String userName;

    @ApiModelProperty(value = "客户银行开户id")
    private String accountId;

    @ApiModelProperty(value = "客户总金额")
    private BigDecimal total;

    @ApiModelProperty(value = "客户可用余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "客户待收金额")
    private BigDecimal await;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getAwait() {
        return await;
    }

    public void setAwait(BigDecimal await) {
        this.await = await;
    }


}