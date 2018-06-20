package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.auto.ActivityListMapper;
import com.hyjf.am.market.dao.model.auto.ActivityList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.market.service.ActivityService;

/**
 * @author xiasq
 * @version ActivityServiceImpl, v0.1 2018/4/19 15:42
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityListMapper activityListMapper;
    /**
     * 活动是否过期
     */
    @Override
    public ActivityList selectActivityList(int activityId) {
        ActivityList activityList=activityListMapper.selectByPrimaryKey(activityId);
        return activityList;
    }
}
