package com.hyjf.cs.market.controller.app.activity;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.cs.market.controller.AbstractActivity51Controller;
import com.hyjf.cs.market.vo.activity51.Activity51VO;
import com.hyjf.cs.market.vo.activity51.ActivityTimeVO;
import com.hyjf.cs.market.vo.activity51.GuessVO;
import com.hyjf.cs.market.vo.activity51.RewardReceiveVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xiasq
 * @version AppActivity51Controller, v0.1 2019-04-17 9:48
 */
@Api(tags = "app端-2019-5-1青马活动")
@RestController
@RequestMapping("/hyjf-app/activity/qingma")
public class AppActivity51Controller extends AbstractActivity51Controller {
    private Logger logger = LoggerFactory.getLogger(AppActivity51Controller.class);

    private final String H5_SUCCESS_STATUS = "000";
    private final String H5_FAIL_STATUS = "99";

    /**
     * 查询当前时间是不是在活动时间范围内
     * @return
     */
    @ApiOperation(value = "查询当前时间是不是在活动时间范围内", notes = "查询当前时间是不是在活动时间范围内")
    @RequestMapping(value = "/isActivityTime", method = RequestMethod.GET)
    public BaseResult<ActivityTimeVO> isActivityTime() {
        logger.info("app端-查询当前时间是不是在活动时间范围内...");
        return isActivityTime();
    }

    /**
     * 查询活动期间当前累计出借金额
     *
     * @return
     */
    @ApiOperation(value = "查询活动期间当前累计出借金额", notes = "累计出借金额")
    @RequestMapping(value = "/sumAmount", method = RequestMethod.GET)
    public BaseResult<Activity51VO> getSumAmount() {
        logger.info("app端-查询活动期间当前累计出借金额...");
        return getSumAmount();
    }

    /**
     * 领取优惠券  判断是否满足条件，发放优惠券
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "领取优惠券", notes = "活动期间累计出借金额达到目标值领取优惠券，用户需要满足出借年化>=1w")
    @RequestMapping(value = "/sendCoupon", method = RequestMethod.GET)
    public BaseResult sendCoupon(@RequestHeader int userId,
                                 @ApiParam(required = true, name = "grade", value = "累计出借金额区间， 从第一档（3000万）开始，依次传递1,2,3,4")
                                 @RequestParam int grade) {
        logger.info("app端-领取优惠券, userId is: {}", userId);
        return sendCoupon(userId, grade);
    }

    @ApiOperation(value = "单个档位判断用户是否已经领取优惠券", notes = "判断用户是否已经领取优惠券-对应累计出借金额区间")
    @RequestMapping(value = "/isReceiveCoupon", method = RequestMethod.GET)
    public BaseResult<RewardReceiveVO> isReceiveCoupon(@RequestHeader int userId,
                                                       @ApiParam(required = true, name = "grade", value = "累计出借金额区间， 从第一档（3000万）开始，依次传递1,2,3,4")
                                                       @RequestParam int grade) {
        logger.info("app端-单个档位判断用户是否已经领取优惠券, userId is: {}， grade is: {}", userId, grade);
        return isReceiveCoupon(userId, grade);
    }

    @ApiOperation(value = "批量判断用户是否已经领取优惠券", notes = "判断用户是否已经领取优惠券-对应累计出借金额区间")
    @RequestMapping(value = "/getReceiveStatusList", method = RequestMethod.GET)
    public BaseResult<List<RewardReceiveVO>> getReceiveStatusList(@RequestHeader int userId) {
        logger.info("app端-批量判断用户是否已经领取优惠券, userId is: {}", userId);
        return getReceiveStatusList(userId);
    }

    /**
     * 用户竞猜
     *
     * @param userId
     * @param grade
     * @return
     */
    @ApiOperation(value = "用户竞猜", notes = "不支持重复竞猜")
    @RequestMapping(value = "/guess", method = RequestMethod.GET)
    public BaseResult guess(@RequestHeader int userId,
                            @ApiParam(required = true, name = "grade", value = "用户竞猜区间， 从第一档（1-99）开始，依次传递1,2,3,4")
                            @RequestParam int grade) {
        logger.info("app端-用户竞猜, userId is: {}, grade is: {}", userId, grade);
        return guess(userId, grade);
    }

    /**
     * app端询用户是否竞猜
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "查询用户是否竞猜", notes = "查询用户是否竞猜")
    @RequestMapping(value = "/isGuess", method = RequestMethod.GET)
    public BaseResult<GuessVO> isGuess(@RequestHeader int userId) {
        logger.info("app端-用户竞猜, userId is: {}", userId);
        return isGuess(userId);
    }

    @Override
    protected String getSuccessStatus() {
        return H5_SUCCESS_STATUS;
    }

    @Override
    protected String getFailStatus() {
        return H5_FAIL_STATUS;
    }
}
