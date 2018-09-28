/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.WhereaboutsPageRequestBean;
import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.config.WhereaboutsPageResponse;

/**
 * @author tanyy
 * @version WhereaboutsPageService, v0.1 2018/7/16 14:14
 */
public interface WhereaboutsPageService extends BaseService{
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
	 * 通过条件查询着落页配置
	 *
	 *
	 */
	WhereaboutsPageResponse getWhereaboutsPageConfigById(WhereaboutsPageRequestBean form);

	/**
	 * 检查渠道
	 *
	 *
	 */
	public StringResponse checkUtmId(Integer utmId);

	/**
	 * 检查渠道
	 *
	 *
	 */
	public StringResponse checkReferrer(String referrer);
}
