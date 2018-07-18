/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.LandingPage;
import com.hyjf.am.response.config.AdminUtmReadPermissionsResponse;
import com.hyjf.am.response.config.LandingPageResponse;
import com.hyjf.am.resquest.admin.LandingPageRequest;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;

/**
 * @author tanyy
 * @version AdminUtmReadPermissionsService, v0.1 2018/7/16 14:33
 */
public interface AdminUtmReadPermissionsService {
	/**
	 * 根据条件查询
	 *
	 * @param request
	 * @return
	 */
	AdminUtmReadPermissionsResponse searchAction(AdminUtmReadPermissionsRequest request);

	/**
	 * 添加
	 *
	 * @param request
	 */
	void insertAction(AdminUtmReadPermissionsRequest request);

	/**
	 * 修改
	 *
	 * @param request
	 */
	void updateAction(AdminUtmReadPermissionsRequest request);



    /**
     * 删除
     *
     * @param id
     */
    void deleteById(Integer id);
}
