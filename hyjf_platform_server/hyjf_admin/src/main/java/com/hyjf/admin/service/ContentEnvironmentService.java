/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.ContentEnvironmentRequestBean;
import com.hyjf.am.response.admin.ContentEnvironmentResponse;
import com.hyjf.am.vo.config.ContentEnvironmentVO;

/**
 * @author fuqiang
 * @version ContentEnvironmentService, v0.1 2018/7/11 11:15
 */
public interface ContentEnvironmentService {
	/**
	 * 根据条件查询内容管理-办公环境
	 *
	 * @param requestBean
	 * @return
	 */
	ContentEnvironmentResponse searchAction(ContentEnvironmentRequestBean requestBean);

	/**
	 * 添加内容管理-办公环境
	 *
	 * @param requestBean
	 * @return
	 */
	int insertAction(ContentEnvironmentRequestBean requestBean);

	/**
	 * 修改内容管理-办公环境
	 *
	 * @param requestBean
	 * @return
	 */
	int updateAction(ContentEnvironmentRequestBean requestBean);

	/**
	 * 修改内容管理-办公环境
	 *
	 * @param requestBean
	 * @return
	 */
	int updateStatus(ContentEnvironmentRequestBean requestBean);

	/**
	 * 删除内容管理-办公环境状态
	 *
	 * @param id
	 * @return
	 */
    int deleteById(Integer id);

	/**
	 * 公司管理-办公环境初始化
	 * @param requestBean
	 * @return
	 */
	ContentEnvironmentVO selectById(ContentEnvironmentRequestBean requestBean);
}
