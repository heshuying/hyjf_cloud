/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.extensioncenter;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ChannelStatisticsDetailService;
import com.hyjf.am.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tanyy
 * @version ExtensionCenterController, v0.1 2018/7/16 16:03
 */
@Api(value = "PC统计明细")
@RestController
@RequestMapping("/hyjf-admin/channelstatisticsdetail")
public class ChannelStatisticsDetailController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(ChannelStatisticsDetailController.class);
	@Resource
	private ChannelStatisticsDetailService channelStatisticsDetailService;

	@ApiOperation(value = "PC统计明细", notes = "PC统计明细列表")
	@PostMapping("/searchaction")
	public AdminResult searchAction(@RequestBody ChannelStatisticsDetailRequest request) {
		logger.info("PC统计明细查询开始......");
		ChannelStatisticsDetailResponse response = channelStatisticsDetailService.searchAction(request);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));

	}

}
