/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.admin.beans.request.EventRequestBean;
import com.hyjf.am.response.config.EventResponse;
import com.hyjf.am.vo.config.EventVO;

/**
 * @author fuqiang
 * @version EventClient, v0.1 2018/7/11 17:52
 */
public interface EventClient {
	/**
	 * 根据条件查询公司管理-公司记事
	 *
	 * @param requestBean
	 * @return
	 */
	EventResponse searchAction(EventRequestBean requestBean);

	/**
	 * 添加公司管理-公司记事
	 *
	 * @param requestBean
	 * @return
	 */
	EventResponse insertAction(EventRequestBean requestBean);

	/**
	 * 修改公司管理-公司记事
	 *
	 * @param requestBean
	 * @return
	 */
	EventResponse updateAction(EventRequestBean requestBean);

	/**
	 * 根据id查找公司管理-公司记事
	 *
	 * @param id
	 * @return
	 */
	EventVO getRecord(Integer id);
}
