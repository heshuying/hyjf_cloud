/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigRequest, v0.1 2018/9/26 15:47
 */
public class BailConfigRequest extends BasePage implements Serializable {

    /**
     * 分页变量
     */
    private int limitStart = -1;
    private int limitEnd = -1;

    private int initQuery;

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

    public int getInitQuery() {
        return initQuery;
    }

    public void setInitQuery(int initQuery) {
        this.initQuery = initQuery;
    }
}
