/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

/**
 * @author yaoyong
 * @version VipUpdateGradeListRequest, v0.1 2018/7/4 9:16
 */
public class VipUpdateGradeListRequest {
    private String userId;

    public int limit;

    private int paginatorPage = 1;
    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
