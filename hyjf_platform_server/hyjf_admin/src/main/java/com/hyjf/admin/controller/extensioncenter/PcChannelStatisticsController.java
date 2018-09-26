/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.extensioncenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.promotion.PcChannelStatisticsService;
import com.hyjf.am.response.admin.promotion.PcChannelStatisticsResponse;
import com.hyjf.am.resquest.admin.PcChannelStatisticsRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author fq
 * @version PcChannelStatisticsController, v0.1 2018/9/26 11:47
 */
@Api(tags = "推广中心-PC渠道统计")
@RestController
@RequestMapping("/hyjf-admin/pc_channel_statistics")
public class PcChannelStatisticsController extends BaseController {
    @Autowired
    private PcChannelStatisticsService pcChannelStatisticsService;

    @ApiOperation(value = "查询pc渠道统计", notes = "查询pc渠道统计")
    @RequestMapping("/search")
    public AdminResult searchAction(@RequestBody PcChannelStatisticsRequest request) {
        PcChannelStatisticsResponse response = pcChannelStatisticsService.searchPcChannelStatistics(request);
        return new AdminResult(response);
    }
}
