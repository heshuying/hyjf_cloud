package com.hyjf.cs.user.bean;

/**
 * @author lisheng
 * @version WrbNoticeinfoRequest, v0.1 2018/9/20 14:45
 */

public class WrbNoticeinfoRequest {
    /** 条数 */
    private Integer limit;

    /** 页数 */
    private Integer page;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

}
