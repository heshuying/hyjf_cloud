/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

/**
 * @author tanyy
 * @version ChannelStatisticsDetailService.java, v0.1 2018年7月17日 下午3:04:29
 */
public interface ChannelStatisticsDetailService {

	/**
	 * 根据条件查询PC统计明细
	 *
	 * @param request
	 * @return
	 */
	ChannelStatisticsDetailResponse searchAction(ChannelStatisticsDetailRequest request);

}
