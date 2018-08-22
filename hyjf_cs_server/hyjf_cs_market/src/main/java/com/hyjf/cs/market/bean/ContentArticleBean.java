package com.hyjf.cs.market.bean;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author lisheng
 * @version ContentArticleBean, v0.1 2018/8/22 10:20
 */

public class ContentArticleBean implements Serializable {
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页")
    private Integer paginatorPage;
    /**
     * 当前页条数
     */
    @ApiModelProperty(value = "当前页条数")
    private Integer pageSize;

    public Integer getPaginatorPage() {
        return paginatorPage;
    }

    public void setPaginatorPage(Integer paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
