/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.ActivityUserGuessService;
import com.hyjf.am.response.admin.ActivityUserGuessResponse;
import com.hyjf.am.resquest.admin.ActivityUserGuessRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaoyong
 * @version ActivityUserGuessServiceImpl, v0.1 2019/4/18 15:35
 */
@Service
public class ActivityUserGuessServiceImpl implements ActivityUserGuessService {

    @Autowired
    AmAdminClient amAdminClient;

    @Override
    public ActivityUserGuessResponse getGuessList(ActivityUserGuessRequest request) {
        return amAdminClient.getGuessList(request);
    }
}
