/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.ActivityUserRewardResponse;
import com.hyjf.am.resquest.admin.ActivityUserRewardRequest; /**
 * @author yaoyong
 * @version ActivityUserRewardService, v0.1 2019/4/19 9:45
 */
public interface ActivityUserRewardService {

    /**
     * 获取奖励领取列表
     * @param rewardRequest
     * @return
     */
    ActivityUserRewardResponse getRewardList(ActivityUserRewardRequest rewardRequest);
}
