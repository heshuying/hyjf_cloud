/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.batch;

import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.AppChannelStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author fuqiang
 * @version AppChannelStatisticsController, v0.1 2018/7/16 14:13
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-market/appchannelstatistics")
@Deprecated
public class AppChannelStatisticsController extends BaseMarketController {
	@Autowired
	private AppChannelStatisticsService statisticsService;

	@RequestMapping("/insertStatistics")
	public String insertStatistics() {
		statisticsService.insertStatistics();
		return "success";
	}
}
