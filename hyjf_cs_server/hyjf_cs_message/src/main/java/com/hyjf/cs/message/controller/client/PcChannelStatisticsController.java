/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.admin.promotion.PcChannelStatisticsResponse;
import com.hyjf.am.resquest.admin.PcChannelStatisticsRequest;
import com.hyjf.am.vo.datacollect.PcChannelStatisticsVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.ic.PcChannelStatistics;
import com.hyjf.cs.message.service.channel.ChannelStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * PC渠道统计
 * @author fq
 * @version PcChannelStatisticsController, v0.1 2018/9/26 14:42
 */
@RestController
@RequestMapping("/cs-message/pc_channel_statistics")
public class PcChannelStatisticsController extends BaseController {
    @Autowired
    private ChannelStatisticsService channelStatisticsService;

    /**
     * 查询PC渠道统计
     * @param request
     * @return
     */
    @RequestMapping("/search")
    public PcChannelStatisticsResponse search(@RequestBody PcChannelStatisticsRequest request) {
        PcChannelStatisticsResponse response = new PcChannelStatisticsResponse();
        List<PcChannelStatistics> list = channelStatisticsService.searchPcChannelStatisticsList(request);
        if (!CollectionUtils.isEmpty(list)) {
            List<PcChannelStatisticsVO> voList = CommonUtils.convertBeanList(list, PcChannelStatisticsVO.class);
            response.setResultList(voList);
        }
        // 查询符合条件的数量
        int count = channelStatisticsService.selectCount(request);
        response.setCount(count);
        return response;
    }
}
