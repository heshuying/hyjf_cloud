/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;

import java.util.Date;

/**
 * @author tanyy
 * @version WhereaboutsPageRequestBean, v0.1 2018/7/20 14:23
 */
public class WhereaboutsPageRequestBean extends BaseRequest {

    private String utmName;
    /**
     * 检索条件 订单id
     */
    private String referrerName;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    public String getUtmName() {
        return utmName;
    }

    public void setUtmName(String utmName) {
        this.utmName = utmName;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
}
