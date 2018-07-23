/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response;

/**
 * @author liubin
 * @version MapResponse, v0.1 2018/7/10 18:22
 */
public class IntegerResponse extends Response{
    Integer resultInt;

    public IntegerResponse() {
    }

    public IntegerResponse(Integer resultInt) {
        this.resultInt = resultInt;
    }

    public Integer getResultInt() {
        return resultInt;
    }

    public void setResultInt(Integer resultInt) {
        this.resultInt = resultInt;
    }
}

