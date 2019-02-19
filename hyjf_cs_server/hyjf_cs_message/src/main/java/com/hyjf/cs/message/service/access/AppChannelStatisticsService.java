package com.hyjf.cs.message.service.access;

import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.vo.datacollect.AppAccesStatisticsVO;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/10/22  16:39
 */
public interface AppChannelStatisticsService {

    /**
     * 根据开始时间、结束时间和来源查询数据
     * @param request
     * @return
     */
    List<AppAccesStatisticsVO> getAppAccesStatisticsVO(AppChannelStatisticsRequest request);

    void insertStatistics();

}
