/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize;

import java.math.BigDecimal;

/**
 * @author fq
 * @version WrbBorrowTenderCustomize, v0.1 2018/9/25 11:40
 */
public class WrbBorrowTenderCustomize {
    // 投资订单号
    private String nid;
    // 投资用户id
    private String userId;
    // 投资用户名
    private String username;
    // 投资金额
    private BigDecimal account;
    //投资时间
    private String investTime;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }
}
