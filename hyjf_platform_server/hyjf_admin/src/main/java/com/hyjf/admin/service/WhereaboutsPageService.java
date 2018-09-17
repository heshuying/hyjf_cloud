/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.WhereaboutsPageRequestBean;
import com.hyjf.am.response.config.WhereaboutsPageResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tanyy
 * @version WhereaboutsPageService, v0.1 2018/7/16 14:14
 */
public interface WhereaboutsPageService {
	/**
	 * 根据条件查询
	 *
	 * @param requestBean
	 * @return
	 */
	WhereaboutsPageResponse searchAction(WhereaboutsPageRequestBean requestBean);

	/**
	 * 添加
	 *
	 * @param requestBean
	 * @return
	 */
	WhereaboutsPageResponse insertAction(WhereaboutsPageRequestBean requestBean);

	/**
	 * 修改
	 *
	 * @param requestBean
	 * @return
	 */
	WhereaboutsPageResponse updateAction(WhereaboutsPageRequestBean requestBean);
	/**
	 * 修改状态
	 *
	 * @param requestBean
	 * @return
	 */
	WhereaboutsPageResponse updateStatus(WhereaboutsPageRequestBean requestBean);
	/**
	 * 根据id删除
	 *
	 * @param id
	 * @return
	 */
	WhereaboutsPageResponse deleteById(Integer id);

	/**
	 * 附件上传
	 *
	 *
	 */
	String uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
