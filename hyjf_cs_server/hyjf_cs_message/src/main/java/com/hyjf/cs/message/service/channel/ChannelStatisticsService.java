/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.channel;

import java.util.List;

import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.resquest.admin.PcChannelStatisticsRequest;
import com.hyjf.cs.message.bean.ic.AppChannelStatistics;
import com.hyjf.cs.message.bean.ic.PcChannelStatistics;

/**
 * @author yaoyong
 * @version ChannelStatisticsService, v0.1 2018/9/21 12:33
 */
public interface ChannelStatisticsService {
    /**
     * 根据条件查询app渠道统计
     * @param request
     * @return
     */
    List<AppChannelStatistics> findChannelStatistics(AppChannelStatisticsRequest request);

    /**
     *根据条件查询数量
     * @param request
     * @return
     */
    int queryChannelStatisticsCount(AppChannelStatisticsRequest request);

    /**
     * 导出报表
     * @param request
     * @return
     */
    List<AppChannelStatistics> exportList(AppChannelStatisticsRequest request);

    /**
     * 查找PC渠道统计
     * @param request
     * @return
     */
    List<PcChannelStatistics> searchPcChannelStatisticsList(PcChannelStatisticsRequest request);

    /**
     * 查询PC渠道统计符合条件的数量
     * @param request
     * @return
     */
    int selectCount(PcChannelStatisticsRequest request);
}
