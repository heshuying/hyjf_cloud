package com.hyjf.cs.message.service.access;

import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.vo.datacollect.AppAccesStatisticsVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/10/22  16:39
 */
public interface AppChannelStatisticsService {

    List<AppAccesStatisticsVO> getAppAccesStatisticsVO(AppChannelStatisticsRequest request);

    List<AppChannelStatisticsDetailVO> getAppChannelStatisticsDetailVO(AppChannelStatisticsRequest request);
}
