package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BaseVO;

/**
 * 我的邀请及奖励列表
 * @author hesy
 * @version MyCouponListRequest, v0.1 2018/6/23 11:46
 */
public class WechatMyProjectRequest extends BaseVO {

    private Integer userId;

    private Integer limitStart;

    private Integer limitEnd;
    //排序列
    private String orderByFlag;
    //排序规则
    private String sortBy;
    //页数
    private Integer currentPage;
    //每页条数
    private Integer pageSize;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getOrderByFlag() {
        return orderByFlag;
    }

    public void setOrderByFlag(String orderByFlag) {
        this.orderByFlag = orderByFlag;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
