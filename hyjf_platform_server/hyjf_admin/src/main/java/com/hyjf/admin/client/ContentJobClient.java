/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.admin.beans.request.ContentJobRequestBean;
import com.hyjf.am.response.config.JobResponse;
import com.hyjf.am.vo.config.JobsVo;

/**
 * @author fuqiang
 * @version ContentJobClient, v0.1 2018/7/12 14:27
 */
public interface ContentJobClient {
	/**
	 * 根据条件查询公司管理-招贤纳士
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
	 * 根据id查询公司管理-招贤纳士
	 *
	 * @param id
	 * @return
	 */
	JobsVo getRecord(Integer id);
}
