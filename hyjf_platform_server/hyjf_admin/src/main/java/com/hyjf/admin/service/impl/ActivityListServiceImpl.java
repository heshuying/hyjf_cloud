/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.admin.service.ActivityListService;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.config.ParamNameVO;

/**
 * @author yaoy
 * @version ActivityListServiceImpl, v0.1 2018/6/26 17:45
 */
@Service
public class ActivityListServiceImpl implements ActivityListService {

    @Autowired
    private AmMarketClient amMarketClient;
    @Autowired
    private AmConfigClient amConfigClient;

    @Override
    public ActivityListResponse getRecordList(ActivityListRequest activityListRequest) {
        return amMarketClient.getRecordList(activityListRequest);
    }

    @Override
    public ActivityListResponse insertRecord(ActivityListRequest request) {
        return amMarketClient.insertRecord(request);
    }

    @Override
    public ActivityListResponse selectActivityById(ActivityListRequest activityListRequest) {
        return amMarketClient.selectActivityById(activityListRequest);
    }

    @Override
    public ActivityListResponse updateActivity(ActivityListRequest activityListRequest) {
        return amMarketClient.updateActivity(activityListRequest);
    }

    @Override
    public ActivityListResponse deleteActivity(ActivityListRequest request) {
        return amMarketClient.deleteActivity(request);
    }

    @Override
    public List<ParamNameVO> getParamNameList(String client) {
        return amConfigClient.getParamNameList(client);
    }
}
