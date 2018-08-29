/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

/**
 * @author: sunpeikai
 * @version: UserDirectRechargeResultBean, v0.1 2018/8/28 19:26
 */
public class UserDirectRechargeResultBean extends BaseResultBean {
    /**
     * 交易金额
     */
    private String txAmount;
    // 充值订单号
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
