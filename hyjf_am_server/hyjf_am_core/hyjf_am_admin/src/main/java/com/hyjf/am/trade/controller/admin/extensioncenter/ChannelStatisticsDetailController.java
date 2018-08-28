/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.extensioncenter;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.response.user.UtmRegResponse;
import com.hyjf.am.user.service.admin.promotion.UtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.extensioncenter.ChannelStatisticsDetailService;

import java.util.Map;

/**
 * @author tanyy
 * @version ChannelStatisticsDetailController, v0.1 2018/7/16 14:32
 */
@RestController
@RequestMapping("/am-admin/extensioncenter/channelstatisticsdetail")
public class ChannelStatisticsDetailController extends BaseController {
	@Autowired
	private ChannelStatisticsDetailService channelStatisticsDetailService;
	/**
	 * 根据条件查询pc统计明细
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("/searchaction")
	public ChannelStatisticsDetailResponse searchAction(@RequestBody ChannelStatisticsDetailRequest request) {
		logger.info("pc统计明细查询开始......");
		ChannelStatisticsDetailResponse response = channelStatisticsDetailService.searchAction(request);
		return response;
	}
	/**
	 * 根据条件查询pc统计明细条数
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("/count")
	public IntegerResponse count(@RequestBody ChannelStatisticsDetailRequest request) {
		logger.info("根据条件查询pc统计明细条数......");
		IntegerResponse response = channelStatisticsDetailService.countList(request);
		return response;
	}

	/**
	 * 获取app渠道列表
	 */
	@PostMapping("/pcutm_list")
	public UtmPlatResponse pcutmList() {
		return channelStatisticsDetailService.pcutmList();
	}
}
