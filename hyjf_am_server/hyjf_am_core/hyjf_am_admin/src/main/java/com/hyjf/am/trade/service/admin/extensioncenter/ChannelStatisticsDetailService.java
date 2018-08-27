/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.extensioncenter;

import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;

/**
 * @author tanyy
 * @version ChannelStatisticsDetailService, v0.1 2018/7/16 14:33
 */
public interface ChannelStatisticsDetailService {
	/**
	 * pc统计明细查询
	 *
	 * @param request
	 * @return
	 */
	ChannelStatisticsDetailResponse searchAction(ChannelStatisticsDetailRequest request);

}
