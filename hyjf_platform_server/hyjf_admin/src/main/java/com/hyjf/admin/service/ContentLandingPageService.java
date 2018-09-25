/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.ContentLandingPageRequestBean;
import com.hyjf.am.response.config.LandingPageResponse;

/**
 * @author tanyy
 * @version ContentLandingPageService, v0.1 2018/7/16 14:14
 */
public interface ContentLandingPageService {
	/**
	 * 根据条件查询着路页管理列表
	 *
	 * @param requestBean
	 * @return
	 */
	LandingPageResponse searchAction(ContentLandingPageRequestBean requestBean);

	/**
	 * 添加着路页管理
	 *
	 * @param requestBean
	 * @return
	 */
	LandingPageResponse insertAction(ContentLandingPageRequestBean requestBean);

	/**
	 * 修改着路页管理
	 *
	 * @param requestBean
	 * @return
	 */
	LandingPageResponse updateAction(ContentLandingPageRequestBean requestBean);

	/**
	 * 根据id删除着路页管理
	 *
	 * @param id
	 * @return
	 */
	LandingPageResponse deleteById(Integer id);
	/**
	 * 根据id获取对象
	 *
	 * @param id
	 * @return
	 */
	LandingPageResponse getRecord(Integer id);
}
