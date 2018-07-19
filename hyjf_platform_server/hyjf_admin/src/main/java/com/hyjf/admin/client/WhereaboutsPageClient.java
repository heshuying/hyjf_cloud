/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.admin.beans.request.ContentLandingPageRequestBean;
import com.hyjf.admin.beans.request.WhereaboutsPageRequestBean;
import com.hyjf.am.response.config.LandingPageResponse;
import com.hyjf.am.response.config.WhereaboutsPageResponse;

/**
 * @author tanyy
 * @version WhereaboutsPageClient, v0.1 2018/7/16 14:27
 */
public interface WhereaboutsPageClient {
	/**
	 * 根据条件查询移动端着陆页管理
	 *
	 * @param requestBean
	 * @return
	 */
	WhereaboutsPageResponse searchAction(WhereaboutsPageRequestBean requestBean);

	/**
	 * 添加移动端着陆页管理
	 *
	 * @param requestBean
	 * @return
	 */
	WhereaboutsPageResponse insertAction(WhereaboutsPageRequestBean requestBean);

	/**
	 * 修改移动端着陆页管理
	 *
	 * @param requestBean
	 * @return
	 */
	WhereaboutsPageResponse updateAction(WhereaboutsPageRequestBean requestBean);

	/**
	 * 根据id删除移动端着陆页管理
	 *
	 * @param id
	 * @return
	 */
	WhereaboutsPageResponse deleteById(Integer id);
}
