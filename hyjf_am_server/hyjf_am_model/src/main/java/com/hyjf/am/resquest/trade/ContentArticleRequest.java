package com.hyjf.am.resquest.trade;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ContentArticleRequest  implements Serializable {

    private static final long serialVersionUID = 1L;
    private String noticeType;
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页")
    private Integer currPage;
    /**
     * 当前页条数
     */
    @ApiModelProperty(value = "当前页条数")
    private Integer pageSize;
    private Integer limitStart;
    private Integer limitEnd;

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public Integer getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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
}
