/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response;

import java.math.BigDecimal;

/**
 * @author liubin
 * @version BigDecimalResponse, v0.1 2018/8/6 20:37
 */
public class BigDecimalResponse extends Response{
    BigDecimal resultDec;

    public BigDecimalResponse() {
    }

    public BigDecimalResponse(BigDecimal resultDec) {
        this.resultDec = resultDec;
    }

    public BigDecimal getResultDec() {
        return resultDec;
    }

    public void setResultDec(BigDecimal resultDec) {
        this.resultDec = resultDec;
    }
}
