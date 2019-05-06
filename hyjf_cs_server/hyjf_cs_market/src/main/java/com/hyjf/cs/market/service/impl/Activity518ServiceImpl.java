package com.hyjf.cs.market.service.impl;

import com.hyjf.am.vo.activity.UserTenderVO;
import com.hyjf.am.vo.activity.ActivityUserRewardVO;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.market.client.AmMarketClient;
import com.hyjf.cs.market.client.AmTradeClient;
import com.hyjf.cs.market.client.AmUserClient;
import com.hyjf.cs.market.service.Activity518Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiasq
 * @version Activity518ServiceImpl, v0.1 2019/4/30 15:03
 */
@Service
public class Activity518ServiceImpl implements Activity518Service {
    private Logger logger = LoggerFactory.getLogger(Activity518ServiceImpl.class);

    @Autowired
    private AmMarketClient amMarketClient;
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmUserClient amUserClient;

    @Override
    public ActivityListVO getActivityById(Integer activityId) {
        ActivityListVO vo = amMarketClient.selectActivityList(activityId);
        if (vo == null) {
            logger.error("活动未配置, activityId is: {}", activityId);
            throw new RuntimeException("活动未配置...");
        }
        return vo;
    }

    @Override
    public BigDecimal getUserTenderAmount(Integer userId, Date activityStartDate, Date activityEndDate) {
        BigDecimal tenderAmount = amTradeClient.getUserTender(userId, activityStartDate, activityEndDate);
        if (tenderAmount != null) {
            logger.info("用户{}出借金额: {}", userId, tenderAmount);
        } else {
            logger.warn("用户{}出借金额查询异常...", userId);
        }
        return tenderAmount;
    }

    @Override
    public String getUsernameByUserId(Integer userId) {
        UserVO userVO = amUserClient.getUserById(userId);
        if (userVO == null) {
            throw new RuntimeException("查询用户异常, userId:" + userId);
        }
        return userVO.getUsername();
    }

    @Override
    public List<UserTenderVO> getLeaderboard(Date activityStartDate, Date activityEndDate) {
        return amTradeClient.getLeaderboard(activityStartDate, activityEndDate);
    }

    @Override
    public List<ActivityUserRewardVO> selectActivityUserReward(int activityId, int userId, int grade) {
        return amMarketClient.selectActivityUserReward(activityId, userId, grade);
    }

    @Override
    public int countRewardTimes(int activityId, int userId, Date activityStartDate, Date activityEndDate){
        /*
        计算规则：
            公式： 剩余抽奖次数 = 总计抽奖次数 - 已抽奖次数

            总计抽奖次数:
                1. 首次登陆汇盈平台获得一次（不限端）         最多获取1次
                2. 累计出借金额每满1万元获得一次（不限端）     最多获取3次
                3. 首次累计出借金额≥3000元获得一次，仅限微信端 最多获取1次
         */
        int sumTimes = 0;
        // 查询活动期间登陆次数，大于等于1次， 获得一次抽奖机会
        if(hasLoginInActivity(userId, activityStartDate, activityEndDate)){
            sumTimes += 1;
        }

        // 查询活动期间出借金额
        BigDecimal tenderAmount = getTenderAmountInActivity(userId, activityStartDate, activityEndDate, null);
        if (tenderAmount != null) {
            logger.info("用户: {}, 518活动期间总计投资: {}", userId, tenderAmount);
            int tenderTimes = tenderAmount.divideToIntegralValue(new BigDecimal(10000)).intValue();
            tenderTimes = tenderTimes > 3 ? 3 : (tenderTimes < 0 ? 0 : tenderTimes);
            sumTimes += tenderTimes;
        }
        // 查询活动期间微信端出借金额
        BigDecimal weTenderAmount = getTenderAmountInActivity(userId, activityStartDate, activityEndDate, 1);
        if (tenderAmount != null) {
            logger.info("用户: {}, 518活动期间微信端投资: {}", userId, weTenderAmount);
            int weTenderTimes = weTenderAmount.subtract(new BigDecimal(3000)).compareTo(BigDecimal.ZERO) >= 0 ? 1 : 0;
            sumTimes += weTenderTimes;
        }

        // 最多5次
        sumTimes = sumTimes > 5 ? 5 : sumTimes;

        //已抽奖次数
        List<ActivityUserRewardVO> rewardVOS = amMarketClient.selectActivityUserReward(activityId, userId, 0);
        int alreadyTimes = CollectionUtils.isEmpty(rewardVOS) ? 0 : rewardVOS.size();


        logger.info("sumTimes is: {}, alreadyTimes is: {}", sumTimes, alreadyTimes);
        int remainderTimes = sumTimes - alreadyTimes;

        return remainderTimes < 0 ? 0 : remainderTimes;
    }

    @Override
    public void saveActivityUserReward(int userId, int activityId, int grade, String rewardName, String rewardType) {
        amMarketClient.insertActivityUserReward(userId,activityId,grade,rewardName,rewardType);
    }

    private BigDecimal getTenderAmountInActivity(int userId, Date activityStartDate, Date activityEndDate, Integer client) {
        return amTradeClient.getTenderAmount(userId, activityStartDate, activityEndDate, client);
    }

    private boolean hasLoginInActivity(int userId, Date activityStartDate, Date activityEndDate) {
        return amUserClient.hasLoginInActivity(userId, activityStartDate, activityEndDate);
    }
}
