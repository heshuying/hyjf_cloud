/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service;

import com.hyjf.am.resquest.admin.ActivityUserRewardRequest;
import com.hyjf.am.vo.activity.ActivityUserRewardVO;

import java.util.List;

/**
 * @author yaoyong
 * @version ActivityUserRewardService, v0.1 2019/4/19 10:00
 */
public interface ActivityUserRewardService {

    /**
     * 获取奖励列表领取条数
     * @param rewardRequest
     * @return
     */
    int getRewardListCount(ActivityUserRewardRequest rewardRequest);

    /**
     * 获取奖励列表
     * @param rewardRequest
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<ActivityUserRewardVO> getRewardList(ActivityUserRewardRequest rewardRequest, int limitStart, int limitEnd);
}
