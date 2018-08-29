/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;

/**
 * @author tanyy
 * @version ChannelStatisticsDetailService.java, v0.1 2018年7月17日 下午3:04:29
 */
public interface ChannelStatisticsDetailService  {

	/**
	 * 根据条件查询PC统计明细
	 *
	 * @param request
	 * @return
	 */
	JSONObject searchAction(ChannelStatisticsDetailRequest request);
	/**
	 * @Description  根据用户Id查询渠道账号管理
	 * @Date 16:54 2018/8/24
	 * @Param userId
	 * @return
	 */
	AdminUtmReadPermissionsVO selectAdminUtmReadPermissions(Integer userId);

}
