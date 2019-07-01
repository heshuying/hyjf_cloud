/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.response;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yaoyong
 * @version Interest, v0.1 2019/6/20 14:10
 */
public class Interest implements Serializable {

    private BigDecimal interest;

    private String time;

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
