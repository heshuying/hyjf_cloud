/**
 * Description:银行卡绑定列表前端显示所用PO
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.user.AdminPreRegistListVO;
import com.hyjf.am.vo.user.AdminUserAuthLogListVO;

/**
 * @author wangqi
 */

public class AdminUserAuthLogListRequest extends AdminUserAuthLogListVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 添加时机 开始
	public String addTimeStart;

	// 添加时间 结束
	public String addTimeEnd;
	
	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	public String getAddTimeStart() {
		return addTimeStart;
	}

	public void setAddTimeStart(String addTimeStart) {
		this.addTimeStart = addTimeStart;
	}

	public String getAddTimeEnd() {
		return addTimeEnd;
	}

	public void setAddTimeEnd(String addTimeEnd) {
		this.addTimeEnd = addTimeEnd;
	}

	public int getPaginatorPage() {
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}


	
}


