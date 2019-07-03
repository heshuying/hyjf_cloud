package com.hyjf.cs.market.service.impl;

import com.hyjf.am.vo.activity.ActivityUserRewardVO;
import com.hyjf.am.vo.activity.UserTenderVO;
import com.hyjf.am.vo.coupon.UserCouponBean;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.common.util.CouponUtil;
import com.hyjf.cs.market.client.AmMarketClient;
import com.hyjf.cs.market.client.AmTradeClient;
import com.hyjf.cs.market.client.AmUserClient;
import com.hyjf.cs.market.dto.activity518.RewardTimesDTO;
import com.hyjf.cs.market.mq.base.CommonProducer;
import com.hyjf.cs.market.mq.base.MessageContent;
import com.hyjf.cs.market.service.Activity518Service;
import com.hyjf.cs.market.util.Activity518Prize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    @Autowired
    private CommonProducer producer;

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
    public RewardTimesDTO countRewardTimes(int activityId, int userId, Date activityStartDate, Date activityEndDate){
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
            logger.info("用户: {}, 518活动期间已登录，抽奖次数: 1", userId);
            sumTimes += 1;
        }

        // 查询活动期间出借金额
        BigDecimal tenderAmount = getTenderAmountInActivity(userId, activityStartDate, activityEndDate, null);
        if (tenderAmount != null) {
            int tenderTimes = tenderAmount.divideToIntegralValue(new BigDecimal(10000)).intValue();
            logger.info("用户: {}, 518活动期间总计投资: {}, 获得抽奖次数: {}", userId, tenderAmount, tenderTimes);
            tenderTimes = tenderTimes > 3 ? 3 : (tenderTimes < 0 ? 0 : tenderTimes);
            sumTimes += tenderTimes;
        }
        // 查询活动期间微信端出借金额
        BigDecimal weTenderAmount = getTenderAmountInActivity(userId, activityStartDate, activityEndDate, 1);
        if (tenderAmount != null) {
            int weTenderTimes = weTenderAmount.subtract(new BigDecimal(3000)).compareTo(BigDecimal.ZERO) >= 0 ? 1 : 0;
            logger.info("用户: {}, 518活动期间微信端投资: {}, 获得抽奖次数: {}", userId, weTenderAmount, weTenderTimes);
            sumTimes += weTenderTimes;
        }

        // 最多5次
        sumTimes = sumTimes > 5 ? 5 : sumTimes;

        //已抽奖次数
        List<ActivityUserRewardVO> rewardVOS = amMarketClient.selectActivityUserReward(activityId, userId, 0);
        int alreadyTimes = CollectionUtils.isEmpty(rewardVOS) ? 0 : rewardVOS.size();


        logger.info("sumTimes is: {}, alreadyTimes is: {}", sumTimes, alreadyTimes);
        int remainderTimes = sumTimes - alreadyTimes;

        RewardTimesDTO dto = new RewardTimesDTO();
        dto.setTimes(remainderTimes < 0 ? 0 : remainderTimes);
        dto.setAlreadyTimes(alreadyTimes);
        return dto;
    }

    @Override
    public void saveActivityUserReward(int userId, int activityId, int grade, String rewardName, String rewardType) {
        amMarketClient.insertActivityUserReward(userId,activityId,grade,rewardName,rewardType);
    }

    @Override
    public Integer saveActivity518UserReward(ActivityUserRewardVO vo) {
        return amMarketClient.saveActivity518UserReward(vo);
    }

    @Override
    public void saveUserDraw(int luckNum, Integer userId, Integer activityId, String couponCodes) {
        String rewardType = luckNum + "";
        //保存用户中奖记录

        ActivityUserRewardVO vo = new ActivityUserRewardVO();
        vo.setActivityId(activityId);
        vo.setRewardName(Activity518Prize.getValue(luckNum));
        vo.setRewardType(rewardType);
        vo.setUserId(userId);
        vo.setSendType("系统发放");
        vo.setSendStatus(1);
        vo.setGrade(0);
        Integer rewardId = this.saveActivity518UserReward(vo);
        // 自动发送用户奖品
        // 根据配置获取配置文件中配置的活动优惠券，把所有优惠券按照上述优惠券代码依次排列，分割为数组，根据中奖编号获取优惠券编号
        String [] st1 = couponCodes.split(",");
        logger.info("活动配置优惠券编号：{}",couponCodes);
        String couponCode = st1[luckNum];
        //系统通过MQ自动发放用户中奖的优惠券
        try {
            logger.info("用户:{}发放优惠券:{}, 活动:{}", userId, couponCode, activityId);
            UserCouponBean couponBean = new UserCouponBean(userId, CouponUtil.NUM_12, couponCode, activityId);
            producer.messageSend(new MessageContent(MQConstant.GRANT_COUPON_TOPIC, userId + "," + "", couponBean));
        } catch (MQException e) {
            logger.error("用户:{}发放优惠券:{}, 活动:{}，发放失败！", userId, couponCode, activityId);
            logger.error("活动发券失败...", e);
        }
    }

    private BigDecimal getTenderAmountInActivity(int userId, Date activityStartDate, Date activityEndDate, Integer client) {
        return amTradeClient.getTenderAmount(userId, activityStartDate, activityEndDate, client);
    }

    private boolean hasLoginInActivity(int userId, Date activityStartDate, Date activityEndDate) {
        return amUserClient.hasLoginInActivity(userId, activityStartDate, activityEndDate);
    }
}
