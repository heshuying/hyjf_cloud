package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.auto.ActivityUserReward;
import com.hyjf.am.vo.activity.ActivityUserRewardVO;

import java.util.List;

/**
 * @author xiasq
 * @version ActivityUserRewardService, v0.1 2019-04-18 16:13
 */
public interface ActivityUserRewardService {
    /**
     * 查询用户在某活动领取奖励记录
     * @param userId
     * @param activityId
     * @param grade  奖励档位
     * @return
     */
    List<ActivityUserReward> selectByUserId(Integer userId, Integer activityId, Integer grade);

    /**
     * 保存领取记录
     * @param record
     * @return
     */
    int insertActivityUserReward(ActivityUserReward record);
}
