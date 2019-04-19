package com.hyjf.cs.market.controller.wechat.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.hyjf.cs.market.controller.app.activity.AppActivity51Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.cs.market.service.Activity51Service;

import io.swagger.annotations.*;

/**
 * @author xiasq
 * @version WechatActivity51Controller, v0.1 2019-04-17 9:48
 */
@Api(tags = "wechat端-2019-5-1青马活动")
@RestController
@RequestMapping("/hyjf-wechat/activity/qingma")
public class WechatActivity51Controller {
    private Logger logger = LoggerFactory.getLogger(WechatActivity51Controller.class);
    /**
     * 计算进度百分比因子
     */
    private final BigDecimal MULTIPLY = new BigDecimal(100);
    private final BigDecimal DIVIDE = new BigDecimal(100000000);
    /**
     * 第一档到末档金额区间
     */
    private final BigDecimal SUM_AMOUNT_GRADE_1 = new BigDecimal(30000000);
    private final BigDecimal SUM_AMOUNT_GRADE_2 = new BigDecimal(50000000);
    private final BigDecimal SUM_AMOUNT_GRADE_3 = new BigDecimal(80000000);
    private final BigDecimal SUM_AMOUNT_GRADE_4 = new BigDecimal(100000000);

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
        BaseResult<Activity51VO> result = new BaseResult("000", "成功");
        if(!activity51Service.isActivityTime()){
            return buildResult("99", "活动未开始");
        }
		BigDecimal sumAmount = activity51Service.getSumAmount();
		sumAmount = sumAmount == null ? BigDecimal.ZERO : sumAmount;
		String rate = String.format("%.2f", sumAmount.multiply(MULTIPLY).divide(DIVIDE)).concat("%");
        if(sumAmount.compareTo(DIVIDE) == 1){
            rate = "100.00%";
        }
		logger.info("当前累计出借金额sumAmount: {}, 进度rate: {}", sumAmount, rate);
        result.setData(new Activity51VO(sumAmount, rate, getGradeFromSumAmount(sumAmount)));
        return result;
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
            return buildResult("99", "活动未开始");
        }

        if(!activity51Service.canSendCoupon(userId)){
            return buildResult("99", "投资年化金额未达到发放标准(1w)");
        }

        //档位奖励
        // int grade = 0;
        BigDecimal sumAmount = activity51Service.getSumAmount();
        //未达到领取奖励最低标准，返回失败
        if(sumAmount == null || sumAmount.compareTo(SUM_AMOUNT_GRADE_1)< 0) {
            return buildResult("99", "累计出借金额未达到最低标准(3000w)");
        }

        // 判断是否已领取奖励
        if(activity51Service.isRepeatReceive(userId, grade)){
            return buildResult("99", "重复领取");
        }

        boolean sendFlag = activity51Service.sendCoupon(userId, grade);
        if (sendFlag == false) {
            return buildResult("99", "优惠券领取异常");
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
            return buildResult("99", "活动未开始");
        }

        if(!activity51Service.canSendCoupon(userId)){
            return buildResult("99", "投资年化金额未达到发放标准(1w)");
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
            return buildResult("99", "活动未开始");
        }

        if (!activity51Service.canSendCoupon(userId)) {
            return buildResult("99", "投资年化金额未达到发放标准(1w)");
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
            return buildResult("99", "活动未开始");
        }

        if(!activity51Service.canSendCoupon(userId)){
            return buildResult("99", "投资年化金额未达到发放标准(1w)");
        }

        // 判断是否重复竞猜
        if(activity51Service.isRepeatGuess(userId)){
            return buildResult("99", "重复竞猜");
        }

        activity51Service.guess(userId, grade);
        return buildResult("000", "竞猜成功");
    }

	private BaseResult buildResult(String code, String msg) {
        BaseResult result = new BaseResult();
		result.setStatus(code);
		result.setStatusDesc(msg);
		return result;
	}


    private int getGradeFromSumAmount(BigDecimal sumAmount) {
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

	@ApiModel(value = "查询当前累计出借金额返回参数")
	class Activity51VO {
		@ApiModelProperty("当前累计出借金额")
		private BigDecimal sumAmount;
		@ApiModelProperty("当前进度， 例如 31.10%")
		private String progressRate;
        @ApiModelProperty("当前档位， 达到3000万返回1，依次2,3,4")
        private int progressGrade;

        public Activity51VO(BigDecimal sumAmount, String progressRate, int progressGrade) {
            this.sumAmount = sumAmount;
            this.progressRate = progressRate;
            this.progressGrade = progressGrade;
        }

		public BigDecimal getSumAmount() {
			return sumAmount;
		}

		public void setSumAmount(BigDecimal sumAmount) {
			this.sumAmount = sumAmount;
		}

		public String getProgressRate() {
			return progressRate;
		}

		public void setProgressRate(String progressRate) {
			this.progressRate = progressRate;
		}

        public int getProgressGrade() {
            return progressGrade;
        }

        public void setProgressGrade(int progressGrade) {
            this.progressGrade = progressGrade;
        }
    }

    @ApiModel(value = "查询用户奖励领取信息")
    class RewardReceiveVO {

        @ApiModelProperty("奖励档位")
        private int grade;
        @ApiModelProperty("领取标志, Y-已领取 N-未领取")
        private String receiveFlag;
        @ApiModelProperty("领取描述")
        private String receiveDesc;

        public RewardReceiveVO(int grade, String receiveFlag, String receiveDesc) {
            this.grade = grade;
            this.receiveFlag = receiveFlag;
            this.receiveDesc = receiveDesc;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getReceiveFlag() {
            return receiveFlag;
        }

        public void setReceiveFlag(String receiveFlag) {
            this.receiveFlag = receiveFlag;
        }

        public String getReceiveDesc() {
            return receiveDesc;
        }

        public void setReceiveDesc(String receiveDesc) {
            this.receiveDesc = receiveDesc;
        }
    }
}
