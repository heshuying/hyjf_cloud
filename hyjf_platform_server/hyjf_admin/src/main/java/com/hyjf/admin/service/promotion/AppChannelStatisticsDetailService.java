package com.hyjf.admin.service.promotion;

import java.util.List;

import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.vo.user.UtmPlatVO;

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
    AppUtmRegResponse getstatisticsList(AppChannelStatisticsDetailRequest request);

    /**
     *导出app渠道统计明细
     * @param request
     * @return
     */
    AppUtmRegResponse exportStatisticsList(AppChannelStatisticsDetailRequest request);
}
