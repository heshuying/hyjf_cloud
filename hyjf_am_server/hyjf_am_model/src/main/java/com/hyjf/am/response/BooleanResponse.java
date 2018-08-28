/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response;

/**
 * @author wangjun
 * @version BooleanResponse, v0.1 2018/8/27 18:11
 */
public class BooleanResponse extends Response{
    Boolean resultBoolean;

    public BooleanResponse() {
    }

    public BooleanResponse(Boolean resultBoolean) {
        this.resultBoolean = resultBoolean;
    }

    public Boolean getResultBoolean() {
        return resultBoolean;
    }

    public void setResultBoolean(Boolean resultBoolean) {
        this.resultBoolean = resultBoolean;
    }
}

