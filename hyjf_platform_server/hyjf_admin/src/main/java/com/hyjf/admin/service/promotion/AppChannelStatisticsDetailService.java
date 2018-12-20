package com.hyjf.admin.service.promotion;

import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
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
     * 分页查询所有渠道出借信息
     * @param request
     * @return
     */
    com.hyjf.am.response.admin.AppUtmRegResponse getstatisticsList(AppChannelStatisticsDetailRequest request);

    /**
     *导出app渠道统计明细
     * @param request
     * @return
     */
    com.hyjf.am.response.admin.AppUtmRegResponse exportStatisticsList(AppChannelStatisticsDetailRequest request);

    /**
     * list 分页
     * @param request
     * @param result
     * @return
     */
    List<AppUtmRegVO> paging(AppChannelStatisticsDetailRequest request, List<AppUtmRegVO> result);
}
