package com.hyjf.am.vo;

/**
 * @author xiasq
 * @version BaseVO, v0.1 2018/1/21 22:18
 */
public class BaseVO {

    /**
     * 当前页码
     */
    private int currPage;

    /**
     * 当前页条数
     */
    private int pageSize;

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
