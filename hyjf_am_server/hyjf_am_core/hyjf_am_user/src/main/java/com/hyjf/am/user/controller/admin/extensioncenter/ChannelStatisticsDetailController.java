/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.extensioncenter;

import com.hyjf.am.response.admin.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.admin.ChannelStatisticsDetailRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.admin.extensioncenter.ChannelStatisticsDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanyy
 * @version ChannelStatisticsDetailController, v0.1 2018/7/16 14:32
 */
@RestController
@RequestMapping("/am-config/extensioncenter/channelstatisticsdetail")
public class ChannelStatisticsDetailController extends BaseController {
	@Autowired
	private ChannelStatisticsDetailService channelStatisticsDetailService;

	/**
	 * 根据条件查询pc统计明细
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/searchaction")
	public ChannelStatisticsDetailResponse searchAction(@RequestBody ChannelStatisticsDetailRequest request) {
		logger.info("pc统计明细查询开始......");
		ChannelStatisticsDetailResponse response = channelStatisticsDetailService.searchAction(request);
		return response;
	}

}
