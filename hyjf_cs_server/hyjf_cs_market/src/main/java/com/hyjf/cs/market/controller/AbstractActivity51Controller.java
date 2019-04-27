package com.hyjf.cs.market.controller;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.vo.activity.ActivityUserGuessVO;
import com.hyjf.cs.market.service.Activity51Service;
import com.hyjf.cs.market.vo.Activity51VO;
import com.hyjf.cs.market.vo.ActivityTimeVO;
import com.hyjf.cs.market.vo.GuessVO;
import com.hyjf.cs.market.vo.RewardReceiveVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiasq
 * @version AbstractActivity51Controller, v0.1 2019/4/22 14:10
 */
public class AbstractActivity51Controller {
    private Logger logger = LoggerFactory.getLogger(AbstractActivity51Controller.class);

    /**
     * 计算进度百分比因子
     */
    protected final BigDecimal MULTIPLY = new BigDecimal(100);
    protected final BigDecimal DIVIDE = new BigDecimal(100000000);
    /**
     * 第一档到末档金额区间
     */
    protected final BigDecimal SUM_AMOUNT_GRADE_1 = new BigDecimal(30000000);
    protected final BigDecimal SUM_AMOUNT_GRADE_2 = new BigDecimal(50000000);
    protected final BigDecimal SUM_AMOUNT_GRADE_3 = new BigDecimal(80000000);
    protected final BigDecimal SUM_AMOUNT_GRADE_4 = new BigDecimal(100000000);

    protected final String ACTIVITY_NOT_START = "活动未开始";
    protected final String ACTIVITY_TENDER_NOT_ENOUGH = "投资年化金额未达到发放标准(1w)";
    protected final String ACTIVITY_SUM_TENDER_NOT_ENOUGH = "累计出借金额未达到最低标准(3000w)";
    protected final String ACTIVITY_GUESS_END = "竞猜活动已结束!";

    private final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //todo 为了适应测试场景， 先改成26截止 上线要修改回去
    // private final String guessEndTime = "2019-05-05 00:00:00";
    private final String guessEndTime = "2019-04-27 00:00:00";

    @Autowired
    protected Activity51Service activity51Service;

    protected BaseResult<ActivityTimeVO> isActivityTime(String successStatus, String failStatus) {
        BaseResult<ActivityTimeVO> result = new BaseResult(successStatus, "成功");

        ActivityTimeVO vo = null;
        switch (activity51Service.isActivityTime()) {
            case -1:
                vo = new ActivityTimeVO("N", "活动未开始");
                break;
            case 0:
                vo = new ActivityTimeVO("Y", "活动进行中");
                break;
            case 1:
                vo = new ActivityTimeVO("N", "活动已结束");
                break;
            default:
                throw new RuntimeException("unknown error");
        }
        result.setData(vo);
        return result;
    }

    protected BaseResult<Activity51VO> getSumAmount(String successStatus, String failStatus) {
        BaseResult<Activity51VO> result = new BaseResult(successStatus, "查询成功");
        if (activity51Service.isActivityTime() != 0) {
            return buildResult(failStatus, ACTIVITY_NOT_START);
        }
        BigDecimal sumAmount = activity51Service.getSumAmount();
        sumAmount = sumAmount == null ? BigDecimal.ZERO : sumAmount;
        String rate = String.format("%.2f", sumAmount.multiply(MULTIPLY).divide(DIVIDE)).concat("%");
        if (sumAmount.compareTo(DIVIDE) == 1) {
            rate = "100.00%";
        }
        logger.info("当前累计出借金额sumAmount: {}, 进度rate: {}", sumAmount, rate);
        result.setData(new Activity51VO(sumAmount, rate, getGradeFromSumAmount(sumAmount)));
        return result;
    }

    protected BaseResult sendCoupon(int userId, int grade, String successStatus, String failStatus) {
        logger.info("领取优惠券, userId is: {}", userId);
        if (activity51Service.isActivityTime() != 0) {
            logger.debug("sendCoupon: {}", ACTIVITY_NOT_START);
            return buildResult(failStatus, ACTIVITY_NOT_START);
        }

        if (!activity51Service.canSendCoupon(userId)) {
            logger.debug("sendCoupon: {}", ACTIVITY_TENDER_NOT_ENOUGH);
            return buildResult(failStatus, ACTIVITY_TENDER_NOT_ENOUGH);
        }

        //档位奖励
        // int grade = 0;
        BigDecimal sumAmount = activity51Service.getSumAmount();
        //未达到领取奖励最低标准，返回失败
        if (sumAmount == null || sumAmount.compareTo(SUM_AMOUNT_GRADE_1) < 0) {
            return buildResult(failStatus, ACTIVITY_SUM_TENDER_NOT_ENOUGH);
        }

        // 判断是否已领取奖励
        if (activity51Service.isRepeatReceive(userId, grade)) {
            return buildResult(failStatus, "重复领取");
        }

        boolean sendFlag = activity51Service.sendCoupon(userId, grade);
        if (sendFlag == false) {
            return buildResult(failStatus, "优惠券领取异常");
        }
        return buildResult(successStatus, "优惠券领取成功");
    }


    protected BaseResult<RewardReceiveVO> isReceiveCoupon(int userId, int grade, String successStatus, String failStatus) {
        logger.info("单个档位判断用户是否已经领取优惠券, userId is: {}， grade is: {}", userId, grade);
        if (activity51Service.isActivityTime() != 0) {
            logger.debug("isReceiveCoupon: {}", ACTIVITY_NOT_START);
            return buildResult(failStatus, ACTIVITY_NOT_START);
        }

        if (!activity51Service.canSendCoupon(userId)) {
            logger.debug("isReceiveCoupon: {}", ACTIVITY_TENDER_NOT_ENOUGH);
            return buildResult(failStatus, ACTIVITY_TENDER_NOT_ENOUGH);
        }

        BaseResult result = new BaseResult(successStatus, "查询成功");
        // 判断是否已领取奖励
        boolean receiveFlag = activity51Service.isRepeatReceive(userId, grade);
        if (receiveFlag) {
            result.setData(new RewardReceiveVO(grade, "Y", "已领取"));
        } else {
            result.setData(new RewardReceiveVO(grade, "N", "未领取"));
        }
        return result;
    }


    protected BaseResult<List<RewardReceiveVO>> getReceiveStatusList(int userId, String successStatus, String failStatus) {
        logger.info("批量判断用户是否已经领取优惠券, userId is: {}", userId);
        if (activity51Service.isActivityTime() != 0) {
            logger.debug("getReceiveStatusList: {}", ACTIVITY_NOT_START);
            return buildResult(failStatus, ACTIVITY_NOT_START);
        }

        if (!activity51Service.canSendCoupon(userId)) {
            logger.debug("getReceiveStatusList: {}", ACTIVITY_TENDER_NOT_ENOUGH);
            return buildResult(failStatus, ACTIVITY_TENDER_NOT_ENOUGH);
        }

        BaseResult result = new BaseResult(successStatus, "查询成功");
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


    protected BaseResult guess(int userId, int grade, String successStatus, String failStatus) {
        logger.info("用户竞猜, userId is: {}, grade is: {}", userId, grade);
        if (activity51Service.isActivityTime() != 0) {
            logger.debug("guess: {}", ACTIVITY_NOT_START);
            return buildResult(failStatus, ACTIVITY_NOT_START);
        }

        if (!activity51Service.canSendCoupon(userId)) {
            logger.debug("guess: {}", ACTIVITY_TENDER_NOT_ENOUGH);
            return buildResult(failStatus, ACTIVITY_TENDER_NOT_ENOUGH);
        }

        // 判断是否重复竞猜
        if (activity51Service.isRepeatGuess(userId)) {
            return buildResult(failStatus, "重复竞猜");
        }

        activity51Service.guess(userId, grade);
        return buildResult(successStatus, "竞猜成功");
    }


    public BaseResult<GuessVO> isGuess(int userId, String successStatus, String failStatus) {
        logger.info("用户竞猜, userId is: {}", userId);
        if (activity51Service.isActivityTime() != 0) {
            return buildResult(failStatus, ACTIVITY_NOT_START);
        }

        if (isGuessEnd()) {
            logger.debug("isGuess: {}", ACTIVITY_GUESS_END);
            return buildResult(failStatus, ACTIVITY_GUESS_END);
        }

        if (!activity51Service.canSendCoupon(userId)) {
            return buildResult(failStatus, ACTIVITY_TENDER_NOT_ENOUGH);
        }


        // 判断是否已经竞猜
        BaseResult result = new BaseResult(successStatus, "查询成功");
        ActivityUserGuessVO vo = activity51Service.getUserGuess(userId);
        if (vo != null) {
            result.setData(new GuessVO("Y", "已竞猜", vo.getGrade()));
        } else {
            result.setData(new GuessVO("N", "未竞猜"));
        }
        logger.info("用户竞猜, userId is: {}", userId);
        return result;
    }

    private boolean isGuessEnd(){
        boolean result = false;
        try{
            Date today = new Date();
            Date dateString = sdf.parse(guessEndTime);
            if (today.compareTo(dateString) == 1) {
                result = true;
            }
        }catch (Exception e){
            logger.error("时间格式化异常");
        }
        logger.debug("result: {}", result);
        return result;
    }

    protected int getGradeFromSumAmount(BigDecimal sumAmount) {
        // 第一档奖励
        if (sumAmount.compareTo(SUM_AMOUNT_GRADE_4) >= 0) {
            return 4;
        }

        // 第二档奖励
        if (sumAmount.compareTo(SUM_AMOUNT_GRADE_3) >= 0) {
            return 3;
        }
        // 第三档奖励
        if (sumAmount.compareTo(SUM_AMOUNT_GRADE_2) >= 0) {
            return 2;
        }
        // 末等奖励
        if (sumAmount.compareTo(SUM_AMOUNT_GRADE_1) >= 0) {
            return 1;
        }
        return 0;
    }

    protected BaseResult buildResult(String code, String msg) {
        BaseResult result = new BaseResult();
        result.setStatus(code);
        result.setStatusDesc(msg);
        return result;
    }

}
