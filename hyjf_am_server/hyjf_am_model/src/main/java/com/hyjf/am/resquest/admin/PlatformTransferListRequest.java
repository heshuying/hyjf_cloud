/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: PlatformTransferListRequest, v0.1 2018/7/9 10:17
 */
public class PlatformTransferListRequest extends BasePage implements Serializable {
    // 用户名
    private String usernameSearch;
    // 订单号
    private String nidSearch;
    // 转账状态
    private String statusSearch;
    // 开始时间(检索用)
    private String startDate;

    // 结束时间(检索用)
    private String endDate;

    private static final long serialVersionUID = 1L;
    /**
     * 分页变量
     */
    private int limitStart = -1;
    private int limitEnd = -1;

    public String getUsernameSearch() {
        return usernameSearch;
    }

    public void setUsernameSearch(String usernameSearch) {
        this.usernameSearch = usernameSearch;
    }

    public String getNidSearch() {
        return nidSearch;
    }

    public void setNidSearch(String nidSearch) {
        this.nidSearch = nidSearch;
    }

    public String getStatusSearch() {
        return statusSearch;
    }

    public void setStatusSearch(String statusSearch) {
        this.statusSearch = statusSearch;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
