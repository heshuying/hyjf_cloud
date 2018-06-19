/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

/**
 * @author zhangqingqing
 * @version BankSmsLogRequest, v0.1 2018/6/15 19:29
 */
public class BankSmsLogRequest {
    private Integer userId;
    private String srvTxCode;
    private String srvAuthCode;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSrvTxCode() {
        return srvTxCode;
    }

    public void setSrvTxCode(String srvTxCode) {
        this.srvTxCode = srvTxCode;
    }

    public String getSrvAuthCode() {
        return srvAuthCode;
    }

    public void setSrvAuthCode(String srvAuthCode) {
        this.srvAuthCode = srvAuthCode;
    }
}
