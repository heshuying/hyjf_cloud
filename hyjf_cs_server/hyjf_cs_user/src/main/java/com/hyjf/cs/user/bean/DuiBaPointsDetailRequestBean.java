/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wangjun
 * @version DuiBaPointsDetailRequestBean, v0.1 2019/6/12 9:25
 */
public class DuiBaPointsDetailRequestBean {
    @ApiModelProperty(value = "当前页")
    private int currentPage;

    @ApiModelProperty(value = "每页显示条数")
    private int pageSize;

    @ApiModelProperty(value = "类型，-1：全部，0：获取，1：使用")
    private int type;

    @ApiModelProperty(value = "时间")
    private String[] date;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String[] getDate() {
        return date;
    }

    public void setDate(String[] date) {
        this.date = date;
    }
}
