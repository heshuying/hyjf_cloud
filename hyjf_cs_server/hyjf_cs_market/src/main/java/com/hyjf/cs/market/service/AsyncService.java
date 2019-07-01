package com.hyjf.cs.market.service;

public interface AsyncService {
    /**
     * @Author walter.limeng
     * @Description //保存用户抽奖信息
     * @Date 19:23 2019-05-13
     * @Param [luckNum, userId, activityId, couponCodes]
     * @return void
     **/
    void saveUserDraw(int luckNum, Integer userId, Integer activityId, String couponCodes);
}
