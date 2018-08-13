package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @version ApplyAgreementRequest, v0.1 2018/8/9 17:05
 * @Author: Zha Daojian
 */
public class ApplyAgreementRequest extends BaseRequest implements Serializable {

    /**
     * 检索条件 借款编号
     */
    @ApiModelProperty(value = "借款编号")
    private String borrowNid;

    /**
     * 期数
     */
    @ApiModelProperty(value = "期数")
    private String borrowPeriod;

    /**
     * 检索条件 申请时间开始
     */
    @ApiModelProperty(value = "申请时间开始")
    private String timeStart;

    /**
     * 检索条件 申请时间结束
     */
    @ApiModelProperty(value = "申请时间结束")
    private String timeEnd;

    public int limit;

    public int limitStart;

    public int limitEnd;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
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

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;

    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private com.hyjf.common.paginator.Paginator paginator;

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
