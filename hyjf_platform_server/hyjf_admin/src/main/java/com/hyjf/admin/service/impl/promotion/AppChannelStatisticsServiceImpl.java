/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.promotion;

import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.promotion.AppChannelStatisticsService;
import com.hyjf.am.response.app.AppChannelStatisticsResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaoyong
 * @version AppChannelStatisticsServiceImpl, v0.1 2018/9/21 9:42
 */
@Service
public class AppChannelStatisticsServiceImpl implements AppChannelStatisticsService {

    @Autowired
    private CsMessageClient csMessageClient;

    @Override
    public AppChannelStatisticsResponse searchList(AppChannelStatisticsRequest statisticsRequest) {
        return csMessageClient.searchList(statisticsRequest);
    }

    @Override
    public AppChannelStatisticsResponse exportList(AppChannelStatisticsRequest statisticsRequest) {
        return csMessageClient.exportList(statisticsRequest);
    }
}
