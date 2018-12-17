/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.app.channel;

import com.hyjf.am.response.app.AppChannelStatisticsResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.ic.AppChannelStatistics;
import com.hyjf.cs.message.service.channel.ChannelStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yaoyong
 * @version ChannelStatisticsController, v0.1 2018/9/21 12:03
 */
@Api(tags = "app端-app渠道统计")
@RestController
@RequestMapping("/cs-message/app")
public class ChannelStatisticsController extends BaseController {

    @Autowired
    private ChannelStatisticsService channelStatisticsService;

    /**
     * 获取app渠道统计列表
     * @param request
     * @return
     */
    @ApiOperation(value = "获取app渠道统计列表", notes = "获取app渠道统计列表")
    @PostMapping("/channelstatistics")
    public AppChannelStatisticsResponse searchList(@RequestBody AppChannelStatisticsRequest request) {
        AppChannelStatisticsResponse response = new AppChannelStatisticsResponse();

        int count = channelStatisticsService.queryChannelStatisticsCount(request);
        if(count > 0){

            List<AppChannelStatistics> list = channelStatisticsService.findChannelStatistics(request);
            if (!CollectionUtils.isEmpty(list)) {
                List<AppChannelStatisticsVO> voList = CommonUtils.convertBeanList(list,AppChannelStatisticsVO.class);
                response.setResultList(voList);
            }
        }

        response.setCount(count);
        return response;
    }


    /**
     * 导出app渠道统计列表
     * @param request
     * @return
     */
    @ApiOperation(value = "导出app渠道统计列表", notes = "导出app渠道统计列表")
    @PostMapping("/export")
    public AppChannelStatisticsResponse exportList(@RequestBody AppChannelStatisticsRequest request) {
        AppChannelStatisticsResponse response = new AppChannelStatisticsResponse();
        List<AppChannelStatistics> list = channelStatisticsService.exportList(request);
        if (!CollectionUtils.isEmpty(list)) {
            List<AppChannelStatisticsVO> voList = CommonUtils.convertBeanList(list, AppChannelStatisticsVO.class);
            response.setResultList(voList);
        }
        return response;
    }
}
