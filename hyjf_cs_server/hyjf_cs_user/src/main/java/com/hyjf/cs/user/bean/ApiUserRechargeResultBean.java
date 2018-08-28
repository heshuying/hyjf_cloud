/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: ApiUserRechargeResultBean, v0.1 2018/8/28 10:27
 */
@ApiModel(value = "api端-用户充值返回参数")
public class ApiUserRechargeResultBean extends BaseResultBean implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8988592724638111679L;

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
