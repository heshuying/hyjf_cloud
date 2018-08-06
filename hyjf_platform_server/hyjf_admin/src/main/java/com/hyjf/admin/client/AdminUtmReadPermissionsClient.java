package com.hyjf.admin.client;

import com.hyjf.am.response.admin.promotion.AppChannelReconciliationResponse;
import com.hyjf.am.response.config.AdminUtmReadPermissionsResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.admin.AppChannelReconciliationRequest;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;

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

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  根据用户Id查询渠道账号管理
	 * @Date 16:54 2018/7/24
	 * @Param userId
	 * @return
	 */
    AdminUtmReadPermissionsVO selectAdminUtmReadPermissions(Integer userId);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  分页查询APP渠道
     * @Date 17:23 2018/7/24
     * @Param AppChannelReconciliationRequest
     * @return
     */
	AppChannelReconciliationResponse getReconciliationPage(AppChannelReconciliationRequest form);
}
