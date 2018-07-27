/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service;

import org.springframework.stereotype.Service;

/**
 * @author fuqiang
 * @version AppChannelStatisticsService, v0.1 2018/7/16 14:18
 */
public interface AppChannelStatisticsService extends BaseMarketService {
	/**
	 * 更新app渠道统计
	 *
	 */
	void insertStatistics();
}
