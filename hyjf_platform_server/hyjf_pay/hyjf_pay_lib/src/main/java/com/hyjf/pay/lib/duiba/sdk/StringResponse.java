/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.pay.lib.duiba.sdk;

/**
 * @author liubin
 * @version StringResponse, v0.1 2018/8/6 20:51
 */
public class StringResponse extends Response{
    String resultStr;

    public StringResponse() {
    }

    public StringResponse(String resultStr) {
        this.resultStr = resultStr;
    }

    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }
}
