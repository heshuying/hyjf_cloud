package com.hyjf.cs.market.controller.web.activity;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.cs.market.service.Activity51Service;

import io.swagger.annotations.*;

/**
 * @author xiasq
 * @version WebActivity51Controller, v0.1 2019-04-17 9:48
 */
@Api(tags = "web端-2019-5-1青马活动")
@RestController
@RequestMapping("/hyjf-web/activity/qingma")
public class WebActivity51Controller {
    private Logger logger = LoggerFactory.getLogger(WebActivity51Controller.class);

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
            return buildResult("1", "活动未开始");
        }
		BigDecimal sumAmount = activity51Service.getSumAmount();
		sumAmount = sumAmount == null ? BigDecimal.ZERO : sumAmount;
		String rate = String.format("%.2f", sumAmount.multiply(MULTIPLY).divide(DIVIDE)).concat("%");
        logger.info("当前累计出借金额sumAmount: {}, 进度rate: {}", sumAmount, rate);
		result.setData(new Activity51VO(sumAmount, rate));
        return result;
    }

    /**
     * 领取优惠券  判断是否满足条件，发放优惠券
     * @param userId
     * @return
     */
    @ApiOperation(value = "领取优惠券", notes = "活动期间累计出借金额达到目标值领取优惠券，用户需要满足出借年化>=1w")
    @RequestMapping(value = "/sendCoupon", method = RequestMethod.GET)
    public BaseResult sendCoupon(@RequestHeader int userId){
        logger.info("领取优惠券, userId is: {}", userId);
        if(!activity51Service.isActivityTime()){
            return buildResult("1", "活动未开始");
        }

        if(!activity51Service.canSendCoupon(userId)){
            return buildResult("1", "投资年化金额未达到发放标准(1w)");
        }

        //档位奖励
        int grade = 0;
        BigDecimal sumAmount = activity51Service.getSumAmount();
        //未达到领取奖励最低标准，返回失败
        if(sumAmount == null || sumAmount.compareTo(SUM_AMOUNT_GRADE_1)< 0) {
            return buildResult("1", "累计出借金额未达到最低标准(3000w)");
        }
        //最高档奖励
        else if(sumAmount.compareTo(SUM_AMOUNT_GRADE_4)> 0){
            grade = 4;
        }
        //第二档奖励
        else if(sumAmount.compareTo(SUM_AMOUNT_GRADE_3)> 0){
            grade = 3;
        }
        // 第三档奖励
        else if(sumAmount.compareTo(SUM_AMOUNT_GRADE_2)> 0){
            grade = 2;
        }
        // 末等奖励
        else if(sumAmount.compareTo(SUM_AMOUNT_GRADE_1)> 0){
            grade = 1;
        }

        // 判断是否已领取奖励
        if(activity51Service.isRepeatReceive(userId)){
            return buildResult("1", "重复领取");
        }

		boolean sendFlag = activity51Service.sendCoupon(userId, grade);
		if (sendFlag == false) {
			return buildResult("1", "优惠券领取异常");
		}
        return buildResult("000", "优惠券领取成功");
    }

    /**
     * 用户竞猜
     * @param userId
     * @param grade
     * @return
     */
    @ApiOperation(value = "用户竞猜", notes = "不支持重复竞猜")
    @ApiParam(required = true, name = "rank", value = "用户竞猜区间， 从第一档（1-99）开始，依次传递1,2,3,4")
    @RequestMapping(value = "/guess", method = RequestMethod.GET)
    public BaseResult guess(@RequestHeader int userId,
                           @RequestParam int grade){
        logger.info("用户竞猜, userId is: {}, rank is: {}", userId, grade);
        if(!activity51Service.isActivityTime()){
            return buildResult("1", "活动未开始");
        }

        if(!activity51Service.canSendCoupon(userId)){
            return buildResult("1", "投资年化金额未达到发放标准(1w)");
        }

        // 判断是否重复竞猜
        if(activity51Service.isRepeatGuess(userId)){
            return buildResult("1", "重复竞猜");
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

	@ApiModel(value = "查询当前累计出借金额返回参数")
	class Activity51VO {
		@ApiModelProperty("当前累计出借金额")
		private BigDecimal sumAmount;
		@ApiModelProperty("当前进度， 例如 31.10%")
		private String progressRate;

		public Activity51VO(BigDecimal sumAmount, String progressRate) {
			this.sumAmount = sumAmount;
			this.progressRate = progressRate;
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
	}
}
