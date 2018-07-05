package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;

public class ContentArticleRequest extends Request {

    private String noticeType;

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
}
