/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.bifa;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jun
 * @version BifaIndexResultBean, v0.1 2018/12/11 11:41
 */
public class UserIdAccountSumBean implements Serializable {

    //出借人userId
    private Integer userId;
    //出借金額
    private BigDecimal account;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }
}
