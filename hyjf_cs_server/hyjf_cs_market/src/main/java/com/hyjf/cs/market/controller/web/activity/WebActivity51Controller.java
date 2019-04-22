package com.hyjf.cs.market.controller.web.activity;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.vo.activity.ActivityUserGuessVO;
import com.hyjf.cs.market.controller.AbstractActivity51Controller;
import com.hyjf.cs.market.service.Activity51Service;
import com.hyjf.cs.market.vo.Activity51VO;
import com.hyjf.cs.market.vo.GuessVO;
import com.hyjf.cs.market.vo.RewardReceiveVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiasq
 * @version WebActivity51Controller, v0.1 2019-04-17 9:48
 */
@Api(tags = "web端-2019-5-1青马活动")
@RestController
@RequestMapping("/hyjf-web/activity/qingma")
public class WebActivity51Controller extends AbstractActivity51Controller {
    private Logger logger = LoggerFactory.getLogger(WebActivity51Controller.class);

    @Autowired
    private Activity51Service activity51Service;

    /**
     * 查询活动期间当前累计出借金额
     * @return
     */
    @ApiOperation(value = "查询活动期间当前累计出借金额", notes = "累计出借金额")
    @RequestMapping(value = "/sumAmount", method = RequestMethod.GET)
    public BaseResult<Activity51VO> getSumAmount(){
        logger.info("查询活动期间当前累计出借金额...");
        return getSumAmount("000", "成功","1", ACTIVITY_NOT_START);
    }

    /**
     * 领取优惠券  判断是否满足条件，发放优惠券
     * @param userId
     * @return
     */
    @ApiOperation(value = "领取优惠券", notes = "活动期间累计出借金额达到目标值领取优惠券，用户需要满足出借年化>=1w")
    @RequestMapping(value = "/sendCoupon", method = RequestMethod.GET)
    public BaseResult sendCoupon(@RequestHeader int userId,
                                 @ApiParam(required = true, name = "grade", value = "累计出借金额区间， 从第一档（3000万）开始，依次传递1,2,3,4")
                                 @RequestParam int grade){
        logger.info("领取优惠券, userId is: {}", userId);
        if(!activity51Service.isActivityTime()){
            return buildResult("1", ACTIVITY_NOT_START);
        }

        if(!activity51Service.canSendCoupon(userId)){
            return buildResult("1", ACTIVITY_TENDER_NOT_ENOUTH);
        }

        //档位奖励
        // int grade = 0;
        BigDecimal sumAmount = activity51Service.getSumAmount();
        //未达到领取奖励最低标准，返回失败
        if(sumAmount == null || sumAmount.compareTo(SUM_AMOUNT_GRADE_1)< 0) {
            return buildResult("1", ACTIVITY_SUM_TENDER_NOT_ENOUTH);
        }

        // 判断是否已领取奖励
        if(activity51Service.isRepeatReceive(userId, grade)){
            return buildResult("1", "重复领取");
        }

        boolean sendFlag = activity51Service.sendCoupon(userId, grade);
        if (sendFlag == false) {
            return buildResult("1", "优惠券领取异常");
        }
        return buildResult("000", "优惠券领取成功");
    }

    @ApiOperation(value = "单个档位判断用户是否已经领取优惠券", notes = "判断用户是否已经领取优惠券-对应累计出借金额区间")
    @RequestMapping(value = "/isReceiveCoupon", method = RequestMethod.GET)
    public BaseResult<RewardReceiveVO> isReceiveCoupon(@RequestHeader int userId,
                                                       @ApiParam(required = true, name = "grade", value = "累计出借金额区间， 从第一档（3000万）开始，依次传递1,2,3,4")
                                      @RequestParam int grade){
        logger.info("单个档位判断用户是否已经领取优惠券, userId is: {}， grade is: {}", userId, grade);
        if(!activity51Service.isActivityTime()){
            return buildResult("1", ACTIVITY_NOT_START);
        }

        if(!activity51Service.canSendCoupon(userId)){
            return buildResult("1", ACTIVITY_TENDER_NOT_ENOUTH);
        }

        BaseResult result = new BaseResult("000","查询成功");
        // 判断是否已领取奖励
        boolean receiveFlag = activity51Service.isRepeatReceive(userId, grade);
        if(receiveFlag){
            result.setData(new RewardReceiveVO(grade, "Y", "已领取"));
        } else {
            result.setData(new RewardReceiveVO(grade, "N", "未领取"));
        }
        return result;
    }

    @ApiOperation(value = "批量判断用户是否已经领取优惠券", notes = "判断用户是否已经领取优惠券-对应累计出借金额区间")
    @RequestMapping(value = "/getReceiveStatusList", method = RequestMethod.GET)
    public BaseResult<List<RewardReceiveVO>> getReceiveStatusList(@RequestHeader int userId) {
        logger.info("批量判断用户是否已经领取优惠券, userId is: {}", userId);
        if (!activity51Service.isActivityTime()) {
            return buildResult("1", ACTIVITY_NOT_START);
        }

        if (!activity51Service.canSendCoupon(userId)) {
            return buildResult("1", ACTIVITY_TENDER_NOT_ENOUTH);
        }

        BaseResult result = new BaseResult("000", "查询成功");
        List<RewardReceiveVO> list = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            // 判断是否已领取奖励
            boolean receiveFlag = activity51Service.isRepeatReceive(userId, i);
            if (receiveFlag) {
                list.add(new RewardReceiveVO(i, "Y", "已领取"));
            } else {
                list.add(new RewardReceiveVO(i, "N", "未领取"));
            }
        }
        result.setData(list);
        return result;
    }


    /**
     * 用户竞猜
     * @param userId
     * @param grade
     * @return
     */
    @ApiOperation(value = "用户竞猜", notes = "不支持重复竞猜")
    @RequestMapping(value = "/guess", method = RequestMethod.GET)
    public BaseResult guess(@RequestHeader int userId,
                            @ApiParam(required = true, name = "grade", value = "用户竞猜区间， 从第一档（1-99）开始，依次传递1,2,3,4")
                            @RequestParam int grade){
        logger.info("用户竞猜, userId is: {}, grade is: {}", userId, grade);
        if(!activity51Service.isActivityTime()){
            return buildResult("1", ACTIVITY_NOT_START);
        }

        if(!activity51Service.canSendCoupon(userId)){
            return buildResult("1", ACTIVITY_TENDER_NOT_ENOUTH);
        }

        // 判断是否重复竞猜
        if(activity51Service.isRepeatGuess(userId)){
            return buildResult("1", "重复竞猜");
        }

        activity51Service.guess(userId, grade);
        return buildResult("000", "竞猜成功");
    }


    /**
     * 查询用户是否竞猜
     * @param userId
     * @return
     */
    @ApiOperation(value = "查询用户是否竞猜", notes = "查询用户是否竞猜")
    @RequestMapping(value = "/isGuess", method = RequestMethod.GET)
    public BaseResult<GuessVO> guess(@RequestHeader int userId){
        logger.info("用户竞猜, userId is: {}", userId);
        if(!activity51Service.isActivityTime()){
            return buildResult("1", ACTIVITY_NOT_START);
        }

        if(!activity51Service.canSendCoupon(userId)){
            return buildResult("1", ACTIVITY_TENDER_NOT_ENOUTH);
        }

        // 判断是否已经竞猜
        BaseResult result = new BaseResult("000","查询成功");
        ActivityUserGuessVO vo = activity51Service.getUserGuess(userId);
        if (vo != null) {
            result.setData(new GuessVO("Y", "已竞猜", vo.getGrade()));
        } else {
            result.setData(new GuessVO("N", "未竞猜"));
        }
        return result;
    }

}
