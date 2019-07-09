package com.hyjf.cs.market.controller;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.vo.activity.ActivityUserRewardVO;
import com.hyjf.am.vo.activity.UserTenderVO;
import com.hyjf.am.vo.coupon.UserCouponBean;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.common.util.CouponUtil;
import com.hyjf.cs.market.dto.activity518.RewardTimesDTO;
import com.hyjf.cs.market.mq.base.CommonProducer;
import com.hyjf.cs.market.mq.base.MessageContent;
import com.hyjf.cs.market.service.Activity518Service;
import com.hyjf.cs.market.service.AsyncService;
import com.hyjf.cs.market.util.Activity518Prize;
import com.hyjf.cs.market.util.LotteryUtil;
import com.hyjf.cs.market.vo.activity518.Activity518DrawVO;
import com.hyjf.cs.market.vo.activity518.Activity518InfoVO;
import com.hyjf.cs.market.vo.activity518.Activity518UserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiasq
 * @version AbstractActivity518Controller, v0.1 2019/4/30 13:55
 */
public abstract class AbstractActivity518Controller extends AbstractController{
    private Logger logger = LoggerFactory.getLogger(AbstractActivity518Controller.class);

    protected final String SUCCESS_DESC = "成功";

    @Value("${activity.518.activityId}")
    private Integer activityId;
    @Value("${activity.518.couponCodes}")
    private String couponCodes;

    @Autowired
    private Activity518Service activity518Service;


    @Resource
    private AsyncService asyncService;

    /**
     * 共有查询活动信息接口
     * @param userId
     * @return
     */
    protected BaseResult<Activity518InfoVO> queryActivityInfo(Integer userId) {
        BaseResult<Activity518InfoVO> result = new BaseResult(getSuccessStatus(), SUCCESS_DESC);
        Activity518InfoVO activity518InfoVO = new Activity518InfoVO();
        ActivityListVO activityListVO = activity518Service.getActivityById(activityId);
        Date activityStartDate = new Date(activityListVO.getTimeStart() * 1000L);
        Date activityEndDate = new Date(activityListVO.getTimeEnd() * 1000L);

        //查询活动状态
        Integer started = isActivityTime(activityStartDate, activityEndDate);
        logger.debug("当前活动状态started: {}", started);
        activity518InfoVO.setStarted(started);

        // 无论活动状态如何，返回用户信息
        String username = userId != null ? activity518Service.getUsernameByUserId(userId) : "";
        activity518InfoVO.setUsername(username);

        //活动未开始不查询排行榜信息和用户信息
        if (started != -1) {
            // 查询排行榜
            List<Activity518InfoVO.Leaderboard> list = new ArrayList<>();
            List<UserTenderVO> vos = activity518Service.getLeaderboard(activityStartDate, activityEndDate);
            if (!CollectionUtils.isEmpty(vos)) {
                for (UserTenderVO vo : vos) {
                    list.add(new Activity518InfoVO.Leaderboard(desensitization(vo.getMobile()), String.valueOf(vo.getAmount() == null ? BigDecimal.ZERO : vo.getAmount())));
                }
            }
            activity518InfoVO.setLeaderboard(list);

            // 查询登陆用户信息，未登陆返回空
            if (userId != null) {
                BigDecimal amount = activity518Service.getUserTenderAmount(userId, activityStartDate, activityEndDate);
                activity518InfoVO.setAmount(String.valueOf(amount == null ? BigDecimal.ZERO : amount));
            }
        }

        result.setData(activity518InfoVO);
        return result;
    }

    /**
     * 共有查询用户信息接口
     * @param userId
     * @return
     */
    protected BaseResult<Activity518UserInfoVO> queryUserInfo(Integer userId) {
        BaseResult<Activity518UserInfoVO> result = new BaseResult(getSuccessStatus(), SUCCESS_DESC);
        Activity518UserInfoVO vo = new Activity518UserInfoVO();
        ActivityListVO activityListVO = activity518Service.getActivityById(activityId);
        Date activityStartDate = new Date(activityListVO.getTimeStart() * 1000L);
        Date activityEndDate = new Date(activityListVO.getTimeEnd() * 1000L);

        // 查询用户剩余抽奖次数
        RewardTimesDTO dto = activity518Service.countRewardTimes(activityId == null ? 0 : activityId, userId, activityStartDate, activityEndDate);
        vo.setTimes(dto.getTimes());
        vo.setAlreadyTimes(dto.getAlreadyTimes());

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

    /**
     *  共有抽奖接口
     *      1.校验剩余抽奖次数大于0
     *      2.随机抽奖
     *      3.保存抽奖记录
     *      4.发送奖励
     *      5.失败回滚，成功返回奖励信息
     * @param userId
     * @return
     */
    protected BaseResult<Activity518DrawVO> doDraw(Integer userId) {
        BaseResult<Activity518DrawVO> resultBean = new BaseResult<>(getSuccessStatus(), SUCCESS_DESC);
        Activity518DrawVO activity518DrawVO = new Activity518DrawVO();

        //查询活动是否存在
        ActivityListVO activityListVO = activity518Service.getActivityById(activityId);
        if(null != activityListVO){
            Date activityStartDate = new Date(activityListVO.getTimeStart() * 1000L);
            Date activityEndDate = new Date(activityListVO.getTimeEnd() * 1000L);

            // 查询用户剩余抽奖次数
            RewardTimesDTO dto = activity518Service.countRewardTimes(activityId == null ? 0 : activityId, userId, activityStartDate, activityEndDate);
            int times = dto == null ? 0 : dto.getTimes();
            logger.info("用户：{},剩余抽奖次数：{}", userId, times);
            if(times > 0){
                //抽奖，保存抽奖记录，   amMarketClient.insertActivityUserReward   grade默认0， rewardName：奖品名称（可选字段）， rewardType奖品代号，代号详情如下
                // 代号详情   0：18元代金券 1：58元代金券  2：518元代金券 3：0.8%加息券 4：1.0%加息券  5：iPhone XS（256G） 6： 华为P30（256G）
                //用户进行抽奖活动
                int luckNum = luckDraw(userId);

                //保存用户抽奖信息
                asyncService.saveUserDraw(luckNum,userId,activityId,couponCodes);
                activity518DrawVO.setCurrentAward(luckNum);
                resultBean.setData(activity518DrawVO);
                logger.info("用户：{},抽奖完成，奖品：{}",userId,Activity518Prize.getValue(luckNum));
            }else{
                resultBean.setStatus(getFailStatus());
                resultBean.setStatusDesc("用户抽奖机会不足！");
            }
        }else{
            resultBean.setStatus(getFailStatus());
            resultBean.setStatusDesc("活动不存在！");
        }

        return resultBean;
    }

    /**
     * @Author walter.limeng
     * @Description //抽奖，0：18元代金券，1：58元代金券，2：518元代金券，3：0.8%加息券，4：1.0%加息券 因产品决定不能让用户抽中手机，故手机不参与抽奖
     * @Date 15:28 2019-05-05
     * @Param []
     * @return int 抽中奖品的代码
     **/
    public int luckDraw(Integer userId){
        //抽奖开始
        LotteryUtil ll = new LotteryUtil(Activity518Prize.getPrize());
        int luckNum = ll.randomColunmIndex();
        logger.info("用户ID：{} 的用户中奖代码：{}",userId, luckNum);
        return luckNum;
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


    /**
     * 脱敏
     * @param mobile
     * @return
     */
    private String desensitization(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        result.append(mobile, 0, 3);
        result.append("****");
        result.append(mobile.substring(mobile.length() - 4));
        return result.toString();
    }
}
