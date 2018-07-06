/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

/**
 * @author yaoyong
 * @version BatchCouponsRequest, v0.1 2018/7/6 18:28
 */
public class BatchCouponsRequest {
    private String userId;

    private String timestamp;

    private String chkValue;

    private String usercoupons;

    private String requestUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getChkValue() {
        return chkValue;
    }

    public void setChkValue(String chkValue) {
        this.chkValue = chkValue;
    }

    public String getUsercoupons() {
        return usercoupons;
    }

    public void setUsercoupons(String usercoupons) {
        this.usercoupons = usercoupons;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}
