package com.hyjf.admin.service.impl.promotion;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.promotion.AppChannelStatisticsDetailService;
import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.vo.user.UtmPlatVO;

/**
 * @author lisheng
 * @version AppChannelStatisticsDetailServiceImpl, v0.1 2018/9/25 11:28
 */
@Service
public class AppChannelStatisticsDetailServiceImpl implements AppChannelStatisticsDetailService {
    @Resource
    private AmAdminClient amAdminClient;

    @Resource
    private CsMessageClient csMessageClient;
    @Override
    public List<UtmPlatVO> getAppUtm(){
        return amAdminClient.getAppUtm();
    }

    @Override
    public AppUtmRegResponse getstatisticsList(AppChannelStatisticsDetailRequest request) {
        return csMessageClient.getstatisticsList(request);
    }
    @Override
    public AppUtmRegResponse exportStatisticsList(AppChannelStatisticsDetailRequest request) {
        return csMessageClient.exportStatisticsList(request);
    }
}
