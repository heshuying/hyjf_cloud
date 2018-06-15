package com.hyjf.am.vo;

/**
 * @author xiasq
 * @version BaseVO, v0.1 2018/1/21 22:18
 */
public class BaseVO {

    /**
     * 当前页码
     */
    private int pageNum;

    /**
     * 当前页条数
     */
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
