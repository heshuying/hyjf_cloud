/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.promotion;

import com.hyjf.am.response.app.AppChannelStatisticsResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsVO;

import java.util.List;

/**
 * @author yaoyong
 * @version AppChannelStatisticsService, v0.1 2018/9/21 9:38
 */
public interface AppChannelStatisticsService {
    /**
     * 获取app渠道统计列表
     * @param statisticsRequest
     * @return
     */
    AppChannelStatisticsResponse searchList(AppChannelStatisticsRequest statisticsRequest);

    /**
     * 查询导出列表
     * @param statisticsRequest
     * @return
     */
    AppChannelStatisticsResponse exportList(AppChannelStatisticsRequest statisticsRequest);

    /**
     * list 分页
     * @param request
     * @param result
     * @return
     */
    List<AppChannelStatisticsVO> paging(AppChannelStatisticsRequest request, List<AppChannelStatisticsVO> result);
}
