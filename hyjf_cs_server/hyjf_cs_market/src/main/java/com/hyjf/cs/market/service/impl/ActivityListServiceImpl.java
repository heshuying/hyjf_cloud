package com.hyjf.cs.market.service.impl;

import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.market.ActivityListBeanVO;
import com.hyjf.cs.market.client.AmMarketClient;
import com.hyjf.cs.market.service.ActivityListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/26 11:03
 * @Description: ActivityListServiceImpl
 */
@Service
public class ActivityListServiceImpl implements ActivityListService {

    @Resource
    private AmMarketClient amMarketClient;
    @Override
    public Integer queryActivityCount(ActivityListRequest activityListRequest) {
        return amMarketClient.queryActivityCount(activityListRequest);
    }

    @Override
    public List<ActivityListBeanVO> queryActivityList(ActivityListRequest activityListRequest) {
        return amMarketClient.queryActivityList(activityListRequest);
    }
}
