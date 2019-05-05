package com.hyjf.cs.market.service;

import com.hyjf.am.vo.activity.UserTenderVO;
import com.hyjf.am.vo.activity.ActivityUserRewardVO;
import com.hyjf.am.vo.market.ActivityListVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xiasq
 * @version Activity518Service, v0.1 2019-04-17 9:53
 */
public interface Activity518Service {

    /**
     * 查询活动配置
     * @param activityId
     * @return
     */
    ActivityListVO getActivityById(Integer activityId);

    /**
     * 查询用户投资年化
     * @param userId
     * @param activityStartDate
     * @param activityEndDate
     * @return
     */
    BigDecimal getUserTenderAmount(Integer userId, Date activityStartDate, Date activityEndDate);

    /**
     * 根据userId查询用户名
     * @param userId
     * @return
     */
    String getUsernameByUserId(Integer userId);

    /**
     * 获取出借排行榜
     * @param activityStartDate
     * @param activityEndDate
     * @return
     */
    List<UserTenderVO> getLeaderboard(Date activityStartDate, Date activityEndDate);

    /**
     * 查询用户领奖记录
     * @param activityId
     * @param userId
     * @param grade
     * @return
     */
    List<ActivityUserRewardVO> selectActivityUserReward(int activityId, int userId, int grade);

    /**
     * 统计用户抽奖次数
     * @param activityId
     * @param userId
     * @param activityStartDate
     * @param activityEndDate
     * @return
     */
    int countRewardTimes(int activityId, int userId, Date activityStartDate, Date activityEndDate);

    /**
     * @Author walter.limeng
     * @Description //用户抽奖成功，保存用户奖品记录
     * @Date 17:00 2019-05-05
     * @Param [userId, activityId, grade 暂无用途，默认0, rewardName 奖励名称, rewardType 奖励类型]
     * @return void
     **/

    void saveActivityUserReward(int userId, int activityId, int grade, String rewardName, String rewardType);
}
