/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.batch;

import com.hyjf.am.response.StringResponse;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.market.service.PcChannelStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author fuqiang
 * @version PcChannelStatisticsController, v0.1 2018/7/16 10:25
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-market/pcchannelstatistics")
public class PcChannelStatisticsController extends BaseController {
	@Autowired
	private PcChannelStatisticsService pcChannelStatisticsService;

	@RequestMapping("/insertStatistics")
	public StringResponse insertStatistics() {
		try {
			pcChannelStatisticsService.insertStatistics();
		} catch (Exception e) {
			logger.error("PC渠道统计定时任务出错...", e);
			return new StringResponse("error");
		}
		return new StringResponse("success");
	}
}
