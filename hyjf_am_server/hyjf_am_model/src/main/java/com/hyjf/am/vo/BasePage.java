package com.hyjf.am.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 封装前端传参需要的参数
 * @author zhangyk
 * @date 2018/7/3 13:44
 */
public class BasePage {

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页")
    private int currPage;

    /**
     * 当前页条数
     */
    @ApiModelProperty(value = "当前页条数")
    private int pageSize = 10;
    /**
     * 分页查询开始条件
     */
    @ApiModelProperty(value = "分页查询开始条件")
    public int limitStart;

    /**
     * 分页查询结束条件
     */
    @ApiModelProperty(value = "分页查询结束条件")
    public int limitEnd;


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
}

