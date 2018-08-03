/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

import com.hyjf.cs.user.result.BaseResultBean;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wangjun
 * @version WxSynBalanceResultBean, v0.1 2018/7/31 9:28
 */
public class WxSynBalanceResultBean extends BaseResultBean {
    @ApiModelProperty("余额数据")
    private String bankBalance;
    @ApiModelProperty("账户总额数据")
    private String bankTotal;

    public String getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(String bankBalance) {
        this.bankBalance = bankBalance;
    }

    public String getBankTotal() {
        return bankTotal;
    }

    public void setBankTotal(String bankTotal) {
        this.bankTotal = bankTotal;
    }
}
