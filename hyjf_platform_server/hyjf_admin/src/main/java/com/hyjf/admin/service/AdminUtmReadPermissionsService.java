/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.config.AdminUtmReadPermissionsResponse;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;

/**
 * @author tanyy
 * @version AdminUtmReadPermissionsService.java, v0.1 2018年7月17日 下午3:04:29
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
	 * @param requestBean
	 * @return
	 */
	AdminUtmReadPermissionsResponse insertAction(AdminUtmReadPermissionsRequest requestBean);

	/**
	 * 修改
	 *
	 * @param requestBean
	 * @return
	 */
	AdminUtmReadPermissionsResponse updateAction(AdminUtmReadPermissionsRequest requestBean);

	/**
	 * 根据id删除
	 *
	 * @param id
	 * @return
	 */
	AdminUtmReadPermissionsResponse deleteById(Integer id);

	/**
	 * 根据用户Id查询渠道账号管理
	 * @param userId
	 * @return
	 */
    AdminUtmReadPermissionsVO selectAdminUtmReadPermissions(String userId);
}
