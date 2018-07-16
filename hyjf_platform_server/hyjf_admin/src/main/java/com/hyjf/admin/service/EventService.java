/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.EventRequestBean;
import com.hyjf.am.response.config.EventResponse;

/**
 * @author fuqiang
 * @version EventService, v0.1 2018/7/11 17:35
 */
public interface EventService {
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
	 * 修改公司管理-公司记事状态
	 *
	 * @param requestBean
	 * @return
	 */
	EventResponse updateStatus(EventRequestBean requestBean);

	/**
	 * 根据id删除公司管理-公司记事状态
	 *
	 * @param id
	 * @return
	 */
    EventResponse deleteById(Integer id);
}
