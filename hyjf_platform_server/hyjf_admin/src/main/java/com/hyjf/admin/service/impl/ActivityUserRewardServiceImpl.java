/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.ActivityUserRewardService;
import com.hyjf.am.response.admin.ActivityUserRewardResponse;
import com.hyjf.am.resquest.admin.ActivityUserRewardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaoyong
 * @version ActivityUserRewardServiceImpl, v0.1 2019/4/19 9:45
 */
@Service
public class ActivityUserRewardServiceImpl implements ActivityUserRewardService {

    @Autowired
    AmAdminClient amAdminClient;

    @Override
    public ActivityUserRewardResponse getRewardList(ActivityUserRewardRequest rewardRequest) {
        return amAdminClient.getRewardList(rewardRequest);
    }
}
