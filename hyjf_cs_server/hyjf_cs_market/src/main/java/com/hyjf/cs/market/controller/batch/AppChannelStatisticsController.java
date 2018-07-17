/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.AppChannelStatisticsService;

/**
 * @author fuqiang
 * @version AppChannelStatisticsController, v0.1 2018/7/16 14:13
 */
@RestController
@RequestMapping("/cs-market/apphannelstatistics")
public class AppChannelStatisticsController extends BaseMarketController {
	@Autowired
	private AppChannelStatisticsService statisticsService;

	@RequestMapping("/insertStatistics")
	public String insertStatistics() {
		statisticsService.insertStatistics();
		return "success";
	}
}
