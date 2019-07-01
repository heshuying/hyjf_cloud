/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.customize.market.ActivityUserRewardCustomizeMapper;
import com.hyjf.am.market.service.ActivityUserRewardService;
import com.hyjf.am.resquest.admin.ActivityUserRewardRequest;
import com.hyjf.am.vo.admin.AdminActivityUserRewardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version ActivityUserRewardServiceImpl, v0.1 2019/4/19 10:00
 */
@Service
public class ActivityUserRewardServiceImpl implements ActivityUserRewardService {

    @Autowired
    private ActivityUserRewardCustomizeMapper customizeMapper;

    @Override
    public int getRewardListCount(ActivityUserRewardRequest rewardRequest) {
        Map<String, Object> paramMap = addParams(rewardRequest);
        return customizeMapper.countRewardList(paramMap);
    }

    @Override
    public List<AdminActivityUserRewardVO> getRewardList(ActivityUserRewardRequest rewardRequest, int limitStart, int limitEnd) {
        Map<String, Object> map = addParams(rewardRequest);
        if (rewardRequest.getSort() != null && rewardRequest.getSort() != 0) {
            map.put("sort", rewardRequest.getSort());
        } else {
            map.put("sort", 2);
        }
        if (limitStart != -1) {
            map.put("limitStart", limitStart);
            map.put("limitEnd", limitEnd);
        }
        List<AdminActivityUserRewardVO> userRewardVOList = customizeMapper.selectRewardList(map);
        return userRewardVOList;
    }

    private Map<String, Object> addParams(ActivityUserRewardRequest rewardRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", rewardRequest.getUserName());
        map.put("trueName", rewardRequest.getTrueName());
        map.put("sendStatus", rewardRequest.getSendStatus());
        map.put("activityId", rewardRequest.getActivityId());
        map.put("rewardCode", rewardRequest.getRewardCode());
        return map;
    }
}
