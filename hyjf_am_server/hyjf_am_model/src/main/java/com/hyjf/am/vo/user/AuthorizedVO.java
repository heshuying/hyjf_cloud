/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.user;

/**
 * @author zhangqingqing
 * @version AuthorizedVO, v0.1 2018/6/19 12:48
 */
public class AuthorizedVO {
    private String platform;
    private String lastSrvAuthCode;
    private String smsCode;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getLastSrvAuthCode() {
        return lastSrvAuthCode;
    }

    public void setLastSrvAuthCode(String lastSrvAuthCode) {
        this.lastSrvAuthCode = lastSrvAuthCode;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
