package com.hyjf.am.resquest.app;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wgx
 * @date 2019/4/18
 */
public class AppFindReportRequest {

    @ApiModelProperty(value = "是否发布")
    Integer isRelease;
    @ApiModelProperty(value = "分页信息")
    Integer paginatorPage;

    public Integer getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(Integer isRelease) {
        this.isRelease = isRelease;
    }

    public Integer getPaginatorPage() {
        return paginatorPage;
    }

    public void setPaginatorPage(Integer paginatorPage) {
        this.paginatorPage = paginatorPage;
    }
}
