package com.hyjf.cs.market.service.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.vo.coupon.UserCouponBean;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.common.util.CouponUtil;
import com.hyjf.cs.market.client.AmMarketClient;
import com.hyjf.cs.market.client.AmTradeClient;
import com.hyjf.cs.market.mq.base.CommonProducer;
import com.hyjf.cs.market.mq.base.MessageContent;
import com.hyjf.cs.market.service.Activity51Service;

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
	 * 活动时间初始化
	 */
	private final LocalDate ACTIVITY_START_DATE = LocalDate.of(2019, 5, 1);
	private final LocalDate ACTIVITY_END_DATE = LocalDate.of(2019, 5, 15);
	private Date activityStartDate = null;
	private Date activityEndDate = null;
	/**
	 *  一次性的redis key，不更新到RedisContants了
	 */
	private final String RECEIVE_COUPON_KEY = "user_receive_coupon_51activity:";
	@Autowired
	private AmTradeClient amTradeClient;
	@Autowired
	private AmMarketClient amMarketClient;
	@Autowired
	private CommonProducer producer;

	@PostConstruct
	public void init() {
		final ZoneId zone = ZoneId.systemDefault();
		final Instant startInstant = ACTIVITY_START_DATE.atStartOfDay().atZone(zone).toInstant();
		final Instant endInstant = ACTIVITY_END_DATE.atStartOfDay().atZone(zone).toInstant();
		activityStartDate = Date.from(startInstant);
		activityEndDate = Date.from(endInstant);
	}

	@Override
	public boolean isActivityTime() {
		LocalDate today = LocalDate.now();
		if (today.isAfter(ACTIVITY_START_DATE.minusDays(1)) && today.isBefore(ACTIVITY_END_DATE.minusDays(-1))){
			return true;
		}
		return false;
	}

	@Override
	public BigDecimal getSumAmount() {
		if (activityStartDate == null || activityEndDate == null) {
			logger.warn("活动时间初始化失败,重新初始化...");
			init();
		}
		return amTradeClient.getSumAmount(activityStartDate, activityEndDate);
	}

	@Override
	public boolean canSendCoupon(int userId) {
		return isUserTenderEnough(userId);
	}

	@Override
	public boolean sendCoupon(int userId, int grade) {
		// 保存领取记录
		boolean insertFlag = amMarketClient.insertActivityUserReward(userId, getRewardByGrade(grade), "加息券");
		if (!insertFlag) {
			return false;
		}

		// 发券
		try {
			UserCouponBean couponBean = new UserCouponBean();
			couponBean.setUserId(userId);
			couponBean.setSendFlg(CouponUtil.NUM_12);
			couponBean.setActivityId(0);
			couponBean.setCouponCode("");
			producer.messageSend(new MessageContent(MQConstant.GRANT_COUPON_TOPIC, userId + "," + "", couponBean));
		} catch (MQException e) {
			logger.error("活动发券失败...", e);
			return false;
		}

		RedisUtils.setObjEx(RECEIVE_COUPON_KEY + userId, String.valueOf(userId), 3600 * 24 * 15);
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
		if (activityStartDate == null || activityEndDate == null) {
			logger.warn("活动时间初始化失败,重新初始化...");
			init();
		}
		BigDecimal tenderAmount = amTradeClient.getUserTender(userId, activityStartDate, activityEndDate);
		return tenderAmount == null || tenderAmount.compareTo(USER_ANNUAL_INVEST_STANDARD) < 0 ? false : true;
	}

	@Override
	public boolean isRepeatReceive(int userId) {
		String value = RedisUtils.getObj(RECEIVE_COUPON_KEY + userId, String.class);
		if (value != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isRepeatGuess(int userId) {
		return amMarketClient.existsActivityUserGuess(userId);
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
}
