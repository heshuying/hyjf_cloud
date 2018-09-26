/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.promotion;

import com.hyjf.am.response.admin.promotion.PcChannelStatisticsResponse;
import com.hyjf.am.resquest.admin.PcChannelStatisticsRequest;

/**
 * @author fq
 * @version PcChannelStatisticsService, v0.1 2018/9/26 14:27
 */
public interface PcChannelStatisticsService {
    /**
     * 查找pc渠道统计
     * @param request
     * @return
     */
    PcChannelStatisticsResponse searchPcChannelStatistics(PcChannelStatisticsRequest request);
}
