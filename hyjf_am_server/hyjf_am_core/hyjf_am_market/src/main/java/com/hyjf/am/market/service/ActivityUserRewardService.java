package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.auto.ActivityUserReward;
import com.hyjf.am.vo.activity.ActivityUserRewardVO;

/**
 * @author xiasq
 * @version ActivityUserRewardService, v0.1 2019-04-18 16:13
 */
public interface ActivityUserRewardService {
    /**
     * 查询用户在某活动是否领取过奖励
     * @param userId
     * @param activityId
     * @return
     */
    ActivityUserReward selectByUserId(Integer userId, Integer activityId);

    /**
     * 保存领取记录
     * @param record
     * @return
     */
    int insertActivityUserReward(ActivityUserReward record);
}
