package com.hyjf.cs.user.controller.app.project;

import com.hyjf.am.vo.app.AppRepayListCustomizeVO;

import java.io.Serializable;

public class RepayListBean extends AppRepayListCustomizeVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3278149257478770256L;

	
	/**用户id*/
	private String userId;
	/**
	 * 翻页机能用的隐藏变量
	 */
	private int page = 1;
	
	/**
	 * 翻页机能用的隐藏变量
	 */
	private int pageSize = 10;
	
	public RepayListBean() {
		super();
	}

	public int getPage() {
		if (page == 0) {
			page = 1;
		}
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		if (pageSize == 0) {
			pageSize = 10;
		}
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}
