/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.wrb;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;

/**
 * @author fq
 * @version WrbBorrowTenderCustomizeVO, v0.1 2018/9/25 11:42
 */
public class WrbBorrowTenderCustomizeVO extends BaseVO {
    // 出借订单号
    private String nid;
    // 出借用户id
    private String userId;
    // 出借用户名
    private String username;
    // 出借金额
    private BigDecimal account;
    //出借时间
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
