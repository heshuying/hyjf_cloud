package com.hyjf.admin.client;

import com.hyjf.am.response.config.AdminUtmReadPermissionsResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;

public interface AdminUtmReadPermissionsClient {

	public AdminUtmReadPermissionsResponse searchAction(AdminUtmReadPermissionsRequest request);
	/**
	 * 添加
	 *
	 * @param requestBean
	 * @return
	 */
	public AdminUtmReadPermissionsResponse insertAction(AdminUtmReadPermissionsRequest requestBean);

	/**
	 * 修改
	 *
	 * @param requestBean
	 * @return
	 */
	public AdminUtmReadPermissionsResponse updateAction(AdminUtmReadPermissionsRequest requestBean);

	/**
	 * 根据id删除
	 *
	 * @param id
	 * @return
	 */
	public AdminUtmReadPermissionsResponse deleteById(Integer id);
}
