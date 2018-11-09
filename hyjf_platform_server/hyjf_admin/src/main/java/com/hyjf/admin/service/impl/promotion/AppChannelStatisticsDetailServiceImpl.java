package com.hyjf.admin.service.impl.promotion;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.promotion.AppChannelStatisticsDetailService;
import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.vo.user.UtmPlatVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lisheng
 * @version AppChannelStatisticsDetailServiceImpl, v0.1 2018/9/25 11:28
 */
@Service
public class AppChannelStatisticsDetailServiceImpl implements AppChannelStatisticsDetailService {
    @Resource
    private AmAdminClient amAdminClient;
    @Resource
    private AmUserClient amUserClient;

    @Override
    public List<UtmPlatVO> getAppUtm(){
        return amAdminClient.getAppUtm();
    }

    @Override
    public AppUtmRegResponse getstatisticsList(AppChannelStatisticsDetailRequest request) {
        return amUserClient.getstatisticsList(request);
    }
    @Override
    public AppUtmRegResponse exportStatisticsList(AppChannelStatisticsDetailRequest request) {
        return amUserClient.exportStatisticsList(request);
    }
}
