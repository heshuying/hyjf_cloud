package com.hyjf.am.vo.activity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xiasq
 * @version UserTenderVO, v0.1 2019/4/30 15:52
 */
public class UserTenderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int userId;

    private String mobile;

    private BigDecimal amount;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
