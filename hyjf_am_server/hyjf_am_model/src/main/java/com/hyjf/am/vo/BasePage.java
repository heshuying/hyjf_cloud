package com.hyjf.am.vo;

/**
 * 封装前端传参需要的参数
 * @author zhangyk
 * @date 2018/7/3 13:44
 */
public class BasePage {

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
