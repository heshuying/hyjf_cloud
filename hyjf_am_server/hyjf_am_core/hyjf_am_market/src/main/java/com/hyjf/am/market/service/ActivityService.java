package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.auto.ActivityList;

/**
 * @author xiasq
 * @version ActivityService, v0.1 2018/4/19 15:42
 */
public interface ActivityService {
    ActivityList selectActivityList(int activityId);
}
