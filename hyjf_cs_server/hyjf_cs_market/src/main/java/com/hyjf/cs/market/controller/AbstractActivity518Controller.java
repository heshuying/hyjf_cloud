package com.hyjf.cs.market.controller;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.vo.activity.Activity518LeaderboardVO;
import com.hyjf.am.vo.activity.ActivityUserRewardVO;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.cs.market.service.Activity518Service;
import com.hyjf.cs.market.vo.activity518.Activity518DrawVO;
import com.hyjf.cs.market.vo.activity518.Activity518InfoVO;
import com.hyjf.cs.market.vo.activity518.Activity518UserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiasq
 * @version AbstractActivity518Controller, v0.1 2019/4/30 13:55
 */
public class AbstractActivity518Controller {
    private Logger logger = LoggerFactory.getLogger(AbstractActivity518Controller.class);

    protected final String SUCCESS_DESC = "成功";

    @Value("${activity.518.activityId}")
    private Integer activityId;

    @Autowired
    private Activity518Service activity518Service;

    protected BaseResult<Activity518InfoVO> queryActivityInfo(Integer userId, String successStatus, String failStatus) {
        BaseResult<Activity518InfoVO> result = new BaseResult(successStatus, SUCCESS_DESC);
        Activity518InfoVO activity518InfoVO = new Activity518InfoVO();
        ActivityListVO activityListVO = activity518Service.getActivityById(activityId);
        Date activityStartDate = new Date(activityListVO.getTimeStart() * 1000L);
        Date activityEndDate = new Date(activityListVO.getTimeEnd() * 1000L);

        //查询活动状态
        Integer started = isActivityTime(activityStartDate, activityEndDate);
        logger.debug("当前活动状态started: {}", started);
        activity518InfoVO.setStarted(started);

        //活动未开始不查询排行榜信息和用户信息
        if (started != -1) {
            // 查询排行榜
            List<Activity518InfoVO.Leaderboard> list = new ArrayList<>();
            List<Activity518LeaderboardVO> vos = activity518Service.getLeaderboard(activityStartDate, activityEndDate);
            if (!CollectionUtils.isEmpty(vos)) {
                for (Activity518LeaderboardVO vo : vos) {
                    list.add(new Activity518InfoVO.Leaderboard(vo.getUsername(), String.valueOf(vo.getAmount() == null ? BigDecimal.ZERO : vo.getAmount())));
                }
            }
            activity518InfoVO.setLeaderboard(list);

            // 查询登陆用户信息
            if (userId != null) {
                activity518InfoVO.setUsername(activity518Service.getUsernameByUserId(userId));
                BigDecimal amount = activity518Service.getUserTenderAmount(userId, activityStartDate, activityEndDate);
                activity518InfoVO.setAmount(String.valueOf(amount == null ? BigDecimal.ZERO : amount));
            }
        }

        result.setData(activity518InfoVO);
        return result;
    }

    protected BaseResult<Activity518UserInfoVO> queryUserInfo(Integer userId, String successStatus, String failStatus) {
        BaseResult<Activity518UserInfoVO> result = new BaseResult(successStatus, SUCCESS_DESC);
        Activity518UserInfoVO vo = new Activity518UserInfoVO();
        ActivityListVO activityListVO = activity518Service.getActivityById(activityId);
        Date activityStartDate = new Date(activityListVO.getTimeStart() * 1000L);
        Date activityEndDate = new Date(activityListVO.getTimeEnd() * 1000L);

        // 查询用户剩余抽奖次数
        vo.setTimes(activity518Service.countRewardTimes(activityId == null ? 0 : activityId, userId, activityStartDate, activityEndDate));

        // 查询用户累计年化出借金额
        BigDecimal amount = activity518Service.getUserTenderAmount(userId, activityStartDate, activityEndDate);
        vo.setAmount(String.valueOf(amount == null ? BigDecimal.ZERO : amount));

        // 查询抽奖记录
        List<Activity518UserInfoVO.RewardRecord> records = new ArrayList<>();
        List<ActivityUserRewardVO> rewardVOS = activity518Service.selectActivityUserReward(activityId == null ? 0 : activityId, userId, 0);
        if(!CollectionUtils.isEmpty(rewardVOS)){
            for(ActivityUserRewardVO rewardVO: rewardVOS){
                records.add(new Activity518UserInfoVO.RewardRecord(rewardVO));
            }
        }
        vo.setRecord(records);

        result.setData(vo);
        return result;
    }

    protected BaseResult<Activity518DrawVO> doDraw(Integer userId, String successStatus, String failStatus) {

        //todo 抽奖，保存抽奖记录，   amMarketClient.insertActivityUserReward   grade默认0， rewardName：奖品名称（可选字段）， rewardType奖品代号，代号详情如下
        // 代号详情   0：18元代金券
        //         * 1：58元代金券
        //         * 2：518元代金券
        //         * 3：0.8%加息券
        //         * 4：1.0%加息券
        //         * 5：iPhone XS（256G）
        //         * 6： 华为P30（256G）

        return null;
    }


    private Integer isActivityTime(Date activityStartDate, Date activityEndDate) {
        Date today = new Date();
        if (today.compareTo(activityStartDate) == -1) {
            return -1;
        }

        if (today.compareTo(activityEndDate) == 1) {
            return 1;
        }

        return 0;
    }
}
