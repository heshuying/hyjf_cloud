/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author dongzeshan 分页字段 ,如果没用到可以不继承
 * @version BaseRequest, v0.1 2018/7/2 14:00
 */
public class BaseRequest {

    @ApiModelProperty(value = "aa")
    private int currPage =1;

    @ApiModelProperty(value = "每页多少条")
    private int pageSize =10;

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
