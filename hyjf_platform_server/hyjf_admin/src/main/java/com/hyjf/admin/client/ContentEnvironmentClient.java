/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.admin.beans.request.ContentEnvironmentRequestBean;
import com.hyjf.am.response.admin.ContentEnvironmentResponse;
import com.hyjf.am.vo.config.ContentEnvironmentVO;

/**
 * @author fuqiang
 * @version ContentEnvironmentClient, v0.1 2018/7/11 14:39
 */
public interface ContentEnvironmentClient {
	/**
	 * 根据条件查询内容管理-办公环境
	 *
	 * @param requestBean
	 * @return
	 */
	ContentEnvironmentResponse searchAction(ContentEnvironmentRequestBean requestBean);

	/**
	 * 添加公司内容管理-办公环境
	 *
	 * @param requestBean
	 * @return
	 */
	ContentEnvironmentResponse insertAction(ContentEnvironmentRequestBean requestBean);

	/**
	 * 修改内容管理-办公环境
	 *
	 * @param requestBean
	 * @return
	 */
	ContentEnvironmentResponse updateAction(ContentEnvironmentRequestBean requestBean);

	/**
	 * 根据id查询内容管理-办公环境
	 *
	 * @param id
	 * @return
	 */
	ContentEnvironmentVO getRecord(Integer id);
}
