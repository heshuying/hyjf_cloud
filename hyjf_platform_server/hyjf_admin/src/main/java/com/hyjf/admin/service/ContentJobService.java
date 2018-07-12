/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.ContentJobRequestBean;
import com.hyjf.am.response.config.JobResponse;

/**
 * @author fuqiang
 * @version ContentJobService, v0.1 2018/7/12 14:14
 */
public interface ContentJobService {
	/**
	 * 根据条件查询公司管理-招贤纳士列表
	 *
	 * @param requestBean
	 * @return
	 */
	JobResponse searchAction(ContentJobRequestBean requestBean);

	/**
	 * 添加公司管理-招贤纳士
	 *
	 * @param requestBean
	 * @return
	 */
	JobResponse insertAction(ContentJobRequestBean requestBean);

	/**
	 * 修改公司管理-招贤纳士
	 *
	 * @param requestBean
	 * @return
	 */
	JobResponse updateAction(ContentJobRequestBean requestBean);

	/**
	 * 修改公司管理-招贤纳士状态
	 *
	 * @param requestBean
	 * @return
	 */
	JobResponse updateStatus(ContentJobRequestBean requestBean);
}
