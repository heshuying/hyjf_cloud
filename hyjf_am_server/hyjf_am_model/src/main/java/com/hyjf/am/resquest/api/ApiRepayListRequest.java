package com.hyjf.am.resquest.api;

import com.hyjf.am.vo.BasePage;
import com.hyjf.common.paginator.Paginator;

import java.io.Serializable;

/**
 * @version ApiRepayListRequest, v0.1 2018/9/1 15:10
 * @Author: Zha Daojian
 */
public class ApiRepayListRequest extends BasePage implements Serializable {

    /*机构编号（必填）*/
    private String instCode;
    /*开始时间（必填）*/
    private String startTime;
    /*结束时间（必填）*/
    private String endTime;
    /*电子账号（选填）*/
    private String accountId;
    /*标的编号（选填）*/
    private String borrowNid;

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;

    public int limit;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private Paginator paginator;
    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }
}
