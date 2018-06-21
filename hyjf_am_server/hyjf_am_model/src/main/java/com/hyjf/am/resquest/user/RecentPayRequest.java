/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

/**
 * @author zhangqingqing
 * @version RecentPayRequest, v0.1 2018/6/21 15:36
 */
public class RecentPayRequest {
    private Integer userId;
    private String limitStart;
    private String limitEnd;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(String limitStart) {
        this.limitStart = limitStart;
    }

    public String getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(String limitEnd) {
        this.limitEnd = limitEnd;
    }
}
