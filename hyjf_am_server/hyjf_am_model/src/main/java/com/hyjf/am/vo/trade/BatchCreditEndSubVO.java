/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author liubin
 * @version BatchCreditEndSubVO, v0.1 2018/7/11 14:13
 */
public class BatchCreditEndSubVO extends BaseVO implements Serializable {
    private String accountId;//电子账号

    private String orderId;//订单号

    private String forAccountId;//对手电子账号

    private String productId;//标的号

    private String authCode;//授权码

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getForAccountId() {
        return forAccountId;
    }

    public void setForAccountId(String forAccountId) {
        this.forAccountId = forAccountId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
