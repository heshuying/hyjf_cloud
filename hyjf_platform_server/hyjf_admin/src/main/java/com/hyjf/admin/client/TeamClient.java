/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.admin.beans.request.TeamRequestBean;
import com.hyjf.am.response.config.TeamResponse;
import com.hyjf.am.vo.config.TeamVO;

/**
 * @author fuqiang
 * @version TeamClient, v0.1 2018/7/11 16:42
 */
public interface TeamClient {
	/**
	 * 根据条件查询公司管理-团队介绍列表
	 *
	 * @param requestBean
	 * @return
	 */
	TeamResponse searchAction(TeamRequestBean requestBean);

	/**
	 * 保存公司管理-团队介绍
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
	 * 根据id获取公司管理-团队介绍
	 *
	 * @param id
	 * @return
	 */
	TeamVO getRecord(Integer id);

	/**
	 * 删除公司管理-团队介绍状态
	 *
	 * @param id
	 * @return
	 */
    TeamResponse deleteById(Integer id);
}
