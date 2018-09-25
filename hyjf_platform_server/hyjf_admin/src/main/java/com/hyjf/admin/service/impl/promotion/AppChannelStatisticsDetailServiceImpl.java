package com.hyjf.admin.service.impl.promotion;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.promotion.AppChannelStatisticsDetailService;
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

    @Override
    public List<UtmPlatVO> getAppUtm(){
        return amAdminClient.getAppUtm();
    }
}
