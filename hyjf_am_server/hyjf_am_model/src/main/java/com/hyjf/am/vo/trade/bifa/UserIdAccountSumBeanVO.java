/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.bifa;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jun
 * @version BifaIndexResultBean, v0.1 2018/12/11 11:41
 */
public class UserIdAccountSumBeanVO implements Serializable {

    //出借人userId
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
