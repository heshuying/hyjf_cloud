/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: sunpeikai
 * @version: UserDirectRechargeResultBean, v0.1 2018/8/28 19:26
 */
@ApiModel(value = "用户充值返回参数(页面调用)")
public class UserDirectRechargeResultBean extends BaseResultBean {

    @ApiModelProperty(value = "交易金额")
    private String txAmount;

    @ApiModelProperty(value = "充值订单号")
    private String orderId;

    public String getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(String txAmount) {
        this.txAmount = txAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
