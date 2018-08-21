package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;
import io.swagger.annotations.ApiModelProperty;

public class ContentArticleRequest extends Request {

    private String noticeType;
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页")
    private int currPage;

    /**
     * 当前页条数
     */
    @ApiModelProperty(value = "当前页条数")
    private int pageSize;


    private Integer limitStart;

    private Integer limitEnd;


    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
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

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
