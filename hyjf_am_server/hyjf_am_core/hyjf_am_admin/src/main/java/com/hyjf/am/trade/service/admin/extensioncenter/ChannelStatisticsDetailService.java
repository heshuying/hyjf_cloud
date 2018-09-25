/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.extensioncenter;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
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
	/**
	 * pc统计明细查询条数
	 *
	 * @param request
	 * @return
	 */
	IntegerResponse countList(ChannelStatisticsDetailRequest request);

	/**
	 * 获取pc渠道列表
	 *
	 * @param
	 * @return
	 */
	UtmPlatResponse selectPcutmList();

	/**
	 * 获取app 渠道列表
	 * @return
	 */
	UtmPlatResponse selectAppUtmList();
}
