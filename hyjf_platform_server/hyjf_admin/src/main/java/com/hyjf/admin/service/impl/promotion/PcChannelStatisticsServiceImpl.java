/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.promotion;

import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.promotion.PcChannelStatisticsService;
import com.hyjf.am.response.admin.promotion.PcChannelStatisticsResponse;
import com.hyjf.am.resquest.admin.PcChannelStatisticsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fq
 * @version PcChannelStatisticsServiceImpl, v0.1 2018/9/26 14:27
 */
@Service
public class PcChannelStatisticsServiceImpl implements PcChannelStatisticsService {
    @Autowired
    private CsMessageClient csMessageClient;

    @Override
    public PcChannelStatisticsResponse searchPcChannelStatistics(PcChannelStatisticsRequest request) {
        return csMessageClient.searchPcChannelStatistics(request);
    }
}
