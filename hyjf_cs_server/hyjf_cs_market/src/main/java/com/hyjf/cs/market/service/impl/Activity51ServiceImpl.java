package com.hyjf.cs.market.service.impl;

import com.hyjf.am.vo.activity.ActivityUserGuessVO;
import com.hyjf.am.vo.activity.ActivityUserRewardVO;
import com.hyjf.am.vo.coupon.UserCouponBean;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.common.util.CouponUtil;
import com.hyjf.cs.market.client.AmMarketClient;
import com.hyjf.cs.market.client.AmTradeClient;
import com.hyjf.cs.market.mq.base.CommonProducer;
import com.hyjf.cs.market.mq.base.MessageContent;
import com.hyjf.cs.market.service.Activity51Service;
import org.apache.axis.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiasq
 * @version Activity51ServiceImpl, v0.1 2019-04-17 9:53
 */
@Service
public class Activity51ServiceImpl implements Activity51Service {
	private Logger logger = LoggerFactory.getLogger(Activity51ServiceImpl.class);
	/**
	 * 用户参与活动投资年化金额基准值
	 */
	private final BigDecimal USER_ANNUAL_INVEST_STANDARD = new BigDecimal(10000);
	/**
	 * 活动时间
	 */
	private Date activityStartDate = null;
	private Date activityEndDate = null;
	//private final String RECEIVE_COUPON_KEY = "user_receive_coupon_51activity:";
	@Autowired
	private AmTradeClient amTradeClient;
	@Autowired
	private AmMarketClient amMarketClient;
	@Autowired
	private CommonProducer producer;
	@Value("${activity.qingma.couponCodes}")
	private String couponCodes;
	@Value("${activity.qingma.activityId}")
	private Integer activityId;

	@Override
	public Integer isActivityTime() {
		initActivityTime();

		Date today = new Date();
		if (today.compareTo(activityStartDate) == -1) {
			return -1;
		}

		if(today.compareTo(activityEndDate) == 1){
			return 1;
		}

		return 0;
	}

	@Override
	public BigDecimal getSumAmount() {
		initActivityTime();
		return amTradeClient.getSumAmount(activityStartDate, activityEndDate);
	}

	@Override
	public boolean canSendCoupon(int userId) {
		return isUserTenderEnough(userId);
	}

	@Override
	public boolean sendCoupon(int userId, int grade) {
		activityId = activityId == null ? 0 : activityId;
		if(StringUtils.isEmpty(couponCodes)){
			logger.error("未配置优惠券编号...");
			return false;
		}
		//仅有4档奖励
		if(grade > 4){
			logger.error("领取档位错误...");
			return false;
		}
		String[] couponCodeArray = couponCodes.split(",");
		if (couponCodeArray.length < 4) {
			logger.error("优惠券编号配置错误...");
			return false;
		}
		String couponCode = couponCodeArray[grade - 1];

		// 保存领取记录
		boolean insertFlag = amMarketClient.insertActivityUserReward(userId, activityId, grade, getRewardByGrade(grade), "加息券");
		if (!insertFlag) {
			return false;
		}

		// 发券
		try {
			logger.info("用户:{}发放优惠券:{}, 活动:{}", userId, couponCode, activityId);
			UserCouponBean couponBean = new UserCouponBean();
			couponBean.setUserId(userId);
			couponBean.setSendFlg(CouponUtil.NUM_12);
			couponBean.setActivityId(activityId);
			couponBean.setCouponCode(couponCode);
			producer.messageSend(new MessageContent(MQConstant.GRANT_COUPON_TOPIC, userId + "," + "", couponBean));
		} catch (MQException e) {
			logger.error("活动发券失败...", e);
			return false;
		}

		// RedisUtils.setObjEx(RECEIVE_COUPON_KEY + userId, String.valueOf(userId), 3600 * 24 * 15);
		return true;
	}

	@Override
	public boolean guess(int userId, int grade) {
		return amMarketClient.insertActivityUserGuess(userId, grade);
	}

	/***
	 * 判断用户是否出借满1W年化金额
	 * 
	 * @return
	 */
	private boolean isUserTenderEnough(int userId) {
		initActivityTime();
		BigDecimal tenderAmount = amTradeClient.getUserTender(userId, activityStartDate, activityEndDate);
		return tenderAmount == null || tenderAmount.compareTo(USER_ANNUAL_INVEST_STANDARD) < 0 ? false : true;
	}

	@Override
	public boolean isRepeatReceive(int userId, int grade) {
		ActivityUserRewardVO vo = amMarketClient.selectActivityUserReward(activityId, userId, grade);
		if (vo != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isRepeatGuess(int userId) {
		if (getUserGuess(userId) != null) {
			return true;
		}
		return false;
	}

	@Override
	public ActivityUserGuessVO getUserGuess(int userId) {
		return amMarketClient.selectActivityUserGuess(userId);
	}

	/**
	 * 根据档位查询对应奖励类型
	 * @param grade
	 * @return
	 */
	private static String getRewardByGrade(int grade) {
		switch (grade) {
		case 1:
			return "0.5%加息券";
		case 2:
			return "0.7%加息券";
		case 3:
			return "0.9%加息券";
		case 4:
			return "1%加息券";
		default:
			throw new RuntimeException("错误的档位");
		}
	}

	/**
	 * 活动时间初始化
	 */
	private void initActivityTime() {
		ActivityListVO vo = amMarketClient.selectActivityList(activityId);
		if (vo == null) {
			logger.error("活动未配置, activityId is: {}", activityId);
			throw new RuntimeException("活动未配置...");
		}
		activityStartDate = new Date(vo.getTimeStart() * 1000L);
		activityEndDate = new Date(vo.getTimeEnd() * 1000L);
	}
}
