package com.hyjf.cs.market.service.impl;

import com.hyjf.am.vo.activity.Activity518LeaderboardVO;
import com.hyjf.am.vo.activity.ActivityUserRewardVO;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.market.client.AmMarketClient;
import com.hyjf.cs.market.client.AmTradeClient;
import com.hyjf.cs.market.client.AmUserClient;
import com.hyjf.cs.market.service.Activity518Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public List<Activity518LeaderboardVO> getLeaderboard(Date activityStartDate, Date activityEndDate) {
        //todo
        return null;
    }

    @Override
    public List<ActivityUserRewardVO> selectActivityUserReward(int activityId, int userId, int grade) {
        return amMarketClient.selectActivityUserReward(activityId, userId, grade);
    }
}
