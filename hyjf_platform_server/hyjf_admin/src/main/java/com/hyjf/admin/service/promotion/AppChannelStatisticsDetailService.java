package com.hyjf.admin.service.promotion;

import com.hyjf.am.response.admin.AppChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.vo.user.UtmPlatVO;

import java.util.List;

/**
 * @author lisheng
 * @version AppChannelStatisticsDetailService, v0.1 2018/9/25 11:27
 */

public interface AppChannelStatisticsDetailService {

    /**
     * 获取app渠道列表
     * @return
     */
    List<UtmPlatVO> getAppUtm();

    /**
     * 分页查询所有渠道投资信息
     * @param request
     * @return
     */
    AppChannelStatisticsDetailResponse getstatisticsList(AppChannelStatisticsDetailRequest request);


}
