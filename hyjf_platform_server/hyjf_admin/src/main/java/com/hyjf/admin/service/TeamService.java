/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.TeamRequestBean;
import com.hyjf.am.response.config.TeamResponse;

/**
 * @author fuqiang
 * @version TeamService, v0.1 2018/7/11 16:38
 */
public interface TeamService {
	/**
	 * 根据条件查询公司管理-团队介绍
	 *
	 * @param requestBean
	 * @return
	 */
	TeamResponse searchAction(TeamRequestBean requestBean);

	/**
	 * 添加公司管理-团队介绍
	 *
	 * @param requestBean
	 * @return
	 */
	TeamResponse insertAction(TeamRequestBean requestBean);

	/**
	 * 修改公司管理-团队介绍
	 *
	 * @param requestBean
	 * @return
	 */
	TeamResponse updateAction(TeamRequestBean requestBean);

	/**
	 * 修改公司管理-团队介绍状态
	 *
	 * @param requestBean
	 * @return
	 */
	TeamResponse updateStatus(TeamRequestBean requestBean);
}
