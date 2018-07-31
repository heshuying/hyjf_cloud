/**
 * Description:用户投资 投资中vO
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年12月4日 下午2:32:36
 * Modification History:
 * Modified by :
 */
package com.hyjf.cs.user.controller.app.project;

import com.hyjf.am.vo.trade.assetmanage.AppAlreadyRepayListCustomizeVO;

import java.io.Serializable;

public class InvestListBean extends AppAlreadyRepayListCustomizeVO implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 3278149257478770256L;
	
	/**用户id*/
	private String userId;

	/**
	 * 翻页机能用的隐藏变量
	 */
	private int page = 1;

	/**
	 * 翻页功能所用分页大小
	 */
	private int pageSize = 10;

	public InvestListBean() {
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
